package com.se.service.impl;

import com.se.dal.ProjectRepository;
import com.se.dal.UCRepository;
import com.se.entity.*;
import com.se.service.CaptureCDService;
import com.se.service.FileStorageService;
import com.se.service.GenerateIRListService;
import com.se.service.ProjectManageService;
import com.se.util.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.Buffer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class GenerateIRListServiceImpl implements GenerateIRListService {
    private final FileStorageService fileStorageService;
    private final ProjectManageService projectManageService;
    private final CaptureCDService captureCDService;
    private UCRepository ucRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public GenerateIRListServiceImpl(FileStorageService fileStorageService,
                                     ProjectManageService projectManageService,
                                     UCRepository ucRepository,
                                     ProjectRepository projectRepository,
                                     CaptureCDService captureCDService){
        this.projectRepository=projectRepository;
        this.projectManageService=projectManageService;
        this.fileStorageService=fileStorageService;
        this.ucRepository=ucRepository;
        this.captureCDService=captureCDService;
    }
    public List<CandidateEntry> computeCandidateList(long pid,String ucname,String algo){
        List<UCTF> uctf=ucRepository.findUCTFBYPidAndUcname(pid,ucname);
        List<SqlClass> ctfs=projectRepository.findClassWithTFByPid(pid);
        List<PJIdf> pjIdfs=projectRepository.findPJIdfByPid(pid);
        if(algo.equals(CandidateEntry.VSM)){
            List<CandidateEntry> candidateEntries=vectorSpaceModel(uctf,ctfs,pjIdfs);
        }else{
            //todo other algorithm
        }
        return new ArrayList<CandidateEntry>();

    }
    public List<CandidateEntry> vectorSpaceModel(List<UCTF> uctfs,List<SqlClass> ctfs,List<PJIdf> pjIdfs){
        List<CandidateEntry> candidateEntries=new ArrayList<>();
        for(SqlClass sqlClass:ctfs){
            double div=0;
            for(UCTF uctf:uctfs){
                for(ClassTF classTF:sqlClass.getCtfs()){
                    if(uctf.getCorpusIndex()==classTF.getCorpusIndex()){
                        for(PJIdf pjIdf:pjIdfs){
                            if(uctf.getCorpusIndex()==pjIdf.getCindex()){
                                div+=uctf.getTf()*classTF.getTf()*Math.log10(pjIdf.getIdf())*Math.log10(pjIdf.getIdf());
                            }
                        }
                    }
                }
            }
            double a=0;
            for(ClassTF classTF:sqlClass.getCtfs()){
                for(PJIdf pjIdf:pjIdfs){
                    if(classTF.getCorpusIndex()==pjIdf.getCindex()){
                        a+=classTF.getTf()*classTF.getTf()*Math.log10(pjIdf.getIdf())*Math.log10(pjIdf.getIdf());
                    }
                }
            }
            double b=0;
            for(UCTF uctf:uctfs){
                for(PJIdf pjIdf:pjIdfs){
                    if(uctf.getCorpusIndex()==pjIdf.getCindex()){
                        b+=uctf.getTf()*uctf.getTf()*Math.log10(pjIdf.getIdf())*Math.log10(pjIdf.getIdf());
                    }
                }
            }
            double res=a/Math.sqrt(a*b);
            CandidateEntry candidateEntry=new CandidateEntry();
            candidateEntry.setAlgorithm(CandidateEntry.VSM);
            candidateEntry.setClassId(sqlClass.getClassId());
            candidateEntry.setSqlClass(sqlClass);
            candidateEntry.setOriginScore(res);
            candidateEntry.setPid(pjIdfs.get(0).getPid());
            candidateEntry.setUcId(uctfs.get(0).getUcId());
            candidateEntries.add(candidateEntry);

        }
        return candidateEntries;
    }


    @Override
    public boolean generateDocCorpus(String path) {
//        Path dir= Paths.get(path);
//        if(!Files.exists(dir)||!Files.isDirectory(dir)) {
//            System.err.println("Document directory not found");
//            return false;
//        }
//        DirectoryStream.Filter<Path> filter=getDirectoryStreamFilter(".txt");
//        try(DirectoryStream<Path> stream=Files.newDirectoryStream(dir,filter)){
//            for(Path entry:stream){
//                BufferedReader br=new BufferedReader(new FileReader(entry.toString()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    @Override
    public boolean generateCodeCorpus(String path) {
        return false;
    }

    @Override
    public boolean initialNewProject(long pid, String pname, MultipartFile uc, MultipartFile code, MultipartFile compile) {
        Path root=Paths.get(FileUtil.PROJECT_ROOT).resolve(String.valueOf(pid));
        System.out.println(root.toString());
        File dir=new File(root.toString());
        if(dir.exists()){
            System.err.println("Error initializing new project:directory '"+root.toString()+"' already exist");
            return false;
        }
        try {
            Files.createDirectories(root);
        }catch (IOException e){
            System.err.println("Error creating directory:"+e.toString());
            return false;
        }
        Path ucZip=root.resolve(StorageNorm.getUCZip(pname));
        Path codeJar=root.resolve(StorageNorm.getCodeJar(pname));
        Path compileJar=root.resolve(StorageNorm.getCompiledJar(pname));
//        Path ucNormDir=root.resolve(StorageNorm.getNomalizedUC());
//        Path codeNormDir=root.resolve(StorageNorm.getNormalizedCodeRoot());
//        try {
//            Files.createDirectory(ucNormDir);
//            Files.createDirectory(codeNormDir);
//        }catch (IOException e){
//            System.err.println("cannot create directory:"+e.toString());
//            return false;
//        }
        if(!fileStorageService.storeWithName(uc,ucZip)||
                !fileStorageService.storeWithName(code,codeJar)||
                !fileStorageService.storeWithName(compile,compileJar)){
            System.err.println("error store file");
            return false;
        }
        Map<String,CorpusIndexAndCount> corpusMap=new HashMap<>();
        doUCZip(pid,ucZip,corpusMap);
        doCodeJar(pid,codeJar,corpusMap);
        calculateIdf(pid,corpusMap);
        captureCDService.getCDFromJarFile(compileJar.toString(),pid,"");

        return true;
    }
    private void calculateIdf(long pid,Map<String,CorpusIndexAndCount> corpusMap){
        int a=projectRepository.countClassByPid(pid);
        int b=ucRepository.countUCByPid(pid);
        double total=a+b;
        if(0==total){
            System.err.println("no found any document or code");
            return;
        }
        PJIdf pjIdf=new PJIdf();
        for(String corpus:corpusMap.keySet()){
            pjIdf.setCindex(corpusMap.get(corpus).getIndex());
            pjIdf.setCorpus(corpus);
            pjIdf.setPid(pid);
            pjIdf.setIdf(total/(double)corpusMap.get(corpus).getDocNum());
            projectRepository.insertPJIdf(pjIdf);
        }
    }

    private boolean doCodeJar(long pid,Path codeJar,Map<String, CorpusIndexAndCount> corpusMap){
        if(!codeJar.toString().endsWith(".jar")){
            return false;
        }
        File file=new File(codeJar.toString());
        try(JarFile jarFile=new JarFile(file)){
            Enumeration<JarEntry> jarEnum=jarFile.entries();
            while (jarEnum.hasMoreElements()){
                JarEntry entry=jarEnum.nextElement();

                if(!entry.getName().endsWith(".java")){
//                    System.out.println("filter:"+entry.getName());
                    continue;
                }

                Map<Integer,Integer> tfMap=new HashMap<>();
                InputStream is=jarFile.getInputStream(entry);
                String content=readSingleCodeFile(is,corpusMap,tfMap);

                SqlClass _class=new SqlClass();
                _class.setPid(pid);
                _class.setClassName(FilenameUtils.getBaseName(entry.getName()));
                String path=StringUtil.jarEntryAsClassName(FilenameUtils.getPath(entry.getName()));
                _class.setPath(path);
                _class.setContent(content);

                projectRepository.insertCode(_class);
                _class=projectRepository.findClassByPidAndPathAndClassname(pid,_class.getClassName(),_class.getPath());

                for(Integer integer:tfMap.keySet()){
                    ClassTF classTF=new ClassTF(_class.getClassId(),integer,tfMap.get(integer));
                    projectRepository.insertClassTF(classTF);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println(e.toString());
            return false;
        }
    }

    private boolean doUCZip(long pid, Path ucZip, Map<String, CorpusIndexAndCount> corpusMap){
        if(!ucZip.toString().endsWith(".zip")){
            return false;
        }
        File file=new File(ucZip.toString());
        try(ZipFile zipFile=new ZipFile(file)){
            Enumeration<? extends ZipEntry> zipEnum=zipFile.entries();
            while (zipEnum.hasMoreElements()){
                ZipEntry entry=zipEnum.nextElement();

                if(FilenameUtils.getPath(entry.getName()).length()>0||!entry.getName().endsWith(".txt")){
                    System.out.println("filter:"+entry.getName());
                    continue;//txt file must be on root of zip file
                }

                Map<Integer,Integer> tfMap=new HashMap<>();
                InputStream is=zipFile.getInputStream(entry);
                String content=readSingleDocFile(is,corpusMap,tfMap);
                UCase uc=new UCase();
                String ucName=FilenameUtils.getBaseName(entry.getName());
                ucRepository.insertUC(pid,content,ucName);
                uc=ucRepository.findUCByPidAndUCName(pid,ucName);
                for(Integer integer:tfMap.keySet()){
                    ucRepository.insertUCTF(uc.getUcId(),integer,tfMap.get(integer));
                }

            }
            return true;
        } catch (IOException e) {
            System.err.println(e.toString());
            return false;
        }
    }

    private String readSingleCodeFile(InputStream is,Map<String,CorpusIndexAndCount> corpusMap,Map<Integer,Integer> tfMap){
        try (BufferedReader br=new BufferedReader(new InputStreamReader(is))){
            String line=br.readLine();
            StringBuilder builder=new StringBuilder();
            StringBuilder resBuilder=new StringBuilder();
            while (null!=line){
                builder.append(line+" ");
                resBuilder.append(line+"\n");
                line=br.readLine();
            }

            String res=resBuilder.toString();
            String str=NormalizeUtil.camelSplit(builder.toString());
            str=NormalizeUtil.normalize(str,3);
            getTfMap(str,corpusMap,tfMap);
            return res;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    private String readSingleDocFile(InputStream is,Map<String,CorpusIndexAndCount> corpusMap,Map<Integer,Integer> tfMap) {
        try (BufferedReader br=new BufferedReader(new InputStreamReader(is))){
            String line=br.readLine();
            StringBuilder builder=new StringBuilder();
            StringBuilder resBuilder=new StringBuilder();
            while (null!=line){
                builder.append(line+" ");
                resBuilder.append(line+"\n");
                line=br.readLine();
            }

            String res=resBuilder.toString();
            String str=NormalizeUtil.normalize(builder.toString(),3);
            getTfMap(str,corpusMap,tfMap);
            return res;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    private Map<Integer,Integer> getTfMap(String str,Map<String,CorpusIndexAndCount> corpusMap,Map<Integer,Integer> tfMap){
        Set<String> tempSet=new HashSet<>();
        for(String corpus:str.split(" ")){
            if(corpusMap.keySet().contains(corpus)&&!tempSet.contains(corpus)){
                CorpusIndexAndCount corpusIndexAndCount=corpusMap.get(corpus);
                int index=corpusIndexAndCount.getIndex();
                corpusIndexAndCount.setDocNum(corpusIndexAndCount.getDocNum()+1);
                corpusMap.put(corpus,corpusIndexAndCount);

                tempSet.add(str);
                tfMap.put(index,1);
            }else if(corpusMap.keySet().contains(corpus)&&tempSet.contains(corpus)){
                CorpusIndexAndCount corpusIndexAndCount=corpusMap.get(corpus);
                int index=corpusIndexAndCount.getIndex();

                tfMap.put(index,tfMap.get(index)+1);
            }else{
                int index=corpusMap.keySet().size();
                CorpusIndexAndCount corpusIndexAndCount=new CorpusIndexAndCount(index,1);
                corpusMap.put(corpus,corpusIndexAndCount);

                tempSet.add(corpus);
                tfMap.put(index,1);
            }
        }
        return tfMap;
    }

    private DirectoryStream.Filter<Path> getDirectoryStreamFilter(String suffix){
        DirectoryStream.Filter<Path> filter=new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                boolean b=entry.toString().endsWith(suffix)&&Files.isReadable(entry);
                return  b;
            }
        };
        return filter;
    }

    synchronized private boolean processUCFile(Path source,Path target) throws IOException {
        String content=FileUtil.readTxtFile(source);
        if(0==content.length()){
            System.err.println("source cannot properly read");
            return false;
        }
        if(Files.exists(target)){
            System.err.println("cannot process uc file '"+
                    source.getFileName()+
                    "':target position '"+target.toString()+"' already exists!");
            return false;
        }

        String res=NormalizeUtil.normalize(content,3);
        if(res.length()<2){
            return false;
        }
        return FileUtil.createAndWriteFile(target,res);
    }

    synchronized private static boolean processCodeFile(Path source,Path target){
        String content=FileUtil.readJavaFile(source);
        if(0==content.length()){
            System.err.println("source cannot properly read");
            return false;
        }
        if(Files.exists(target)){
            System.err.println("cannot process uc file '"+
                    source.getFileName()+
                    "':target position '"+target.toString()+"' already exists!");
            return false;
        }

        String camel=NormalizeUtil.camelSplit(content);
        String res=NormalizeUtil.normalize(camel,3);
        if(res.length()<2){
            return false;
        }
        return FileUtil.createAndWriteFile(target,res);
    }

    public static void main(String[] args) throws IOException {
        Path path=Paths.get("C:\\Users\\Jerry\\Desktop\\material\\Main.java");
        processCodeFile(path,path.resolve("C:\\Users\\Jerry\\Desktop\\material\\c.txt"));
    }
}

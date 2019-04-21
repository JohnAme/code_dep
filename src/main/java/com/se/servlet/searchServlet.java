package com.se.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩类型枚举
 *
 */
enum CompressType {
    //	GZIP是用于UNIX系统的文件压缩，在Linux中经常会使用到*.gz的文件，就是GZIP格式
    ZIP, JAR, GZIP
}

@WebServlet(name = "searchServlet")
public class searchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("调用searchDo");
        String url = request.getParameter("url");
        String[] name_repo = url.substring(19).split("/");
        String name = name_repo[0];
        String repo = name_repo[1].split("\\.")[0];


        String urlStr = "https://api.github.com/repos/" + name + "/" + repo + "/zipball";
        try{
            downLoadFromUrl(urlStr, "a.zip","/Users/shadow/Downloads/");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("sth is wrong.");
        }

        unZip(new File("/Users/shadow/Downloads/a.zip"),"/Users/shadow/Downloads/b");
        File file = new File("/Users/shadow/Downloads/b");
        FixFileName(file.listFiles()[0].getAbsolutePath(),"new");
        zip("/Users/shadow/Downloads/b/new","/Users/shadow/Downloads/c.jar",CompressType.JAR);

        String message = "{\"message\":\"登录成功\"}";
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(message);
    }

    /**
     * 通过文件路径直接修改文件名
     *
     * @param filePath    需要修改的文件的完整路径
     * @param newFileName 需要修改的文件的名称
     * @return
     */
    private String FixFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return null;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return null;
        String newFilePath = null;
        if (f.isDirectory()) { // 判断是否为文件夹
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        } else {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName
                    + filePath.substring(filePath.lastIndexOf("."));
        }
        File nf = new File(newFilePath);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
        return newFilePath;
    }


    private void zip(String inputFile, String outputFile, CompressType type) {
        zip(new File(inputFile), new File(outputFile), type);
    }

    /**
     * 初始化压缩包信息并开始进行压缩
     *
     * @param inputFile  需要压缩的文件或文件夹
     * @param outputFile 压缩后的文件
     * @param type       压缩类型
     */
    private void zip(File inputFile, File outputFile, CompressType type) {
        ZipOutputStream zos = null;
        try {
            if (type == CompressType.ZIP) {
                zos = new ZipOutputStream(new FileOutputStream(outputFile));
            } else if (type == CompressType.JAR) {
                zos = new JarOutputStream(new FileOutputStream(outputFile));
            } else {
                zos = new ZipOutputStream(new FileOutputStream(outputFile));
            }
            // 设置压缩包注释
            zos.setComment("From Log");
            zipFile(zos, inputFile, null);
            System.err.println("压缩完成!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("压缩失败!");
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 如果是单个文件，那么就直接进行压缩。如果是文件夹，那么递归压缩所有文件夹里的文件
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     */
    private void zipFile(ZipOutputStream zos, File inputFile, String path) {
        if (inputFile.isDirectory()) {
            // 记录压缩包中文件的全路径
            String p = "";
            File[] fileList = inputFile.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                File file = fileList[i];
                // 如果路径为空，说明是根目录
                if (path == null) {
                    p = file.getName();
                } else {
                    p = path + File.separator + file.getName();
                }
                // 打印路径
                System.out.println(p);
                // 如果是目录递归调用，直到遇到文件为止
                zipFile(zos, file, p);
            }
        } else {
            zipSingleFile(zos, inputFile, path);
        }
    }

    /**
     * 压缩单个文件到指定压缩流里
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     * @throws FileNotFoundException
     */
    private void zipSingleFile(ZipOutputStream zos, File inputFile, String path) {
        try {
            InputStream in = new FileInputStream(inputFile);
            zos.putNextEntry(new ZipEntry(path));
            write(in, zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压压缩包到指定目录
     *
     * @param inputFile
     * @param outputFile
     */
    private void unZip(File inputFile, String outputFile) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!inputFile.exists()) {
            throw new RuntimeException(inputFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(inputFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = outputFile + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(outputFile + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[128];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 从输入流写入到输出流的方便方法 【注意】这个函数只会关闭输入流，且读写完成后会调用输出流的flush()函数，但不会关闭输出流！
     *
     * @param input
     * @param output
     */
    private void write(InputStream input, OutputStream output) {
        int len = -1;
        byte[] buff = new byte[1024];
        try {
            while ((len = input.read(buff)) != -1) {
                output.write(buff, 0, len);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    private void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");
        conn.disconnect();

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}

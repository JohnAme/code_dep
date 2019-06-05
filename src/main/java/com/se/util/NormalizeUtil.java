package com.se.util;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.se.snowball.EnglishStemmer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalizeUtil {
    private static final String[] stopwords ={"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost",
            "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and",
            "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became",
            "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides",
            "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
            "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else",
            "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few",
            "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from",
            "front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
            "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself",
            "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into",
            "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many",
            "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must",
            "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none",
            "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto",
            "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
            "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
            "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something",
            "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their",
            "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon",
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru",
            "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until",
            "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
            "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while",
            "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
            "you", "your", "yours", "yourself", "yourselves"};

    public static String normalize(String str,int length){
        str=str.toLowerCase();
        StringBuilder sb=new StringBuilder();
        str=charactorClean(str);

        String corpus[]=str.split(" ");
        List<String> stopWordList= Arrays.asList(stopwords);

        EnglishStemmer stemmer=new EnglishStemmer();
        for(int i=0;i<corpus.length;i++){
            String tmp=corpus[i].trim();
            if(stopWordList.contains(tmp)){
                continue;
            }
            if(tmp.length()<length){
                continue;
            }
            stemmer.setCurrent(tmp);
            stemmer.stem();
            sb.append(stemmer.getCurrent()+" ");
        }
        return sb.toString();
    }

    public static String camelSplit(String str){
        str=doCapitalSeqenceProcess(str);
        StringBuilder builder=new StringBuilder();
        String words[]=str.split(" ");

        for(String word:words){
            for(String s:word.split("(?<!^)(?=[A-Z])")){
                if(s.length()>0){
                    builder.append(s+" ");
                }
            }
        }
        return builder.toString();
    }
    private static String doCapitalSeqenceProcess(String str){
        String res=str;
        boolean lastIsUpperCase=false;
        for(int i=0;i<str.length();i++){
            char c=str.charAt(i);
            if(!Character.isUpperCase(c)){
                lastIsUpperCase=false;
            }else if(Character.isUpperCase(c)){
                if(lastIsUpperCase){
                    //do nothing
                }else{
                    res=res.substring(0,i)+String.valueOf(Character.toLowerCase(c))+res.substring(i+1,str.length());
                }
                lastIsUpperCase=true;
            }
        }
        return res;
    }

    public static String charactorClean(String input) {
        StringBuilder sb = new StringBuilder();

        Pattern p = Pattern.compile("[a-z]+");
        Matcher m = p.matcher(input);

        while (m.find()) {
            sb.append(m.group().trim());
            sb.append(" ");
        }
        return sb.toString();
    }
    public static void main(String[] args){
        System.out.println(camelSplit(camelSplit("abstrArabCreaDke9o")));
        System.out.println(camelSplit(camelSplit("AbsdiDsdjCsidjfDBdsdifjDSIFDIJsdf")));
    }

}

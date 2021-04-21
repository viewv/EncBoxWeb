package top.viewv.EncBoxWeb.utils;

import java.util.UUID;

public class FilenameUUID {
    public static String getRandomname(String filename){
        int index = filename.lastIndexOf(".");
        String substring = filename.substring(index);
        return UUID.randomUUID().toString().replace("-","") + substring;
    }

    public static String getRandomname(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

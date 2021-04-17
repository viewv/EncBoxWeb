package top.viewv.EncBoxWeb.utils;

import java.util.UUID;

public class FilenameUUID {
    public static String getRandomname(String filename){
        int index = filename.lastIndexOf(".");
        String substring = filename.substring(index);
        String uuidfilename = UUID.randomUUID().toString().replace("-","") + substring;
        return uuidfilename;
    }
}

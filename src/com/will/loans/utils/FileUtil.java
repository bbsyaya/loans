
package com.will.loans.utils;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {

    /**
     * 取得文件夹大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception//取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (File element : flist) {
            if (element.isDirectory()) {
                size = size + getFileSize(element);
            } else {
                size = size + element.length();
            }
        }
        return size;
    }

    public static String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            return "0.00B";
        }

        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

}

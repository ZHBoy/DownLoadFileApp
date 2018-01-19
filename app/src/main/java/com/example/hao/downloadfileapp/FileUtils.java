package com.example.hao.downloadfileapp;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
* @作者 hao
* @创建日期 2018/1/19 17:02
* Description: 文件操作类
*/
public class FileUtils {

    /**
     * sd卡中写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }
}
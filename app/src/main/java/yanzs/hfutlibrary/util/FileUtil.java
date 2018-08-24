package yanzs.hfutlibrary.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    /**
     * 得到sd卡根路径
     */
    public static String getSdDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 通过原路径和文件名构建新路径
     *
     * @param path1 原路径
     * @param path2 文件名
     * @return 新路径
     */
    public static String makePath(String path1, String path2) {
        if (path1.endsWith(File.separator))
            return path1 + path2;

        return path1 + File.separator + path2;
    }

    /**
     * 删除文件/文件夹
     *
     * @param file 路径
     * @return 是否成功
     */
    public static boolean deleteFileOrFolder(File file) {
        if (file.isFile()) {
            return file.delete();
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return file.delete();
            }

            for (File childFile : childFiles) {
                deleteFileOrFolder(childFile);
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 从文件路径得到文件名字
     *
     * @param filepath 文件路径
     * @return 文件名字
     */
    public static String getNameFromFilePath(String filepath) {
        int pos = filepath.lastIndexOf('/');
        if (pos != -1) {
            return filepath.substring(pos + 1);
        }
        return "";
    }

    /**
     * 保存文件
     *
     * @param bm       需要保存的bitmap
     * @param path     路径
     * @param fileName 想保存的文件名
     */
    public static void saveBitmapToFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String filePath = path.endsWith(File.separator) ? path + fileName : path + File.separator + fileName;
        System.out.println(filePath);
        File myCaptureFile = new File(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}

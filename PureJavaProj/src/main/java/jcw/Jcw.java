package jcw;

import java.io.File;

public class Jcw {

    public static void main(String[] arg) {
        System.out.print("test pure java app");

        //deleteDirectory(new File("/home/test/Desktop/dirtodelete"));
        //删除目录及文件
        deleteFolderFile(new String("/home/test/Desktop/dirtodelete"));

        String string = new String("0.0.0.0");

        System.out.print("\n");

        // 字符串比较
        System.out.print("is equal: " + (string == "0.0.0.0") + "\n");

        System.out.print("is equal: " + string.equals("0.0.0.0"));

        for (int i = 0; i < 10; ++i) {
            if (i == 2) {
                continue;
            }
            System.out.print("curr: " + i + "\n");
        }

        //修改形参字符串，可以使用StringBuilder，不能使用String
        StringBuilder inoutput = new StringBuilder("123");
        setStringValue(inoutput);
        System.out.print("inoutput: " + inoutput + "\n");
    }


    public static boolean deleteDirectory(final File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void deleteFolderFile(final String filePath) {
        deleteDirectory(new File(filePath));
    }

    static void setStringValue(StringBuilder inoutString) {
        inoutString.setLength(0);
        inoutString.append(new String("456"));
    }
}
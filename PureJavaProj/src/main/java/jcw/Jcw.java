package jcw;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

//        downloadApp("https://events.static.linuxfound.org/images/stories/slides/abs2013_gargentas.pdf", new File("abc"));

        changeFinal(new TestFinal());

        ContentProvider contentProvider;

    }

    static void changeFinal(final TestFinal testFinal) {
//        testFinal = new TestFinal();  // compile error
        testFinal.mAge = 100;
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

    // extract http response code from IOException
    static private boolean downloadApp(String downloadUrl, File file) {
        boolean result = false;
        int resultCode = HttpURLConnection.HTTP_OK;
        if ((downloadUrl == null) || downloadUrl.isEmpty() || (file == null)) {
            resultCode = HttpURLConnection.HTTP_SEE_OTHER;
            return result;
        }
        

        int retryCount = 0;
        boolean retryFlag = false;
        long completedSize = 0;
        long fileSize = 0;
        int oldProgress = 0;
        int curProgress = 0;
        int oldPro = 0;
        int curPro = 0;
        HttpURLConnection connection;
        do {
            retryFlag = false;
            try {
                System.out.print("\n downloadUrl=" + downloadUrl);
                URL url = new URL(downloadUrl);
                try {
                    // 打开服务器
                    connection = (HttpURLConnection) url.openConnection();
                    // 设置请求的方法为GET
                    connection.setRequestMethod("GET");
                    //connection.setRequestProperty("Range", "bytes=" + String.valueOf(completedSize) + "-");
                    // 设置超时时间
                    connection.setConnectTimeout(45 * 1000);    // 45s
                    connection.setReadTimeout(2 * 60 * 1000);    // 120s

                    // 连接数据库
                    System.out.print("\n >>>>prepare http request");
                    int responseCode = connection.getResponseCode();
                    System.out.print("\n Connection,responseCode=" + responseCode);
                    if (responseCode != HttpURLConnection.HTTP_OK) {
                        System.out.print("Connection != HTTP_OK, responseCode=" + responseCode);
                        resultCode = responseCode;
                        //break;
                    }

                    fileSize = connection.getContentLength();
                    fileSize = fileSize + completedSize;
                    System.out.print("\n downloadApp() fileSize=" + fileSize);
                    if (fileSize == 0) {
                        resultCode = HttpURLConnection.HTTP_SEE_OTHER;
                        break;
                    }

                    /**读服务器数据**/
                    InputStream is = connection.getInputStream();

                    RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
                    System.out.print("\n local path: " + file.getAbsolutePath());
                    if (accessFile == null) {
                        break;
                    }
                    accessFile.seek(completedSize);

                    int count = 0;
                    byte[] buffer = new byte[2048];
                    while ((count = is.read(buffer)) != -1) {
                        try {
                            System.out.print("\n st");
                            Thread.sleep(10);
                            System.out.print("\n ed");
                        } catch (InterruptedException e) {}
                        accessFile.write(buffer, 0, count);
                        completedSize = completedSize + count;
                        if (completedSize == fileSize) {
                            System.out.print("\n completedSize=" + completedSize + ",fileSize=" + fileSize);
                            result = true;
                            break;
                        }
                    }
                    accessFile.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("IOException");
                    break;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.print("MalformedURLException");
                break;
            }
        } while (retryFlag);

        System.out.print("\n finish");
        return result;
    }
}
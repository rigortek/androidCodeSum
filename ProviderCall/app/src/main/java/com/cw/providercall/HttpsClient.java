package com.cw.providercall;


import android.util.Log;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{
    public static final String LOG_TAG = "http";

    public void testIt(){

        String https_url = "https://www.baidu.com";
        URL url;
        try {

            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            //dumpl all cert info
            print_https_cert(con);

            //dump all the content
            print_content(con);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*
     * read json data from specify server address.
     *
     */
    public static boolean readData(String serverUrl, StringBuffer data) {
        boolean enableLog = true;
        Log.v(LOG_TAG, ">>>>enter readData.");
    /*try {
        Log.v(TAG, ">>>>start sleep.");
        Thread.currentThread().sleep(5000);//闃绘柇2绉�
        Log.v(TAG, ">>>>stop sleep.");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }*/
        boolean result = false;
        int resultCode = HttpsURLConnection.HTTP_OK;
        if (data == null) {
            resultCode = HttpsURLConnection.HTTP_SEE_OTHER;
            return result;
        }
        HttpsURLConnection connection;
        try {
            URL url = new URL(serverUrl);
            try {
                // 鎵撳紑鏈嶅姟鍣�
                connection = (HttpsURLConnection) url.openConnection();
                // 璁剧疆璇锋眰鐨勬柟娉曚负GET
                connection.setRequestMethod("GET");
                // 閰嶇疆璇锋眰Content-Type
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 璁剧疆瓒呮椂鏃堕棿
                connection.setConnectTimeout(45 * 1000);    // 45s
                connection.setReadTimeout(2 * 60 * 1000);    // 120s
                connection.setRequestProperty("Charset", "UTF-8");

                // 杩炴帴鏁版嵁搴�
                Log.v(LOG_TAG, ">>>>prepare http request");
                int responseCode = connection.getResponseCode();
                Log.v(LOG_TAG, "Utility.readData(): Connection,responseCode=" + responseCode);
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    Log.v(LOG_TAG, "Utility.readData(): Connection != HTTP_OK, responseCode=" + responseCode);
                    resultCode = responseCode;
                    return result;
                }

                /**璇绘湇鍔″櫒鏁版嵁**/
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    data.append(line);
                }
                is.close();
                br.close();
                if (data.length() > 0) {
                    if (enableLog) {
                        Log.v(LOG_TAG, "Utility.readData(): Connection,data=" + data);
                    }
                    result = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.v(LOG_TAG, "IOException");
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "MalformedURLException");
            return result;
        }
        Log.v(LOG_TAG, ">>>>normal exit readData.");
        return result;
    }

    private void print_https_cert(HttpsURLConnection con){

        if(con!=null){

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    private void print_content(HttpsURLConnection con){
        if(con!=null){

            try {

                System.out.println("****** Content of the URL ********");
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null){
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

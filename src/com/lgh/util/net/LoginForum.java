/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lgh.util.logging.LogUtil;



/**
 *
 * @author Administrator
 */
public class LoginForum {

    public static void main(String[] args) throws Exception{
//        SystemProxy.setSystemProxy("proxy.tay.cpqcorp.net", "8080", null, null);
//    	 List<Proxy> o = SystemProxy.getSystemProxy();
//    	System.setProperty("sun.net.spi.nameservice.nameservers", "192.193.215.65");
//    	System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
        loginBaidu();

    }

    public static void loginBaidu() {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL("http://www.baidu.com/");
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Internet Explorer");
            httpurlconnection.setRequestProperty("Host", "www.baidu.com");
            httpurlconnection.connect();
            String cookie0 = httpurlconnection.getHeaderField("Set-Cookie");
            httpurlconnection.disconnect();
            //String cookie0 = "BAIDUID=8AF5EA24DBF1275CE15C02B5FF65A265:FG=1;BDSTAT=61a1d3a7118ce8a7ce1b9d16fdfaaf51f3deb48f8e5494eef01f3a292cf5b899; BDUSE=deleted";
            url = new URL("http://passport.baidu.com/?login");
            String strPost = "username=lgh3292&password=lghlgh&mem_pass=on";
            httpurlconnection = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpurlconnection.setInstanceFollowRedirects(true);
            httpurlconnection.setDoOutput(true); // 需要向服务器写数据
            httpurlconnection.setDoInput(true); //
            httpurlconnection.setUseCaches(false); // 获得服务器最新的信息
            httpurlconnection.setAllowUserInteraction(false);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.addRequestProperty(
                    "Accept",
                    "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-silverlight, */*");
            httpurlconnection.setRequestProperty("Referer",
                    "http://passport.baidu.com/?login&tpl=mn&u=http%3A//www.baidu.com/");
            httpurlconnection.setRequestProperty("Accept-Language", "zh-cn");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpurlconnection.setRequestProperty("Accept-Encoding",
                    "gzip, deflate");
            httpurlconnection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727;MEGAUPLOAD 1.0)");
            httpurlconnection.setRequestProperty("Host", "passport.baidu.com");
            httpurlconnection.setRequestProperty("Content-Length", strPost.length()
                    + "");
            httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
            httpurlconnection.setRequestProperty("Cache-Control", "no-cache");
            httpurlconnection.setRequestProperty("Cookie", cookie0);
            httpurlconnection.getOutputStream().write(strPost.getBytes());
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
            httpurlconnection.connect();

            int code = httpurlconnection.getResponseCode();
            LogUtil.info("code   " + code);
            String cookie1 = httpurlconnection.getHeaderField("Set-Cookie");
            System.out.print(cookie0 + "; " + cookie1);
            httpurlconnection.disconnect();

            // cookie1 = "BAIDUID=96700C1674C3996840C03ACEF4C7C364:FG=1; USERID=13818598e77751707e2a3b; MCITY=-289%3A; BDUSS=DQ5LTZHdGVxNndtdjNaYU94bVp0ME00b1U3bHpPWkhKZW1aQU5XNW1zbmNEd2RPQVFBQUFBJCQAAAAAAAAAAAokNw-fldUAbGdoMzI5MgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADgOsV1AAAAAOA6xXUAAAAAcF1CAAAAAAAxMC42NS4yMtyC303cgt9NO; Hm_lvt_9f14aaa038bbba8b12ec2a4a3e51d254=1303176453281";
            url = new URL("http://www.baidu.com");
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestProperty("User-Agent", "Internet Explorer");
            httpurlconnection.setRequestProperty("Host", "http://www.baidu.com");
            httpurlconnection.setRequestProperty("Cookie", cookie0 + "; " + cookie1);
            httpurlconnection.connect();
            InputStream urlStream = httpurlconnection.getInputStream();
            BufferedInputStream buff = new BufferedInputStream(urlStream);
            Reader r = new InputStreamReader(buff, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            StringBuffer strHtml = new StringBuffer("");
            String strLine = null;
            
            while ((strLine = br.readLine()) != null) {
                strHtml.append(strLine + "\r\n");
            }
            
            File f = new File("LoginForum.txt");
            f.createNewFile();
            System.out.println(f.getPath());
            BufferedWriter fous = new BufferedWriter(new FileWriter(f));
            fous.append(strHtml);
            fous.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
    }
}

package testpackage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpAndHttpsProxy {

    public static String HttpsProxy(String url, String param, String proxy, int port) {
        HttpsURLConnection httpsConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            System.out.println("�����URL========��" + urlClient);

                SSLContext sc = SSLContext.getInstance("SSL");
                // ָ������https
                sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
                //����������Ȼ��httpsҲ��Type.HTTP
                Proxy proxy1=new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
                //���ô���
                httpsConn = (HttpsURLConnection) urlClient.openConnection();

                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
                 // ����ͨ�õ���������
                httpsConn.setRequestProperty("accept", "*/*");
                httpsConn.setRequestProperty("connection", "Keep-Alive");
                httpsConn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // ����POST�������������������
                httpsConn.setDoOutput(true);
                httpsConn.setDoInput(true);
                // ��ȡURLConnection�����Ӧ�������
                out = new PrintWriter(httpsConn.getOutputStream());
                // �����������
                out.print(param);
                // flush������Ļ���
                out.flush();
                // ����BufferedReader����������ȡURL����Ӧ
                in = new BufferedReader(
                        new InputStreamReader(httpsConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                // �Ͽ�����
                httpsConn.disconnect();
                System.out.println("====result===="+result);
                System.out.println("���ؽ����" + httpsConn.getResponseMessage());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

         return result;
    }

    public static String HttpProxy(String url, String param, String proxy, int port) {
        HttpURLConnection httpConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            System.out.println("�����URL========��" + urlClient);
                //��������
                Proxy proxy1=new Proxy(Type.HTTP, new InetSocketAddress(proxy, port));
                //���ô���
                httpConn = (HttpURLConnection) urlClient.openConnection(proxy1);
                // ����ͨ�õ���������
                httpConn.setRequestProperty("accept", "*/*");
                httpConn.setRequestProperty("connection", "Keep-Alive");
                httpConn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // ����POST�������������������
                httpConn.setDoOutput(true);
                httpConn.setDoInput(true);
                // ��ȡURLConnection�����Ӧ�������
                out = new PrintWriter(httpConn.getOutputStream());
                // �����������
                out.print(param);
                // flush������Ļ���
                out.flush();
                // ����BufferedReader����������ȡURL����Ӧ
                in = new BufferedReader(
                        new InputStreamReader(httpConn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                // �Ͽ�����
                httpConn.disconnect();
                System.out.println("====result===="+result);
                System.out.println("���ؽ����" + httpConn.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

         return result;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static void main(String[] args) {
        HttpsProxy("https://thirdpartypaymentdc.gcbcn.citigroup.net/cn3pps/dologin", "", "", 81);
        HttpProxy("http://www.aseoe.com/", "", "127.0.0.1", 81);
    }

}
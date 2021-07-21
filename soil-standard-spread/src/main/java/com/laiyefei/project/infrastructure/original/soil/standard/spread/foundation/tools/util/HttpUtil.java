package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 12:05
 * @Desc : this is class named HttpUtil for do HttpUtil
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class HttpUtil implements IUtil {
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static final Map<String, String> REQUEST_PROPERTY = new HashMap<String, String>() {
        private static final long serialVersionUID = 2181073842647501521L;

        {
            put("accept", "*/*");
            put("connection", "Keep-Alive");
            put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            put("Accept-Charset", "utf-8");
            put("contentType", "utf-8");
        }
    };

    private HttpUtil() {
        throw new RuntimeException("error: the http util is can not be an instance.");
    }

    private static final URLConnection buildURLConnection(final String url, final Map<String, String> params) {
        final StringBuilder urlBuilder = new StringBuilder(url);
        if (JudgeUtil.IsNotNull(params)) {
            if (0 < params.size()) {
                urlBuilder.append("?");
                urlBuilder.append(params.entrySet().stream().map(e -> {
                    return e.getKey().concat("=").concat(e.getValue());
                }).collect(Collectors.joining("&")));
            }
        }
        try {
            return new URL(urlBuilder.toString()).openConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String GET(final String url, final Map<String, String> params) {
        final StringBuilder sb = new StringBuilder();
        final URLConnection urlConnection = buildURLConnection(url, params);
        try {
            urlConnection.connect();
            for (final Map.Entry<String, String> item : REQUEST_PROPERTY.entrySet()) {
                urlConnection.setRequestProperty(item.getKey(), item.getValue());
            }
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String line;
                while (JudgeUtil.IsNotNull(line = br.readLine())) {
                    sb.append(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static final String POST(final String url, final Map<String, String> params) {
        final StringBuilder sb = new StringBuilder();
        final URLConnection urlConnection = buildURLConnection(url, params);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        try {
            urlConnection.connect();
            try (final PrintWriter out = new PrintWriter(urlConnection.getOutputStream())) {
                try (final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    String line;
                    while (JudgeUtil.IsNotNull(line = in.readLine())) {
                        sb.append(line);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static final String SSLPost(final String url, final Map<String, String> params) {
        final StringBuilder sb = new StringBuilder();
        final HttpsURLConnection urlConnection = (HttpsURLConnection) buildURLConnection(url, params);
        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(ObjectUtil.GetNULL(), new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            urlConnection.setSSLSocketFactory(sc.getSocketFactory());
            urlConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            urlConnection.connect();
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String ret;
                while (JudgeUtil.IsNotNull(ret = br.readLine())) {
                    if (!StringUtil.IsTrimEmpty(ret)) {
                        sb.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        urlConnection.disconnect();
        return sb.toString();
    }

}

package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-15 09:05
 * @Desc : this is class named IPUtil for do IPUtil
 * @Version : v2.0.0.20200315
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class IPUtil implements IUtil {
    private static final String UNKNOWN = "unknown";
    private static final String[] X_FORWARDED_FOR = new String[]{
            "x-forwarded-for",
            "X-Forwarded-For"
    };
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String X_REAL_IP = "X-Real-IP";
    private static final String $0_0_0_0_0_0_0_1 = "0:0:0:0:0:0:0:1";
    private static final String $127_0_0_1 = "127.0.0.1";
    private static final String $UNKNOWN = "未知";
    private static final String DEFAULT_SERVER_PORT = "8080";

    private IPUtil() {
        throw new RuntimeException("can not be an instance.");
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (JudgeUtil.IsNull(request)) {
            return IPUtil.UNKNOWN;
        }
        String ip = StringUtil.EMPTY;
        if (JudgeUtil.IsNotNull(IPUtil.X_FORWARDED_FOR)) {
            for (final String item : IPUtil.X_FORWARDED_FOR) {
                ip = request.getHeader(item);
                if (!StringUtil.IsEmpty(ip)) {
                    break;
                }
            }
        }
        if (StringUtil.IsEmpty(ip) || IPUtil.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = StringUtil.NDVL(
                    IPUtil.UNKNOWN,
                    request.getHeader(IPUtil.PROXY_CLIENT_IP),
                    request.getHeader(IPUtil.WL_PROXY_CLIENT_IP),
                    request.getHeader(IPUtil.X_REAL_IP)
            );
        }
        ip = StringUtil.NDVL(IPUtil.UNKNOWN, request.getRemoteAddr());
        return IPUtil.$0_0_0_0_0_0_0_1.equals(ip) ? IPUtil.$127_0_0_1 : ip;
    }

    public static boolean IsInternalIp(final String ip) {
        byte[] addr = TransTextToNumericFormatV4(ip);
        return internalIp(addr) || $127_0_0_1.equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (SECTION_3 <= b1 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    //将IPv4地址转换成字节
    public static final byte[] TransTextToNumericFormatV4(String text) {
        if (StringUtil.IsEmpty(text)) {
            return new byte[0];
        }
        final byte[] bytes = new byte[4];
        final String[] elements = text.split("\\" + StringUtil.DOT, -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L))
                        return new byte[0];
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L))
                        return new byte[0];
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L))
                        return new byte[0];
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return new byte[0];
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L))
                        return new byte[0];
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return new byte[0];
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return new byte[0];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new byte[0];
        }
        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return IPUtil.$127_0_0_1;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return IPUtil.$UNKNOWN;
    }

    public static String getHostIpPort(Environment environment) {
        return getHostIp().concat(":").concat(JudgeUtil.NVL(environment.getProperty("local.server.port"), IPUtil.DEFAULT_SERVER_PORT));
    }
}

package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.hu.json.JSONObject;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.hu.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 18:05
 * @Desc : this is class named AddressUtil for do AddressUtil
 * @Version : v1.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AddressUtil implements IUtil {
    private static final String INNER_ADDRESS = "【内网】";
    private static final String IP_SERVER_URL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String IP_SERVER_URL_KEY_DATA = "data";
    private static final String IP_SERVER_URL_KEY_DATA_REGION = "region";
    private static final String IP_SERVER_URL_KEY_DATA_CITY = "city";

    public static final String GetRealAddressByIP(final String ip) {
        if (JudgeUtil.IsNull(ip)) {
            throw new RuntimeException("error: ip is can not be null.");
        }
        if (IPUtil.IsInternalIp(ip)) {
            return AddressUtil.INNER_ADDRESS;
        }
        final Map<String, String> params = new HashMap<>();
        params.put("ip", ip);
        final String result = HttpUtil.POST(IP_SERVER_URL, params);
        if (StringUtil.IsTrimEmpty(result)) {
            return StringUtil.UNKNOWN;
        }
        final JSONObject jsonObject = JSONUtil.Unmarshal(result, JSONObject.class);
        final JSONObject data = jsonObject.getObj(IP_SERVER_URL_KEY_DATA);
        final String region = data.getString(IP_SERVER_URL_KEY_DATA_REGION);
        final String city = data.getString(IP_SERVER_URL_KEY_DATA_CITY);
        return region.concat(StringUtil.SPACE_CHINESE).concat(city);
    }
}

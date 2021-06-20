package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.aid;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IResourceWhiteList;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : swagger 配置
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IDataHolder extends IAid {
    Map<IKey, IData> dataContainer = new HashMap<>();
    List<IWhiteKey> whiteKeys = new ArrayList<>();

    interface IKey extends IAid {
    }

    interface IData extends IAid {
    }

    static void register(IKey key, IData data) {
        Assert.isTrue(!dataContainer.containsKey(key), "error: sorry, the container is exist the key.");
        dataContainer.put(key, data);
    }

    interface IWhiteKey extends IKey {
        String gainBasePackage();

        String gainArtifactId();
    }

    interface IWhiteData extends IData {
        List<? extends IWhiteList> gainWhiteLists();

        List<? extends IResourceWhiteList> gainResourceWhiteLists();
    }

    static void registerWhite(IWhiteKey whiteKey, IWhiteData whiteData) {
        register(whiteKey, whiteData);
        Assert.isTrue(whiteKeys.add(whiteKey), "error: sorry, the whiteKey named [" + whiteKey.gainArtifactId() + "] is add error.");
    }

    static IWhiteData gainWhiteData(IWhiteKey whiteKey) {
        Assert.isTrue(dataContainer.containsKey(whiteKey), "error: sorry, the datacontainer is not have the key named [" + whiteKey.getClass().getName() + "]");
        return IWhiteData.class.cast(dataContainer.get(whiteKey));
    }

}

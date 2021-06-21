package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.performer;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.aid.IDataHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IResourceWhiteList;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 数据注册
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsDataHolder implements IPerformer, IDataHolder, IDataHolder.IWhiteKey, IDataHolder.IWhiteData {

    protected List<? extends IWhiteList> whiteLists = new ArrayList<>();
    protected List<? extends IResourceWhiteList> resourceWhiteLists = new ArrayList<>();

    public void installData() {
        this.whiteLists = Optional.of(ClassUtil.DigEnumBy(this.gainBasePackage(), IWhiteList.class)).orElse(new ArrayList<>());
        this.resourceWhiteLists = Optional.of(ClassUtil.DigEnumBy(this.gainBasePackage(), IResourceWhiteList.class)).orElse(new ArrayList<>());
    }

    public List<? extends IWhiteList> gainWhiteLists() {
        return whiteLists;
    }

    public List<? extends IResourceWhiteList> gainResourceWhiteLists() {
        return resourceWhiteLists;
    }

    @PostConstruct
    private void afterCheck() {
        Assert.notNull(this.gainBasePackage(), "error: sorry, the basepackage is can not be null.");
        Assert.notNull(this.gainArtifactId(), "error: sorry, the artifactId is can not be null.");

        installData();
        IDataHolder.registerWhite(this, this);
    }
}

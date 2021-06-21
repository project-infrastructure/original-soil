package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.performer;

import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IPermissionService;
import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IRoleService;
import com.laiyefei.project.infrastructure.original.soil.die.yard.central.service.IUserService;
import com.laiyefei.project.infrastructure.original.soil.standard.adaptive.controller.IController;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.action.IAction;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.ao.Power;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.GlobalUserCache;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.IInitializer;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid.PowerWrapper;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.DefaultRole;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.DefaultUser;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.co.YardModuleInfo;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.dao.IUserRoleDao;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.fo.IHolderHandler;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Permission;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Role;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.User;
import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.UserRole;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : 初始执行器
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public class YardInitializer implements IAction, IInitializer, CommandLineRunner, ApplicationContextAware {

    private ApplicationContext context;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private IUserService userService;
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private IRoleService roleService;
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private IPermissionService permissionService;
    @Resource
    private IUserRoleDao userRoleDao;


    @PostConstruct
    public void afterCheck() {
        Assert.notNull(this.userService, "error: userService is null.");
        Assert.notNull(this.roleService, "error: userService is null.");
        Assert.notNull(this.permissionService, "error: permissionService is null.");
        Assert.notNull(this.userRoleDao, "error: sorry, the user role dao is null.");
    }

    public String gainBasePackage() {
        return YardModuleInfo.BASE_PACKAGE;
    }

    public String gainArtifactId() {
        return YardModuleInfo.Artifact.getCode();
    }

    public static void OperateByAdmin(IHolderHandler.IExecute execute) {
        GlobalUserCache.INSTANCE.holdUser(DefaultUser.ADMIN.getUser());
        execute.done();
        GlobalUserCache.INSTANCE.releaseUser();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(String... args) {
        //初始化操作
        OperateByAdmin(() -> {
            //默认用户初始化添加
            for (final DefaultUser value : DefaultUser.values()) {
                value.doInit((condition, dump) -> this.userService.ensureExists(condition, dump));
            }
            //默认角色初始化添加
            for (final DefaultRole value : DefaultRole.values()) {
                value.doInit((condition, dump) -> this.roleService.ensureExists(condition, dump));
            }
            //默认用户角色关联
            for (final DefaultUser value : DefaultUser.values()) {
                final User user = value.justUser();
                final Role role = value.getDefaultRole().justRole();
                Assert.isTrue(
                        this.userRoleDao.ensureExists(UserRole.BuildBy(user, role)),
                        "error: sorry, the user and role link is failed.");
            }
            //默认权限初始化添加
            final List<PowerWrapper> powerWrappers = new ArrayList<>();
            final Set<Class<? extends IController>> classes = ClassUtil.GainClassBy(gainBasePackage(), IController.class);
            for (final Class<? extends IController> item : classes) {
                if (item.isInterface() //忽略接口
                        || Modifier.isAbstract(item.getModifiers()) //忽略抽象类
                        || item.isAnnotation()//忽略注解
                ) {
                    continue;
                }
                //非控制层无视
                if (JudgeUtil.IsNull(AnnotationUtils.findAnnotation(item, Controller.class))) {
                    continue;
                }
                String[] rootPaths;
                final RequestMapping requestMapping = AnnotationUtils.findAnnotation(item, RequestMapping.class);
                if (JudgeUtil.IsNotNULL(requestMapping)) {
                    rootPaths = JudgeUtil.NVL(requestMapping.value(), new String[0]);
                } else {
                    rootPaths = new String[0];
                }

                final Method[] methods = ArrayUtils.addAll(item.getMethods(), item.getDeclaredMethods());
                for (final Method method : methods) {
                    final Power power = AnnotationUtils.findAnnotation(method, Power.class);
                    if (JudgeUtil.IsNull(power)) {
                        //无注解无视
                        continue;
                    }

                    final Annotation annotation = ClassUtil.gainMergedAnnotation(method, RequestMapping.class);
                    if (JudgeUtil.IsNull(annotation) || !(RequestMapping.class.isAssignableFrom(annotation.getClass()))) {
                        continue;
                    }
                    RequestMapping methodMapping = RequestMapping.class.cast(annotation);
                    String[] methodPaths = JudgeUtil.NVL(methodMapping.value(), new String[0]);

                    List<String> collectPaths = new ArrayList<>();
                    if (rootPaths.length <= 0) {
                        Assert.isTrue(collectPaths.addAll(Arrays.stream(methodPaths.clone()).map(StringUtil.SLASH::concat).collect(Collectors.toList())),
                                "error: sorry, the collectPaths is add all failed.");
                    } else {
                        for (String rootPath : rootPaths) {
                            for (String methodPath : methodPaths) {
                                Assert.isTrue(collectPaths.add(rootPath.concat(StringUtil.SLASH).concat(methodPath)),
                                        "error: sorry, the collect paths is add failed.");
                            }
                        }
                    }

                    for (final RequestMethod requestMethod : methodMapping.method()) {
                        for (final String collectPath : collectPaths) {
                            Assert.isTrue(powerWrappers.add(
                                    PowerWrapper
                                            .BuildBy(power, methodMapping, requestMethod.name())
                                            .append(collectPath)),
                                    "error: sorry, the permissionWrappers add permissionWrapper failed.");
                        }
                    }
                }
            }

            if (powerWrappers.size() <= 0) {
                return;
            }
            List<Permission> permissions = PowerWrapper.Transfer(powerWrappers);
            for (Permission permission : permissions) {
                Assert.isTrue(this.permissionService.ensureExists(permission),
                        "error: sorry, the permissionService saveBatch is failed.");
            }
        });
        //run after
        List<? extends IInitializer> initializers = ClassUtil.GetBeansOrderedBy(this.context, IInitializer.class);
        for (final IInitializer initializer : initializers) {
            initializer.runAfter();
        }
    }

    @Override
    public void runAfter() {
        //ignore..
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

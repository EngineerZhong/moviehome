package com.dalididilo.moviehome.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {


    /**
     * ShiroFilterFactoryBean
     *
     * 过滤器:Filter
     * 使用Shiro内置的过滤器来实现基本的权限过滤。
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager webSecurityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

//         设置安全管理器
        filterFactoryBean.setSecurityManager(webSecurityManager);


//        内置过滤器，实现权限的相关拦截器
/** 常用过滤器
 *      anon  : 无需认证（登录）可以访问
 *      authc : 必须认证才可以访问
 *      user  : 如果使用rememberMe的功能可以直接访问
 *      perms : 资源授权
 *      role  : 角色权限
 *
 */
        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");

        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/index","anon");
        filterMap.put("/user/Login","anon");
        filterMap.put("/user/*","authc");
        filterMap.put("/user/logout", "logout");


        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        修改跳转登录页面
        filterFactoryBean.setLoginUrl("/user/toLogin");
        filterFactoryBean.setUnauthorizedUrl("/user/unAuth");

        return filterFactoryBean;
    }

    /**
     * DefaultSecurityManager
     */
    @Bean(name = "webSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm realm){
        DefaultWebSecurityManager securityWebManager = new DefaultWebSecurityManager();
        securityWebManager.setRealm(realm);
        return securityWebManager;
    }

    /**
     * Realm （自定义）
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm getShiroRealm(){
        return new ShiroRealm();
    }
}

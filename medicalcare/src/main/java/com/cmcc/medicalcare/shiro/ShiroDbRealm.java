package com.cmcc.medicalcare.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmcc.medicalcare.model.SystemResource;
import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.ISystemResourceService;
import com.cmcc.medicalcare.service.ISystemUserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author zhouzhou
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private static Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);

    @Autowired
    private ISystemUserService systemUserService;
    
    @Autowired
    private ISystemResourceService systemResourceService;

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        LOGGER.info("Shiro开始登录认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        
        String username = (String) token.getPrincipal();
        SystemUser user = systemUserService.findByParam("selectByUserName", username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if (user.getIsEnable() == 0) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
        
//        SystemUser user = new SystemUser();
        
        List<Integer> roleList = new ArrayList<Integer>();
        roleList.add(user.getUserRole());
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUserName(), user.getUserRealname(), roleList);
        // 认证缓存信息
        return new SimpleAuthenticationInfo(shiroUser, user.getUserPassword(), getName());
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<Integer> roleList = shiroUser.roleList;

        Set<String> urlSet = new HashSet<String>();
        for (Integer roleId : roleList) {
        	//根据角色id查找资源url列表
        	List<SystemResource> resourceList = systemResourceService.findResourceUrlListByRoleId(roleId);
            if (resourceList != null) {
                for (SystemResource resource : resourceList) {
                	if (resource != null) {
                		if (StringUtils.isNotBlank(resource.getUrl())) {
                            urlSet.add(resource.getUrl());
                        }
                	}
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(urlSet);
        return info;
    }
}

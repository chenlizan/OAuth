package com.oauth.provisioning;

import com.oauth.mongo.dao.UserInfoDao;
import com.oauth.mongo.entity.UserInfo;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;

public class MongoUserDetailsManager implements UserDetailsManager {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserInfoDao userInfoDao;

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(),
                    "getAuthority() method must return a non-empty string");
        }
    }

    public MongoUserDetailsManager() {
    }

    public MongoUserDetailsManager(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    // ~ UserDetailsManager implementation
    // ==============================================================================

    @Override
    public void createUser(final UserDetails user) {
        validateUserDetails(user);
        this.userInfoDao.save((UserInfo) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);
//        this.userInfoDao.upsert((UserInfo)user);
    }

    @Override
    public void deleteUser(String username) {
//        this.userInfoDao.findOne({username:username})
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = this.userInfoDao.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(
                    this.messages.getMessage("UserInfoDao.notFound",
                            new Object[] { username }, "Username {0} not found"));
        }
        return userInfo;
    }
}

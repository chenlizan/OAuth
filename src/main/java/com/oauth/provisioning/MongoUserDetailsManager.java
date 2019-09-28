package com.oauth.provisioning;

import com.oauth.mongo.entity.UserInfo;
import com.oauth.mongo.repository.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;

public class MongoUserDetailsManager implements UserDetailsManager {

    private UserInfoRepository userInfoRepository;

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

    public MongoUserDetailsManager(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    // ~ UserDetailsManager implementation
    // ==============================================================================

    @Override
    public void createUser(final UserDetails user) {
        validateUserDetails(user);
        this.userInfoRepository.save((UserInfo) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);
//        this.userInfoRepository.upsert((UserInfo)user);
    }

    @Override
    public void deleteUser(String username) {
//        this.userInfoRepository.findOne({username:username})
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
        return null;
    }
}

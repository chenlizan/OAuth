package com.oauth.provisioning;

import com.oauth.mongo.dao.UserInfoDao;
import com.oauth.mongo.entity.UserInfo;
import com.oauth.user.UserInfoDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;

public class MongoUserDetailsManager implements UserDetailsManager {

    protected final Log logger = LogFactory.getLog(getClass());

    private AuthenticationManager authenticationManager;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserInfoDao userInfoDao;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

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

    private String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
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
        UserInfo userInfo = this.userInfoDao.findByUsername(user.getUsername());

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(userInfo.getUsername()));
        Update update = new Update();
        update.set("nickname", userInfo.getNickname());

        mongoTemplate.upsert(query, update, UserInfo.class);
    }

    @Override
    public void deleteUser(String username) {
        Long deletedCount = this.userInfoDao.deleteUserInfoByUsername(username);
        if (deletedCount == 0) {
            throw new UsernameNotFoundException(
                    this.messages.getMessage("UserInfoDao.notFound",
                            new Object[]{username}, "Username {0} not found"));
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the
        // supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '" + username
                    + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, oldPassword));
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO(username, newPassword, new String[]{});

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("password", passwordEncoder.encode(userInfoDTO.getPassword()));

        mongoTemplate.upsert(query, update, UserInfo.class);
    }

    @Override
    public boolean userExists(String username) {
        UserInfo userInfo = new UserInfo(username, null, new String[]{});
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths(getFiledName(userInfo))
                .withMatcher("username", matcher -> matcher.exact());
        Example<UserInfo> userInfoExample = Example.of(userInfo, exampleMatcher);
        return this.userInfoDao.exists(userInfoExample);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = this.userInfoDao.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(
                    this.messages.getMessage("UserInfoDao.notFound",
                            new Object[]{username}, "Username {0} not found"));
        }
        return userInfo;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}

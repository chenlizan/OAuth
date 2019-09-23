package com.oauth.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {
}

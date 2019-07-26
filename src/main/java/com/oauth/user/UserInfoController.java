package com.oauth.user;


import com.oauth.mongo.entity.Customer;
import com.oauth.mongo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private MongoOperations mongoOperations;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo() {
        repository.save(new Customer("Alice", "Smith"));
        long count = mongoOperations.getCollection("customer").count();
        return "abc";
    }
}

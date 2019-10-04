package com.springdata.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<Users, Users> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

    @Override
    public Users process(final Users users) throws Exception {
        /*final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();*/
    	final String id = users.getId().toUpperCase();
    	final String name = users.getName().toUpperCase();
    	final int age = users.getAge();

        final Users transformedUser = new Users(id,name,age);

        log.info("Converting (" + users + ") into (" + transformedUser + ")");

        return transformedUser;
    }

}

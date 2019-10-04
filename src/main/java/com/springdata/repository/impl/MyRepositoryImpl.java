package com.springdata.repository.impl;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.ItemReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.springdata.dao.mongo.MyMongoRepository;
import com.springdata.dao.mysql.MySQLRepository;
import com.springdata.mysql.configuration.MySQLConfiguration;
import com.springdata.mysql.mongo.BatchConfiguration;

@Service
public class MyRepositoryImpl {

    @Autowired
    private MyMongoRepository myMongoRepository;

    @Autowired
    private MySQLRepository mySQLRepository;
    
    @Autowired
    private MySQLConfiguration mysqlConfig;
    
    /*@Autowired
     BatchConfiguration batchConfig;*/

    /*@PostConstruct
    public void extractUsers(){
        
    	mySQLRepository.findAll().forEach((user) -> System.out.println("User name from mysql is : "+user.getName()));
    	myMongoRepository.findAll().forEach((user) -> System.out.println("user name from mongo is : "+user.getName()));
    	//mysqlConfig.myMySQLDataSource();
    	//batchConfig.databaseXmlItemReader(mysqlConfig.myMySQLDataSource());
    }*/
    
   

}

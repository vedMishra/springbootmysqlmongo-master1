package com.springdata.mysql.mongo;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdata.models.Users;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    EntityManagerFactory emf;
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
 
    @Bean
    public Job readFrmMySql() {
      return jobBuilderFactory.get("readFrmMySql").incrementer(new RunIdIncrementer()).start(step1())
          .build();
    }
    //org.springframework.jdbc.datasource.DriverManagerDataSource
    @Bean
    public Step step1() {
      try {
		return stepBuilderFactory.get("step1").<Users, Users>chunk(10).reader(productItemReader())
				 .processor(processor())
		      .writer(writer()).build();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
    }
    
  
    @Bean
    public ItemReader<Users> productItemReader() throws Exception {
    try
	{
		JdbcPagingItemReader<Users> reader = new JdbcPagingItemReader<>();
		final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
		sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
		sqlPagingQueryProviderFactoryBean.setSelectClause("select id, name, age");
		sqlPagingQueryProviderFactoryBean.setFromClause("from Users");
		sqlPagingQueryProviderFactoryBean.setSortKey("name");
		reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
		reader.setDataSource(dataSource);
		reader.setPageSize(1);
		reader.setRowMapper(new BeanPropertyRowMapper<>(Users.class));
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		//logger.info("Reading users anonymized in chunks of {}", USERS_CHUNK_SIZE);
		//ObjectMapper mapper = new ObjectMapper();
		//String jsonString = mapper.writeValueAsString(sqlPagingQueryProviderFactoryBean);
		//System.out.println("jsonString----"+jsonString);
		return reader;
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	return null;
    }
   
    @Bean
    public UsersItemProcessor processor() {
    	return new UsersItemProcessor();
    	//ObjectMapper mapper = new ObjectMapper();
		/*try {
			String jsonString = mapper.writeValueAsString(Users.class);
			System.out.println(jsonString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 
    }
  
    @Bean
    public MongoItemWriter<Users> writer() {
      MongoItemWriter<Users> writer = new MongoItemWriter<Users>();
      writer.setTemplate(mongoTemplate);
      writer.setCollection("users1");
      //ObjectMapper mapper = new ObjectMapper();
		/*try {
			String jsonString = mapper.writeValueAsString(Users.class);
			System.out.println(jsonString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
      System.out.println("writer.toString()----"+writer.toString());
      return writer;
    }
 

}

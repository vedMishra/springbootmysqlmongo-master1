package com.springdata.mysql.mongo;

import org.springframework.batch.item.ItemProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdata.models.Users;
	 
	public class UsersItemProcessor implements ItemProcessor<Users,Users>
	{
		@Override
	    public Users process(Users users) throws Exception
	    {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(users);
		
			System.out.println(jsonString);
			
	        return users;
	    }
	}



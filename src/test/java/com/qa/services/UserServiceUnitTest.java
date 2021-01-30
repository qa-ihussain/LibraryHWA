package com.qa.services;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.qa.LibraryApplication;

@SpringBootTest(classes = LibraryApplication.class)
@Sql(scripts = { "classpath:schema-test.sql" ,"classpath:data-test.sql" }, 
executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceUnitTest {

}
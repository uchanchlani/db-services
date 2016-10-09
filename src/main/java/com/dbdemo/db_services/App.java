package com.dbdemo.db_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Starting DB Services Demo..." );
        SpringApplication.run(App.class, args);
    }
}

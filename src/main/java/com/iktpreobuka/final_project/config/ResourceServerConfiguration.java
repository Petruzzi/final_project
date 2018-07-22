package com.iktpreobuka.final_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
 
    private static final String RESOURCE_ID = "my_rest_api";
     
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }
 
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
        anonymous().disable()
        
        .requestMatchers()
        .antMatchers("/**")
        //.antMatchers("/parent/**").antMatchers("/professor/**").antMatchers("/admin/**").antMatchers("/student/**")
        .and().authorizeRequests()
       // .antMatchers("/users/**").access("hasRole('USER') or hasRole('ADMIN')")
     //.antMatchers("/users/**").hasRole("USER")
        .antMatchers("/student/test/**").permitAll()
          
        
        .antMatchers("/school_year/get_active_semester/**").permitAll()
        .antMatchers("/user/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").permitAll()
        .antMatchers("/class/user/**").permitAll()
        .antMatchers("/mark/user/**").permitAll()
        .antMatchers("/subject_grade/get_subject_by_student_id/**").permitAll()
        .antMatchers("/subject_grade/user/**").permitAll()
        .antMatchers("/schedule/user/get_schedules_by_student_id/**").permitAll()
        
        //PARENT ACCESS
        .antMatchers("/parent/parent/**").hasRole("PARENT")
        .antMatchers("/mark/parent/**").hasRole("PARENT")
        
        //STUDENT ACCESS
        .antMatchers("/student/student/**").hasRole("STUDENT")
        .antMatchers("/mark/student/**").hasRole("STUDENT")
        
        
        //PROF ACCESS
        .antMatchers("/professor/professor/**").hasRole("PROFESSOR")
        .antMatchers("/mark/professor/**").hasRole("PROFESSOR")
        .antMatchers("/final_mark/professor/**").hasRole("PROFESSOR")// *** final mark ne postoji vise
        
        .antMatchers("/class/professor/**").hasRole("PROFESSOR")
        
         //ADMIN ACCESS
        .antMatchers("/**").hasRole("ADMIN")
        

        
        
     
        
        
        
        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
 
}


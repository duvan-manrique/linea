package com.duvan.linea;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.duvan.linea.exception.AuthException;




//Indica los recursos autenticados y liberados del aplicatico
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	//Trae todo lo que configuramos en el SecurityConfig
	@Autowired
    private ResourceServerTokenServices tokenServices;
	
	
    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
    
    //De donde se van a crear los tokens y la configuración del resourcesIds
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }
    
    //Url que vamos a proteger y como
    @Override
    public void configure(HttpSecurity http) throws Exception {
                http
                .exceptionHandling().authenticationEntryPoint(new AuthException())
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests()                  
                .antMatchers("/personas/persona/**").authenticated()
                .antMatchers("/libros/**").authenticated()
                .antMatchers("/editoriales/**").authenticated()
                .antMatchers("/relacion/**").authenticated()
                .antMatchers("/roles/**").permitAll()
                .antMatchers("/tokens/**").permitAll().and().cors().configurationSource(configurationSource());              
    }
    
    @Bean
    public CorsConfigurationSource configurationSource() {
    	CorsConfiguration config = new CorsConfiguration();
    	System.out.println(config.getAllowedHeaders() + " estos son los autorizados");
    	
    	config.setAllowedOrigins(Arrays.asList("*"));
    	config.setAllowedMethods(Arrays.asList("DELETE", "GET", "OPTIONS", "PATCH", "POST", "PUT"));
    	config.setAllowCredentials(true);
    	config.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "x-requested-with", "authorization",
    			"Content-Type", "Authorization", "credential", "X-XSRF-TOKEN"));
    
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", config);
    	
    	return source;
    }
    
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
    	FilterRegistrationBean<CorsFilter> cors = new FilterRegistrationBean<CorsFilter>(new CorsFilter(configurationSource()));
    	cors.setOrder(Ordered.HIGHEST_PRECEDENCE);
    	return cors;
    }

}

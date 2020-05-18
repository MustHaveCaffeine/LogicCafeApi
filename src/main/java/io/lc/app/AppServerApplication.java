package io.lc.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AppServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AppServerApplication.class, args);
  }

  @Value("${graphql.url:graphql}")
  private String graphqlUrl;
  /*
  * No cors origin global setting.
  */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
      String graphqlUrl = "/" + this.graphqlUrl;
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping(graphqlUrl).allowedOrigins("*");
          }
      };
  }
}
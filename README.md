# Spring Security Examples
Differents ways to handle authentication and authorization with Spring Security.

### Examples
- Basic authentication [basic](https://github.com/CamiloDelReal/projects-spring-security/tree/main/basic)
  * Spring basic authentication
  * User defined in application.yml file
  * Unsecured, secured and secured with role requirement endpoints
- Basic authentication with InMemory database [basic-inmemory](https://github.com/CamiloDelReal/projects-spring-security/tree/main/basic-inmemory)
  * Spring basic authentication
  * InMemory database for users management
  * User declaration in WebSecurityConfigurerAdapter class
  * Unsecured, secured and secured with authority requirement endpoints
- Basic authentication with relational database [basic-jdbc](https://github.com/CamiloDelReal/projects-spring-security/tree/main/basic-jdbc)
  * Spring basic authentication
  * Relational database for users management (MySQL)
  * User declaration in WebSecurityConfigurerAdapter class and data.sql
  * Unsecured, secured and secured with authority requirement endpoints
  
### Technologies used
- Spring Boot
  * Web application
  * Security
  * JDBC
  * JPA
- MySQL
- Lombok
- Json Web Token
- Docker
  * Deployment

### Others
- Docker compose deployment script for requirements

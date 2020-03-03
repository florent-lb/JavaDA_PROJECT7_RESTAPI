# Poseidon
A simple app to input data. All views are be secured. You can register new users with an Administrator account.
## Technical:

1. Framework: Spring Boot v2.2.4
2. Java 13
3. Thymeleaf
4. Bootstrap v.4.3.1
5. MySQL 8.0.19
## Installation
Default database : MySQL 8.0.19
### Testing
For Spring : Use application.properties<br/>
For Docker BDD : Use Dockerfile-test

Ready for Testing from your IDE

### Build Production

For Spring : Use application-prod.properties (update jdbc connection values)
<ul>
<li><code>spring.jpa.hibernate.ddl-auto=update</code> Set value to none if the dll is already loaded ( and you want to update it manually)</li>
</ul>
if you don't have Mysql : 
Use Dockerfile(Update SQL password), from the directory where is the Dockerfile : <code>docker build -f Dockerfile -t poseidon:test .</code>
<br/>To build the app : <code>clean package spring-boot:repackage</code>
<br/>To launch it : <code>java -jar "your jar name"</code> (by default the name is : poseidon-1.0.jar)

### BDD DLL
Use the file doc/data.sql for create DLL and import the first admin account (login : ADMIN, password : Admin1234!)






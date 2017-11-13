Instructions
============

1. Install JDK

`sudo apt-get install openjdk-8-jdk`

2. Set $JAVA_HOME in PATH. For example open .bashrc from home folder and add these lines:

`export JAVA_HOME=<path to java>`

`export PATH=$JAVA_HOME/bin:$PATH`

3. Install maven

`sudo apt-get install maven`

4. Add this line to .bashrc aswell

`export PATH=<maven path>:$PATH`

5. Install postgreSQL

`sudo apt-get install postgresql postgresql-client`

6. Launch postgreSQL from terminal

`sudo su - postgres -c 'psql'`

7. Run this commad in postgreSQL UI

`CREATE DATABASE "Dlearn_db";`

8. Run this commad in postgreSQL UI

`ALTER USER Postgres WITH PASSWORD 'admin';`

9. Run this command from backend root. Use password `admin`

`psql -U  postgres -W -h localhost -d "Dlearn_db" -f database/db.sql`

10. Add some data to the database

`\connect Dlearn_db

INSERT INTO "Students" (username, pwd, gender, age) 
	VALUES ('student', '$2a$16$pdcURh3hIwiiCvkiU8zwLOCfNR3h/R2WuCTonoRBRZHry7/4.m5Hm', 'Male', 18);
	
INSERT INTO "Teachers" (lastname, firstname, username, pwd) 
	VALUES ('Test', 'Teacher', 'teacher', '$2a$16$pdcURh3hIwiiCvkiU8zwLOCfNR3h/R2WuCTonoRBRZHry7/4.m5Hm');
	
INSERT INTO "Researchers" (username, pwd) 
	VALUES ('researcher', '$2a$16$pdcURh3hIwiiCvkiU8zwLOCfNR3h/R2WuCTonoRBRZHry7/4.m5Hm');`

11. Install  maven dependencies

`mvn clean install`

12. Create jar file

`mvn jar:jar`

13. Run the jar file

`java -jar target/dependency/webapp-runner.jar target/*.war`

14. Finally

`Have fun!` :smile:

::for Windows
::author: Tatsuya
:: only for MySQL DBMS
@echo off
set /p glassfish_home="enter glassfish path folder (GLASSFISH_HOME): "
chdir /D %glassfish_home%/glas*/bin
@echo -------------------STARTING DOMAIN------------------------
@echo available domain: 
cmd /C asadmin.bat list-domains
set /p domainName="enter the domain to start (default value is domain1): "
@echo copying necessary library..
chdir /D %glassfish_home%
chdir /D ..
chdir /D ./net*/ide/modules/ext/
xcopy "mysql*.jar" "%glassfish_home%/glassfish/domains/%domainName%/lib"

@echo starting domain %domainName%
chdir /D %glassfish_home%/gla*/bin
cmd /C asadmin.bat start-domain %domainName%
@echo -------------------CREATING JDBC CONNECTION POOL------------------------
set /p connectionPool="enter JDBC connection pool name to add: " 
@echo database information....
set /p serverName="enter server ip (enter localhost for local machine) :" 
set /p portNumber="enter database Port: " 
set /p databaseName="enter database name: " 
set /p user="enter user: " 
set /p password="enter password: " 

cmd /C asadmin.bat create-jdbc-connection-pool --property serverName=%serverName%:portNumber=%portNumber%:databaseName=%databaseName%:User=%user%:Password=%password%:URL=jdbc\:mysql\://%serverName%\:%portNumber%/%databaseName%:driverClass=com.mysql.jdbc.Driver --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource %connectionPool%
@echo -------------------CREATING JDBC RESOURCES------------------------
@echo creating jdbc resources for connection pool" %connectionPool%
set /p jndiName="enter JNDI name: " 
cmd /C asadmin.bat create-jdbc-resource --connectionpoolid %connectionPool% %jndiName%
pause
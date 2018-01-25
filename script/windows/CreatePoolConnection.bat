::for Windows
::author: Tatsuya
:: only for MySQL DBMS
@echo off
set connectionPool=%1
set serverName=%2
set portNumber=%3
set databaseName=%4
set user=%5
set password=%6
set glassfish_home=%7
chdir /D %glassfish_home%/gla*/bin
cmd /C asadmin.bat create-jdbc-connection-pool --property serverName=%serverName%:portNumber=%portNumber%:databaseName=%databaseName%:User=%user%:Password=%password%:URL=jdbc\:mysql\://%serverName%\:%portNumber%/%databaseName%:driverClass=com.mysql.jdbc.Driver --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource %connectionPool%
::for Windows
::author: Tatsuya
:: only for MySQL DBMS
@echo off
set jndiName=%1
set connectionPool=%2
set glassfish_home=%3
chdir /D %glassfish_home%/gla*/bin
cmd /C asadmin.bat create-jdbc-resource --connectionpoolid %connectionPool% %jndiName%
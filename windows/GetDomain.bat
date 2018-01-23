::for Windows
::author: Tatsuya
:: only for MySQL DBMS
@echo off
set glassfish_home=%1
@echo %glassfish_home%
chdir /D %glassfish_home%/glas*/bin
@echo -------------------STARTING DOMAIN------------------------
@echo available domain: 
cmd /C asadmin.bat list-domains
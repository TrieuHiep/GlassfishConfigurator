::for Windows
::author: Tatsuya
:: only for MySQL DBMS
@echo off
set glassfish_home=%1
set domainName=%2
@echo starting domain %domainName%
chdir /D %glassfish_home%/gla*/bin
cmd /C asadmin.bat start-domain %domainName%
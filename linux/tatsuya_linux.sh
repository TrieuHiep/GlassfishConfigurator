#!/bin/bash

#@author: Tatsuya
#for MYSQL DBMS Only!!!

read -p "enter glassfish path folder (GLASSFISH_HOME): " glassfish_home
cd $glassfish_home/gla*/bin
echo "-------------------STARTING DOMAIN------------------------"
echo "available domain: "
./asadmin list-domains
read -p "enter the domain to start (default value is domain1): " domainName
echo "copying necessary library.."
cd $glassfish_home
cd ../
sudo cp ./net*/ide/modules/ext/mysql-connector* $glassfish_home/gla*/domains/$domainName/lib

echo "starting domain" $domainName
cd $glassfish_home/gla*/bin
./asadmin start-domain $domainName
echo "-------------------CREATING JDBC CONNECTION POOL------------------------"
read -p "enter JDBC connection pool name to add: " connectionPool
echo 	"database information...."
read -p "enter server ip (enter localhost for local machine) :" serverName
read -p "enter database Port: " portNumber
read -p "enter database name: " databaseName
read -p "enter user: " user
read -s -p "enter password(type and enter): " password

./asadmin create-jdbc-connection-pool --property serverName=$serverName:portNumber=$portNumber:databaseName=$databaseName:User=$user:Password=$password:URL=jdbc\\:mysql\\://$serverName\\:$portNumber/$databaseName:driverClass=com.mysql.jdbc.Driver --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource $connectionPool
echo "-------------------CREATING JDBC RESOURCES------------------------"
echo "creating jdbc resources for connection pool" $connectionPool
read -p "enter JNDI name: " jndiName
./asadmin create-jdbc-resource --connectionpoolid $connectionPool $jndiName

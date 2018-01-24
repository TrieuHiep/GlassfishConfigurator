#!/bin/bash
connectionPool=$1
serverName=$2
portNumber=$3
databaseName=$4
user=$5
password=$6
glassfish_home=$7
glassfish_home=${glassfish_home//\"/}
cd $glassfish_home/gla*/bin
./asadmin create-jdbc-connection-pool --property serverName=$serverName:portNumber=$portNumber:databaseName=$databaseName:User=$user:Password=$password:URL=jdbc\\:mysql\\://$serverName\\:$portNumber/$databaseName:driverClass=com.mysql.jdbc.Driver --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource $connectionPool

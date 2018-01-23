#!/bin/bash
jndiName=$1
connectionPool=$2
glassfish_home=$3
glassfish_home=${glassfish_home//\"/}
cd $glassfish_home/gla*/bin
./asadmin.bat create-jdbc-resource --connectionpoolid $connectionPool $jndiName

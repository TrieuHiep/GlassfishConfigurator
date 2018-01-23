#!/bin/bash
glassfish_home=$1
domainName=$2
glassfish_home=${glassfish_home//\"/}
echo "stopping domain" $domainName
cd $glassfish_home/gla*/bin
./asadmin stop-domain $domainName

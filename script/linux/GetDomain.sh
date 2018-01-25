#!/bin/bash
glassfish_home=$1
glassfish_home=${glassfish_home//\"/}
cd $glassfish_home/glas*/bin
echo "-------------------LOADING DOMAIN STATUS------------------------"
echo "available domain:" 
./asadmin list-domains
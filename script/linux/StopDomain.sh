#!/bin/bash
glassfishHome=$1
domainName=$2
glassfishHome=${glassfishHome//\"/}
echo "glassfish home: "$glassfishHome
cd $glassfishHome/glas*/bin
./asadmin stop-domain $domainName
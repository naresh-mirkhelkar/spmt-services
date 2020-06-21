#!/bin/bash

# Create Default Databases
docker exec -d spmt_database curl -u admin:password -s --max-time 5.5 -X PUT http://127.0.0.1:5984/_users
docker exec -d spmt_database curl -u admin:password -s --max-time 5.5 -X PUT http://127.0.0.1:5984/_replicator
docker exec -d spmt_database curl -u admin:password -s --max-time 5.5 -X PUT http://127.0.0.1:5984/_global_changes

# Create Data Bases -- use the load balancer
#curl -u admin:password -s --max-time 5.5 -X PUT "http://127.0.0.1:5984/_users"
#curl -u admin:password -s --max-time 5.5 -X PUT "http://127.0.0.1:5984/_replicator"
#curl -u admin:password -s --max-time 5.5 -X PUT "http://127.0.0.1:5984/_global_changes"
curl --insecure -u admin:password -X PUT "http://127.0.0.1:5984/prj-mgmt"

#!/bin/sh
type="dev"
if [ $# -gt 0 ];then
    type=$1
fi
if [ $type == 'prod' ];then
    mvn clean install  -Dmaven.test.skip=true
    scp -r ./target/demo-0.0.1-SNAPSHOT.jar root@192.168.1.5:/root/services/
fi
if [ $type == 'test' ]; then
    mvn clean install  -Dmaven.test.skip=true
    scp -r ./target/archetype-0.0.1.jar work@192.168.1.2:/home/work/services/
fi

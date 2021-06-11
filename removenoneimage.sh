#!/bin/bash

d_work=/app/jenkins/workspace/eproc-dev/removenoneimages
f_work=noneimages.txt
fc_work=exitedcontainer.txt
l_work=$d_work/log/imageremove.log

docker ps -a | grep Exited | awk {'print $1}' > $fc_work
docker images | grep '<none>' | awk {'print $3'} > $f_work

exited_containers=$(cat $fc_work | sort)
none_images=$(cat $f_work | sort)

for y in $exited_containers
do
  echo Exited Container $y 
  docker rm $y -f
done

for z in $none_images
do
  echo Remove Images $z
  docker rmi $z -f
done



#!/bin/bash

# run degreeCentrality 10 times
for i in {1..10}
do
    echo "Running degreeCentrality for the $i time"
    spark-submit --class degreeCentrality /home/user/workspace-graphx/target/scala-2.12/workspace-graphx_2.12-0.1.0-SNAPSHOT.jar
done

# run shortestPaths 10 times
for i in {1..10}
do
    echo "Running shortestPaths for the $i time"
    spark-submit --class shortestPaths /home/user/workspace-graphx/target/scala-2.12/workspace-graphx_2.12-0.1.0-SNAPSHOT.jar
done

# run triangleCount 10 times
for i in {1..10}
do
    echo "Running triangleCount for the $i time"
    spark-submit --class triangleCount /home/user/workspace-graphx/target/scala-2.12/workspace-graphx_2.12-0.1.0-SNAPSHOT.jar
done

# run weaklyConnectedComponents 10 times
for i in {1..10}
do
    echo "Running weaklyConnectedComponents for the $i time"
    spark-submit --class weaklyConnectedComponents /home/user/workspace-graphx/target/scala-2.12/workspace-graphx_2.12-0.1.0-SNAPSHOT.jar
done

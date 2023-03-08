#!/bin/bash

# run spark-submit --class degreeCentrality target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar 10 times
for i in {1..10}
do
    echo "Running degreeCentrality for the $i time"
    spark-submit --class degreeCentrality target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar

# run spark-submit --class shortestPaths target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar 10 times
for i in {1..10}
do
    echo "Running shortestPaths for the $i time"
    spark-submit --class shortestPaths target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar
done

# run spark-submit --class triangleCount target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar  10 times
for i in {1..10}
do
    echo "Running triangleCount for the $i time"
    spark-submit --class triangleCount target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar
done

# run spark-submit --class weaklyConnectedComponents target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar 10 times
for i in {1..10}
do
    echo "Running weaklyConnectedComponents for the $i time"
    spark-submit --class weaklyConnectedComponents target/scala-2.12/graphx_2.12-0.1.0-SNAPSHOT.jar
done

# ntua-information-systems

Semester Project for the [**Analysis and Design of Information Systems**](https://www.ece.ntua.gr/en/undergraduate/courses/3321) course, during the 9th semester of the **School of Electrical and Computer Engineering at the National Technical University of Athens**.

## Team 24 - Members

- [**Kyriakopoulos Georgios**](https://github.com/geokyr)
- [**Tzelepis Serafeim**](https://github.com/sertze)

## Project Description

The goal of this project is to develop a system that allows for the **comparison of the performance of graph processing systems**. The comparison is based on the same dataset, workload, and hardware environment, and the results are validated. The system executes predefined queries on each system under test, measures the time required to complete each task, computes performance metrics for each system, and produces graphical representations of each metric for each SUT.

The functions that are compared include finding the **shortest path**, **degree centrality**, **triangle count**, and **weakly connected components**. The two systems compared are [**Apache Spark GraphX**](https://spark.apache.org/graphx/) and [**Apache Flink**](https://flink.apache.org/). Performance metrics include **average, minimum and maximum query execution time**.

## Dataset

For this project, we chose to use a [Google web graph dataset](http://konect.cc/networks/web-Google/), which represents the structure of the World Wide Web with nodes representing web pages and edges representing hyperlinks between them. This dataset was selected because it is challenging to process due to its size and complexity, with 875,713 nodes and 5,105,039 edges, and provides a real-world scenario to test the scalability and efficiency of the 2 graph processing systems, Apache Spark GraphX and Apache Flink. 

We downloaded the dataset from the [Stanford Network Analysis Platform](https://snap.stanford.edu/data/web-Google.html) using the following commands.
```
wget https://snap.stanford.edu/data/web-Google.txt.gz
gzip -d web-Google.txt.gz
```
While larger datasets may produce different results, we believe that the Google web graph dataset provides a suitable representation of a large-scale graph for our research needs.

## Setup

Setup instructions are available on the project report and the guides under the `sources` directory. It mainly involves setting up and configuring a multi-node cluster, installing Java, Apache Spark, Scala, SBT and Apache Flink and configuring the tools.

We used ~okeanos to set up the cluster, which consists of one master and two slave nodes. We then used SSH to connect to the machines and set up a local network to allow the machines to communicate with each other and a NAT network, since we were only given one public IPv4, which was assigned to the master node. 

The above procedure and part of the rest of the setup is automated using some scripts, which are available under the `scripts` directory. We also changed the hostnames and added the hostname and IP pairs to the `/etc/hosts` file, using a script. 

For the rest of the setup that follows, bear in mind that the master node was set as the Spark master and the Flink JobManager, while also being used as a Spark worker and Flink TaskManager. The slave nodes were set as Spark workers and Flink TaskManagers.

## Apache Spark GraphX

### Installation

First, we installed Java and Apache Spark using scripts. We opted to use Scala for writing our GraphX jobs, so we also had to install Scala and SBT using the following commands.
```
sudo apt-get install scala
```
```
wget https://github.com/sbt/sbt/releases/download/v1.8.2/sbt-1.8.2.tgz
tar -xf sbt-1.8.2.tgz
rm sbt-1.8.2.tgz
```
We also added the following line on our `~/.bashrc` file, to add SBT bin folder to our path.
```
export PATH=$PATH:/home/user/sbt/bin
```
Don't forger to also source the file.
```
source ~/.bashrc
```

### Project Setup

To set up a new project to use for our GraphX jobs, we used the following command.
```
sbt new scala/scala-seed.g8
```
It is also necessary to create a `build.sbt` file inside the project folder, with the following content.
```
libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.3.1",
      "org.apache.spark" %% "spark-graphx" % "3.3.1"
    )
```

### Running a GraphX Job
To compile, package and submit a GraphX job we used the following commands.
```
sbt package
spark-submit --class <class-name> <jar-path>
```
where `<class-name>` is the name of the class that contains the main method that we wish to execute and `<jar-path>` is the path to the jar file that was created by the `sbt package` command.

### Monitoring
To monitor the execution of a GraphX job, we used the Spark Web UI, which is available at `http://<master-ip>:8080`. We also used the following commands to start or stop the Spark master and workers.
```
start-all.sh
stop-all.sh
start-workers.sh
stop-workers.sh
```

## Apache Flink

### Installation

First, we had to install Apache Flink on our master node, using the following commands.
```
wget --no-check-certificate https://dlcdn.apache.org/flink/flink-1.16.1/flink-1.16.1-bin-scala_2.12.tgz
tar -xvf flink-1.16.1-bin-scala_2.12.tgz
mv flink-1.16.1 flink
rm flink-1.16.1-bin-scala_2.12.tgz
cd flink
cp opt/flink-gelly-1.16.1.jar lib/
```
We then added the following line to our `~/.bashrc` file, to add Flink bin folder to our path.
```
export PATH=$PATH:/home/user/flink/bin
```
Don't forger to also source the file.
```
source ~/.bashrc
```

### Configuration

Then, we had to configure Flink, as we did with Spark using the respective script.
First edit the `~/flink/conf/masters/` file, to contain the following line.
```
master
```
Then edit the `~/flink/conf/workers` file, to contain the following lines.
```
master
slave1
slave2
```
Finally, edit the `~/flink/conf/flink-conf.yaml` file, and change the following lines.
```
jobmanager.rpc.address: <public-ipv4>
jobmanager.bind-host: 0.0.0.0
jobmanager.memory.flink.size: 2g
taskmanager.bind-host: 0.0.0.0
# change to the hostname of your machine (check note below)
taskmanager.host: {master, slave1, slave2}
# select the respective memory size (3g for master, 7g for slaves)
taskmanager.memory.flink.size: {3g, 7g, 7g}
taskmanager.numberOfTaskSlots: 2
parallelism.default: 6
rest.address: <public-ipv4>
rest.bind-address: 0.0.0.0
akka.framesize: "104857600b"
```

After configuring Flink, we can copy the flink directory to the slave nodes, using the following commands.
```
cd ~
scp -r flink slave1:~
scp -r flink slave2:~
```

Note: After copying the directories, the `taskmanager.host` and `taskmanager.memory.flink.size` lines on the `flink-conf.yaml` file should be changed to the hostname of the machine and the respective memory size, as shown in the comments above.

We opted to use Java for writing our Flink jobs, so we just had to install Maven, using the following command.
```
sudo apt install maven
```

### Project Setup

To set up a new project to use for our Flink jobs, we used the following command.
```
mvn archetype:generate \
  -DarchetypeGroupId=org.apache.flink \
  -DarchetypeArtifactId=flink-quickstart-java \
  -DarchetypeVersion=1.16.0
```
This creates a new maven project in the current directory and asks for the group, name, version and package of the project. We used the following values.
```
groupId: adis
artifactId: {degreeCentrality, shortestPaths, triangleCount, weaklyConnectedComponents}
version: 1.0
packaging: jar
```
After that, we had to do the following changes:
- Change `name` on `pom.xml` to `{Degree Centrality, Shortest Paths, Triangle Count, Weakly Connected Components}`
- Change `mainClass` on `pom.xml` to `jar.{degreeCentrality, shortestPaths, triangleCount, weaklyConnectedComponents}`
- Add `gelly` dependency on `pom.xml`
```
<dependency>
    <groupId>org.apache.flink</groupId>
    <artifactId>flink-gelly</artifactId>
    <version>1.16.0</version>
</dependency>
```
- Rename main `*.java` file to `{degreeCentrality, shortestPaths, triangleCount, weaklyConnectedComponents}.java`
- Change java main `public class` name to `{degreeCentrality, shortestPaths, triangleCount, weaklyConnectedComponents}`

### Running a Flink Job

To compile, package and submit a Flink job we used the following commands.
```
mvn clean package
flink run <jar-path>
```
where `<jar-path>` is the path to the jar file of the job we want to execute, that was created by the `mvn clean package` command.

### Monitoring

To monitor the execution of a Flink job, we used the Flink Web UI, which is available at `http://<master-ip>:8081`. We also used the following commands to start or stop the Flink cluster.
```
start-cluster.sh
stop-cluster.sh
```

## Results

A detailed analysis of the outputs obtained by running the same jobs on the two different platforms is available on the project report. 

There, we first compare and validate the results of the two platforms, using some statistics available for the dataset we chose.

After that, we continue with the main comparison of the two platforms in terms of performance, using the execution time of the jobs as a metric. We analyze how we tracked the execution time of the jobs, and how we obtained the results and we then compare the results and draw some final conclusions on the strengths and weaknesses of each platform.

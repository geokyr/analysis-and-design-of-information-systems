package jar;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import org.apache.flink.graph.*;
import org.apache.flink.graph.library.*;
import org.apache.flink.types.LongValue;
import org.apache.flink.types.NullValue;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class degreeCentrality {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master:9000");

		FileSystem hdfs = FileSystem.get(conf);
		Path inputPath = new Path("hdfs://master:9000/user/user/data/web-Google.txt");
		InputStream input = hdfs.open(inputPath);

		BufferedReader reader = new BufferedReader(new InputStreamReader(hdfs.open(inputPath)));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();

		// --------------------------------------------------------------------------------------------------------

		// import org.apache.flink.api.java.ExecutionEnvironment;
		// import org.apache.flink.api.java.io.CsvReader;
		// import org.apache.flink.api.java.tuple.Tuple2;
		// import org.apache.flink.graph.Graph;
		// import org.apache.flink.graph.library.metric.directed.VertexMetrics;
		// import org.apache.flink.types.NullValue;

		// public class HdfsGraphExample {

		// 	public static void main(String[] args) throws Exception {
		// 		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		// 		// Set up HDFS file system
		// 		org.apache.hadoop.conf.Configuration hadoopConfig = new org.apache.hadoop.conf.Configuration();
		// 		hadoopConfig.set("fs.defaultFS", "hdfs://localhost:9000"); // replace with your HDFS namenode address
		// 		hadoopConfig.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		// 		hadoopConfig.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		// 		org.apache.hadoop.fs.FileSystem hdfs = org.apache.hadoop.fs.FileSystem.get(hadoopConfig);

		// 		// Set up HDFS file path
		// 		String edgeListFilePath = "hdfs://localhost:9000/path/to/edge/list.csv"; // replace with your edge list file path on HDFS

		// 		// Read CSV file from HDFS
		// 		CsvReader csvReader = new CsvReader(hdfs.open(new org.apache.hadoop.fs.Path(edgeListFilePath)));
		// 		csvReader.fieldDelimiter(',')
		// 				.lineDelimiter("\n")
		// 				.ignoreFirstLine()
		// 				.ignoreComments("#")
		// 				.types(Integer.class, Integer.class);

		// 		// Construct graph from CSV data
		// 		Graph<Integer, NullValue, NullValue> graph = Graph.fromCsvReader(csvReader, env)
		// 				.keyType(Integer.class);

		// 		// Perform some computation on the graph
		// 		VertexMetrics<Integer, NullValue> metrics = new VertexMetrics<>(0);
		// 		Graph<Integer, NullValue, Tuple2<Double, Double>> result = metrics.run(graph);

		// 		// Print the result
		// 		result.getVertices().print();
		// 	}
		// }

		// --------------------------------------------------------------------------------------------------------

		// String edgeListFilePath = "/home/user/data/flink.txt";

		// Graph<Integer, NullValue, NullValue> graph = Graph.fromCsvReader(edgeListFilePath, env).keyType(Integer.class);

		// long toc = System.currentTimeMillis();

		// DataSet<Tuple2<Integer, LongValue>> result = graph.getDegrees();

		// ArrayList<Tuple2<Integer, LongValue>> degrees = new ArrayList<Tuple2<Integer, LongValue>>();
		// result.collect().forEach(degrees::add);

		// long tic = System.currentTimeMillis();

		// long totalMicros = tic-toc;

		// File times = new File("/home/user/workspace-flink/times/degreeCentrality.txt");
		// BufferedWriter bw = new BufferedWriter(new FileWriter(times, true));
		// bw.write(totalMicros +" ms\n");
		// bw.close();

		// File outputs = new File("/home/user/workspace-flink/outputs/degreeCentrality.txt");
		// BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputs));
		// for (Tuple2<Integer, LongValue> vertex : degrees) {
		// 	bw2.write(vertex.f0 + " " + vertex.f1 + "\n");
		// }
		// bw2.close();
	}
}

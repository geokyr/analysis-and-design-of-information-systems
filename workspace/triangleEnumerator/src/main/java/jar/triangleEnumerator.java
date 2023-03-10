package jar;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import org.apache.flink.graph.*;
import org.apache.flink.graph.library.*;
import org.apache.flink.types.NullValue;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class triangleEnumerator {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		String edgeListFilePath = "/home/user/data/flink.txt";

		Graph<Integer, NullValue, NullValue> graph = Graph.fromCsvReader(edgeListFilePath, env).keyType(Integer.class);

		long toc = System.currentTimeMillis();

		DataSet<Tuple3<Integer,Integer,Integer>> result = graph.run(new TriangleEnumerator<Integer, NullValue, NullValue>());

		long triangles = result.count();

		long tic = System.currentTimeMillis();

		long totalMicros = tic-toc;

		File times = new File("/home/user/workspace/times/triangleEnumerator.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(times, true));
		bw.write(totalMicros +" ms\n");
		bw.close();

		File outputs = new File("/home/user/workspace/outputs/triangleEnumerator.txt");
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputs));
		bw2.write(triangles + "\n");
		bw2.close();
	}
}

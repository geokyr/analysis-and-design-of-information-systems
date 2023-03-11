package jar;

import java.io.File;
import java.util.List;
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

public class degreeCentrality {

    public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		String edgeListFilePath = "/home/user/data/web-Google.csv";

		Graph<Integer, NullValue, NullValue> graph = Graph.fromCsvReader(edgeListFilePath, env).keyType(Integer.class);

		long toc = System.currentTimeMillis();

		DataSet<Tuple2<Integer, LongValue>> result = graph.getDegrees();

		ArrayList<Tuple2<Integer, LongValue>> degrees = new ArrayList<Tuple2<Integer, LongValue>>();
		result.collect().forEach(degrees::add);

		long tic = System.currentTimeMillis();

		long totalMicros = tic-toc;

		File times = new File("/home/user/workspace-flink/times/degreeCentrality.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(times, true));
		bw.write(totalMicros +" ms\n");
		bw.close();

		File outputs = new File("/home/user/workspace-flink/outputs/degreeCentrality.txt");
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputs));
		for (Tuple2<Integer, LongValue> vertex : degrees) {
			bw2.write(vertex.f0 + " " + vertex.f1 + "\n");
		}
		bw2.close();
	}
}

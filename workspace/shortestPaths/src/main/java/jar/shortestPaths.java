package jar;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;
import org.apache.flink.types.NullValue;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.graph.library.SingleSourceShortestPaths;

public class shortestPaths {

	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		String edgeListFilePath = "/home/user/data/flink.txt";
		int source = 0;
		int iters = 10; // Maximum number of iterations

		Graph<Integer, NullValue, NullValue> graph = Graph.fromCsvReader(edgeListFilePath, env).keyType(Integer.class);

		// Adding weights to graph edges for the shortest path algorithm
		Graph<Integer, NullValue, Double> weightedGraph = graph.mapEdges(new MapFunction<Edge<Integer, NullValue>, Double>(){
			@Override
			public Double map(Edge<Integer, NullValue> edge) throws Exception {
				return 1.0;
			}
		});

		long toc = System.currentTimeMillis();

		SingleSourceShortestPaths<Integer, NullValue> singleSourceShortestPaths = new SingleSourceShortestPaths<>(source,iters);
		DataSet<Vertex<Integer, Double>> result = singleSourceShortestPaths.run(weightedGraph);

		ArrayList<Vertex<Integer, Double>> paths = new ArrayList<Vertex<Integer, Double>>();
		result.collect().forEach(paths::add);

		long tic = System.currentTimeMillis();

		long totalMicros = tic-toc;

		File times = new File("/home/user/workspace/times/shortestPaths.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(times, true));
		bw.write(totalMicros +" ms\n");
		bw.close();

		File outputs = new File("/home/user/workspace/outputs/shortestPaths.txt");
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputs));
		for (Vertex<Integer, Double> vertex : paths) {
			bw2.write(vertex.getId() + " " + vertex.getValue() + "\n");
		}
		bw2.close();
	}
}

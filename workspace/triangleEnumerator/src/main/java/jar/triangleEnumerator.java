/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */

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

		File times = new File("../times/triangleEnumerator.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(times, true));
		bw.write(totalMicros +" ms\n");
		bw.close();

		File outputs = new File("../outputs/triangleEnumerator.txt");
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputs));
		bw2.write(triangles + "\n");
		bw2.close();
	}
}

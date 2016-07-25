package src.com.goeuro.exec;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by sophie on 24/07/16.
 */

public class ApiAccess {

	public void getConsoleInput() throws Exception {
		Console console = System.console();
		if (console == null) {
			throw new Exception("Unable to fetch console");
		}
		System.out.println("Please enter the location:");
		getAPIData(console.readLine());
	}

	private void writeCsv(InputStream input) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("test.csv"));
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				writer.writeNext(line);
			}
			writer.close();
			System.out.println("done");
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the csv:" + e);
		}

	}

	private void getAPIData(String location) throws IOException {
		String url = "http://api.goeuro.com/api/v2/position/suggest/en/";
		String charset = StandardCharsets.UTF_8.name();

		String query = String.format(url + "%s", URLEncoder.encode(location, charset));
		URLConnection connection = new URL(query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);

		writeCsv(connection.getInputStream());

	}
}

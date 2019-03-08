import com.sun.net.httpserver.HttpServer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;


/**
 * Class responsible for server which converts xml to json
 */
public class MyService {
	private final HttpServer server;

   /**
	* Constructor creating the server
	* @param port - port to listen
	* @throws  IOException
	*/
	public MyService(int port) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);

		server.createContext("/status", httpExchange -> {
			final String response = "ONLINE\n";
			httpExchange.sendResponseHeaders(200, response.length());
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		});

		server.createContext("/convert", httpExchange -> {
			String response = convert(httpExchange.getRequestBody());
			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		});
	}

   /**
	* Function converting stream to json (or error) string
	* @param xml - input stream, xml document expected
	* @return json document as string or one of the two error messages (empty input document / wrong xml format)
	*/
	private String convert(InputStream xml) {
		try {
			String xmlString = convertStreamToString(xml);
			if (xmlString.isEmpty()) return "Error. Empty xml file";
			final JSONObject json = XML.toJSONObject(xmlString);
			return json.toString(4);
		} catch (Exception e) {
			return "Error";
		}
	}

   /**
	* InputStream to String function
	* @param is - input stream to be converted to string
	* @return string read from is
	*/
	static private String convertStreamToString(java.io.InputStream is) throws IOException {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

   /**
	* Function starting the server
	* May be called from Main.java
	*/
	public void start() {
		server.start();
	}
}

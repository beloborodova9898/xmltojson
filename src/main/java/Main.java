import java.io.IOException;

/**
 * XML to JSON converter
 *
 * @author beloborodova9898
 */
public class Main {

	/**
	 * Start service
	 * @param args command line args (not used)
	 */
	public static void main(String[] args) {
		try {
			MyService service = new MyService(8080);
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

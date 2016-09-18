package Server.FileReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * 
 * Starts the Application as a Jetty Server and Indexes  the file "Sampledatat.txt"
 * @author Anusha
 *
 */
public class AppMain {

	static ExecutorService executor = Executors.newSingleThreadExecutor();
	static Server server ;
	static int portNo = 2222 ;

	public static void main(String[] args) throws Exception {
		String filePath = args[0];
		if(args.length==2){
			try{
			portNo=Integer.parseInt(args[1]);
			}catch(Exception e){
				portNo = 2222;
				System.out.println("Input port no is invalid , initializing with default port 2222");
			}
		}
		System.out.println("Starting FileReader for " + filePath+" at port:"+portNo);
		FileOffsetIndexer indexer = new FileOffsetIndexer(filePath);
		indexer.indexLineNumber();
		initializeJetty();

	}

	/**
	 * Initializes jetty server and creates a servlet context handler for root
	 */
	private static void initializeJetty() {
		ResourceConfig config = new ResourceConfig();
		config.packages("Server.FileReader");
		ServletHolder servlet = new ServletHolder(new ServletContainer(config));

		server = new Server(portNo);
		ServletContextHandler context = new ServletContextHandler(server, "/*");
		context.addServlet(servlet, "/*");

		try {
			server.start();
			server.join();
		} catch (InterruptedException e) {
			System.err.println("Failed to initializeJetty:" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Failed to initializeJetty:" + e.getMessage());
		} finally {
			server.destroy();
		}
	}
	
	public static void shutdown(String cause){
		System.out.println("Shutting down due to:"+cause);
		executor.shutdownNow();
		 try {
			server.stop();
		} catch (Exception e) {
			System.err.println("Shutdown: error while stopping jetty"+e.getMessage());
		}
		
	}

}
package Server.FileReader;

import java.util.concurrent.Future;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Rest API service that submits a task to the executor , creates and sends back
 * the response on the response is available
 * 
 * @author Anusha
 *
 */
@Path("/lines")
public class LineReaderService {

	@GET
	@Path("/{param}")
	public Response getLineNumber(@PathParam("param") String msg) {
		try {
			if (isInteger(msg)) {
				int line = Integer.parseInt(msg);
				if (line  >= FileOffsetIndexer.getMaxLineNumber() || line <= 0) {
					return Response.status(413).entity("Invalid Line number").build();
				} else {

					Future<Response> task = AppMain.executor.submit(new ReaderThreads(line));
					Response response = task.get();
					return response;
				}
			} else {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("Invalid input. Please retry with an line number").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	private static boolean isInteger(String s) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), 10) < 0)
				return false;
		}
		return true;
	}

}

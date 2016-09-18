package Server.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Callable Threads that reads the content of a given line number.
 * 
 * @author Anusha
 *
 */
public class ReaderThreads implements Callable<Response> {

	public Response call() {
		Status statusCode = Response.Status.NO_CONTENT;
		String res = statusCode.getReasonPhrase();
		try {
			long startTime = System.currentTimeMillis();

			res = readLine(line);
			statusCode = Response.Status.ACCEPTED;

			long elapsedTime = System.currentTimeMillis() - startTime;

			System.out.println(Thread.currentThread().getName() + " Finished Time:" + elapsedTime);
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = Response.Status.INTERNAL_SERVER_ERROR;
			res = statusCode.getReasonPhrase();
		}
		return getResponse(res, statusCode);

	}

	private RandomAccessFile file;
	private int line;

	public ReaderThreads(int line) {

		this.line = line;
		try {
			file = new RandomAccessFile(FileOffsetIndexer.filePath, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Response getResponse(String msg, Status error) {
		return Response.status(error).entity(msg).build();
	}

	public String readLine(int lineNumber) throws IOException, Exception {
		String msg = "";
		try {
			System.out.println(Thread.currentThread().getName() + ":readline:" + lineNumber);
			lineNumber--;
			int index = OffsetHolder.getInstance().getNearestCachedIndex(lineNumber);
			int indexDiff = lineNumber - index;
			long offset = OffsetHolder.getInstance().getOffset(index);
			file.seek(offset);
			for (int i = 0; i <= indexDiff; i++) {
				msg = file.readLine();
			}
			System.out.println(Thread.currentThread().getName() + ":readline:" + lineNumber + ":" + msg);
		} finally {
			file.close();
		}

		return msg;
	}

}

package Server.FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Indexes the file offsets. If the file size is greater than the OffsetHolder
 * Array. The indexes divides the files into blocks and only stores the offset
 * of the star of the block
 * 
 * @author Anusha
 *
 */
public class FileOffsetIndexer {

	static int maxLineNumber;
	public static int lineChunkSize;
	public static String filePath;

	public FileOffsetIndexer(String fileName) {
		this.filePath = fileName;
		try {
			Initialize(filePath);
		} catch (IOException e) {
			AppMain.shutdown(e.getMessage());
		}
	}

	/**
	 * calculating no of lines in the file
	 * 
	 * @throws IOException
	 */
	private static void calculateNoOfLines() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			int lines = 0;
			while (reader.readLine() != null)
				lines++;
			setMaxLineNumber(lines);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}

	}

	public void indexLineNumber() throws IOException {
		System.out.println("Indexing the contents .... ");
		Path path = Paths.get(filePath);
		BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset());
		try {

			long offset = 0L; // offset of first line.
			int lineNumber = 0;
			int maxLines = 0;
			String strline = br.readLine();
			maxLines++;
			while (strline != null) {
				OffsetHolder.getInstance().indexMap(lineNumber, offset);
				offset += strline.getBytes(Charset.defaultCharset()).length + 1;
				lineNumber++;

				if (lineNumber == OffsetHolder.getInstance().getCapacity()) {
					lineNumber = 0;
				}
				for (int i = 0; i < FileOffsetIndexer.lineChunkSize; i++) {
					strline = br.readLine();
					if (strline == null) {
						break;
					}
				}
				if(maxLineNumber/10==lineNumber)
					System.out.print(".");
			}
			System.out.println();
			OffsetHolder.getInstance().indexMap(lineNumber, offset);

		} catch (Exception e) {
			AppMain.shutdown(e.getMessage());
		} finally {
			System.out.println();
			br.close();
		}
	}

	/**
	 * Calculates the no of lines to be skipped between the stored offsets based
	 * on the no of lines in the file and capacity of the Offset holder array
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static void Initialize(String fileName) throws IOException {
		calculateNoOfLines();
		int idealBlockSize = 1;
		if (maxLineNumber < OffsetHolder.getInstance().getCapacity()) {
			OffsetHolder.getInstance().initializeWithCapacity(maxLineNumber+1);
		} else {
			OffsetHolder.getInstance().initializeWithCapacity(OffsetHolder.getInstance().getCapacity());
		}
		while (maxLineNumber > OffsetHolder.getInstance().getCapacity() * idealBlockSize) {
			idealBlockSize++;
		}
		lineChunkSize = idealBlockSize;
		System.out.println("Max Lines:" + maxLineNumber + ":no of partition" + OffsetHolder.getInstance().getCapacity()
				+ ":no of lines in Partition" + FileOffsetIndexer.lineChunkSize);

	}

	public static void setMaxLineNumber(int no) {

		maxLineNumber = no;
		
	}

	public static int getMaxLineNumber() {
		return maxLineNumber;
	}
}

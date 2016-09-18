package Server.FileReader;

/**
 * Singleton that holds the start offset of the blocks in the file
 * 
 * @author Anusha
 *
 */
public class OffsetHolder {
	
	private  int capacity;
	public  int getCapacity() {
		return capacity;
	}

	private static Long line2Offset[];

	private OffsetHolder() {
		// the capacity is calculated to be 25 % of the freememory
		capacity = (int) (Runtime.getRuntime().freeMemory() / 4);
	}

	private static class SingletonHelper {
		private static final OffsetHolder INSTANCE = new OffsetHolder();
	}

	public static OffsetHolder getInstance() {
		return SingletonHelper.INSTANCE;
	}

	public  void initializeWithCapacity(int value){
		capacity = value;
		line2Offset = new Long[capacity];

	}
	
	

	
	/**
	 * gets the offset for a particular line number
	 * 
	 * @param lineNumber
	 * @return
	 */
	public  Long getOffset(int lineNumber) {
		return line2Offset[lineNumber];
	}

	/**
	 * gets the nearest index of offset for a given line number
	 * 
	 * @param lineNumber
	 * @return
	 */
	public  int getNearestCachedIndex(int lineNumber) {
		return lineNumber = lineNumber / FileOffsetIndexer.lineChunkSize;
	}

	public  void indexMap(int lineNumber, Long offset) {
		line2Offset[lineNumber] = offset;
	}

}

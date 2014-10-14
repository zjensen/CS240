package shared.communication;
/**
 * results returned from downloading a file
 * @author zsjensen
 *
 */
public class DownloadFile_Result 
{
	private byte[] fileBytes;
	/**
	 * Bytes returned by file referenced by URL
	 * @param fileBytes
	 */
	public DownloadFile_Result(byte[] fileBytes)
	{
		this.fileBytes = fileBytes;
	}

	/**
	 * @return the fileBytes
	 */
	public byte[] getFileBytes() {
		return fileBytes;
	}

	/**
	 * @param fileBytes the fileBytes to set
	 */
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
}

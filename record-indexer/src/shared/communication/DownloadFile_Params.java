package shared.communication;
/**
 * Parameters for downloading a file
 * @author zsjensen
 *
 */
public class DownloadFile_Params 
{
	private String url;
	/**
	 * @param url
	 */
	public DownloadFile_Params(String url) 
	{
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

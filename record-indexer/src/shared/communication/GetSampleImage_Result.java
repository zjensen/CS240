package shared.communication;

public class GetSampleImage_Result
{
	private String imageURL;
	/**
	 * URL of project's sample image
	 * @param imageURL
	 */
	public GetSampleImage_Result(String imageURL) 
	{
		this.imageURL = imageURL;
	}
	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	/**
	 * @return FAILED if invalid parameters, else url of sample image
	 */
	public String toString()
	{
		if(imageURL == null || imageURL.isEmpty())
		{
			return "FAILED\n";
		}
		else
		{
			return(imageURL + "\n");
		}
	}
}

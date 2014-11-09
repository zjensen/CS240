package shared.communication;

public class SubmitBatch_Result 
{
	private boolean submitted;

	
	public SubmitBatch_Result(boolean submitted) 
	{
		this.submitted = submitted;
	}
	/**
	 * @return TRUE or FALSE, whether or not batch was successfully submitted
	 */
	@Override
	public String toString()
	{
		if(submitted)
		{
			return"TRUE\n";
		}
		else
		{
			return"FAILED\n";
		}
	}
	/**
	 * @return the submitted
	 */
	public boolean isSubmitted() {
		return submitted;
	}

	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	

}

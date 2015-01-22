package client.gui.state;

public class Cell 
{
	private int record;
	private int column;
	public Cell(int record, int column) {
		super();
		this.record = record;
		this.column = column;
	}
	@Override
	public boolean equals(Object obj) 
	{
		Cell other = (Cell) obj;
		if (column != other.column)
			return false;
		if (record != other.record)
			return false;
		return true;
	}
	/**
	 * @return the record
	 */
	public int getRecord() {
		return record;
	}
	/**
	 * @param record the record to set
	 */
	public void setRecord(int record) {
		this.record = record;
	}
	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
}

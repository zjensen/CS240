package client.gui.state;

public interface BatchStateListener 
{
	void valueChanged(Cell cell, String newValue);
	void currentCellChanged(Cell newCurrentCell);
}

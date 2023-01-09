package icia.js.changyong.services.auth;

public interface Auth {
	
	public void backController(); //ActionBean
	public void initCtl(); //ActionBean
	public void accessCtl(); //ActionBean
	public void access(); //GroupBean
	public void storeList(); //String
	public void accessOutCtl(); //ActionBean
	public boolean isAccess(); //Boolean
	public boolean convertToBoolean(); //Boolean
	
}

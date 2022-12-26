package beans;

import java.util.ArrayList;

public class GroupBean {
	private String groupCode;
	private String groupName;
	private String groupCeo;
	private String groupPin;
	private ArrayList<StoreBean> storeInfoList;
	
	public ArrayList<StoreBean> getStoreInfoList() {
		return storeInfoList;
	}
	public void setStoreInfoList(ArrayList<StoreBean> storeInfoList) {
		this.storeInfoList = storeInfoList;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupCeo() {
		return groupCeo;
	}
	public void setGroupCeo(String groupCeo) {
		this.groupCeo = groupCeo;
	}
	public String getGroupPin() {
		return groupPin;
	}
	public void setGroupPin(String groupPin) {
		this.groupPin = groupPin;
	}
}

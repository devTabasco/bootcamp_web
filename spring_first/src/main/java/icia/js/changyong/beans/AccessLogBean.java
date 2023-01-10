package icia.js.changyong.beans;

public class AccessLogBean {
	private String accessDate;
	private String accessPublicIp;
	private String accessLocation;
	private int accessType;
	private String accessState;
	private String accessBrowser;
	
	public String getAccessPublicIp() {
		return accessPublicIp;
	}
	public void setAccessPublicIp(String accessPublicIp) {
		this.accessPublicIp = accessPublicIp;
	}
	public String getAccessState() {
		return accessState;
	}
	public void setAccessState(String accessState) {
		this.accessState = accessState;
	}
	public String getAccessBrowser() {
		return accessBrowser;
	}
	public void setAccessBrowser(String accessBrowser) {
		this.accessBrowser = accessBrowser;
	}
	public String getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	public String getAccessLocation() {
		return accessLocation;
	}
	public void setAccessLocation(String accessLocation) {
		this.accessLocation = accessLocation;
	}
	public int getAccessType() {
		return accessType;
	}
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
}

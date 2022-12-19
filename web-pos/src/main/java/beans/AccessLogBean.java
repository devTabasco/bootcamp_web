package beans;

public class AccessLogBean {
	private String accessDateTime;
	private String accessLocate;
	private int accessType;
	private String accessPublicIp;
	private String accessState;
	
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
	private String accessBrowser;
	
	
	public String getAccessDateTime() {
		return accessDateTime;
	}
	public void setAccessDateTime(String accessDateTime) {
		this.accessDateTime = accessDateTime;
	}
	public String getAccessLocate() {
		return accessLocate;
	}
	public void setAccessLocate(String accessLocate) {
		this.accessLocate = accessLocate;
	}
	public int getAccessType() {
		return accessType;
	}
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
}

package beans;

public class AccessLogBean {
	private String accessDateTime;
	private String accessLocate;
	private String accessType;
	
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
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
}

package icia.js.hoonzzang.beans;

import lombok.Data;

@Data
public class AccessLogBean {
	private String accessDate;
	private String accessPublicIp;
	private String accessLocation;
	private int accessType;
	private String accessState;
	private String accessBrowser;
	
}

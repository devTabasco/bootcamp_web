package icia.js.hoonzzang.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class EmployeesBean {
	private String empCode;
	private String empLevCode;
	private String empLevName;
	private String empName;
	private String empPin;
	private String empImgCode;
	private ArrayList<AccessLogBean> accessList;
	
}

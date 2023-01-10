package icia.js.changyong.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GroupBean {
	private String message;
	private String groupName;
	private String groupCode;
	private String groupCeo;
	private String groupPin;
	private ArrayList<StoreBean> storeInfoList;
}

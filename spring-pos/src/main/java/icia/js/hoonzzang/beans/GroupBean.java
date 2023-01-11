package icia.js.hoonzzang.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GroupBean {
	private String message;
	private String groupCode;
	private String groupName;
	private String groupCeo;
	private String groupPin;
	private ArrayList<StoreBean> storeInfoList;
	//storeInforList[0].storeCode
}

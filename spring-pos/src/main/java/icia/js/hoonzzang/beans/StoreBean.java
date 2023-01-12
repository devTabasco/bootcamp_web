package icia.js.hoonzzang.beans;

import java.util.ArrayList;

import lombok.Data;

@Data
public class StoreBean {
	private String storeCode;
	private String storeName;
	private String storeZip;
	private String storeAddr;
	private String storeAddrDetail;
	private String storePhone;
	private String storeImageCode;
	private ArrayList<GoodsBean> goodsList;
	private ArrayList<CategoriesBean> cateList;
	private ArrayList<EmployeesBean> empList;
	
}

package beans;

import java.util.ArrayList;

public class StoreBean {
	private String storeCode;
	private String storeName;
	private String storeZip;
	private String storeAddr;
	private String storeAddrDetail;
	private String storePhone;
	private String storeImageCode;
	private ArrayList<CategoriesBean> cateList;
	private ArrayList<EmployeesBean> empList;
	
	public ArrayList<EmployeesBean> getEmpList() {
		return empList;
	}
	public void setEmpList(ArrayList<EmployeesBean> empList) {
		this.empList = empList;
	}
	public ArrayList<CategoriesBean> getCateList() {
		return cateList;
	}
	public void setCateList(ArrayList<CategoriesBean> cateList) {
		this.cateList = cateList;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreZip() {
		return storeZip;
	}
	public void setStoreZip(String storeZip) {
		this.storeZip = storeZip;
	}
	public String getStoreAddr() {
		return storeAddr;
	}
	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}
	public String getStoreAddrDetail() {
		return storeAddrDetail;
	}
	public void setStoreAddrDetail(String storeAddrDetail) {
		this.storeAddrDetail = storeAddrDetail;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getStoreImageCode() {
		return storeImageCode;
	}
	public void setStoreImageCode(String storeImageCode) {
		this.storeImageCode = storeImageCode;
	}	
}

package icia.js.changyong.beans;

import java.util.ArrayList;

public class GoodsBean {
	private String goodsCode;
	private String goodsCategoryCode;
	private String goodsCategoryName;
	private String goodsName;
	private String goodsStateCode;
	private String goodsStateName;
	private String goodsColorCode;
	private String goodsImageCode;
	private ArrayList<StoreImages> storeImagesList;
	private ArrayList<CostPriceBean> goodsPriceList;
	
	public ArrayList<StoreImages> getStoreImagesList() {
		return storeImagesList;
	}
	public void setStoreImagesList(ArrayList<StoreImages> storeImagesList) {
		this.storeImagesList = storeImagesList;
	}
	public String getGoodsCategoryName() {
		return goodsCategoryName;
	}
	public void setGoodsCategoryName(String goodsCategoryName) {
		this.goodsCategoryName = goodsCategoryName;
	}
	public String getGoodsStateName() {
		return goodsStateName;
	}
	public void setGoodsStateName(String goodsStateName) {
		this.goodsStateName = goodsStateName;
	}
	public ArrayList<CostPriceBean> getGoodsPriceList() {
		return goodsPriceList;
	}
	public void setGoodsPriceList(ArrayList<CostPriceBean> goodsPriceList) {
		this.goodsPriceList = goodsPriceList;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsCategoryCode() {
		return goodsCategoryCode;
	}
	public void setGoodsCategoryCode(String goodsCategoryCode) {
		this.goodsCategoryCode = goodsCategoryCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsStateCode() {
		return goodsStateCode;
	}
	public void setGoodsStateCode(String goodsStateCode) {
		this.goodsStateCode = goodsStateCode;
	}
	public String getGoodsColorCode() {
		return goodsColorCode;
	}
	public void setGoodsColorCode(String goodsColorCode) {
		this.goodsColorCode = goodsColorCode;
	}
	public String getGoodsImageCode() {
		return goodsImageCode;
	}
	public void setGoodsImageCode(String goodsImageCode) {
		this.goodsImageCode = goodsImageCode;
	}
}

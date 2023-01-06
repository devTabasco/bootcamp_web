package beans;

import java.util.ArrayList;

public class GoodsBean {
	private String goodsCode;
	private String goodsCategoryCode;
	private String goodsName;
	private String goodsStateCode;
	private String goodsColorCode;
	private String goodsImageCode;
	private ArrayList<CostPriceBean> goodsPriceList;
	
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

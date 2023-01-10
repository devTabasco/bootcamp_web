package icia.js.changyong.beans;

public class CostPriceBean {
	private String priceDday;
	private int	cost;
	private int price;
	private String priceCategoryCode;
	private String priceCategoryName;
	private String priceComment;
	
	public String getPriceCategoryName() {
		return priceCategoryName;
	}
	public void setPriceCategoryName(String priceCategoryName) {
		this.priceCategoryName = priceCategoryName;
	}
	public String getPriceDday() {
		return priceDday;
	}
	public void setPriceDday(String priceDday) {
		this.priceDday = priceDday;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPriceCategoryCode() {
		return priceCategoryCode;
	}
	public void setPriceCategoryCode(String priceCategoryCode) {
		this.priceCategoryCode = priceCategoryCode;
	}
	public String getPriceComment() {
		return priceComment;
	}
	public void setPriceComment(String priceComment) {
		this.priceComment = priceComment;
	}
}

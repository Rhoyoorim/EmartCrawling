package model;

public class EmartMallVO {
	private int eId;
	private int eCategory;
	private String eName;
	private int ePrice;
	private int eReview;
	private int maxPrice;
	private int minPrice;
	private String eImg;
	private String Category;
	
	
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String geteImg() {
		return eImg;
	}
	public void seteImg(String eImg) {
		this.eImg = eImg;
	}
	public int geteId() {
		return eId;
	}
	public void seteId(int eId) {
		this.eId = eId;
	}
	
	public int geteCategory() {
		return eCategory;
	}
	public void seteCategory(int eCategory) {
		this.eCategory = eCategory;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public int getePrice() {
		return ePrice;
	}
	public void setePrice(int ePrice) {
		this.ePrice = ePrice;
	}
	public int geteReview() {
		return eReview;
	}
	public void seteReview(int eReview) {
		this.eReview = eReview;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	
	@Override
	public String toString() {
		return "[번호: "+ eId + "  이름: "+ eName +"  금액: "+ ePrice
				+"]";
	}
	
}
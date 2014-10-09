package news;
import java.util.HashMap;
import java.util.Map;
public class Shop {
	static int idx=0;
	int id=0;
	String homePage, shopName, rank, product;
	int sales;
	static Map<String,String> rate = new HashMap<String,String>();
	static{
		rate.put("rank seller-rank-16", "È«¹Ú");
		rate.put("rank seller-rank-15", "À¶¹Ú5");
		rate.put("rank seller-rank-14", "À¶¹Ú4");
		rate.put("rank seller-rank-13", "À¶¹Ú3");
		rate.put("rank seller-rank-12", "À¶¹Ú2");
		rate.put("rank seller-rank-11", "À¶¹Ú1");
		rate.put("rank seller-rank-10", "×©Ê¯5");
		rate.put("rank seller-rank-9", "×©Ê¯4");
		rate.put("rank seller-rank-8", "×©Ê¯3");
		rate.put("rank seller-rank-7", "×©Ê¯2");
		rate.put("rank seller-rank-6", "×©Ê¯1");
		rate.put("rank seller-rank-5", "ºìÐÄ5");
		rate.put("rank seller-rank-4", "ºìÐÄ4");
		rate.put("rank seller-rank-3", "ºìÐÄ3");
		rate.put("rank seller-rank-2", "ºìÐÄ2");
		rate.put("rank seller-rank-1", "ºìÐÄ1");
	}
	public static void resetIndex(){
		idx=0;
	}
	public void setSales(String value){
		sales = Integer.valueOf(value).intValue();
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Shop(String homePage, String shopName, String rank, String product,int sales) {
		id= ++idx;
		this.homePage = homePage;
		this.shopName = shopName;
		this.rank = rank;
		this.product = product;
		this.sales = sales;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public String getRank() {
		return rate.get(rank);
	}
	public String toStringForDb() {
		return "insert into taobao() values (" + id +",'"+shopName+"','"+homePage+"','"+rate.get(rank) +"'," + sales +")";
	}
	
	@Override
	public String toString() {
		return id +","+shopName+","+homePage+","+rate.get(rank) +"," + sales;
	}
}
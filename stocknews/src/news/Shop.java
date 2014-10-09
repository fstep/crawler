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
		rate.put("rank seller-rank-16", "ȫ��");
		rate.put("rank seller-rank-15", "����5");
		rate.put("rank seller-rank-14", "����4");
		rate.put("rank seller-rank-13", "����3");
		rate.put("rank seller-rank-12", "����2");
		rate.put("rank seller-rank-11", "����1");
		rate.put("rank seller-rank-10", "שʯ5");
		rate.put("rank seller-rank-9", "שʯ4");
		rate.put("rank seller-rank-8", "שʯ3");
		rate.put("rank seller-rank-7", "שʯ2");
		rate.put("rank seller-rank-6", "שʯ1");
		rate.put("rank seller-rank-5", "����5");
		rate.put("rank seller-rank-4", "����4");
		rate.put("rank seller-rank-3", "����3");
		rate.put("rank seller-rank-2", "����2");
		rate.put("rank seller-rank-1", "����1");
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
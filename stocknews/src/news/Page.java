package news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class Page {
	Map<String, Shop> shopMap = new HashMap<String, Shop>();
	String url = null;

	public Page(String url) {
		this.url = url;
		extractLinks();
	}

	public Map<String, Shop> getShopMap() {
		return shopMap;
	}

	public String toString() {
		String buf = null;
		Iterator it = shopMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Shop> pair = (Entry<String, Shop>) it.next();
			if (buf == null)
				buf = pair.getValue().toString() + "\n";
			else
				buf += pair.getValue().toString() + "\n";
		}
		return buf;
	}

	public void extractLinks() {
		Parser parser;
		String uid;
		try {
			parser = new Parser(url);
			NodeList list = parser.parse(null);
			ArrayList<LinkTag> set = HtmlParserTool.extracLinks(url,
					new LinkFilter() {
						public boolean accept(String url) {
							if (url.contains("http://shop")
									|| url.contains("http://rate"))
								return true;
							else
								return false;
						}
					});
			
			for (LinkTag link : set) {
				uid = link.getAttribute("data-uid");
				Shop shop = null;
				if (!shopMap.containsKey(uid)) {
					shopMap.put(uid, new Shop(link.getAttribute("href")
							.toString(), link.getAttribute("title").toString()
							.split(",")[0], "", "", 0));
				} else {
					shop = shopMap.get(uid);
				}
				if (link.getAttribute("class") != null) {
					shop.setRank(link.getAttribute("class"));
					shop.setSales(HtmlParserTool.sales.get(uid));
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
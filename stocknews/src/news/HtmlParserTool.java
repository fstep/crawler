package news;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
public class HtmlParserTool {
	
	static Map<String, String> sales = new HashMap<String, String>();
	
	// 获取一个网站上的链接,filter 用来过滤链接
	public static ArrayList<LinkTag> extracLinks(String url, LinkFilter filter) {
		ArrayList<LinkTag> links = new ArrayList<LinkTag>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
//					String text = node.getText();
//					if (node.getText().startsWith("")) {
//						return true;
//					} else {
//						return false;
//					}
					return true;
				}
			};
			
			// OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);
			String uid = null;
			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(frameFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> 标签
				{
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();// url
					if (filter.accept(linkUrl)) {
						uid=link.getAttribute("data-uid");
						links.add(link);						
					}
				}
				if (tag instanceof Span) {
					Span span = (Span) tag;
					String attr = span.getAttribute("class");
					if (attr != null && attr.equals("info-sale")) {
						String[] data1 = span.getStringText().split(">");
						String value = data1[1].split("<")[0];
//						System.out.println(uid + "\t" +dat);
						sales.put(uid, value);
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}
}

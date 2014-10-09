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
	
	// ��ȡһ����վ�ϵ�����,filter ������������
	public static ArrayList<LinkTag> extracLinks(String url, LinkFilter filter) {
		ArrayList<LinkTag> links = new ArrayList<LinkTag>();
		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gb2312");
			// ���� <frame >��ǩ�� filter��������ȡ frame ��ǩ��� src ��������ʾ������
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
			
			// OrFilter �����ù��� <a> ��ǩ���� <frame> ��ǩ
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);
			String uid = null;
			// �õ����о������˵ı�ǩ
			NodeList list = parser.extractAllNodesThatMatch(frameFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag)// <a> ��ǩ
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

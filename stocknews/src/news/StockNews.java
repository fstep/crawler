package news;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class StockNews {
	public static void main(String[] args) {
		String url = "http://finance.qq.com/stock/";
		// getLink(url);
		getLink(url);
		// getContent("http://finance.qq.com/a/20140517/004797.htm?pgv_ref=aio2012&ptlang=2052");
	}

	static StringBuilder sb = new StringBuilder();

	private static String getNewsText(String url) {
		try {
			Parser p = new Parser(url);
			p.setEncoding("gb2312");
			p.visitAllNodesWith(new NodeVisitor() {
				@Override
				public void visitTag(Tag tag) {
					if (tag instanceof Div) {
						String id = tag.getAttribute("id");
						if (null != id && id.equals("Cnt-Main-Article-QQ")) {
							Div div = (Div) tag;
							extractText(div);
						}
					}
				}
			});
		} catch (ParserException e) {
			e.printStackTrace();
		}
		String temp = sb.toString();
		sb.delete(0, sb.length());
		return temp;
	}

	protected static void extractText(Node tag) {
		if (tag instanceof ParagraphTag)
			sb.append(removeBracket(((ParagraphTag) tag).getStringText()));
		Node node = tag.getFirstChild();
		if (node != null) {
			NodeList list = tag.getChildren();
			if (list.size() > 0)
				for (int i = 0; i < list.size(); i++) {
					extractText(list.elementAt(i));
				}
		}
	}

	private static String removeBracket(String stringText) {
		String pattern = "<.+?>";
		return stringText.replaceAll(pattern, "");
	}

	static Db db = new Db();

	private static void getLink(String url) {
		try {
			new Parser(url).visitAllNodesWith(new NodeVisitor() {
				@Override
				public void visitTag(Tag tag) {
					if (tag instanceof LinkTag) {
						LinkTag link = ((LinkTag) tag);
						if (link.getLink().contains("http://finance.qq.com")) {
							linkTask(link);
						}
					}
				}
			});
		} catch (ParserException e) {
			e.printStackTrace();
		}
		db.closeDb();
	}

	protected static void linkTask(LinkTag link) {		
		String linkUrl = link.getLink();
		String info = link.getAttribute("title");
		String text;
		if (info != null)
			if (info.length() > 5) {
				System.out.println(String.format("%s <%s>", info, linkUrl));
				text = getNewsText(linkUrl);
				System.out.println(info);
				db.insert(info, linkUrl, text);
			}
	}

}

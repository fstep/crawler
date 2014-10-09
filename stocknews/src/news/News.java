package news;

public class News {

	String title, url, body;

	public News(String title, String url, String body) {
		this.title = title;
		this.url = url;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getBody() {
		return body;
	}

}

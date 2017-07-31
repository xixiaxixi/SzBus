package xixiaxixi.szbus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SearchInternet implements Runnable {
    @Override
    public void run() {
        try {
            searchHtml = Jsoup.connect(searchUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearchInternet(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    private Document searchHtml;
    private String searchUrl;

    /**
     * 获取搜索结果
     * @return 搜索结果的网页
     */
    public Document getSearchHtml() {
        return searchHtml;
    }
}

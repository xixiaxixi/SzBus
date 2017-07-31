package xixiaxixi.szbus;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Lines {
    public Lines(String busNo)
    {
        if (busNo.equals("")) {
            lines=null;
            return;
        }
        String searchUrl="http://bus.2500.tv/line.php?line="+busNo;//搜索的URL
        try {
            SearchInternet search = new SearchInternet(searchUrl);
            Thread thread = new Thread(search);
            thread.start();
            thread.join();

            Document searchHtml = search.getSearchHtml();
            Element elementDiv = searchHtml.getElementsByClass("m2").last();
            if (!elementDiv.text().equals("查询结果不存在")) {
                Elements elementDds = elementDiv.select("dd");
                if (elementDds.isEmpty()) {
                    return;
                }
                int listSize=elementDds.size()>10?10:elementDds.size();
                lines = new ArrayList<Line>(listSize);
                for (int i=0;i<listSize;i++) {
                    Element e = elementDds.get(i);
                    String searchResult=e.text();
                    String[] busNoAndFromTo=searchResult.split("[ ]");
                    lines.add(new Line(busNoAndFromTo[0],busNoAndFromTo[1], e.select("a[href]").first().attr("href")));
                }
//                获取全部实在是太慢了，最多读取10条,如上
//                for (Element e : elementDds) {
//                    String searchResult=e.text();
//                    String[] busNoAndFromTo=searchResult.split("[ ]");
//                    lines.add(new Line(busNoAndFromTo[0],busNoAndFromTo[1], e.select("a[href]").first().attr("href")));
//                }
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Line> lines;

}

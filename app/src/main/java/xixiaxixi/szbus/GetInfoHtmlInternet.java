package xixiaxixi.szbus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetInfoHtmlInternet  implements Runnable {
    private Document lineInfoHtml;
    private String jsonStatus;

    public Document getLineInfoHtml() {
        return lineInfoHtml;
    }

    public String getJsonStatus() {
        return jsonStatus;
    }

    private String aUrl;
    private String fromId;

    public GetInfoHtmlInternet(LineInfo lineInfo) {
        aUrl = lineInfo.aUrl;
        fromId = lineInfo.fromId;
    }

    @Override
    public void run(){
        try {
            lineInfoHtml = Jsoup.connect(aUrl).get();//GET
            jsonStatus=Jsoup.connect("http://bus.2500.tv/api_line_status.php")
                    .data("lineID", fromId)
                    .header("Host", "bus.2500.tv")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Referer", aUrl)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .post().body().text();//POST
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

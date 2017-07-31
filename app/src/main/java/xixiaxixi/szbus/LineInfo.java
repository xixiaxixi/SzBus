package xixiaxixi.szbus;

import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class LineInfo extends Line {

//    public String busNo;   //super
//    public String fromTo;  //super
//    public String rUrl;    //super
    public String aUrl;
    public String fromId;
    public String toId;

    public String scheduleList;
    public String noticeBList;
    public List<BusStation> busStations;

    public LineInfo(Line line) {
        super(line);
        aUrl = "http://bus.2500.tv/" + rUrl;
        String[] splitRUrl=rUrl.split("[?=&]");
        fromId = splitRUrl[2];
        toId = splitRUrl[4];
    }

    public LineInfo(LineRecord lineRecord) {
        super(lineRecord.busNo, lineRecord.fromTo, lineRecord.rUrl);
        aUrl = "http://bus.2500.tv/" + rUrl;
        String[] splitRUrl=rUrl.split("[?=&]");
        fromId = splitRUrl[2];
        toId = splitRUrl[4];
    }


    /**
     * 反向
     */
    public void changeDirection() {

        /**
         * 更改fromTo
         */
        try {
            String[] splitFromTo = fromTo.split("[—]");
            fromTo=splitFromTo[1]+"—"+splitFromTo[0];
        } catch (Exception e) {
            fromTo = "Exception Occurred!!";
        }

        /**
         * 交换fromId toId
         */
        String exchangeTemp=fromId;
        fromId=toId;
        toId=exchangeTemp;


        /**
         * 更改rUrl和aUrl
         */
        rUrl = "lineInfo.php?lineID=" + fromId + "&roLine=" + toId;
        aUrl = "http://bus.2500.tv/" + rUrl;

    }

    public boolean getInfo() {
        GetInfoHtmlInternet get = new GetInfoHtmlInternet(this);
        Thread thread = new Thread(get);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        //获取时间和公告
        Document lineInfoHtml = get.getLineInfoHtml();
        Element elementScheduleList = lineInfoHtml.getElementById("scheduleList");//车辆时间
        scheduleList = elementScheduleList != null ? elementScheduleList.text() : "NULL";
        Element elementNoticeBList = lineInfoHtml.getElementById("noticeBList").select("li").first();//公告
        noticeBList = elementNoticeBList != null ? elementNoticeBList.text(): "NULL";

        //获取车辆位置信息
        String json = get.getJsonStatus();
        try {
            JSONObject obj = new JSONObject(new JSONTokener(json));

            if (obj.getInt("status") == 0) {
                return false;
            }
            JSONArray arr = obj.getJSONArray("data");
            busStations = new ArrayList<BusStation>(arr.length());
            for (int i = 0, length = arr.length(); i < length; i++) {
                JSONObject bs = arr.getJSONObject(i);
                busStations.add(new BusStation(bs.getString("StationCName"), bs.getString("InTime"), bs.getString("BusInfo")));
            }
        }  catch (Exception e) {
            return false;
        }

        return true;
    }
}

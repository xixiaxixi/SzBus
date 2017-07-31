package xixiaxixi.szbus;

import java.util.HashMap;
import java.util.Map;

public class Line {
    public Line(Line line) {
        this.busNo = line.busNo;
        this.fromTo = line.fromTo;
        this.rUrl = line.rUrl;
    }

    public Line(String busNo, String fromTo, String rUrl) {
        this.busNo = busNo;
        this.fromTo = fromTo;
        this.rUrl = rUrl;
    }

    public String busNo;
    public String fromTo;
    public String rUrl;

    @Override
    public String toString() {
        return fromTo;
    }

    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("busNo", busNo);
        map.put("fromTo", fromTo);
        map.put("rUrl", rUrl);
        return map;
    }
}

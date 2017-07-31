package xixiaxixi.szbus;

public class BusStation {
    public String stationName;
    public String inTime;
    public String busInfo;

    public BusStation(String stationName, String inTime, String busInfo) {
        this.stationName=stationName;
        this.inTime=inTime;
        this.busInfo=busInfo;
    }

    @Override
    public String toString() {
        return stationName+" "+inTime+" "+busInfo;
    }
}


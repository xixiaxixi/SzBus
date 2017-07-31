package xixiaxixi.szbus;

import com.orm.SugarRecord;

public class LineRecord extends SugarRecord{
    public String busNo;
    public String fromTo;
    public String rUrl;
    public int type;//啊啊，懒得enum了，也不知道继承了ORM会发生些啥

    public LineRecord() {
    }

    public LineRecord(LineInfo info,int type) {
        this.busNo = info.busNo;
        this.fromTo = info.fromTo;
        this.rUrl = info.rUrl;
        this.type = type;
    }

}

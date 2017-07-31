package xixiaxixi.szbus;

import android.app.Application;
import com.orm.SugarContext;

import java.util.List;

public class ShareDataApplication extends Application {
    public String strSearchLine;
    public Line lineChosen;

    @Override
    public void onCreate() {
        super.onCreate();
        strSearchLine = "";
        lineChosen = new Line("-1","贤者之森—幻想乡","written by ℃C");
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}

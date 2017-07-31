package xixiaxixi.szbus;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class BusLineButton extends Button {
    public BusLineButton(final Context context, String btnText, final LineRecord lineRecord,final Line line) {
        super(context);
        this.setText(btnText);
        this.setTextSize(12);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                line.busNo = lineRecord.busNo;
                line.fromTo=lineRecord.fromTo;
                line.rUrl = lineRecord.rUrl;
                Intent intent = new Intent(context, LineInformationActivity.class);
                context.startActivity(intent);
            }
        });
    }
}

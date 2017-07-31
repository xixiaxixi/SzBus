package xixiaxixi.szbus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LineInformationActivity extends AppCompatActivity {

    private LineInfo lineInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * 获取公共资源
         */
        final ShareDataApplication shareDataApplication = (ShareDataApplication) getApplication();
        Line line = shareDataApplication.lineChosen;

        /**
         * 私有 初始化
         */
        lineInfo = new LineInfo(line);

        /**
         * 返回键
         */
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 时间及公告按钮
         */
        Button btnTime = (Button) findViewById(R.id.btn_time);
        Button btnNotice = (Button) findViewById(R.id.btn_notice);
        View.OnClickListener onClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LineInformationActivity.this);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                switch (v.getId()) {
                    case R.id.btn_time:{
                        builder.setTitle("发车间隔").setMessage(lineInfo.scheduleList);
                        break;
                    }
                    case R.id.btn_notice:{
                        builder.setTitle("公告").setMessage(lineInfo.noticeBList);
                        break;
                    }
                }
                builder.create().show();
            }
        };
        btnTime.setOnClickListener(onClickListener);
        btnNotice.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_information, menu);
        return true;
    }
    /**
     * 菜单栏
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            /**
             * 换向
             */
            case R.id.action_change_direction:{
                lineInfo.changeDirection();
                lineInfo.getInfo();
                refresh();
                return true;
            }
            /**
             * 刷新
             */
            case R.id.action_refresh:{
                refresh();
                return true;
            }
            /**
             * 收藏
             */
            case R.id.action_put_into_favourites:{
                LineRecord record = new LineRecord(lineInfo, 1);
                if(LineRecord.find(LineRecord.class,"bus_no = ? and from_to = ? and type = 1",new String[]{lineInfo.busNo,lineInfo.fromTo}).isEmpty())
                {
                    new LineRecord(lineInfo, 1).save();
                    Toast.makeText(LineInformationActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LineInformationActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 刷新界面，好吧其实算是整个绘制界面了吧
     */
    private void refresh() {
        if (lineInfo.getInfo()) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_line_list);
            linearLayout.removeAllViews();
            LayoutInflater inflater = getLayoutInflater();
            for (BusStation station : lineInfo.busStations) {
                View v=inflater.inflate(R.layout.list_items, null);
                TextView title = (TextView) v.findViewById(R.id.ItemTitle);
                title.setText(station.stationName);
                if (!station.inTime.equals("")) {
                    TextView textView = (TextView) v.findViewById(R.id.ItemText);
                    textView.setText("进站时间： "+station.inTime + "   " + station.busInfo);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));//R.color.colorAccent);
                    title.setTextColor(getResources().getColor(R.color.colorAccent));//R.color.colorAccent);
                }
                linearLayout.addView(v);
            }
        } else {
            Toast.makeText(LineInformationActivity.this, "查询失败", Toast.LENGTH_LONG);
        }
        /**
         * 设置标题
         */
        setTitle(lineInfo.busNo+" "+lineInfo.fromTo);
    }

    @Override
    protected void onStop() {
        super.onStop();

        /**
         * 添加到历史记录
         */
        if(LineRecord.find(LineRecord.class,"bus_no = ? and from_to = ?",new String[]{lineInfo.busNo,lineInfo.fromTo}).isEmpty())
        {
            new LineRecord(lineInfo, 0).save();
        }
    }

}

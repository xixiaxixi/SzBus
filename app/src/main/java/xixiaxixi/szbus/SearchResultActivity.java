package xixiaxixi.szbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

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
         * 获取公共资源
         */
        final ShareDataApplication shareDataApplication = (ShareDataApplication) getApplication();
        final Lines lines = new Lines(shareDataApplication.strSearchLine);

        /**
         * 设置标题
         */
        setTitle(shareDataApplication.strSearchLine + " 搜索结果：");

        /**
         * 建表 速度好慢啊
         */
        ListView listView = (ListView) findViewById(R.id.lv_result_container);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        if (lines.lines != null) {
            for (Line line : lines.lines) {
                listItem.add(line.getMap());
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                listItem,
                R.layout.list_items,
                new String[]{"busNo", "fromTo"},
                new int[]{R.id.ItemTitle, R.id.ItemText}
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shareDataApplication.lineChosen=lines.lines.get(position);
                Toast.makeText(SearchResultActivity.this, shareDataApplication.lineChosen.toString(), 500).show();

                //start activity
                Intent intent = new Intent(SearchResultActivity.this, LineInformationActivity.class);
                startActivity(intent);
            }
        });

    }

}

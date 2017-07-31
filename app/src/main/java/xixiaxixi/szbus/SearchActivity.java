package xixiaxixi.szbus;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);

        /**
         *   搜索栏  清除键
         */
        ivDeleteText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 搜索栏 搜索键
         */
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
//                Button newButton = new Button(SearchActivity.this);
//                newButton.setText("6666");
//                newButton.setTextSize(12);
//                linearLayout.addView(newButton);
                startSearch(etSearch.getText().toString());
            }
        });

        /**
         * 回车 搜索
         */
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event == null ? false : event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    startSearch(etSearch.getText().toString());
                }
                return true;
            }
        });

    }

    /**
     * 启动搜索
     */
    private void startSearch(String lineName) {
        lineName = lineName.trim();
        lineName = lineName.replaceAll("D", "路大");
        if (lineName.equals("")) {
            Toast.makeText(SearchActivity.this, "未输入", Toast.LENGTH_SHORT).show();
            return;
        }
        ((ShareDataApplication)getApplication()).strSearchLine=lineName;
        //start activity
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
            case R.id.action_about: {
                new AlertDialog.Builder(SearchActivity.this)
                        .setTitle(R.string.title_about_dialog)
                        .setMessage(R.string.content_about_dialog)
                        .setPositiveButton("哦", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
            case R.id.action_delete_history:{
                LineRecord.deleteAll(LineRecord.class, "type = ?", new String[]{"0"});
                ((LinearLayout) findViewById(R.id.llSearchHistory)).removeAllViews();
                return true;
            }
            case R.id.action_delete_favourites:{
                LineRecord.deleteAll(LineRecord.class, "type = ?", new String[]{"1"});
                ((LinearLayout) findViewById(R.id.llFavourites)).removeAllViews();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * 清理历史 啊啊啊 我也不知道该放在那里清理比较好
         */
        List<LineRecord> historyRecords = LineRecord.find(LineRecord.class, "type = ?", new String[]{"0"});
        if (historyRecords.size() > 5) {
            for (int i = 0, cc = historyRecords.size() - 5; i < cc; i++) {
                historyRecords.get(i).delete();
            }
        }

        /**
         * 显示历史 和 收藏
         */
        LinearLayout llHistory = (LinearLayout) findViewById(R.id.llSearchHistory);
        LinearLayout llFavourites = (LinearLayout) findViewById((R.id.llFavourites));
        llHistory.removeAllViews();
        llFavourites.removeAllViews();
        for (LineRecord record : LineRecord.listAll(LineRecord.class, "rowid desc")) {
            BusLineButton button = new BusLineButton(SearchActivity.this,
                    record.busNo + " " + record.fromTo,
                    record,
                    ((ShareDataApplication) getApplication()).lineChosen
            );
            if (record.type == 0) {
                llHistory.addView(button);
            } else {
                llFavourites.addView(button);
            }
        }
    }
}

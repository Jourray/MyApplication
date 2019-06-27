package com.example.textgittwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar mMytoolbar;
    private XRecyclerView mMyrec;
    private CheckBox mQuanxuan;
    /**
     * 全选
     */
    private TextView mQuanxuanTv;
    /**
     * 0.0
     */
    private TextView mYuan;
    private static final String TAG = "MainActivity";
    private ArrayList<DataBean> dataBeans;
    private MyAdapter myAdapter;
    private int math;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
                .build();
        okHttpClient.newCall(build)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Bean bean = new Gson().fromJson(string, Bean.class);
                        final List<DataBean> data = bean.getData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataBeans.addAll(data);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });


    }

    private void initView() {
        mMytoolbar = findViewById(R.id.mytoolbar);
        mMyrec = findViewById(R.id.myrec);
        mQuanxuan = findViewById(R.id.quanxuan);
        mQuanxuanTv = findViewById(R.id.quanxuan_tv);
        mYuan = findViewById(R.id.yuan);
        mMytoolbar.setTitle("");
        setSupportActionBar(mMytoolbar);
        mMyrec.setLayoutManager(new LinearLayoutManager(this));
        dataBeans = new ArrayList<>();
        myAdapter = new MyAdapter(this, dataBeans);
        mMyrec.setAdapter(myAdapter);
        myAdapter.setListener(new MyAdapter.OnClickListener() {
            @Override
            public void ClickItem(View v, int position) {
                DataBean dataBean = dataBeans.get(position);
                int num = dataBean.getNum();
                CheckBox cb = v.findViewById(R.id.item_check);
                if (cb.isChecked()) {
                    math += num;
                }
                mYuan.setText(math+"");
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}

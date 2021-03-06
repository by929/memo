package com.example.lenovo.at;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by L on 2016/12/13.
 */
public class AddInYours  extends AppCompatActivity {
    private EditText thing_customize;
    private EditText start_customize;
    private EditText cur_customize;
    private EditText end_customize;
    private EditText customized_remarks;
    private Intent intent;
    private Bundle bl;

    private ArrayList<ImageView> myImg = new ArrayList<ImageView>();
    private int icon = 1;
    private boolean hideIconIsChecked = false;
    private boolean hidePSIsChecked = false;
    private ImageView hideIcon;
    private ImageView hidePS;
    private ScrollView icon_view;
    private RelativeLayout chooseIcon;
    private RelativeLayout addPS;

    private static final String DB_NAME = "myDB.db";
    private static final String TABLE_NAME = "Memo";
    private static final int DB_VERSION = 1;
    private myDB mydb;
    private Menu mMenu;

    private static int CATEGORY_THIRD = 2;
    private static final String url = "http://172.18.69.108:8080";
    private String SynchronizeResult = "同步失败";
    private int userId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_in_yours);

        //获取用户ID
        SharedPreferences sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        //初始化控件
        initializeViews();
        //获取到上一个页面传过来的Intent
        intent = this.getIntent();
        //获取Intent中的Bundle数据
        bl = intent.getExtras();
        if (bl != null) {
            //thing_customize.setVisibility(View.GONE);
            start_customize.setText(bl.getString("start"));
            end_customize.setText(bl.getString("end"));
            customized_remarks.setText(bl.getString("remarks"));
            thing_customize.setText(bl.getString("thing"));
            thing_customize.setEnabled(false);
            cur_customize.setText(bl.getString("process"));
            icon = bl.getInt("icon");
        }
        myImg.get(icon - 1).setBackground(getResources().getDrawable(R.drawable.shadow));
    }

    /**
     * image点击事件监听器
     */
    private View.OnClickListener myImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myImg.get(icon - 1).setBackgroundColor(Color.TRANSPARENT);
            switch (v.getId()) {
                case R.id.img1: icon = 1; break;
                case R.id.img2: icon = 2; break;
                case R.id.img3: icon = 3; break;
                case R.id.img4: icon = 4; break;
                case R.id.img5: icon = 5; break;
                case R.id.img6: icon = 6; break;
                case R.id.img7: icon = 7; break;
                case R.id.img8: icon = 8; break;
                case R.id.img9: icon = 9; break;
                case R.id.img10: icon = 10; break;
                case R.id.img11: icon = 11; break;
                case R.id.img12: icon = 12; break;
                case R.id.img13: icon = 13; break;
                case R.id.img14: icon = 14; break;
                case R.id.img15: icon = 15; break;
                case R.id.img16: icon = 16; break;
                case R.id.img17: icon = 17; break;
                case R.id.img18: icon = 18; break;
                case R.id.img19: icon = 19; break;
                case R.id.img20: icon = 20; break;
                case R.id.img21: icon = 21; break;
                case R.id.img22: icon = 22; break;
                case R.id.img23: icon = 23; break;
                case R.id.img24: icon = 24; break;
                case R.id.img25: icon = 25; break;
                case R.id.img26: icon = 26; break;
                case R.id.img27: icon = 27; break;
                case R.id.img28: icon = 28; break;
            }
            v.setBackground(getResources().getDrawable(R.drawable.shadow));
        }
    };

    /**
     * 初始化控件和UI视图
     */
    private void initializeViews() {
        thing_customize = (EditText)findViewById(R.id.thing_customize);
        start_customize = (EditText)findViewById(R.id.start_customize);
        customized_remarks = (EditText) findViewById(R.id.customized_remarks);
        cur_customize = (EditText)findViewById(R.id.cur_customize);
        end_customize = (EditText)findViewById(R.id.end_customize);
        mydb = new myDB(this, DB_NAME, null, DB_VERSION);

        hideIcon = (ImageView) findViewById(R.id.hideIcon);
        hidePS = (ImageView) findViewById(R.id.hidePS);
        icon_view = (ScrollView) findViewById(R.id.icon_view);
        chooseIcon = (RelativeLayout) findViewById(R.id.chooseIcon);
        addPS = (RelativeLayout) findViewById(R.id.addPS);

        myImg.add((ImageView)findViewById(R.id.img1));
        myImg.add((ImageView)findViewById(R.id.img2));
        myImg.add((ImageView)findViewById(R.id.img3));
        myImg.add((ImageView)findViewById(R.id.img4));
        myImg.add((ImageView)findViewById(R.id.img5));
        myImg.add((ImageView)findViewById(R.id.img6));
        myImg.add((ImageView)findViewById(R.id.img7));
        myImg.add((ImageView)findViewById(R.id.img8));
        myImg.add((ImageView)findViewById(R.id.img9));
        myImg.add((ImageView)findViewById(R.id.img10));
        myImg.add((ImageView)findViewById(R.id.img11));
        myImg.add((ImageView)findViewById(R.id.img12));
        myImg.add((ImageView)findViewById(R.id.img13));
        myImg.add((ImageView)findViewById(R.id.img14));
        myImg.add((ImageView)findViewById(R.id.img15));
        myImg.add((ImageView)findViewById(R.id.img16));
        myImg.add((ImageView)findViewById(R.id.img17));
        myImg.add((ImageView)findViewById(R.id.img18));
        myImg.add((ImageView)findViewById(R.id.img19));
        myImg.add((ImageView)findViewById(R.id.img20));
        myImg.add((ImageView)findViewById(R.id.img21));
        myImg.add((ImageView)findViewById(R.id.img22));
        myImg.add((ImageView)findViewById(R.id.img23));
        myImg.add((ImageView)findViewById(R.id.img24));
        myImg.add((ImageView)findViewById(R.id.img25));
        myImg.add((ImageView)findViewById(R.id.img26));
        myImg.add((ImageView)findViewById(R.id.img27));
        myImg.add((ImageView)findViewById(R.id.img28));

        for (ImageView i : myImg)
            i.setOnClickListener(myImgListener);


        chooseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hideIconIsChecked) {
                    icon_view.setVisibility(View.GONE);
                    hideIcon.setImageResource(R.mipmap.triangle_right);
                    hideIconIsChecked = false;
                } else {
                    icon_view.setVisibility(View.VISIBLE);
                    hideIcon.setImageResource(R.mipmap.triangle_down);
                    hideIconIsChecked = true;
                    if (hidePSIsChecked) {
                        hidePSIsChecked = false;
                        customized_remarks.setVisibility(View.GONE);
                        hidePS.setImageResource(R.mipmap.triangle_right);
                    }
                }
            }
        });

        addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidePSIsChecked) {
                    customized_remarks.setVisibility(View.GONE);
                    hidePS.setImageResource(R.mipmap.triangle_right);
                    hidePSIsChecked = false;
                } else {
                    customized_remarks.setVisibility(View.VISIBLE);
                    hidePS.setImageResource(R.mipmap.triangle_down);
                    hidePSIsChecked = true;
                    if (hideIconIsChecked) {
                        icon_view.setVisibility(View.GONE);
                        hideIcon.setImageResource(R.mipmap.triangle_right);
                        hideIconIsChecked = false;
                    }
                }
            }
        });

    }

    // 分享
    public void share() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        String shareText = "事项名称：" + thing_customize.getText().toString() + "\n"
                +"开始时间：" + start_customize.getText().toString() + "\n"
                +"结束时间：" + end_customize.getText().toString() + "\n"
                +"备注：" + customized_remarks.getText().toString() + "\n"
                +"——这是一条来自Memo的分享";
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(sendIntent, "???"));
    }

    //处理标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        mMenu = menu;
        if (bl == null) {
            mMenu.findItem(R.id.share_menu).setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                if(bl != null) {
                    mydb.updateOneData(new Affair(bl.getString("thing"), Integer.parseInt(cur_customize.getText().toString()),
                            start_customize.getText().toString(), end_customize.getText().toString(),
                            CATEGORY_THIRD, icon, customized_remarks.getText().toString(), 0));
                    String response = AddEvent("/memo/user/changeEvent", thing_customize.getText().toString(),
                            start_customize.getText().toString(), end_customize.getText().toString(),
                            customized_remarks.getText().toString(), Integer.parseInt(cur_customize.getText().toString()),
                            CATEGORY_THIRD, icon, "0");
                    Toast.makeText(AddInYours.this, response, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    if (thing_customize.getText().toString().isEmpty()) {
                        Toast.makeText(AddInYours.this, "事件名称为空，请完善", Toast.LENGTH_LONG).show();
                    } else if (start_customize.getText().toString().isEmpty()
                            || end_customize.getText().toString().isEmpty()//contains("结束")
                            || cur_customize.getText().toString().isEmpty()/*.contains("现在")*/) {
                        Toast.makeText(AddInYours.this, "请输入对应数值", Toast.LENGTH_LONG).show();
                    } else if (Integer.valueOf(end_customize.getText().toString()) <= Integer.valueOf(start_customize.getText().toString())
                            || Integer.valueOf(end_customize.getText().toString()) < Integer.valueOf(cur_customize.getText().toString())
                            || Integer.valueOf(cur_customize.getText().toString()) < Integer.valueOf(start_customize.getText().toString())){
                        Toast.makeText(AddInYours.this, "输入数据不合理", Toast.LENGTH_LONG).show();
                    } else {
                        if (mydb.insertOneData(new Affair(thing_customize.getText().toString(), Integer.parseInt(cur_customize.getText().toString()),
                                start_customize.getText().toString(), end_customize.getText().toString(),
                                CATEGORY_THIRD, icon, customized_remarks.getText().toString(), 0))) {
                            String response = AddEvent("/memo/user/addEvent", thing_customize.getText().toString(), start_customize.getText().toString(),
                                    end_customize.getText().toString(), customized_remarks.getText().toString(),
                                    Integer.parseInt(cur_customize.getText().toString()),
                                    CATEGORY_THIRD, icon, "0");
                            Toast.makeText(AddInYours.this, response, Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AddInYours.this, "事件名称重复啦，请核查", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case R.id.share_menu:
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*JSON解析*/
    public void parserJSON (String response) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(response);
        String status = jsonObject.getString("resultCode");
        // 登录情况
        if (status.equals("1")) {
            SynchronizeResult = "同步成功";
        }
    }

    private String AddEvent(final String path, final String eventName, final String startTime, final String endTime,
                            final String content, final int process, final int category, final int icon, final String timeStamp) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    //HTTP请求操作
                    // 建立Http连接
                    String current_url = url + path;
                    connection = (HttpURLConnection) (new URL(current_url).openConnection());
                    // 设置访问方法和时间设置
                    connection.setRequestMethod("POST");          //设置以Post方式提交数据
                    connection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                    connection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setUseCaches(false);               //使用Post方式不能使用缓存
                    connection.connect();
                    PrintWriter out = new PrintWriter(connection.getOutputStream());
                    String message = "userId=" + userId + "&process=" + process + "&icon=" + icon +
                            "&category=" + category + "&eventName=" + eventName +
                            "&content=" + content + "&startTime=" + startTime +
                            "&endTime=" + endTime + "&timestamps=" + timeStamp;
                    out.print(message);  //在输出流中写入参数
                    out.flush();

                    StringBuilder response = new StringBuilder();
                    if(connection.getResponseCode() == 200){
                        // 网页获取json转换为字符串
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        parserJSON(response.toString());   //解析服务器返回的数据
                    }
                    System.out.println("服务器返回的结果是：" + response.toString());   //打印服务器返回的数据

                } catch (Exception e) {
                    // 抛出异常
                    e.printStackTrace();
                } finally {
                    // 关闭connection
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return SynchronizeResult;
    }
}

package com.example.lenovo.at;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 2016/12/8.
 */
public class AddDDL extends AppCompatActivity {
    private static final String DB_NAME = "myDB.db";
    private static final String TABLE_NAME = "Memo";
    private static final int DB_VERSION = 1;
    private myDB mydb;

    private static int CATEGORY_FIRST = 0;

    private EditText thing_ddl;
    private EditText ddl_remarks;
    private Button pick_startDate = null;
    private Button pick_endDate = null;
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

    private static final int START_DATAPICK = 0;
    private static final int START_DATE_DIALOG = 1;
    private static final int END_DATAPICK = 4;
    private static final int END_DATE_DIALOG = 5;

    private Calendar calendar = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;

    private Menu mMenu;
    private static final String url = "http://172.18.69.108:8080";
    private String SynchronizeResult = "同步失败";
    private int userId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ddl);

        //获取用户ID
        SharedPreferences sharedPreferences= getSharedPreferences("User", Context.MODE_PRIVATE);
        userId =sharedPreferences.getInt("userId", -1);

        //初始化控件
        initializeViews();
        //获取到上一个页面传过来的Intent
        intent = this.getIntent();
        //获取Intent中的Bundle数据
        bl = intent.getExtras();
        if (bl != null) {
            //thing_ddl.setVisibility(View.GONE);
            pick_startDate.setText(bl.getString("start"));
            pick_endDate.setText(bl.getString("end"));
            ddl_remarks.setText(bl.getString("remarks"));
            thing_ddl.setText(bl.getString("thing"));
            thing_ddl.setEnabled(false);
            icon = bl.getInt("icon");
        }
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
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

    public int differentDays(Date date1,Date date2) {
        return  (int)((date2.getTime() - date1.getTime()) / (1000*3600*24));
    }

    /**
     * 初始化控件和UI视图
     */
    private void initializeViews() {
        thing_ddl = (EditText) findViewById(R.id.thing_ddl);
        ddl_remarks = (EditText) findViewById(R.id.ddl_remarks);
        pick_startDate = (Button) findViewById(R.id.pick_startDate);
        pick_endDate = (Button) findViewById(R.id.pick_endDate);
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

        pick_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (pick_startDate.equals((Button) v)) {
                    msg.what = AddDDL.START_DATAPICK;
                }
                AddDDL.this.dateandtimeHandler.sendMessage(msg);
            }
        });

        pick_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (pick_endDate.equals((Button) v)) {
                    msg.what = AddDDL.END_DATAPICK;
                }
                AddDDL.this.dateandtimeHandler.sendMessage(msg);
            }
        });


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
                        ddl_remarks.setVisibility(View.GONE);
                        hidePS.setImageResource(R.mipmap.triangle_right);
                    }
                }
            }
        });

        addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hidePSIsChecked) {
                    ddl_remarks.setVisibility(View.GONE);
                    hidePS.setImageResource(R.mipmap.triangle_right);
                    hidePSIsChecked = false;
                } else {
                    ddl_remarks.setVisibility(View.VISIBLE);
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

    /**
     * 更新日期显示
     */
    private void StartDateDisplay(){
        pick_startDate.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener StartDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            StartDateDisplay();
        }
    };

    private void EndDateDisplay(){
        pick_endDate.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener EndDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            EndDateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG:
                return new DatePickerDialog(this, StartDateSetListener, mYear, mMonth, mDay);
            case END_DATE_DIALOG:
                return new DatePickerDialog(this, EndDateSetListener, mYear, mMonth, mDay);
            default:
                break;
        }

        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case START_DATE_DIALOG:
            case END_DATE_DIALOG:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
            default:
                break;
        }
    }

    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AddDDL.START_DATAPICK:
                    showDialog(START_DATE_DIALOG);
                    break;
                case AddDDL.END_DATAPICK:
                    showDialog(END_DATE_DIALOG);
                    break;
                default:
                    break;
            }
        }

    };

    public int CalculatePro() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ((pick_startDate.getText() != null) && (pick_endDate.getText() != null)) {
            try
            {
                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int month = calendar1.get(Calendar.MONTH) + 1;
                int date = calendar1.get(Calendar.DATE);
                String date_current = year + "-" + ((month + 1) < 10 ? "0" + month : month) + "-"
                        + ((date < 10) ? "0" + date : date) + " 00:00:00";
                Date date_start = format.parse(pick_startDate.getText().toString() + " 00:00:00");
                Date date_end = format.parse(pick_endDate.getText().toString() + " 00:00:00");
                Date date_cur = format.parse(date_current);
                return differentDays(date_start, date_cur) * 100 / differentDays(date_start, date_end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 分享
    public void share() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        String shareText = "事项名称：" + thing_ddl.getText().toString() + "\n"
                +"开始时间：" + pick_startDate.getText().toString() + "\n"
                +"结束时间：" + pick_endDate.getText().toString() + "\n"
                +"备注：" + ddl_remarks.getText().toString() + "\n"
                +"——这是一条来自Memo的分享";
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(sendIntent, "???"));
    }

    // 将string类型的日期改为绝对时间（时间戳
    long str2timeStamp(String date) {
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " 20:00"));
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
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
        // 从pick_endTime中获取的时间转为yyyy-MM-dd 20:00（前一天晚上八点）
        long timeStamp = str2timeStamp(pick_endDate.getText().toString());

        switch (item.getItemId()) {
            case R.id.add:
                if (bl != null) {
                    int pro = CalculatePro();

                    // 获取item
                    Cursor cursor = mydb.getTask(thing_ddl.getText().toString());
                    cursor.moveToNext();
                    // 旧的时间戳
                    long oldTimeStamp = cursor.getLong(6);
                    int ddl_code = (int) (oldTimeStamp / 1000); // 旧时间戳/1000 （id
                    String itemName = cursor.getString(0); // 名称
                    int icon_id = cursor.getInt(5); // icon id
                    // 旧的PendingIntent
                    PendingIntent ddl_operation = PendingIntent.getBroadcast(this,
                            ddl_code, new Intent(this, AlarmReceiver.class).
                                    putExtra("id", ddl_code).
                                    putExtra("thing_time", itemName).
                                    putExtra("icon_id", icon_id),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    // 取消旧的ddl_operation
                    am.cancel(ddl_operation);

                    // 创建新的ddl_operation
                    ddl_code = (int) (timeStamp / 1000);
                    ddl_operation = PendingIntent.getBroadcast(this, ddl_code,
                            new Intent(this, AlarmReceiver.class).
                                    putExtra("id", ddl_code).
                                    putExtra("thing_time", itemName).
                                    putExtra("icon_id", icon),
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar.setTimeInMillis(timeStamp);
                    // 设置AlarmManager在对应的时间启动Activity
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ddl_operation);

                    // 更新数据库
                    mydb.updateOneData(new Affair(bl.getString("thing"), pro, pick_startDate.getText().toString(),
                            pick_endDate.getText().toString(), CATEGORY_FIRST, icon, ddl_remarks.getText().toString(), timeStamp));
                    String response = AddEvent("/memo/user/changeEvent", thing_ddl.getText().toString(),
                            pick_startDate.getText().toString(), pick_endDate.getText().toString(),
                            ddl_remarks.getText().toString(), pro, CATEGORY_FIRST, icon, timeStamp+"");
                    Toast.makeText(AddDDL.this, response, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    if (thing_ddl.getText().toString().isEmpty()) {
                        Toast.makeText(AddDDL.this, "事件名称为空，请完善", Toast.LENGTH_LONG).show();
                    } else if (pick_endDate.getText().toString().contains("结束日期")
                            || pick_startDate.getText().toString().contains("开始日期")){
                        Toast.makeText(AddDDL.this, "请输入时间", Toast.LENGTH_LONG).show();
                    } else if (str2timeStamp(pick_endDate.getText().toString()) <= str2timeStamp(pick_startDate.getText().toString())){
                        Toast.makeText(AddDDL.this, "结束日期应在开始日期之后", Toast.LENGTH_LONG).show();
                    } else {
                        int pro = CalculatePro();
                        if (mydb.insertOneData(new Affair(thing_ddl.getText().toString(), pro, pick_startDate.getText().toString(),
                                pick_endDate.getText().toString(), CATEGORY_FIRST, icon, ddl_remarks.getText().toString(), timeStamp))) {

                            // 获取item
                            Cursor cursor = mydb.getTask(thing_ddl.getText().toString());
                            cursor.moveToNext();
                            String itemName = cursor.getString(0); // 获取名称
                            int icon_id = cursor.getInt(5); // 获取icon的值
                            int ddl_code = (int) (timeStamp / 1000); // 绝对时间/1000
                            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

                            PendingIntent ddl_operation = PendingIntent.getBroadcast(this, ddl_code,
                                    new Intent(this, AlarmReceiver.class).
                                            putExtra("id", ddl_code). // 绝对时间/1000作为id
                                            putExtra("thing_time", itemName). // 该事项名称
                                            putExtra("icon_id", icon_id), // 对应icon作为LargeIcon
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            calendar.setTimeInMillis(timeStamp);
                            // 设置AlarmManager在对应的时间启动Activity
                            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ddl_operation);
                            String response = AddEvent("/memo/user/addEvent", thing_ddl.getText().toString(),
                                    pick_startDate.getText().toString(), pick_endDate.getText().toString(),
                                    ddl_remarks.getText().toString(), pro, CATEGORY_FIRST, icon, timeStamp+"");
                            Toast.makeText(AddDDL.this, response, Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AddDDL.this, "事件名称重复啦，请核查", Toast.LENGTH_LONG).show();
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


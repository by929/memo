﻿package com.example.lenovo.at;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
    private static final String TABLE_NAME = "BirthNote";
    private static final int DB_VERSION = 1;
    private myDB mydb;

    private static int CATEGORY_FIRST = 0;

    private EditText thing_ddl;
    private Button pick_startDate = null;
    private Button pick_endDate = null;
    private Intent intent;
    private Bundle bl;

    private ArrayList<ImageView> myImg = new ArrayList<ImageView>();
    private int icon = 1;
    private boolean iconIsChecked = false;
    private ImageView hindIcon;
    private ScrollView icon_view;
    private RelativeLayout rela;

    private static final int START_DATAPICK = 0;
    private static final int START_DATE_DIALOG = 1;
    private static final int END_DATAPICK = 4;
    private static final int END_DATE_DIALOG = 5;


    private int mYear;
    private int mMonth;
    private int mDay;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ddl);
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
        pick_startDate = (Button) findViewById(R.id.pick_startDate);
        pick_endDate = (Button) findViewById(R.id.pick_endDate);
        mydb = new myDB(this, DB_NAME, null, DB_VERSION);

        hindIcon = (ImageView) findViewById(R.id.hindIcon);
        icon_view = (ScrollView) findViewById(R.id.icon_view);
        rela = (RelativeLayout) findViewById(R.id.rela);
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


        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iconIsChecked) {
                    icon_view.setVisibility(View.GONE);
                    hindIcon.setImageResource(R.mipmap.triangle_right);
                    iconIsChecked = false;
                } else {
                    icon_view.setVisibility(View.VISIBLE);
                    hindIcon.setImageResource(R.mipmap.triangle_down);
                    iconIsChecked = true;
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
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int date = calendar.get(Calendar.DATE);
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

    //处理标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                if (bl != null) {
                    int pro = CalculatePro();
                    mydb.updateOneData(new Affair(bl.getString("thing"), pro, pick_startDate.getText().toString(),
                            pick_endDate.getText().toString(), CATEGORY_FIRST, icon));
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
                                pick_endDate.getText().toString(), CATEGORY_FIRST, icon))) {
                            finish();
                        } else {
                            Toast.makeText(AddDDL.this, "事件名称重复啦，请核查", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

}

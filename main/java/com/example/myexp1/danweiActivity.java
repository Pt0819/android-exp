package com.example.myexp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class danweiActivity extends AppCompatActivity {

    RadioGroup radioGroup1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    Spinner spinner1;
    Spinner spinner2;
    List<String> list2 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();

    TextView editText1;
    TextView editText2;

    int firstChoice1 = 0;
    int secondChoice1 = 0;

    int CButton1 = 0;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danwei);

        radioGroup1 = (RadioGroup) findViewById(R.id.choice1);
        radioButton2 = (RadioButton) findViewById(R.id.radio2);
        radioButton3 = (RadioButton) findViewById(R.id.radio3);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        editText1 = (TextView) findViewById(R.id.edittext1);
        editText2 = (TextView) findViewById(R.id.edittext2);

        Button btn_six = (Button) findViewById(R.id.six);
        Button btn_seven = (Button) findViewById(R.id.seven);
        Button btn_eight = (Button) findViewById(R.id.eight);
        Button btn_nine = (Button) findViewById(R.id.nine);
        Button btn_two = (Button) findViewById(R.id.two);
        Button btn_three = (Button) findViewById(R.id.three);
        Button btn_four = (Button) findViewById(R.id.four);
        Button btn_five = (Button) findViewById(R.id.five);
        Button btn_one = (Button) findViewById(R.id.one);
        Button btn_zero = (Button) findViewById(R.id.zero);

        Button btn_changeTo = (Button) findViewById(R.id.changeTo);
        Button btn_c = (Button) findViewById(R.id.c);
        Button btn_delete = (Button) findViewById(R.id.delete);
        Button btn_point = (Button) findViewById(R.id.point);

        Button jisuan = (Button) findViewById(R.id.jisuan);
        Button jinzhi = (Button) findViewById(R.id.jinzhi);
        //进制转换和计算器页面的跳转
        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(danweiActivity.this , MainActivity.class);
                startActivity(intent1);
            }
        });
        jinzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(danweiActivity.this , OtherfuctionActivity.class);
                startActivity(intent2);
            }
        });

        //其余按键的设定监听器
        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + ".");
            }
        });
        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "0");
            }
        });
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "1");
            }
        });
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "2");
            }
        });
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "3");
            }
        });
        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "4");
            }
        });
        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "5");
            }
        });
        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "6");
            }
        });
        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "7");
            }
        });
        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "8");
            }
        });
        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(editText1.getText() + "9");
            }
        });

        btn_changeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CButton1 == 2){
                    editText2.setText(show2(firstChoice1,secondChoice1));

                }
                else if (CButton1 == 3){
                    editText2.setText(show3(firstChoice1,secondChoice1));

                }
            }
        });

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText("");
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                char[] cc = editText1.getText().toString().toCharArray();
                String ss = "";
                for(int i = 0;i<cc.length-1;i++){
                    ss = ss + cc[i];
                }
                editText1.setText(ss);
            }
        });

        list2.add("千米");
        list2.add("米");
        list2.add("厘米");
        list2.add("毫米");

        list3.add("立方米");
        list3.add("立方分米");
        list3.add("立方厘米");


        radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListenerImp());

        adapter = new ArrayAdapter<String>(danweiActivity.this, android.R.layout.simple_spinner_item, list2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);


        //OnItemSelectedListener用于捕捉下拉框内容
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                firstChoice1 = i;
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secondChoice1 = i;
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private class OnCheckedChangeListenerImp implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (radioButton2.getId() == checkedId) {
                CButton1 = 2;
                adapter = new ArrayAdapter<String>(danweiActivity.this, android.R.layout.simple_spinner_item, list2);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter);
                spinner2.setAdapter(adapter);

                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        firstChoice1 = i;
                        adapterView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        secondChoice1 = i;
                        adapterView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else {
                CButton1 = 3;
                adapter = new ArrayAdapter<String>(danweiActivity.this, android.R.layout.simple_spinner_item, list3);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter);
                spinner2.setAdapter(adapter);

                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        firstChoice1 = i;
                        adapterView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        secondChoice1 = i;
                        adapterView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

        }
    }

    public String show2(int f , int s) {
        Double d1 = Double.parseDouble(editText1.getText().toString());
        Double d2 = 0.0;

        if(f == 0){
            if(s == 0){
                d2 = 1.0;
            }
            else if(s == 1){
                d2 = 1000.0;
            }
            else if(s == 2){
                d2 = 1000000.0;
            }
            else {
                d2 = 1000000000.0;
            }
        }
        else  if(f == 1){
            if(s == 0){
                d2 = 0.001;
            }
            else if(s == 1){
                d2 = 1.0;
            }
            else if(s == 2){
                d2 = 1000.0;
            }
            else {
                d2 = 1000000.0;
            }
        }
        else if(f == 2){
            if(s == 0){
                d2 = 0.000001;
            }
            else if(s == 1){
                d2 = 0.001;
            }
            else if(s == 2){
                d2 = 1.0;
            }
            else {
                d2 = 1000.0;
            }
        }
        else {
            if(s == 0){
                d2 = 0.000000001;
            }
            else if(s == 1){
                d2 = 0.000001;
            }
            else if(s == 2){
                d2 = 0.001;
            }
            else {
                d2 = 1.0;
            }
        }

        return (d1 * d2 ) + "";
    }

    public String show3(int f,int s){
        Double d1 = Double.parseDouble(editText1.getText().toString());
        Double d2 = 0.0;
        if(f == 0){
            if (s == 0){
                d2 = 1.0;
            }
            else if(s == 1){
                d2 = 1000.0;
            }
            else {
                d2 = 1000000.0;
            }
        }
        else if(f == 1){
            if (s == 0){
                d2 = 0.001;
            }
            else if(s == 1){
                d2 = 1.0;
            }
            else {
                d2 = 1000.0;
            }
        }
        else {
            if (s == 0){
                d2 = 0.000001;
            }
            else if(s == 1){
                d2 = 0.001;
            }
            else {
                d2 = 1.0;
            }
        }
        return (d1 * d2 ) + "";
    }

}
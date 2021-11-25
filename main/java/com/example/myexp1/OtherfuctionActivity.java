package com.example.myexp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OtherfuctionActivity extends AppCompatActivity {


    private EditText edBinarySystem;
    private EditText edOctalSystem;
    private EditText edDecimalSystem;
    private EditText edHexSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherfuction);


        Button jinzhi = findViewById(R.id.convert);
        Button clear = findViewById(R.id.clear1);
        Button jisuan = findViewById(R.id.jisuanqi);
        Button danwei = findViewById(R.id.danwei);
        edBinarySystem = findViewById(R.id.edBinarySystem);
        edOctalSystem = findViewById(R.id.edOctalSystem);
        edDecimalSystem = findViewById(R.id.edDecimalSystem);
        edHexSystem = findViewById(R.id.edHexSystem);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edBinarySystem.setText("");
                edOctalSystem.setText("");
                edDecimalSystem.setText("");
                edHexSystem.setText("");
            }
        });

        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OtherfuctionActivity.this , MainActivity.class);
                startActivity(intent1);
            }
        });

        danwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(OtherfuctionActivity.this , danweiActivity.class);
                startActivity(intent2);
            }
        });

        jinzhi.setOnClickListener(actionMovies);

    }

    View.OnClickListener actionMovies = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String binary = edBinarySystem.getText().toString();
            String octal = edOctalSystem.getText().toString();
            String decimal = edDecimalSystem.getText().toString();
            String hex = edHexSystem.getText().toString();

            if (!binary.equals("")) {
                int _binary = Integer.valueOf(binary,2);
                edOctalSystem.setText(Integer.toOctalString(_binary));
                edDecimalSystem.setText("" + _binary);
                edHexSystem.setText(Integer.toHexString(_binary));
            } else if (!octal.equals("")) {
                int _octal = Integer.valueOf(octal,8);
                edBinarySystem.setText(Integer.toBinaryString(_octal));
                edDecimalSystem.setText("" + _octal);
                edHexSystem.setText(Integer.toHexString(_octal));
            } else if (!decimal.equals("")) {
                edBinarySystem.setText(Integer.toBinaryString(Integer.parseInt(decimal)));
                edOctalSystem.setText(Integer.toOctalString(Integer.parseInt(decimal)));
                edHexSystem.setText(Integer.toHexString(Integer.parseInt(decimal)));
            } else if (!hex.equals("")) {
                int _hex = Integer.valueOf(hex,16);
                edBinarySystem.setText(Integer.toBinaryString(_hex));
                edOctalSystem.setText(Integer.toOctalString(_hex));
                edDecimalSystem.setText("" + _hex);
            } else {
                Toast.makeText(OtherfuctionActivity.this,"输入为空",Toast.LENGTH_LONG);
            }
        }
    };

}
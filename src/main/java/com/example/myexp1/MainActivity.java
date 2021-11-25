package com.example.myexp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Button[] btn = new Button[10];
    private EditText input;             //显示器显示输出结果
    private TextView _drg;              //弧度角度提示框
    private TextView tips;

    private Button
            div, mul, sub, add, equal,                  //+,-,*,/,=
            log, ln, Sin, Cos, Tan,                     //函数
            sqrt, square, backspace,                  //开方,乘方,退格
            left_bracket, right_bracket, dot, exit, drg,         //(,),.,exit,drg
            MC, Clear;                          //清除存储,input清除

    private Button jinzhi, danwei;              //进制转换跳转，单位转换跳转

    //原式子和新式子
    public String str_old;
    public String str_new;
    //进行输入控制，true重新输入，false继续输入
    public boolean rebegin = true;

    //角度控制按键RAD，true角度，false弧度
    public boolean RAD_flag = true;

    //设定Π值为3.14
    public double pi = 4 * Math.atan(1);

    //判断是否是按=之后的输入，true表示输入在=之前，false表示输入在=之后
    public boolean equal_flag = true;

    //true表示正确，可以继续输入；false表示有误，输入被锁定
    public boolean tip_lock = true;

    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        //界面元素
        input = (EditText) findViewById(R.id.input);
        _drg = (TextView) findViewById(R.id.degradText);
        tips = (TextView)findViewById(R.id.tips);

        btn[0] = (Button) findViewById(R.id.zero_0);
        btn[1] = (Button) findViewById(R.id.one_1);
        btn[2] = (Button) findViewById(R.id.two_2);
        btn[3] = (Button) findViewById(R.id.three_3);
        btn[4] = (Button) findViewById(R.id.four_4);
        btn[5] = (Button) findViewById(R.id.five_5);
        btn[6] = (Button) findViewById(R.id.six_6);
        btn[7] = (Button) findViewById(R.id.seven_7);
        btn[8] = (Button) findViewById(R.id.eight_8);
        btn[9] = (Button) findViewById(R.id.nine_9);

        div = (Button) findViewById(R.id.div_chu);
        mul = (Button) findViewById(R.id.mul_cheng);
        sub = (Button) findViewById(R.id.sub_jian);
        add = (Button) findViewById(R.id.add_jia);
        equal = (Button) findViewById(R.id.equal_dengyu);

        Sin = (Button) findViewById(R.id.Sin);
        Cos = (Button) findViewById(R.id.Cos);
        Tan = (Button) findViewById(R.id.Tan);
        log = (Button) findViewById(R.id.log);
        ln = (Button) findViewById(R.id.ln);

        drg = (Button) findViewById(R.id.drgButton);
        sqrt = (Button) findViewById(R.id.sqrt_kaifang);
        square = (Button) findViewById(R.id.square_chengfang);

        backspace = (Button) findViewById(R.id.backspace);
        left_bracket = (Button) findViewById(R.id.left_bracket);
        right_bracket = (Button) findViewById(R.id.right_bracket);
        dot = (Button) findViewById(R.id.dot);
        exit = (Button) findViewById(R.id.exit);
        MC = (Button) findViewById(R.id.MC);
        Clear = (Button) findViewById(R.id.clear);

        //进制和单位转换
        jinzhi = (Button) findViewById(R.id.jinzhi);
        danwei = (Button) findViewById(R.id.danwei);

        //数字按键绑定监听器
        for (int i=0;i<10;i++) {
            btn[i].setOnClickListener(actionPerformed);
        }
        div.setOnClickListener(actionPerformed);
        mul.setOnClickListener(actionPerformed);
        add.setOnClickListener(actionPerformed);
        sub.setOnClickListener(actionPerformed);
        equal.setOnClickListener(actionPerformed);

        Sin.setOnClickListener(actionPerformed);
        Cos.setOnClickListener(actionPerformed);
        Tan.setOnClickListener(actionPerformed);
        log.setOnClickListener(actionPerformed);
        ln.setOnClickListener(actionPerformed);
        square.setOnClickListener(actionPerformed);
        sqrt.setOnClickListener(actionPerformed);

        backspace.setOnClickListener(actionPerformed);
        left_bracket.setOnClickListener(actionPerformed);
        right_bracket.setOnClickListener(actionPerformed);
        MC.setOnClickListener(actionPerformed);
        Clear.setOnClickListener(actionPerformed);
        dot.setOnClickListener(actionPerformed);
        drg.setOnClickListener(actionPerformed);
        exit.setOnClickListener(actionPerformed);

        //其他的功能按钮及其按键监听器
        jinzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this , OtherfuctionActivity.class);
                startActivity(intent1);
            }
        });

        danwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this , danweiActivity.class);
                startActivity(intent2);
            }
        });


    }

    //检测输入合法性
    String[] Tipcommand = new String[500];
    //检测输入Tipcommand指针
    int tip_i = 0;

    View.OnClickListener actionPerformed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //获取键盘上的命令
            String input_command = ((Button) view).getText().toString();
            //显示器上的字符串
            String string = input.getText().toString();
            //检测输入合法性
            if (equal_flag == false && "0123456789.()SinCosTanlogln+-*/√^".indexOf(input_command) != -1) {
                //检测显示器上的字符串是否违法,indexOf()方法，如果此字符串中无此字符则返回-1
                if (right(string)) {
                    if ("+-*/√^)".indexOf(input_command) != -1) {
                        for (int i = 0; i < string.length(); i++) {
                            //charAt(i)返回指定的第i个字符，方法从0开始
                            Tipcommand[tip_i] = String.valueOf(string.charAt(i));
                            tip_i++;
                        }
                        rebegin = false;
                    }
                } else {
                    input.setText("0");
                    rebegin = true;
                    tip_i = 0;
                    tip_lock = true;
                    tips.setText("欢迎使用多功能计算器2");
                }

                equal_flag = true;
            }
            if (tip_i > 0)
                TipCheck(Tipcommand[tip_i - 1], input_command);
            else if (tip_i == 0)
                TipCheck("#", input_command);
            if ("0123456789.()SinCosTanlogln+-*/√^".indexOf(input_command) != -1 && tip_lock) {
                Tipcommand[tip_i] = input_command;
                tip_i++;
            }
            //若输入正确，则将输入信息显示到显示器上
            if ("0123456789.()SinCosTanlogln+-*/√^".indexOf(input_command) != -1 && tip_lock) {
                //计算器的所有控件
                printf(input_command);
                //若果点击了“Radian”，则切换当前弧度角度制，并将切换后的结果显示到按键上方。
            } else if (input_command.compareTo("Rad") == 0 && tip_lock) {
                if (RAD_flag == true) {
                    RAD_flag = false;
                    _drg.setText("弧度");
                } else {
                    RAD_flag = true;
                    _drg.setText("角度");
                }
                //如果输入是退格键，并且是在按=之前
            } else if (input_command.compareTo("back") == 0 && equal_flag) {
                //在按键退格时，如果遇到函数如sin，cos，tan，log时删除3个字符
                if (bkspTTO(string) == 3) {
                    //字符数量大于3
                    if (string.length() > 3)
                        input.setText(string.substring(0, string.length() - 3));
                        //字符数量等于3
                    else if (string.length() == 3) {
                        input.setText("0");
                        rebegin = true;
                        tip_i = 0;
                        tips.setText("欢迎使用多功能计算器3");
                    }
                    //退格一次性删除两个字符
                } else if (bkspTTO(string) == 2) {
                    if (string.length() > 2)
                        input.setText(string.substring(0, string.length() - 2));
                    else if (string.length() == 2) {
                        input.setText("0");
                        rebegin = true;
                        tip_i = 0;
                        tips.setText("欢迎使用多功能计算器4");
                    }
                    //删除一个字符
                } else if (bkspTTO(string) == 1) {
                    //若之前输入的合法则删除一个字符
                    if (right(string)) {
                        if (string.length() > 1)
                            input.setText(string.substring(0, string.length() - 1));
                        else if (string.length() == 1) {
                            input.setText("0");
                            rebegin = true;
                            tip_i = 0;
                            tips.setText("欢迎使用多功能计算器5");
                        }
                        //若之前输入的字符串不合法则删除全部字符
                    } else {
                        input.setText("0");
                        rebegin = true;
                        tip_i = 0;
                        tips.setText("欢迎使用多功能计算器6");
                    }
                }
                if (input.getText().toString().compareTo("-") == 0 || equal_flag == false) {
                    input.setText("0");
                    rebegin = true;
                    tip_i = 0;
                    tips.setText("欢迎使用多功能计算器7");
                }
                tip_lock = true;
                if (tip_i > 0)
                    tip_i--;
                //如果是在按=之后输入退格键
            } else if (input_command.compareTo("back") == 0 && equal_flag == false) {
                //将显示器内容设置为0
                input.setText("0");
                rebegin = true;
                tip_i = 0;
                tip_lock = true;
                tips.setText("欢迎使用多功能计算器8");
                //如果输入的是清除键
            } else if (input_command.compareTo("C") == 0) {
                //将显示器内容设置为0
                input.setText("0");
                //重新输入标志置为true
                rebegin = true;
                //缓存命令位数清0
                tip_i = 0;
                //表明可以继续输入
                tip_lock = true;
                //表明输入=之前
                equal_flag = true;
                tips.setText("欢迎使用多功能计算器9");
            } else if (input_command.compareTo("exit") == 0) {      //如果按”exit“则退出程序
                System.exit(0);
                //如果输入的是=号，并且输入合法
            } else if (input_command.compareTo("=") == 0 && tip_lock && equal_flag && right(string)) {
                tip_i = 0;
                tip_lock = false;           //表明不可以继续输入
                equal_flag = false;        //表明输入=以后
                str_old = string;           //将式子保存原来算式样子

                //替换算式中的运算符，便于计算
                string = string.replaceAll("Sin", "S");
                string = string.replaceAll("Cos", "C");
                string = string.replaceAll("Tan", "T");
                string = string.replaceAll("log", "g");
                string = string.replaceAll("ln", "l");
                //重新输入标志设置true
                rebegin = true;
                //将-1x转换成-
                str_new = string.replaceAll("-", "-1×");
                //计算算式结果
                new calculation().process(str_new);
            }

            //表明可以继续输入
            tip_lock = true;
        }
    };
    //向input输出字符
    private void printf(String string) {
        //清屏后输出
        if(rebegin)
            input.setText(string);
            //在屏幕原str后增添字符
        else
            input.append(string);
        rebegin = false;
    }
    /*
     * 判断string的值是否合法，boolean方法
     * 包含所有控件以外的为非法的string，返回值为true
     * √^0123456789.()SinCosTanlogln/+-*是所有的合法字符
     * */
    private boolean right(String string) {
        int i = 0;
        for (i = 0;i < string.length();i++) {
            if(string.charAt(i)!='0' && string.charAt(i)!='1' && string.charAt(i)!='2' &&
                    string.charAt(i)!='3' && string.charAt(i)!='4' && string.charAt(i)!='5' &&
                    string.charAt(i)!='6' && string.charAt(i)!='7' && string.charAt(i)!='8' &&
                    string.charAt(i)!='9' && string.charAt(i)!='.' && string.charAt(i)!='-' &&
                    string.charAt(i)!='+' && string.charAt(i)!='*' && string.charAt(i)!='/' &&
                    string.charAt(i)!='√' && string.charAt(i)!='^' && string.charAt(i)!='s' &&
                    string.charAt(i)!='i' && string.charAt(i)!='n' && string.charAt(i)!='C' &&
                    string.charAt(i)!='o' && string.charAt(i)!='T' && string.charAt(i)!='a' &&
                    string.charAt(i)!='l' && string.charAt(i)!='g' && string.charAt(i)!='(' &&
                    string.charAt(i)!=')'&&string.charAt(i)!='S')
                break;
        }
        if (i == string.length()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 作为退格backspace按键的检测函数，bkspTTO()
     * 返回值为3，2，1，表示一次性删除的个数
     * 返回为3，表示是Sin，Cos，Tan,log中的一个函数，一次删除3个
     * 返回为2，表示为ln
     * 返回为1，其余的情况删除一个
     */
    private int bkspTTO(String str) {
        if ((str.charAt(str.length() - 1) == 'n' &&
                str.charAt(str.length() - 1) == 'i' &&
                str.charAt(str.length() - 1) == 'S')||
                (str.charAt(str.length() - 1) == 's' &&
                        str.charAt(str.length() - 1) == 'o' &&
                        str.charAt(str.length() - 1) == 'C') ||
                (str.charAt(str.length() - 1) == 'n' &&
                        str.charAt(str.length() - 1) == 'a' &&
                        str.charAt(str.length() - 1) == 'T') ||
                (str.charAt(str.length() - 1) == 'g' &&
                        str.charAt(str.length() - 1) == 'o' &&
                        str.charAt(str.length() - 1) == 'l')) {
            return 3;
        } else if ((str.charAt(str.length() - 1) == 'n' &&
                str.charAt(str.length() - 1) == 'l')) {
            return 2;
        } else return 1;
    }

    /*
     * 检测函数，对str进行前后语法检测
     * 为Tip的提示方式提供依据，与TipShow()配合使用
     *  编号 字符    其后可以跟随的合法字符
     *   1  （                 数字|（|-|.|函数
     *   2   ）                算符|）|√ ^
     *   3  .      数字|算符|）|√ ^
     *   4   数字        .|数字|算符|）|√ ^
     *   5   算符             数字|（|.|函数
     *   6 √ ^     （ |. | 数字
     *   7  函数           数字|（|.
     *
     * 小数点前后均可省略，表示0
     * 数字第一位可以为0
     */
    private void TipCheck(String tipcommand1,String tipcommand2) {
        //错误类型：Tipcode1，名词解释类型：Tipcode2
        int Tipcode1 = 0, Tipcode2 = 0;
        //表示命令类型
        int tiptype1 = 0, tiptype2 = 0;
        //括号数
        int bracket = 0;
        //“+-*/√^”不能作为第一位
        if (tipcommand1.compareTo("#") == 0 && (tipcommand2.compareTo("/") == 0 ||
                tipcommand2.compareTo("*") == 0 || tipcommand2.compareTo("+") == 0 ||
                tipcommand2.compareTo(")") == 0 || tipcommand2.compareTo("√") == 0 ||
                tipcommand2.compareTo("^") == 0)) {
            Tipcode1 = -1;
        }
        //定义存储字符串中最后一位的类型
        else if (tipcommand1.compareTo("#") != 0) {
            if (tipcommand1.compareTo("(") == 0) {
                tiptype1 = 1;
            } else if (tipcommand1.compareTo(")") == 0) {
                tiptype1 = 2;
            } else if (tipcommand1.compareTo(".") == 0) {
                tiptype1 = 3;
            } else if ("0123456789".indexOf(tipcommand1) != -1) {
                tiptype1 = 4;
            } else if ("+-*/".indexOf(tipcommand1) != -1) {
                tiptype1 = 5;
            } else if ("√^".indexOf(tipcommand1) != -1) {
                tiptype1 = 6;
            } else if ("SinCosTanlogln".indexOf(tipcommand1) != -1) {
                tiptype1 = 7;
            }
            //定义欲输入的按键类型
            if (tipcommand2.compareTo("(") == 0) {
                tiptype2 = 1;
            } else if (tipcommand2.compareTo(")") == 0) {
                tiptype2 = 2;
            } else if (tipcommand2.compareTo(".") == 0) {
                tiptype2 = 3;
            } else if ("0123456789".indexOf(tipcommand2) != -1) {
                tiptype2 = 4;
            } else if ("+-*/".indexOf(tipcommand2) != -1) {
                tiptype2 = 5;
            } else if ("√^".indexOf(tipcommand2) != -1) {
                tiptype2 = 6;
            } else if ("SinCosTanlogln".indexOf(tipcommand2) != -1) {
                tiptype2 = 7;
            }

            switch (tiptype1) {
                case 1:
                    //左括号后面直接接右括号,“+*/”（负号“-”不算）,或者"√^"
                    if (tiptype2 == 2 || (tiptype2 == 5 && tipcommand2.compareTo("-") != 0) ||
                            tiptype2 == 6)
                        Tipcode1 = 1;
                    break;
                case 2:
                    //右括号后面接左括号，数字，“+-*/sin^...”
                    if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7)
                        Tipcode1 = 2;
                    break;
                case 3:
                    //“.”后面接左括号或者“sincos...”
                    if (tiptype2 == 1 || tiptype2 == 7)
                        Tipcode1 = 3;
                    //连续输入两个“.”
                    if (tiptype2 == 3)
                        Tipcode1 = 8;
                    break;
                case 4:
                    //数字后面直接接左括号或者“sincos...”
                    if (tiptype2 == 1 || tiptype2 == 7)
                        Tipcode1 = 4;
                    break;
                case 5:
                    //“+-x÷”后面直接接右括号，“+-*/√^”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6)
                        Tipcode1 = 5;
                    break;
                case 6:
                    //“√^”后面直接接右括号，“+-*/√^”以及“sincos...”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7)
                        Tipcode1 = 6;
                    break;
                case 7:
                    //“sincos...”后面直接接右括号“+-*/√^”以及“sincos...”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7)
                        Tipcode1 = 7;
                    break;
            }
        }
        //检测小数点的重复性，Tipconde1=0,表明满足前面的规则
        if(Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {
            int tip_point = 0;
            for(int i = 0;i < tip_i;i++) {
                //若之前出现一个小数点点，则小数点计数加1
                if(Tipcommand[i].compareTo(".") == 0) {
                    tip_point++;
                }
                //若出现以下几个运算符之一，小数点计数清零
                if(Tipcommand[i].compareTo("Sin") == 0 || Tipcommand[i].compareTo("Cos") == 0 ||
                        Tipcommand[i].compareTo("Tan") == 0 || Tipcommand[i].compareTo("log") == 0 ||
                        Tipcommand[i].compareTo("ln") == 0 || Tipcommand[i].compareTo("√") == 0 ||
                        Tipcommand[i].compareTo("^") == 0 || Tipcommand[i].compareTo("/") == 0 ||
                        Tipcommand[i].compareTo("*") == 0 || Tipcommand[i].compareTo("-") == 0 ||
                        Tipcommand[i].compareTo("+") == 0 || Tipcommand[i].compareTo("(") == 0 ||
                        Tipcommand[i].compareTo(")") == 0 ) {
                    tip_point = 0;
                }
            }
            tip_point++;
            //若小数点计数大于1，表明小数点重复了
            if(tip_point > 1) {
                Tipcode1 = 8;
            }
        }
        //检测右括号是否匹配
        if(Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {
            int tip_right_bracket = 0;
            for(int i = 0;i < tip_i;i++) {
                //如果出现一个左括号，则计数加1
                if(Tipcommand[i].compareTo("(") == 0) {
                    tip_right_bracket++;
                }
                //如果出现一个右括号，则计数减1
                if(Tipcommand[i].compareTo(")") == 0) {
                    tip_right_bracket--;
                }
            }
            //如果右括号计数=0,表明没有响应的左括号与当前右括号匹配
            if(tip_right_bracket == 0) {
                Tipcode1 = 10;
            }
        }
        //检查输入=的合法性
        if(Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {
            //括号匹配数
            int tip_bracket = 0;
            for(int i = 0;i < tip_i;i++) {
                if(Tipcommand[i].compareTo("(") == 0) {
                    tip_bracket++;
                }
                if(Tipcommand[i].compareTo(")") == 0) {
                    tip_bracket--;
                }
            }
            //若大于0，表明左括号还有未匹配的
            if(tip_bracket > 0) {
                Tipcode1 = 9;
                bracket = tip_bracket;
            } else if(tip_bracket == 0) {
                //若前一个字符是以下之一，表明=号不合法
                if("√^SinCosTanlogln".indexOf(tipcommand1) != -1) {
                    Tipcode1 = 6;
                }
                //若前一个字符是以下之一，表明=号不合法
                if("+-*/".indexOf(tipcommand1) != -1) {
                    Tipcode1 = 5;
                }
            }
        }
        //若命令式以下之一，则显示相应的帮助信息
        if(tipcommand2.compareTo("MC") == 0) Tipcode2 = 1;
        if(tipcommand2.compareTo("Clear") == 0) Tipcode2 = 2;
        if(tipcommand2.compareTo("") == 0) Tipcode2 = 3;
        if(tipcommand2.compareTo("back") == 0) Tipcode2 = 4;
        if(tipcommand2.compareTo("Sin") == 0) Tipcode2 = 5;
        if(tipcommand2.compareTo("Cos") == 0) Tipcode2 = 6;
        if(tipcommand2.compareTo("Tan") == 0) Tipcode2 = 7;
        if(tipcommand2.compareTo("log") ==0) Tipcode2 = 8;
        if(tipcommand2.compareTo("ln") == 0) Tipcode2 = 9;
        if(tipcommand2.compareTo("√") == 0) Tipcode2 = 11;
        if(tipcommand2.compareTo("^") == 0) Tipcode2 = 12;
        //显示帮助和错误信息
        TipShow(bracket , Tipcode1 , Tipcode2 , tipcommand1 , tipcommand2);
    }

    /*
     * 反馈Tip信息，加强人机交互，与TipChecker()配合使用
     */
    private void TipShow(int bracket , int tipcode1 , int tipcode2 ,
                         String tipcommand1 , String tipcommand2) {
        String tipmessage = "";
        if(tipcode1 != 0)
            tip_lock = false;//表明输入有误
        switch(tipcode1) {
            case -1:
                tipmessage = tipcommand2 + "  不能作为第一个算符\n";
                break;
            case 1:
                tipmessage = tipcommand1 + "  后应输入：数字/(/./-/函数 \n";
                break;
            case 2:
                tipmessage = tipcommand1 + "  后应输入：)/算符 \n";
                break;
            case 3:
                tipmessage = tipcommand1 + "  后应输入：)/数字/算符 \n";
                break;
            case 4:
                tipmessage = tipcommand1 + "  后应输入：)/./数字 /算符 \n";
                break;
            case 5:
                tipmessage = tipcommand1 + "  后应输入：(/./数字/函数 \n";
                break;
            case 6:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 7:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 8:
                tipmessage = "小数点重复\n";
                break;
            case 9:
                tipmessage = "不能计算，缺少 "+ bracket +" 个 )";
                break;
            case 10:
                tipmessage = "不需要  )";
                break;
        }
        switch(tipcode2) {
            case 1:
                tipmessage = tipmessage + "[MC 用法: 清除记忆 MEM]";
                break;
            case 2:
                tipmessage = tipmessage + "[Clear 用法: 归零]";
                break;
            case 3:
                tipmessage = tipmessage + "[RAD 用法: 选择 DEG 或 RAD]";
                break;
            case 4:
                tipmessage = tipmessage + "[<- 用法: 退格]";
                break;
            case 5:
                tipmessage = tipmessage + "Sin 函数用法示例：\n" +
                        "DEG：sin30 = 0.5      RAD：sin1 = 0.84\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "sin(cos45)，而不是sincos45" ;
                break;
            case 6:
                tipmessage = tipmessage + "Cos 函数用法示例：\n" +
                        "DEG：cos60 = 0.5      RAD：cos1 = 0.54\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "cos(sin45)，而不是cossin45" ;
                break;
            case 7:
                tipmessage = tipmessage + "Tan 函数用法示例：\n" +
                        "DEG：tan45 = 1      RAD：tan1 = 1.55\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "tan(cos45)，而不是tancos45" ;
                break;
            case 8:
                tipmessage = tipmessage + "log 函数用法示例：\n" +
                        "log10 = log(5+5) = 1\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "log(tan45)，而不是logtan45" ;
                break;
            case 9:
                tipmessage = tipmessage + "ln 函数用法示例：\n" +
                        "ln10 = le(5+5) = 2.3   lne = 1\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "ln(tan45)，而不是lntan45" ;
                break;
            case 11:
                tipmessage = tipmessage + "√ 用法示例：开任意次根号\n" +
                        "如：27开3次根为  27√3 = 3\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "(函数)√(函数) ， (n!3)√(log100) = 2.45";
                break;
            case 12:
                tipmessage = tipmessage + "^ 用法示例：开任意次平方\n" +
                        "如：2的3次方为  2^3 = 8\n" +
                        "注：与其他函数一起使用时要加括号，如：\n" +
                        "(函数)√(函数) ， (n!3)^(log100) = 36";
                break;
        }
        //将提示信息显示到tip
        tips.setText(tipmessage);
    }
    /*
     * 整个计算核心，只要将表达式的整个字符串传入calculation().process()就可以实行计算了
     * 算法包括以下几部分：
     * 1、计算部分           process(String str)  当然，这是建立在查错无错误的情况下
     * 2、数据格式化      FP(double n)         使数据有相当的精确度
     * 3、阶乘算法           N(double n)          计算n!，将结果返回
     * 4、错误提示          showError(int code ,String str)  将错误返回
     */
    public class calculation {
        public calculation() {}
        final int MAXLEN = 500;
        /*
         * 计算表达式
         * 从左向右扫描，数字入number栈，运算符入operator栈
         * +-基本优先级为1，×÷基本优先级为2，log ln sin cos tan 基本优先级为3，√^基本优先级为4
         * 括号内层运算符比外层同级运算符优先级高4
         * 当前运算符优先级高于栈顶压栈，低于栈顶弹出一个运算符与两个数进行运算
         * 重复直到当前运算符大于栈顶
         * 扫描完后对剩下的运算符与数字依次计算
         */
        public void process(String str) {
            int weightPlus = 0, topOp = 0,
                    topNum = 0, flag = 1, weightTemp = 0;
            //weightPlus为同一（）下的基本优先级，weightTemp临时记录优先级的变化
            //topOp为weight[]，operator[]的计数器；topNum为number[]的计数器
            //flag为正负数的计数器，1为正数，-1为负数
            int weight[];  //保存operator栈中运算符的优先级，以topOp计数
            double number[];  //保存数字，以topNum计数
            char ch, ch_gai, operator[];//operator[]保存运算符，以topOp计数
            String num;//记录数字，str以+-×÷()sctgl!√^分段，+-×÷()sctgl!√^字符之间的字符串即为数字
            weight = new int[MAXLEN];
            number = new double[MAXLEN];
            operator = new char[MAXLEN];
            String expression = str;
            StringTokenizer expToken = new StringTokenizer(expression,"+-*/()SCTgl√^");
            int i = 0;
            while (i < expression.length()) {
                ch = expression.charAt(i);
                //判断正负数
                if (i == 0) {
                    if (ch == '-')
                        flag = -1;
                } else if(expression.charAt(i-1) == '(' && ch == '-')
                    flag = -1;
                //取得数字，并将正负符号转移给数字
                if (ch <= '9' && ch >= '0'|| ch == '.' || ch == 'E') {
                    num = expToken.nextToken();
                    ch_gai = ch;
                    Log.e("guojs",ch+"--->"+i);
                    //取得整个数字
                    while (i < expression.length() &&
                            (ch_gai <= '9' && ch_gai >= '0'|| ch_gai == '.' || ch_gai == 'E'))
                    {
                        ch_gai = expression.charAt(i++);
                        Log.e("guojs","i的值为："+i);
                    }
                    //将指针退回之前的位置
                    if (i >= expression.length()) i-=1; else {i-=2;}
                    if (num.compareTo(".") == 0) number[topNum++] = 0;
                        //将正负符号转移给数字
                    else {
                        number[topNum++] = Double.parseDouble(num)*flag;
                        flag = 1;
                    }
                }
                //计算运算符的优先级
                if (ch == '(') weightPlus+=4;
                if (ch == ')') weightPlus-=4;
                if (ch == '-' && flag == 1 || ch == '+' || ch == '*'|| ch == '/' ||
                        ch == 'S' ||ch == 'C' || ch == 'T' || ch == 'g' || ch == 'l' ||
                        ch == '√' || ch == '^') {
                    switch (ch) {
                        //+-的优先级最低，为1
                        case '+':
                        case '-':
                            weightTemp = 1 + weightPlus;
                            break;
                        //x÷的优先级稍高，为2
                        case '*':
                        case '/':
                            weightTemp = 2 + weightPlus;
                            break;
                        //sincos之类优先级为3
                        case 'S':
                        case 'C':
                        case 'T':
                        case 'g':
                        case 'l':
                            weightTemp = 3 + weightPlus;
                            break;
                        //其余优先级为4
                        //case '^':
                        //case '√':
                        default:
                            weightTemp = 4 + weightPlus;
                            break;
                    }
                    //如果当前优先级大于堆栈顶部元素，则直接入栈
                    if (topOp == 0 || weight[topOp-1] < weightTemp) {
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                        //否则将堆栈中运算符逐个取出，直到当前堆栈顶部运算符的优先级小于当前运算符
                    }else {
                        while (topOp > 0 && weight[topOp-1] >= weightTemp) {
                            switch (operator[topOp-1]) {
                                //取出数字数组的相应元素进行运算
                                case '+':
                                    number[topNum-2]+=number[topNum-1];
                                    break;
                                case '-':
                                    number[topNum-2]-=number[topNum-1];
                                    break;
                                case '*':
                                    number[topNum-2]*=number[topNum-1];
                                    break;
                                //判断除数为0的情况
                                case '/':
                                    if (number[topNum-1] == 0) {
                                        showError(1,str_old);
                                        return;
                                    }
                                    number[topNum-2]/=number[topNum-1];
                                    break;
                                case '√':
                                    if(number[topNum-1] == 0 || (number[topNum-2] < 0 &&
                                            number[topNum-1] % 2 == 0)) {
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-2] =
                                            Math.pow(number[topNum-2], 1/number[topNum-1]);
                                    break;
                                case '^':
                                    number[topNum-2] =
                                            Math.pow(number[topNum-2], number[topNum-1]);
                                    break;
                                //计算时进行角度弧度的判断及转换
                                //sin
                                case 'S':
                                    if(RAD_flag == true) {
                                        number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                                    } else {
                                        number[topNum-1] = Math.sin(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                //cos
                                case 'C':
                                    if(RAD_flag == true) {
                                        number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                                    } else {
                                        number[topNum-1] = Math.cos(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                //tan
                                case 'T':
                                    if(RAD_flag == true) {
                                        if((Math.abs(number[topNum-1])/90)%2 == 1) {
                                            showError(2,str_old);
                                            return;
                                        }
                                        number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                                    } else {
                                        if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {
                                            showError(2,str_old);
                                            return;
                                        }
                                        number[topNum-1] = Math.tan(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                //log
                                case 'g':
                                    if(number[topNum-1] <= 0) {
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-1] = Math.log10(number[topNum-1]);
                                    topNum++;
                                    break;
                                //ln
                                case 'l':
                                    if(number[topNum-1] <= 0) {
                                        showError(2,str_old);
                                        return;
                                    }
                                    number[topNum-1] = Math.log(number[topNum-1]);
                                    topNum++;
                                    break;
                            }
                            //继续取堆栈的下一个元素进行判断
                            topNum--;
                            topOp--;
                        }
                        //将运算符如堆栈
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                    }
                }
                i++;
            }
            //依次取出堆栈的运算符进行运算
            while (topOp>0) {
                //+-x直接将数组的后两位数取出运算
                switch (operator[topOp-1]) {
                    case '+':
                        number[topNum-2]+=number[topNum-1];
                        break;
                    case '-':
                        number[topNum-2]-=number[topNum-1];
                        break;
                    case '*':
                        number[topNum-2]*=number[topNum-1];
                        break;
                    //涉及到除法时要考虑除数不能为零的情况
                    case '/':
                        if (number[topNum-1] == 0) {
                            showError(1,str_old);
                            return;
                        }
                        number[topNum-2]/=number[topNum-1];
                        break;
                    case '√':
                        if(number[topNum-1] == 0 || (number[topNum-2] < 0 &&
                                number[topNum-1] % 2 == 0)) {
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-2] =
                                Math.pow(number[topNum-2], 1/number[topNum-1]);
                        break;
                    case '^':
                        number[topNum-2] =
                                Math.pow(number[topNum-2], number[topNum-1]);
                        break;
                    //sin
                    case 'S':
                        if(RAD_flag == true) {
                            number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                        } else {
                            number[topNum-1] = Math.sin(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    //cos
                    case 'C':
                        if(RAD_flag == true) {
                            number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                        } else {
                            number[topNum-1] = Math.cos(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    //tan
                    case 'T':
                        if(RAD_flag == true) {
                            if((Math.abs(number[topNum-1])/90)%2 == 1) {
                                showError(2,str_old);
                                return;
                            }
                            number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                        } else {
                            if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {
                                showError(2,str_old);
                                return;
                            }
                            number[topNum-1] = Math.tan(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    //对数log
                    case 'g':
                        if(number[topNum-1] <= 0) {
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-1] = Math.log10(number[topNum-1]);
                        topNum++;
                        break;
                    //自然对数ln
                    case 'l':
                        if(number[topNum-1] <= 0) {
                            showError(2,str_old);
                            return;
                        }
                        number[topNum-1] = Math.log(number[topNum-1]);
                        topNum++;
                        break;
                }
                //取堆栈下一个元素计算
                topNum--;
                topOp--;
            }
            //如果是数字太大，提示错误信息
            if (number[0] > 7.3E306) {
                showError(3,str_old);
                return;
            }
            //输出最终结果
            input.setText(String.valueOf(FP(number[0])));
            tips.setText("计算完毕，要继续请按归零键 Clear");
        }
        /*
         * FP = floating point 控制小数位数，达到精度
         * 否则会出现 0.6-0.2=0.39999999999999997的情况，用FP即可解决，使得数为0.4
         * 本格式精度为15位
         */
        public double FP(double n) {
            //NumberFormat format=NumberFormat.getInstance();  //创建一个格式化类f
            //format.setMaximumFractionDigits(18);    //设置小数位的格式
            DecimalFormat format = new DecimalFormat("0.#############");
            return Double.parseDouble(format.format(n));
        }
        /*
         * 错误提示，按了"="之后，若计算式在process()过程中，出现错误，则进行提示
         */
        public void showError(int code ,String str) {
            String message="";
            switch (code) {
                case 1:
                    message = "零不能作除数";
                    break;
                case 2:
                    message = "函数格式错误";
                    break;
                case 3:
                    message = "值太大了，超出范围";
            }
            input.setText("\""+str+"\""+": "+message);
            tips.setText(message+"\n"+"计算完毕，要继续请按归零键 Clear");
        }
    }

}
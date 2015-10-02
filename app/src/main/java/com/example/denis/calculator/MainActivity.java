package com.example.denis.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    Button btnDot;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btnClean;
    EditText tvResult;
    float num1 = 0;
    float num2 = 0;
    float result = 0;
    String oper = "";
    byte operPos = -1;
    HashSet<Character> hs;
    boolean showHint = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // находим элементы
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        tvResult = (EditText) findViewById(R.id.tvResult);
        btn0 = (Button) findViewById(R.id.btnZero);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnClean = (Button) findViewById(R.id.btnClean);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnClean.setOnClickListener(this);

        btnClean.setOnLongClickListener(new View.OnLongClickListener() {
            // удаление всего по долгому нажатию
            @Override
            public boolean onLongClick(View v) {
                tvResult.setText("");
                num1 = num2 = result = 0;
                oper = "";
                btnDot.setEnabled(true);
                return false;
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // удаление последнего символа
                if (showHint) {
                    Snackbar.make(v, "Удерживайте эту кнопку для полного удаления",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    showHint = false;
                }
                String s = tvResult.getText().toString();
                // если есть что удалять
                if (tvResult.length() != 0) {
                    // если последний символ - оператор     //tvResult.getText().toString() вместо s
                    Log.d("TAG", "I'm here");
                    if (hs.contains(s.charAt(tvResult.length() - 1))) {
                        num1 = 0;
                        tvResult.setText(s.substring(0, s.length() - 1));
                        oper = "";
                        // если после удаления оператора в строке есть точка
                        if (tvResult.getText().toString().contains(".")) {
                            btnDot.setEnabled(false);
                        } else {
                            btnDot.setEnabled(true);
                        }
                    }
                    // если последний символ - десятичная запятая
                    else if (s.charAt(tvResult.length() - 1) == '.') {
                        btnDot.setEnabled(true);
                        tvResult.setText(s.substring(0, s.length() - 1));
                    } else {
                        tvResult.setText(s.substring(0, s.length() - 1));
                    }
                }
            }
        });


        hs = new HashSet<>();
        hs.add('+');
        hs.add('-');
        hs.add('×');
        hs.add('/');

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.equals2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvResult.length() == 0) {
                    Snackbar.make(view, "Вы ничего не ввели!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                else {
                    num2 = Float.parseFloat(tvResult.getText().toString().substring(operPos + 1));
                    switch (oper) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "×":
                            result = num1 * num2;
                            break;
                        case "/":
                            result = num1 / num2;
                            break;
                    }
                    tvResult.setText(Float.toString(result));
                    btnDot.setEnabled(false);
                }
            }
        });
    }

    public boolean CheckFloatingPoint(String s, View v) {
        if (s.charAt(s.length() - 1) == '.') {
            Snackbar.make(v, "Вы не ввели десятичную часть!",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return false;
        } else if (hs.contains(s.charAt(s.length() - 1))) {
            Snackbar.make(v, "Вы уже ввели оператор!",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }

    public void AddOperator(String op, View v) {
        String s;
        s = tvResult.getText().toString();
        if (CheckFloatingPoint(s, v)) {
            num1 = Float.parseFloat(tvResult.getText().toString());
            oper = op;
            tvResult.append(op);
            operPos = (byte) s.length();
            btnDot.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                AddOperator("+", v);
                break;
            case R.id.btnSub:
                AddOperator("-", v);
                break;
            case R.id.btnMult:
                AddOperator("×", v);
                break;
            case R.id.btnDiv:
                AddOperator("/", v);
                break;
            case R.id.btn1:
                tvResult.append("1");
                break;
            case R.id.btn2:
                tvResult.append("2");
                break;
            case R.id.btn3:
                tvResult.append("3");
                break;
            case R.id.btn4:
                tvResult.append("4");
                break;
            case R.id.btn5:
                tvResult.append("5");
                break;
            case R.id.btn6:
                tvResult.append("6");
                break;
            case R.id.btn7:
                tvResult.append("7");
                break;
            case R.id.btn8:
                tvResult.append("8");
                break;
            case R.id.btn9:
                tvResult.append("9");
                break;
            case R.id.btnZero:
                tvResult.append("0");
                break;
            case R.id.btnDot:
                tvResult.append(".");
                btnDot.setEnabled(false);
                break;
            /*case R.id.btnClean:

                break;*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Вывод информации о приложении
     * @param item
     */
    public void about(MenuItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("О программе")
                .setMessage("Простой калькулятор, выполняющий 4 арифметических действия " +
                        "с десятичными дробями\n" +
                        "        © Denis Korobkov, 2015")
                .setIcon(R.drawable.icon_small)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

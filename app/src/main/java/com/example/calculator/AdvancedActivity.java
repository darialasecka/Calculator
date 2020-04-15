package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Character.isDigit;

public class AdvancedActivity extends AppCompatActivity {
    private String current_value = "0";
    private String expr = "0";
    private TextView result;
    private double rslt = 0;
    private TextView expression;
    private String operation = null;
    private String last_adv_operation = null;
    private boolean operation_made = false;
    private boolean commaAdded = false;
    private String error = "Error";
    private boolean cleared = false;

    private void addToResult(String value){
        if(current_value.equals("0") || current_value.equals(error)) current_value = value;
        else current_value += value;
        result.setText(current_value);
    }

    private void addToExpression(String value){
        if(expr.equals("0") || expr.equals(error)) expr = value;
        else expr += value;
        expression.setText(expr);
    }

    private void getNumber(View view){
        operation_made = false;
        cleared = false;
        Button button = (Button) view;
        String value = button.getText().toString();
        addToResult(value);
        addToExpression(value);

    }

    private void addComma(){
        cleared = false;
        if(!commaAdded){
            operation_made = false;
            if(current_value.equals("0") || current_value.equals(error)){
                addToResult("0.");
                addToExpression("0.");
            }
            else{
                addToResult(".");
                addToExpression(".");
            }
            commaAdded = true;
        }
    }

    private void changeSign(){
        if(current_value.startsWith("-")) {
            current_value = current_value.substring(1);
            if (expr.length() == current_value.length() + 1) expr = current_value;
            else expr = expr.substring(0, expr.length() - current_value.length() - 1) + current_value;
        }
        else {
            current_value = '-' + current_value;
            if(expr.length() == current_value.length() - 1) expr = current_value;
            else expr = expr.substring(0, expr.length() - current_value.length() + 1) + current_value;
        }
        result.setText(current_value);
        expression.setText(expr);
    }

    private void count(View view){
        Button button = (Button) view;
        cleared = false;


        if(operation_made){
            operation = button.getText().toString();
            expr = expr.substring(0, expr.length() - 1) + operation;
        }

        if(last_adv_operation != null){ //jak wykona się advanced operation to musi pobrać operacje
            operation = button.getText().toString();
            expr += operation;
            last_adv_operation = null;
        }
        System.out.println(rslt + " " + operation);

        if(isDigit(expr.charAt(expr.length() - 1))) {
            operation_made = true;

            double value = Double.parseDouble(current_value);

            if(operation == null)  rslt = value;
            else {

                if(operation.equals("+")) {
                    rslt += value;
                }
                else if(operation.equals("-")){
                    rslt -= value;
                }
                else if(operation.equals("*")){
                    rslt *= value;
                }
                else if(operation.equals("/")){
                    if(value == 0){
                        allClear();
                        result.setText(error);
                        return;
                    } else {
                        rslt /= value;
                    }
                }
            }

            last_adv_operation = null;
            current_value = "0";
            result.setText(current_value);

            //prev_operation = operation;
            operation = button.getText().toString();
            expr += operation;

            commaAdded = false;
        }
        expression.setText(expr);
    }
    
    private void countAdvanced(/*View view, */String adv_operation){
        cleared = false;
       /*if(view != null){
           Button button = (Button) view;
           adv_operation = button.getText().toString();
       }*/

        if(isDigit(expr.charAt(expr.length() - 1))) {
            double value = Double.parseDouble(current_value);

            //System.out.println(operation);

            if(adv_operation == "%") {
                //12+50% = 18 , 12-25% = 9, 12*25% = 3, 12/50% = 24,
                double prc = value * 0.01;
                if(operation == null)  rslt = prc;

                else if(operation.equals("+")){
                    rslt += rslt * prc;
                }
                else if(operation.equals("-")){
                    rslt -= rslt * prc;
                }
                else if(operation.equals("*")){
                    rslt *= prc;
                }
                else if(operation.equals("/")){
                    rslt /= prc;
                }
                expr += "%";
                last_adv_operation = "%";
            }
            else if(adv_operation.equals("sin")) {
                double sin = Math.toRadians(value);

                if(operation.equals("+")){
                    rslt += rslt * sin;
                }
                else if(operation.equals("-")){
                    rslt -= rslt * sin;
                }
                else if(operation.equals("*")){
                    rslt *= sin;
                }
                else if(operation.equals("/")){
                    rslt /= sin;
                }
                expr += "sin(" + value + ")";
                last_adv_operation = "sin";
            }


            current_value = "0";
            result.setText(current_value);


            expression.setText(expr);

            commaAdded = true; //right after percent, we don't want comma
        }
    }
    private void showResult(View view){
        if(last_adv_operation == "%") countAdvanced(/*null,*/ "%");
        //dołożyć do reszty
        else count(view);
        String temp = Double.toString(rslt);
        if(result.getText().equals(error)) temp = error;
        allClear();
        result.setText(temp);
    }

    private void allClear(){
        current_value = "0";
        expr = "0";
        rslt = 0;
        operation = null;
        last_adv_operation = null;
        operation_made = false;
        commaAdded = false;
        cleared = false;
        result.setText(current_value);
        expression.setText("");
    }

    private void clear(){
        if(cleared) allClear();
        else {
            current_value = current_value.substring(0, current_value.length() - 1);
            expr = expr.substring(0, expr.length() - 1);
            if(current_value.length() == 0) current_value = "0";
            result.setText(current_value);
            expression.setText(expr);
            cleared = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        result = findViewById(R.id.result);
        expression = findViewById(R.id.expression);

        //numbers
        final Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        final Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(view);
            }
        });

        //operations
        final Button pm = findViewById(R.id.pm_button);
        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSign();
            }
        });

        final Button plus = findViewById(R.id.plus_button);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count(view);
            }
        });

        final Button minus = findViewById(R.id.minus_button);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count(view);
            }
        });

        final Button multiply = findViewById(R.id.multiply_button);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count(view);
            }
        });

        final Button divide = findViewById(R.id.divide_button);
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count(view);
            }
        });

        final Button percent = findViewById(R.id.percent_button);
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countAdvanced("%");
            }
        });

        final Button sin = findViewById(R.id.sin_button);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countAdvanced("sin");
            }
        });

        final Button equals = findViewById(R.id.equals_button);
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResult(view);
            }
        });

        final Button ac = findViewById(R.id.ac_button);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allClear();
            }
        });

        final Button cce = findViewById(R.id.c_ce_button);
        cce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        final Button comma = findViewById(R.id.comma_button);
        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComma();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current_value", current_value);
        outState.putString("expr", expr);
        outState.putDouble("rslt", rslt);
        outState.putString("result", result.getText().toString());
        outState.putString("expression", expression.getText().toString());
        outState.putString("operation", operation);
        outState.putBoolean("operation_made", operation_made);
        outState.putBoolean("commaAdded", commaAdded);
        outState.putBoolean("cleared", cleared);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        current_value = savedInstanceState.getString("current_value");
        expr = savedInstanceState.getString("expr");
        rslt = savedInstanceState.getDouble("rslt");
        result.setText(savedInstanceState.getString("result"));
        expression.setText(savedInstanceState.getString("expression"));
        operation = savedInstanceState.getString("operation");
        operation_made = savedInstanceState.getBoolean("operation_made");
        commaAdded = savedInstanceState.getBoolean("commaAdded");
        cleared = savedInstanceState.getBoolean("cleared");
    }


}

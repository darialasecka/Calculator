package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Character.isDigit;

public class BasicActivity extends AppCompatActivity {
    private String current_value = "0";
    private String expr = "0";
    private TextView result;
    private double rslt = 0;
    private TextView expression;
    private String operation = new String();
    private boolean operation_made = false;

    private void addToResult(String value){
        if(current_value.equals("0")) current_value = value;
        else current_value += value;
        result.setText(current_value);
    }

    private void addToExpression(String value){
        if(expr.equals("0")) expr = value;
        else expr += value;
        expression.setText(expr);
    }

    private void getNumber(View view){
        operation_made = false;
        Button button = (Button) view;
        String value = button.getText().toString();
        addToResult(value);
        addToExpression(value);

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

        if(operation_made){
            operation = button.getText().toString();
            expr = expr.substring(0, expr.length() - 1) + operation;
        }
        if(isDigit(expr.charAt(expr.length() - 1))) {
            operation_made = true;
            
            if(operation == null) {
                rslt = Double.parseDouble(current_value);
                return;
            }

            if(operation.equals("+")) {
                rslt += Double.parseDouble(current_value);
            }
            else if(operation.equals("-")){
                rslt -= Double.parseDouble(current_value);
            }
            else if(operation.equals("*")){
                rslt *= Double.parseDouble(current_value);
            }

            operation = button.getText().toString();
            expr += operation;

            current_value = "0";
            result.setText(current_value);
        }
        expression.setText(expr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        result = findViewById(R.id.result);
        expression = findViewById(R.id.expression);


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
    }
}

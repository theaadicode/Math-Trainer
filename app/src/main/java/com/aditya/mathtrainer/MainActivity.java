package com.aditya.mathtrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int random_1,random_2,random_o1,random_o2,random_o3,answer;
    final String correct = "Correct :)";
    final String wrong = "Wrong :(";
    final int min = 3,max = 48;
    int total = 0, corr = 0;
    String operator;
    Button button;
    TextView question,option_one,option_two,option_three,option_four,result,track,timer;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.play_again);
        question = findViewById(R.id.question);
        option_one = findViewById(R.id.option_one);
        option_two = findViewById(R.id.option_two);
        option_three = findViewById(R.id.option_three);
        option_four = findViewById(R.id.option_four);
        result = findViewById(R.id.result);
        track = findViewById(R.id.tracker);
        timer = findViewById(R.id.timer);
        gridLayout = findViewById(R.id.gridLayout);

        Intent intent = getIntent();
        operator = intent.getStringExtra("operator");
        Log.i("operator",operator);
        questionGenerator();
        optionGenerator();

        final CountDownTimer countDownTimer =  new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            button.setVisibility(View.INVISIBLE);
            timer.setText(millisUntilFinished/1000 + "s");
            gameStart();
            }

            @Override
            public void onFinish() {
            button.setVisibility(View.VISIBLE);
            result.setText("Your Result is " + corr + " Out of " + total );
            result.setTextSize(35);
            gameStop();
            gridLayout.setVisibility(View.GONE);
            question.setVisibility(View.INVISIBLE);
            }
        };

        countDownTimer.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void questionGenerator()
    {
        random_1 = new Random().nextInt(max - min + 1) + 3;
        random_2 = new Random().nextInt(max - min + 1) + 3;
        String q = "";
        Log.i("opinq",operator);
        Log.i("Question","its "+q);
        switch (operator) {
            case "Addition (+)":
                q = random_1 + " + " + random_2;
                break;
            case "Subtraction (-)":
                q = random_1 + " - " + random_2;
                break;
            case "Multiplication (*)":
                q = random_1 + " X " + random_2;
                break;
            default:
                Log.i("This is","Default Case");
            }
        question.setText(q);
        Log.i("Question",q);

    }
    private void optionGenerator()
    {
        switch (operator) {
            case "Addition (+)":
                random_o1 = new Random().nextInt(2 * max - 2 * min + 1) + 6;
                random_o2 = new Random().nextInt(2 * max - 2 * min + 1) + 6;
                random_o3 = new Random().nextInt(2 * max - 2 * min + 1) + 6;
                answer = random_1 + random_2;
                break;
            case "Subtraction (-)":
                random_o1 = new Random().nextInt(2 * max - 2 * min + 1) - 45;
                random_o2 = new Random().nextInt(2 * max - 2 * min + 1) - 45;
                random_o3 = new Random().nextInt(2 * max - 2 * min + 1) - 45;
                answer = random_1 - random_2;
                break;
            case "Multiplication (*)":
                random_o1 = new Random().nextInt(max * max - min * min + 1) + 9;
                random_o2 = new Random().nextInt(max * max - min * min + 1) + 9;
                random_o3 = new Random().nextInt(max * max - min * min + 1) + 9;
                answer = random_1 * random_2;
                break;
        }

        int option = 4;

        ArrayList<Integer> options =new ArrayList<>(Arrays.asList(random_o1,random_o2,random_o3,answer));
        ArrayList<TextView> opt = new ArrayList<>(Arrays.asList(option_one,option_two,option_three,option_four));

        while(option != 0) {
                int ran = new Random().nextInt(option);
                int op_ran = new Random().nextInt(option);
                opt.get(op_ran).setText(String.valueOf(options.get(ran)));
                opt.remove(op_ran);
                options.remove(ran);
                option--;
        }
    }
    private void gameStart()
    {
        int child = gridLayout.getChildCount();
        for(int i=0;i<child;i++)
        {
            TextView textView = (TextView) gridLayout.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    TextView textView1 = findViewById(id);
                    int clickedAnswer = Integer.parseInt(textView1.getText().toString());
                    if(clickedAnswer == answer)
                    {
                        result.setText(correct);
                        result.setTextSize(45);
                        track.setText(++corr + "/" + ++total);
                    }
                    else{
                        result.setText(wrong);
                        track.setText(corr + "/" + ++total);
                        result.setTextSize(45);
                    }
                    questionGenerator();
                    optionGenerator();
                }
            });
        }
    }
    private void gameStop()
    {
        corr = 0;total = 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
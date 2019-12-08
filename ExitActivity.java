package com.example.recyclearquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExitActivity extends AppCompatActivity {
    private Button yes_button;
    private Button no_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        yes_button = (Button)findViewById(R.id.yes_button);
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartActivity();
            }
        });

        no_button = (Button)findViewById(R.id.no_button);
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuizActivity();
            }
        });
    }
    public void openStartActivity(){
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
    }

    public void openQuizActivity(){
        Intent intent = new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
}
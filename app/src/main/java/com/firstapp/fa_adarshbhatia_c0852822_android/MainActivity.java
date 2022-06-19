package com.firstapp.fa_adarshbhatia_c0852822_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*-----------Optimized for Google Pixel 5------------------*/

    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn1=findViewById(R.id.button);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view==btn1)
        {
            Intent intent = new Intent(this, AddNew.class);
            startActivity(intent);
        }
        else if(view==btn2)
        {
            Intent intent = new Intent(this, Visited.class);
            startActivity(intent);
        }
        else if(view==btn3)
        {
            Intent intent = new Intent(this, ToVisit.class);
            startActivity(intent);
        }
    }
}
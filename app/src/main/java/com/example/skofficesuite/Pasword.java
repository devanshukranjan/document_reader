package com.example.skofficesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Pasword extends AppCompatActivity {

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasword);
        final EditText pass=(EditText) findViewById(R.id.pass);
        Button button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s=pass.getEditableText().toString();
                Intent start = new Intent(getApplicationContext(),PDF_Reader.class);
                start.putExtra("",s);
                startActivity(start);
            }
        });
    }
}
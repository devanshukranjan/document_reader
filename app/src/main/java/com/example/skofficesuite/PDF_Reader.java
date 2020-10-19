package com.example.skofficesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDF_Reader extends AppCompatActivity {

   public String givenpas="23042002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f__reader);
        String pdfFile;
        pdfFile = getIntent().getStringExtra("PdfReader");

        File pdfFile1= new File(pdfFile);
        PDFView pdfView=findViewById(R.id.pdfView);
        pdfView.setVisibility(View.VISIBLE);
        pdfView.getCurrentPage();
        pdfView.getPageCount();
            pdfView.fromFile(pdfFile1).password(givenpas).load();
    }

}
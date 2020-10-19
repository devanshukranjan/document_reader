package com.example.skofficesuite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class openFile extends AppCompatActivity {

    public String givenpas="23042002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file);
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
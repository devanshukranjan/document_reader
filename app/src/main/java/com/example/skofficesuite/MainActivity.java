package com.example.skofficesuite;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Adaptor adaptor;
    private List<String> data = new ArrayList<>();
    public String pdfFile;
    public Toolbar toolbar;
    public Intent myFileIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for tool bar  V
       toolbar=findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        // go to 1 end
        final ListView lv =findViewById(R.id.lv);
        adaptor= new Adaptor();
        lv.setAdapter(adaptor);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pdfFile=new File(data.get(position)).toString();
                Log.v("dev====",pdfFile.toString());
                Intent start = new Intent(getApplicationContext(),PDF_Reader.class);
                start.putExtra("PdfReader",pdfFile);
                startActivity(start);
            }
        });

    }
    // 1/////////////////////..........................................
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.share){
            Toast.makeText(getApplicationContext(),"You Click Share",Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.about){
            Toast.makeText(getApplicationContext(),"You Click About",Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.exit){
            Toast.makeText(getApplicationContext(),"AUTHOR DEVANSHU.K.R",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if (id==R.id.search){
            Toast.makeText(getApplicationContext(),"You Click Search",Toast.LENGTH_SHORT).show();
        }
        else if (id==R.id.settings){
            Toast.makeText(getApplicationContext(),"You Click Settings",Toast.LENGTH_SHORT).show();
        }
        //elsif 5
        else if(id==R.id.open){
            Intent myFileIntent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            myFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            myFileIntent.setType("*/*");
            startActivityForResult(myFileIntent,10);
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:

                if (resultCode==RESULT_OK){
                    String pdfFile=data.getData().getPath().toString();
                    lodder(pdfFile);
                }
        }

    }
   public void lodder(String pdfFile){
       if (pdfFile.endsWith(".pdf")){
           data.add(pdfFile);
       }
       adaptor.setData(data);
       Intent start = new Intent(getApplicationContext(),PDF_Reader.class);
       start.putExtra("PdfReader",pdfFile);
       startActivity(start);
   }
    //eeeennnnnnnnndddddd................................................
    //this is for permission    V
//first
    private static final String[] PERMISSIONS={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSIONS_COUNT=2;
    private static final int REQUEST_PERMISSIONS=1234;

    @SuppressLint("NewApi")
    @Override
    protected void onResume(){
        super.onResume();
        if (notPermissions()){
            requestPermissions(PERMISSIONS,REQUEST_PERMISSIONS);
        }
        else{
            loadData();
        }
    }

    private boolean notPermissions(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionPtr=0;
            while (permissionPtr<PERMISSIONS_COUNT){
                if (checkSelfPermission(PERMISSIONS[permissionPtr])!= PackageManager.PERMISSION_GRANTED){
                    return true;
                }
                permissionPtr++;
            }
        }
        return false;
    }
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode==REQUEST_PERMISSIONS&&grantResults.length>0){
            if (notPermissions()){
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                recreate();
            }
            else{
                loadData();
            }
        }
    }
    //last

    //this is to find files of pdf    V
    //first
   private void loadData(){
        data.clear();
        File downloadsFolder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
       File[] files = downloadsFolder.listFiles();
       for (int i=0;i<files.length;i++){
           String filename=files[i].getAbsolutePath();
           if (filename.endsWith(".pdf")){
               data.add(filename);
           }
       }
       adaptor.setData(data);
    }
//last

    class Adaptor extends BaseAdapter{
        List<String> data = new ArrayList<>();

        void setData(List<String> mData){
            data.clear();
            data.addAll(mData);
            notifyDataSetChanged();
        }

        @Override
        public  int getCount(){
            return data.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView==null){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.item);
            textView.setText(data.get(position).substring(data.get(position).lastIndexOf('/')+1));

            return convertView;
        }
    }
}
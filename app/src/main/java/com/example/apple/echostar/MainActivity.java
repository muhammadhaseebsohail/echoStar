package com.example.apple.echostar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button image, video, pdf;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {

            Toast.makeText(this,
                    "Url-09090990",
                    Toast.LENGTH_LONG).show();
            ContentResolver contentResolver= getContentResolver();

            Uri VideoUrl = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Cursor Videocursor =contentResolver.query(VideoUrl,null,null,null,null);

            if(Videocursor !=null && Videocursor.moveToFirst()){

                int videoLocation=Videocursor.getColumnIndex(MediaStore.Video.Media.DATA);

                int no=Videocursor.getCount();
                int sum=0;
                String arr[]=new String[1000];
                arr[0]=Videocursor.getString(videoLocation);
                do{

                    //videoLocation

                    String UrlVideo=Videocursor.getString(videoLocation);
                    arr[sum]=UrlVideo;
//                    Toast.makeText(this,
//                            "Url-"+UrlVideo,
//                            Toast.LENGTH_LONG).show();
                    sum++;

                }while(Videocursor.moveToNext());

                if(sum==no)
                {
                    Intent ii=new Intent(MainActivity.this, VideoActivity.class);
                   ii.putExtra("VideoArr", arr);
                    startActivity(ii);
                    //startActivity( new Intent( this, VideoActivity.class ) );

                }

            }
            else{
                Toast.makeText(this,
                        "No Video Found ",
                        Toast.LENGTH_LONG).show();
            }

        }


    }

    public void LoadData(){



        ContentResolver contentResolver= getContentResolver();

        Uri VideoUrl = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor Videocursor =contentResolver.query(VideoUrl,null,null,null,null);

        if(Videocursor !=null && Videocursor.moveToFirst()){

            int videoLocation=Videocursor.getColumnIndex(MediaStore.Video.Media.DATA);

            int no=Videocursor.getCount();
            int sum=0;
            String arr[]=new String[1000];
            arr[0]=Videocursor.getString(videoLocation);
            do{

                //videoLocation

                String UrlVideo=Videocursor.getString(videoLocation);
                arr[sum]=UrlVideo;

                sum++;

            }while(Videocursor.moveToNext());

            if(sum==no)
            {
                Intent ii=new Intent(MainActivity.this, VideoActivity.class);
                ii.putExtra("VideoArr", arr);
                startActivity(ii);
                //startActivity( new Intent( this, VideoActivity.class ) );

            }

        }
        else{
            Toast.makeText(this,
                    "No Video Found ",
                    Toast.LENGTH_LONG).show();
        }



    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LoadData();


                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }



}
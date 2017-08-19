package com.example.ithunnathorn.iottest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    TextView tempData;
    TextView pirData;
    TextView statusData;
    ImageView statusImg;
    TextView bpmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempData = (TextView) findViewById(R.id.textViewTempData);
        pirData = (TextView) findViewById(R.id.textViewPIRStatus);
        statusData = (TextView) findViewById(R.id.textViewStatus);
        statusImg = (ImageView) findViewById(R.id.statusImageView);
        bpmData = (TextView) findViewById(R.id.textViewBPM);
        Glide.with(this).load(R.drawable.grandmacartoon).into(statusImg);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.child("temperature").getValue().toString();
                tempData.setText(temp);
                String status = dataSnapshot.child("Status").getValue().toString();
                statusData.setText(status);
                String stringIntStatus = dataSnapshot.child("intStatus").getValue().toString();
                int intStatus = Integer.parseInt(stringIntStatus);
                if (intStatus == 1) {
                    statusData.setTextColor(Color.parseColor("#FF0000"));
                    dialogBox();
                    statusImg.findViewById(R.drawable.grandmacartoon);
                    statusData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBox();
                        }
                    });
                }else if (intStatus == 0){
                    statusData.setTextColor(Color.parseColor("#00FF00"));
                    statusImg.findViewById(R.drawable.silverkey);

                }


                String stringTempStatus = dataSnapshot.child("TempStatus").getValue().toString();
                int tempStatus = Integer.parseInt(stringTempStatus);
                if (tempStatus == 1){
                    tempData.setTextColor(Color.parseColor("#FF0000"));
                }else if (tempStatus == 0){
                    tempData.setTextColor(Color.parseColor("#00FF00"));
                }
                String pir = dataSnapshot.child("PIRSensor").getValue().toString();
                pirData.setText(pir);
                String bpm = dataSnapshot.child("BPM").getValue().toString();
                bpmData.setText(bpm);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }

    public void dialogBox(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("โทรเบอร์ติดต่อด่วน !!!");
        alertDialog.setMessage("ติดต่อเบอร์นี้เพื่อทำการช่วยเหลือผู้สูงอายุ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "โทร",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = "+66903198808";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }

//    public void AddOnClick(View view) {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("PIRSensor");
//
//        myRef.setValue("Hello");
//    }
}

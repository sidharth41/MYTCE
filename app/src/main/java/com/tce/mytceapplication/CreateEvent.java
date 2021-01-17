package com.tce.mytceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEvent extends AppCompatActivity {
    TextInputEditText enamee,edetailse;
    DatePicker dp;
    String Day,Month,Year,enamefb,edetailsfb,edatefb;
    Button create;
    ImageView backbutton1;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        enamee=findViewById(R.id.Eventnameedit);
        edetailse=findViewById(R.id.Eventdetailsedit);
        dp=findViewById(R.id.datp);
        backbutton1=findViewById(R.id.backbutton);
        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEvent.this,Home.class);


                startActivity(intent);
            }
        });

        create=findViewById(R.id.createvent);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enamefb=enamee.getText().toString();
                edetailsfb=edetailse.getText().toString();
                Day = String.valueOf(dp.getDayOfMonth());
                Month = String.valueOf(dp.getMonth());
                int m=Integer.parseInt(Month);
                Year = String.valueOf(dp.getYear());
                edatefb=Day+" "+MONTHS[m]+" "+Year;
                FirebaseDatabase fbase=FirebaseDatabase.getInstance();
                DatabaseReference dbref=fbase.getReference("Event").child(enamefb);
                dbref.child("Eventname").setValue(enamefb);
                dbref.child("Eventdetails").setValue(edetailsfb);
                dbref.child("Eventdate").setValue(edatefb);
                Toast.makeText(CreateEvent.this, "Event Created Successfully", Toast.LENGTH_SHORT).show();



            }
        });








    }
}

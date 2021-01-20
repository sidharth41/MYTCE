package com.tce.mytceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateEvent extends AppCompatActivity {
    TextInputEditText enamee,edetailse;
    DatePicker dp;
    DatabaseReference dbref;
    AutoCompleteTextView time;
    String dept;
    String Day,Month,Year,enamefb,edetailsfb,edatefb,timetext;
    Button create;
    String[] Timearray = new String[] {"10:00 AM","11:00 AM","12:00 PM","1:00 PM","2:00 PM","3:00 PM","4:00 PM"};
    ImageView backbutton1;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        enamee=findViewById(R.id.Eventnameedit);
        edetailse=findViewById(R.id.Eventdetailsedit);
        dp=findViewById(R.id.datp);
        time=findViewById(R.id.Timeevent);
        FirebaseAuth fa=FirebaseAuth.getInstance();
        String useridfordept=fa.getCurrentUser().getUid();
        DatabaseReference deptref=FirebaseDatabase.getInstance().getReference("Members").child(useridfordept);
        deptref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dept=dataSnapshot.child("department").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_item,
                        Timearray);
        time.setAdapter(adapter);
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




                timetext=time.getText().toString();
                enamefb=enamee.getText().toString();
                edetailsfb=edetailse.getText().toString();
                Day = String.valueOf(dp.getDayOfMonth());
                Month = String.valueOf(dp.getMonth());
                int m=Integer.parseInt(Month);
                Year = String.valueOf(dp.getYear());
                edatefb=Day+" "+MONTHS[m]+" "+Year;
                FirebaseDatabase fbase=FirebaseDatabase.getInstance();
                dbref=fbase.getReference("Event");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edatefb+" "+timetext+" "+dept).exists()){
                            Toast.makeText(CreateEvent.this, "An event has been scheduled during this time", Toast.LENGTH_SHORT).show();

                        }
                        else{

                            dbref.child(edatefb+" "+timetext+" "+dept).child("Eventname").setValue(enamefb);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("Eventdetails").setValue(edetailsfb);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("Eventdate").setValue(edatefb);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("EventDepartment").setValue(dept);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("EventTime").setValue(timetext);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("date").setValue(Day);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("month").setValue(Month);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("year").setValue(Year);
                            dbref.child(edatefb+" "+timetext+" "+dept).child("ROOT").setValue(edatefb+" "+timetext+" "+dept);
                            Toast.makeText(CreateEvent.this, "Event Created Successfully", Toast.LENGTH_SHORT).show();



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });








    }
}

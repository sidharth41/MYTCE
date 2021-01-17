package com.tce.mytceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Orderfood extends AppCompatActivity {
    AutoCompleteTextView o1,o2,o3;
    int amount=0;

    String o1text,o2text,o3text;
    String tokennum,orders="",bill,uidorder,status;
    Button placeorder;
    String[] Foodnames = new String[] {"Tea","Coffee","Vada","Samosa","Parota","Idli","Dosa"};
    int[] Foodmoney=new int[]{10,10,5,10,25,20,30};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderfood);
        o1=findViewById(R.id.order1);
        o2=findViewById(R.id.order2);
        o3=findViewById(R.id.order3);
        uidorder= FirebaseAuth.getInstance().getUid();
        placeorder=findViewById(R.id.orderfood);
        ImageView back=findViewById(R.id.backbutton1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orderfood.this, Home.class);


                startActivity(intent);
            }
        });



        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_item,
                        Foodnames);
        o1.setAdapter(adapter);
        o2.setAdapter(adapter);
        o3.setAdapter(adapter);






        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                o1text=o1.getText().toString();
                o2text=o2.getText().toString();
                o3text=o3.getText().toString();
                orders="";


                if(o1text.isEmpty()&&o2text.isEmpty()&&o3text.isEmpty()){
                    Toast.makeText(Orderfood.this, "Select food to place an order", Toast.LENGTH_SHORT).show();
                }
                else {


                    if (!o1text.isEmpty()) {
                        int oi=0;
                        for(int i=0;i<Foodnames.length;i++){
                            if(Foodnames[i].equals(o1text)){
                                oi=i;
                                amount += Foodmoney[oi];
                                orders+=o1text;
                                break;
                            }
                        }



                    }
                    if (!o2text.isEmpty()) {
                        int oi=0;
                        for(int i=0;i<Foodnames.length;i++){
                            if(Foodnames[i].equals(o2text)){
                                oi=i;
                                amount += Foodmoney[oi];
                                orders+=" "+o2text;
                                break;
                            }
                        }

                    }
                    if (!o3text.isEmpty()) {
                        int oi=0;
                        for(int i=0;i<Foodnames.length;i++){
                            if(Foodnames[i].equals(o3text)){
                                oi=i;
                                amount += Foodmoney[oi];
                                orders+=" "+o3text;
                                break;
                            }
                        }

                    }
                    bill=String.valueOf(amount);
                    amount=0;


                    MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(Orderfood.this);
                    builder.setTitle("Order Food");

                    builder.setMessage("Your Bill amount is "+bill+" Do you want to place the order? ");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final DatabaseReference databaseReferencetoken=FirebaseDatabase.getInstance().getReference("FoodOrdertoken");
                           databaseReferencetoken.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   int m= Integer.parseInt(dataSnapshot.child("Tokeninitial").getValue().toString());
                                   tokennum=String.valueOf(m);
                                   databaseReferencetoken.child("Tokeninitial").setValue(m+1);
                                   DatabaseReference dbrefplaceorder= FirebaseDatabase.getInstance().getReference("FoodOrders").child(tokennum);
                                   dbrefplaceorder.child("Tokenno").setValue(tokennum);
                                       dbrefplaceorder.child("UID").setValue(uidorder);
                                   dbrefplaceorder.child("Bill").setValue(bill);
                                   dbrefplaceorder.child("Orders").setValue(orders);
                                   dbrefplaceorder.child("Status").setValue("Pending");
                                   DatabaseReference dbrefplaceorderuser= FirebaseDatabase.getInstance().getReference("Members").child(uidorder).child("Myorders").child(tokennum);
                                   dbrefplaceorderuser.child("Tokenno").setValue(tokennum);
                                   dbrefplaceorderuser.child("Bill").setValue(bill);
                                   dbrefplaceorderuser.child("Orders").setValue(orders);
                                   dbrefplaceorderuser.child("Status").setValue("Pending");
                                   Toast.makeText(Orderfood.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });










                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Orderfood.this, "You have cancelled", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();


                }



            }}
        );




    }


}

package com.tce.mytceapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FoodFragment extends Fragment {
    View viewfood;
    String uidfood;
    DatabaseReference databaseReferencefood;
    FloatingActionButton addorder;
    RecyclerView meRecyclerViewfood;
    LottieAnimationView animationView;
    public FoodFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewfood= inflater.inflate(R.layout.fragment_food ,container,false);
        addorder=(FloatingActionButton)viewfood.findViewById(R.id.fabfood);
        animationView=(LottieAnimationView)viewfood.findViewById(R.id.animationView);


        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Orderfood.class);


                startActivity(intent);

            }
        });


        meRecyclerViewfood=(RecyclerView)viewfood.findViewById(R.id.rvforfood);
        meRecyclerViewfood.setLayoutManager(new LinearLayoutManager(getContext()));



        return viewfood;

    }
    public void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuthfood=FirebaseAuth.getInstance();
        String uidfood1=firebaseAuthfood.getCurrentUser().getUid();
        FirebaseDatabase databasefood=FirebaseDatabase.getInstance();

        databaseReferencefood= FirebaseDatabase.getInstance().getReference("Members").child(uidfood1).child("Myorders");
        DatabaseReference dbranimation=FirebaseDatabase.getInstance().getReference("Members").child(uidfood1);
        dbranimation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Myorders")){
                    animationView.setVisibility(View.INVISIBLE);
                }
                else {
                    animationView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database1();





    }
    public void  database1(){ FirebaseRecyclerOptions<modelforfood> options=new FirebaseRecyclerOptions.Builder<modelforfood>()
            .setQuery(databaseReferencefood,modelforfood.class).build();
        FirebaseRecyclerAdapter<modelforfood,Notificationsfragmentvh> adapter=new FirebaseRecyclerAdapter<modelforfood, Notificationsfragmentvh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Notificationsfragmentvh notificationsfragmentvh, int i, @NonNull modelforfood modelfornoti1) {
                notificationsfragmentvh.setDetails(getContext(),modelfornoti1.getBill(),modelfornoti1.getOrders(),modelfornoti1.getStatus(),modelfornoti1.getTokenno());



            }

            @NonNull
            @Override
            public Notificationsfragmentvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.rowbill,parent,false);

                Notificationsfragmentvh notificationsfragmentvh=new Notificationsfragmentvh(view1);
                return notificationsfragmentvh;
            }
        };
        meRecyclerViewfood.setAdapter(adapter);
        adapter.startListening();










    }
    public  class Notificationsfragmentvh extends RecyclerView.ViewHolder{
        TextView tokennumberview,orderplacedview,orderstatusview,billamountview;
        Button OrderReceived;







        public Notificationsfragmentvh(@NonNull View itemView) {
            super(itemView);




            tokennumberview=itemView.findViewById(R.id.tokennumber);
            orderplacedview=itemView.findViewById(R.id.placedorder);
            orderstatusview=itemView.findViewById(R.id.orderstatus);
            billamountview=itemView.findViewById(R.id.billamount);
            OrderReceived=itemView.findViewById(R.id.Received_order);





        }

        public void setDetails(final Context ctx, final String Bill, final String Order, final String Status,final String Token)  {

            tokennumberview.setText("Token No : "+Token);
            orderplacedview.setText(Order);
            orderstatusview.setText("Order Status : "+Status);
            billamountview.setText("Bill Amount : ₹"+Bill);
            if(Status.equals("PAID")){
                OrderReceived.setVisibility(View.VISIBLE);
            }









            OrderReceived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth firebaseAuthfood1=FirebaseAuth.getInstance();
                    String uidfood12=firebaseAuthfood1.getCurrentUser().getUid();
                    DatabaseReference databaseReferencereceive= FirebaseDatabase.getInstance().getReference("Members").child(uidfood12).child("Myorders").child(Token);
                    databaseReferencereceive.removeValue();


                }
            });










        }

    }

}

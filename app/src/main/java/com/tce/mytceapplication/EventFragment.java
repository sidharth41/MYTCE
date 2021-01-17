package com.tce.mytceapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;


public class EventFragment extends Fragment {
    View eview;
    DatabaseReference databaseReference;
    FloatingActionButton addevent;
    private  RecyclerView meRecyclerView;
    public EventFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eview= inflater.inflate(R.layout.fragment_event,container,false);
        addevent=(FloatingActionButton)eview.findViewById(R.id.fab);


        meRecyclerView=(RecyclerView)eview.findViewById(R.id.rvforevents);
        meRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAuth firebaseAuth1=FirebaseAuth.getInstance();
        String uid=firebaseAuth1.getCurrentUser().getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dbref=database.getReference("Members").child(uid);
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("role").getValue().equals("Faculty")){
                    addevent.setVisibility(View.VISIBLE);

                }
                else if(dataSnapshot.child("role").getValue().equals("Student")){
                    addevent.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CreateEvent.class);


                startActivity(intent);

            }
        });








        return eview;
    }
    public void onStart() {
        super.onStart();
        databaseReference= FirebaseDatabase.getInstance().getReference("Event");
         database();





    }
    public void  database(){ FirebaseRecyclerOptions<modelforevent> options=new FirebaseRecyclerOptions.Builder<modelforevent>()
            .setQuery(databaseReference,modelforevent.class).build();
        FirebaseRecyclerAdapter<modelforevent,Notificationsfragmentvh> adapter=new FirebaseRecyclerAdapter<modelforevent, Notificationsfragmentvh>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Notificationsfragmentvh notificationsfragmentvh, int i, @NonNull modelforevent modelfornoti1) {
                notificationsfragmentvh.setDetails(getContext(),modelfornoti1.getEventname(),modelfornoti1.getEventdate(),modelfornoti1.getEventdetails());



            }

            @NonNull
            @Override
            public Notificationsfragmentvh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rowevent,parent,false);

                Notificationsfragmentvh notificationsfragmentvh=   new Notificationsfragmentvh(view);
                return notificationsfragmentvh;
            }
        };
        meRecyclerView.setAdapter(adapter);
        adapter.startListening();










    }
    public  class Notificationsfragmentvh extends RecyclerView.ViewHolder{
        TextView nameview,detailsview,dateview,calopen;
        ImageView shareev;







        public Notificationsfragmentvh(@NonNull View itemView) {
            super(itemView);




            nameview=itemView.findViewById(R.id.nameevent);
            dateview=itemView.findViewById(R.id.eventtime);
            detailsview=itemView.findViewById(R.id.eventdetails);
            calopen=itemView.findViewById(R.id.calendaropen);
            shareev=itemView.findViewById(R.id.shareev);





        }
        @SuppressLint("ResourceAsColor")
        public void setDetails(final Context ctx, final String name, final String date, final String details)  {
            nameview.setText(name);
            dateview.setText(date);
            detailsview.setText(details);









            calopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", false);
                    intent.putExtra("rrule", "FREQ=DAILY");

                    intent.putExtra("title",name);
                    startActivity(intent);

                }
            });
            shareev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");

                    whatsappIntent.putExtra(Intent.EXTRA_TEXT,name+"\n"+"On "+date+" "+"\n"+details );
                    startActivity(Intent.createChooser(whatsappIntent, "Share using"));

                }
            });










        }

    }
}

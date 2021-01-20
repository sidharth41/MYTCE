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
    String year1,month1,date1;
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
                notificationsfragmentvh.setDetails(getContext(),modelfornoti1.getEventname(),modelfornoti1.getEventdate(),modelfornoti1.getEventdetails(),modelfornoti1.getEventTime(),modelfornoti1.getEventDepartment(),modelfornoti1.getDate(),modelfornoti1.getMonth(),modelfornoti1.getYear(),modelfornoti1.getROOT());



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
        TextView nameview,detailsview,dateview,calopen,tmvw,dpmtvw;
        ImageView shareev;







        public Notificationsfragmentvh(@NonNull View itemView) {
            super(itemView);




            nameview=itemView.findViewById(R.id.nameevent);
            dateview=itemView.findViewById(R.id.eventtime);
            detailsview=itemView.findViewById(R.id.eventdetails);
            tmvw=itemView.findViewById(R.id.timeev);
            dpmtvw=itemView.findViewById(R.id.deptevent);
            calopen=itemView.findViewById(R.id.calendaropen);
            shareev=itemView.findViewById(R.id.shareev);





        }
        @SuppressLint("ResourceAsColor")
        public void setDetails(final Context ctx, final String name, final String date, final String details,final String time,final String dept,final String date2,final String month2,final String year2,final String root)  {

            int yr= Calendar.getInstance().get(Calendar.YEAR);
            int mnt= Calendar.getInstance().get(Calendar.MONTH)+1;
            int dt= Calendar.getInstance().get(Calendar.DATE);
            Toast.makeText(ctx, " "+yr+" "+mnt+" "+dt, Toast.LENGTH_SHORT).show();
            int dt1=Integer.parseInt(date2);
            int mnt1=Integer.parseInt(month2)+1;
            int yr1=Integer.parseInt(year2);
            DatabaseReference deleteevent=FirebaseDatabase.getInstance().getReference("Event");

            if(yr>yr1){
                deleteevent.child(root).removeValue();
            }
            else if(yr==yr1) {

                if(mnt>mnt1){
                    deleteevent.child(root).removeValue();

                }
                else if(mnt==mnt1){
                    if(dt>dt1){
                        deleteevent.child(root).removeValue();
                    }
                }
            }






            nameview.setText(name);
            dateview.setText(date);
            detailsview.setText(details);
            tmvw.setText(time);
            dpmtvw.setText(dept);









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

                    whatsappIntent.putExtra(Intent.EXTRA_TEXT,name+"\n"+"On "+date+" "+"\n"+details+" Time: "+time+" Department: "+dept);
                    startActivity(Intent.createChooser(whatsappIntent, "Share using"));

                }
            });










        }

    }
}

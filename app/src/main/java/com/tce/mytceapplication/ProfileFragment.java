package com.tce.mytceapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextInputEditText namepr,deppr,yearpr,emailidpr,rolepr;
    String uid;
    DatabaseReference dbreference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View vw= inflater.inflate(R.layout.fragment_profile,container,false);

        namepr=(TextInputEditText)vw.findViewById(R.id.nameprofile);
        deppr=(TextInputEditText)vw.findViewById(R.id.departmentprofile);
        yearpr=(TextInputEditText)vw.findViewById(R.id.yearprofile);
        emailidpr=(TextInputEditText)vw.findViewById(R.id.emailprofile);
        rolepr=(TextInputEditText)vw.findViewById(R.id.roleprofile);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbreference= FirebaseDatabase.getInstance().getReference("Members").child(uid);

        dbreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String namep,depp,yearp,emailp,rolep;
                namep=dataSnapshot.child("name").getValue().toString();
                emailp=dataSnapshot.child("email").getValue().toString();
                yearp=dataSnapshot.child("year").getValue().toString();
                depp=dataSnapshot.child("department").getValue().toString();
                rolep=dataSnapshot.child("role").getValue().toString();
                namepr.setText(namep);
                emailidpr.setText(emailp);
                yearpr.setText(yearp);
                deppr.setText(depp);
                rolepr.setText(rolep);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








       return vw;
    }
}

package com.tce.mytceapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {

    ImageView Itnav,Civilnav;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewmap= inflater.inflate(R.layout.fragment_map,container,false);
        Itnav=(ImageView)viewmap.findViewById(R.id.NavigateIT);
        Civilnav=(ImageView)viewmap.findViewById(R.id.NavigateCIVIL);
        Itnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=9.882548376266229, 78.08356460782605&mode=1"));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });
        Civilnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=9.882213, 78.081113&mode=1"));
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });



        return viewmap;
    }
}

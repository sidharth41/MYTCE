package com.tce.mytceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextInputEditText Name,Emailaddress,Password;
    AutoCompleteTextView Department,Year;
    Button Register;
    ProgressBar Pbar;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        firebaseAuth=FirebaseAuth.getInstance();
        Name=(TextInputEditText) findViewById(R.id.Name);
        Name=(TextInputEditText) findViewById(R.id.password);
        Emailaddress=(TextInputEditText) findViewById(R.id.email);
        Password=(TextInputEditText) findViewById(R.id.password);
        Year=(AutoCompleteTextView) findViewById(R.id.year1);
        Department=(AutoCompleteTextView) findViewById(R.id.department1);
        Register=(Button)findViewById(R.id.register);
        String[] Departmentnames = new String[] {"IT","CSE","CIVIL","MECHANICAL","ECE","EEE","DATA SCIENCE"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_item,
                        Departmentnames);
        Department.setAdapter(adapter);
        String[] Yearlist = new String[] {"I","II","III","IV"};
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_item,
                        Yearlist);
        Year.setAdapter(adapter1);
        Pbar=findViewById(R.id.progressBardetailspage);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference=firebaseDatabase.getInstance().getReference();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                final String mEmail = Emailaddress.getText().toString().trim();
                String password = Password.getText().toString().trim();
                final String fullName = Name.getText().toString();
                final String mDepartment1 =Department.getText().toString();
                final String mYearm =Year.getText().toString();
                final String role;

                if(mEmail.endsWith("student.tce.edu")){
                    role="Student";

                }
                else if(mEmail.endsWith("tce.edu")){
                    role="Faculty";
                }
                else{
                    Toast.makeText(MainActivity.this, "You are not a member of TCE", Toast.LENGTH_SHORT).show();
                    role=null;
                }





                if(TextUtils.isEmpty(mEmail)||TextUtils.isEmpty(password)||password.length() < 6||role==null){

                    Toast.makeText(MainActivity.this, "Enter proper Credentials", Toast.LENGTH_SHORT).show();
                }
                else{



                    Pbar.setVisibility(View.VISIBLE);



                    firebaseAuth.createUserWithEmailAndPassword(mEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Registermember registerMember=new Registermember();
                                registerMember.setName(fullName);
                                registerMember.setDepartment(mDepartment1);
                                registerMember.setYear(mYearm);
                                registerMember.setRole(role);
                                registerMember.setEmail(mEmail);
                                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();


                                databaseReference.child("Members").child(uid).setValue(registerMember);

                                Pbar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,Home.class);


                                startActivity(intent);







                            }
                            else {
                                Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Pbar.setVisibility(View.GONE);
                            }
                        }
                    });






                }
            }
        });















    }
}

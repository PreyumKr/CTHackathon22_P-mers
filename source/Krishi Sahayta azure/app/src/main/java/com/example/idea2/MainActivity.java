package com.example.idea2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button b1;
    TextView reg;
    EditText name;
    EditText mail;
    EditText p;
    EditText cp;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://krishi-sahayta-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        name=findViewById(R.id.textView);
        mail=findViewById(R.id.textView2);
        p=findViewById(R.id.textView3);
        cp=findViewById(R.id.textView4);
        b1=findViewById(R.id.button);
        reg=findViewById(R.id.textView6);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final String nam=name.getText().toString();
               final String email=mail.getText().toString();
               final String pass=p.getText().toString();
               final String conf=cp.getText().toString();

               if(nam.isEmpty()||email.isEmpty()||pass.isEmpty()||conf.isEmpty()){
                   Toast.makeText(MainActivity.this, "Enter al the fields", Toast.LENGTH_SHORT).show();
               }
               else if(!pass.equals(conf)){
                   Toast.makeText(MainActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
               }
               else{

                   databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.hasChild(email)){
                               Toast.makeText(MainActivity.this, "This mail is already registered", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               databaseReference.child("users").child(email).child("name").setValue(nam);
                               databaseReference.child("users").child(email).child("password").setValue(pass);
                               Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(MainActivity.this,login.class);
                               startActivity(intent);
                           }
                       }
                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                       }
                   });
               }

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
    }
}
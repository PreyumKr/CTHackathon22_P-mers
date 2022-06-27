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

public class login extends AppCompatActivity {
    Button b2;
    TextView log;
    EditText email;
    EditText pass;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://krishi-sahayta-default-rtdb.firebaseio.com/");




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b2=findViewById(R.id.button2);
        log=findViewById(R.id.textView6);
        email=findViewById(R.id.textView7);
        pass=findViewById(R.id.textView8);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,MainActivity.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mail=email.getText().toString();
                final String pas=pass.getText().toString();
                if (mail.isEmpty()||pas.isEmpty()) {
                    Toast.makeText(login.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mail)){
                                final String getPass=snapshot.child(mail).child("password").getValue(String.class);

                                if(getPass.equals(pas)){
                                    Toast.makeText(login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(login.this,Dashboard.class);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(login.this, "Wrong email id", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });




    }
}
package com.example.shabab.foodbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.Rsturant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingInrestuResturantManager extends AppCompatActivity {

    private  EditText email,pass;
    private Button signin;
    private FirebaseAuth firebaseAuth;
    private  ProgressDialog progressDialog;

    String EMAIL,RESNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_inrestu_resturant_manager);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //if someone is already logged in
        if (firebaseAuth.getCurrentUser() != null){
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
             // then close this activity
            EMAIL = firebaseAuth.getCurrentUser().getEmail();
            geresname();

            //startActivity(in);
        }


        email= (EditText) findViewById(R.id.signinrmusername);
        pass = (EditText) findViewById(R.id.signinrmpass);
        signin = (Button)findViewById(R.id.signinbutton);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
    }
    private  void userlogin(){
        String mail = email.getText().toString().trim();
        EMAIL=mail;
        String password = pass.getText().toString();
        if (TextUtils.isEmpty(mail)){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Signing in, Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Toast.makeText(SingInrestuResturantManager.this,EMAIL,Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            geresname();
                            return;

                            // startActivity(in);
                        } else {
                            Toast.makeText(SingInrestuResturantManager.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                });
    }
    private  void geresname()
    {

        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Resturant");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Rsturant rs = snapshot.getValue(Rsturant.class);
                    String em = rs.getEmail();
//                    Toast.makeText(SingInrestuResturantManager.this,em,Toast.LENGTH_LONG).show();


                    if(em.equals(EMAIL)){
                        progressDialog.hide();
                        RESNAME = rs.getName();
                        Intent in = new Intent(SingInrestuResturantManager.this,Resturant_page.class);
                        in.putExtra("category","manager");
                        in.putExtra("resname",RESNAME);
                        startActivity(in);
                        return ;
                    }
                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

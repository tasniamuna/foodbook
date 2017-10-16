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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.Rsturant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupResturantManager extends AppCompatActivity implements View.OnClickListener{
    private EditText username,pass,name,confirmpass,location,description,o_hour,c_hour,c_no;
    private Button signup;
    private ProgressDialog progressDialog;
    String uname;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_resturant_manager);
        username = (EditText) findViewById(R.id.resturantemail);
        pass = (EditText) findViewById(R.id.resturantpassword);
        confirmpass = (EditText) findViewById(R.id.resturantpasswordconfirm);
        name = (EditText) findViewById(R.id.resturantname);
        location = (EditText) findViewById(R.id.resturantlocation);
        signup = (Button) findViewById(R.id.signupbutton);
        description = (EditText) findViewById(R.id.res_des);
        o_hour = (EditText) findViewById(R.id.open_hour);
        c_hour = (EditText) findViewById(R.id.colse_hour);
        c_no = (EditText) findViewById(R.id.res_contact);



        progressDialog = new ProgressDialog(this);

       signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        registeruser();
    }
    private void registeruser(){
        uname = username.getText().toString().trim();
        String password = pass.getText().toString();
        final String rname = name.getText().toString();
        String repass = confirmpass.getText().toString();
        final String area = location.getText().toString();
        final String des = description.getText().toString();
        final String open = o_hour.getText().toString();
        final String close = c_hour.getText().toString();
        final String contact= c_no.getText().toString();

        if (TextUtils.isEmpty(uname)){
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rname)){
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT).show();
            return;

        }
        if(!password.equals(repass)){
            Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(open)){
            Toast.makeText(this, "Please enter Opening Hour", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(close)){
            Toast.makeText(this, "Please enter Closing Hour!", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.setMessage("Registering, Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(uname, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            enterdata(rname,uname,area,des,open,close,contact);
                            Toast.makeText(SignupResturantManager.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            startActivity(new Intent(getApplicationContext(), SingInrestuResturantManager.class));
                            return;

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignupResturantManager.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                return;
                            }
                            Toast.makeText(SignupResturantManager.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();

                            progressDialog.hide();
                            return;
                        }
                    }
                });

    }
    private  void  enterdata(String name, String email, String location,String des,String open,String close,String contact){
        DatabaseReference resturant = FirebaseDatabase.getInstance().getReference();
        Rsturant res = new Rsturant(name,location,email,des,open,close,contact);
        //String Id = resturant.push().getKey();
       // Toast.makeText(SignupResturantManager.this, name+location+email, Toast.LENGTH_LONG).show();

        resturant.child("Resturant").child(name).setValue(res);
        return;
    }
}

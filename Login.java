package com.example.shabab.foodbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.AuthCredential;

import java.io.Serializable;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, Serializable{
    String name ;
    public  static  GoogleSignInAccount acct;
    public  static  GoogleApiClient mGoogleApiClient;
    private  static final  String TAG ="Sign In Activity";
    private  static final int RC_SIGN_IN = 9001;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        Button singingmail = (Button) findViewById(R.id.sigingmail);
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        ///newLinetest


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        /*SING IN*/
        singingmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Signing In...");
                progressDialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        /* SIGN OUT */
//        final Button signout =(Button) findViewById(R.id.sout);
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Auth.GoogleSignInApi.signOut(Login.mGoogleApiClient);
//            }
//        });
        final Intent signupactivity = new Intent(this,SignupResturantManager.class);
        final Intent singinactivity = new Intent(this,SingInrestuResturantManager.class);
        TextView signuprm = (TextView) findViewById(R.id.signuprm);
        signuprm.setPaintFlags(signuprm.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        signuprm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signupactivity);
            }
        });

        Button SignInrm = (Button) findViewById(R.id.signinrm);
        SignInrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(singinactivity);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            acct = result.getSignInAccount();
            name = acct.getGivenName();
            String email = acct.getEmail();
            Intent home = new Intent(this,Welcome.class);
            home.putExtra("name",name);
            home.putExtra("email",email);
            progressDialog.hide();
            startActivity(home);

        } else {
           // Toast toast = Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_LONG);
            //toast.show();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "OnconnectionFailed:" + connectionResult);
        Toast toast = Toast.makeText(getApplicationContext(),"CHECK INTERNET CONNECTION",Toast.LENGTH_LONG);
        toast.show();
    }
}

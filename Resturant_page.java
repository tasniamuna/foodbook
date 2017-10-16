package com.example.shabab.foodbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.Rsturant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Resturant_page extends AppCompatActivity implements View.OnClickListener{
    TextView resname,description,address,contact_no,o_clcok,c_clock;
    Button view_menu;
    RatingBar env,service;
    String user_categoty,RESNAME;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_page);
        resname = (TextView) findViewById(R.id.r_name);
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);
        contact_no = (TextView) findViewById(R.id.contact_no);
        o_clcok = (TextView) findViewById(R.id.o_clock);
        c_clock = (TextView) findViewById(R.id.c_clock);
        view_menu = (Button)findViewById(R.id.view_menu);
        env = (RatingBar) findViewById(R.id.ratingBar1);
        service = (RatingBar) findViewById(R.id.ratingBar2);




        Intent in = getIntent();
        user_categoty= in.getStringExtra("category");
        RESNAME = in.getStringExtra("resname");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        //resname.setText(RESNAME);
        //description.setText(user_categoty);
        //Toast.makeText(this,resname+user_categoty,Toast.LENGTH_LONG).show();
        view_menu.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.show();

        DatabaseReference resdata ;
        resdata = FirebaseDatabase.getInstance().getReference("Resturant").child(RESNAME);
        resdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Rsturant rs = dataSnapshot.getValue(Rsturant.class);
                resname.setText(rs.getName());
                address.setText(rs.getLocation());
                progressDialog.hide();
                description.setText(rs.getDescription());
                o_clcok.setText(rs.getOpenhour());
                c_clock.setText(rs.getClosehour());
                contact_no.setText(rs.getContact());
                env.setClickable(false);
                env.setFocusable(false);
                env.setIsIndicator(true);
                env.setRating((float) rs.getEnvrrating());

                service.setClickable(false);
                service.setFocusable(false);
                service.setIsIndicator(true);
                service.setRating((float) rs.getServrating());
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                LOGOUT();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    private  void  LOGOUT(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity( new Intent(Resturant_page.this,Login.class));
    }

    @Override
    public void onClick(View view) {
        if(view == view_menu){
            Intent in = new Intent(Resturant_page.this,Menu_list.class);
            in.putExtra("category",user_categoty);
            in.putExtra("resname",RESNAME);
            startActivity(in);
        }
    }
}

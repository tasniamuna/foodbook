package com.example.shabab.foodbook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.MENU;
import com.example.shabab.foodbook.Databse.Rsturant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResturantManger extends AppCompatActivity implements View.OnClickListener {
    Button add;

    ProgressDialog progressDialog;
    ListView listView;
    List<MENU> menuList;
    DatabaseReference menudata,resdata;
    private FirebaseAuth firebaseAuth;
    String resemail,resname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_manger);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        add = (Button) findViewById(R.id.addmenu);
        add.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
        menuList = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ResturantManger.this,"Thik kora hoy nai akhono", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ResturantManger.this,Show_menu.class);
                in.putExtra("list", menuList.get(i));
                startActivity(in);

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
        firebaseAuth.signOut();
        startActivity( new Intent(ResturantManger.this,Login.class));
    }



    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        resemail = firebaseAuth.getCurrentUser().getEmail();
        resdata = FirebaseDatabase.getInstance().getReference("Resturant");
        resdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Rsturant rs = snapshot.getValue(Rsturant.class);
                    String em = rs.getEmail();

                    if(em.equals(resemail)){
                        call(rs);
                        progressDialog.hide();
                        return;
                    }
                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void  call(Rsturant rs){


        resname = rs.getName();
        menudata = FirebaseDatabase.getInstance().getReference("Resturant").child(resname).child("Menu");

        menudata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    MENU temp = snapshot.getValue(MENU.class);

                    //adding artist to the list
                    menuList.add(temp);

                }


                MENU_ADAPTAR menuAdaptar = new MENU_ADAPTAR(ResturantManger.this,  menuList);
                //set the adapter to the list view
                listView.setAdapter(menuAdaptar);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view==add){
            menupage();
            return;
        }
    }
    private  void menupage(){
        Intent intent = new Intent(ResturantManger.this,Addmenu.class);
        intent.putExtra("name",resname);
        startActivity(intent);
    }
}

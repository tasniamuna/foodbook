package com.example.shabab.foodbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.MENU;
import com.example.shabab.foodbook.Databse.Rsturant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu_list extends AppCompatActivity {
    ListView listView;
    ProgressDialog progressDialog ;
    String user_category,RESNAME;
    List<MENU> menuList;
    TextView resname,addmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        listView = (ListView) findViewById(R.id.listview);
        progressDialog = new ProgressDialog(this);
        menuList= new ArrayList<>();
        resname = (TextView) findViewById(R.id.resname);
        addmenu = (TextView) findViewById(R.id.addmenu);

        progressDialog.setMessage("Please Wait");
        Intent in = getIntent();
        user_category = in.getStringExtra("category");
        RESNAME = in.getStringExtra("resname");
        resname.setText(RESNAME);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ResturantManger.this,"Thik kora hoy nai akhono", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Menu_list.this,Show_menu.class);
                in.putExtra("name",RESNAME);
                in.putExtra("category",user_category);
                in.putExtra("list", menuList.get(i));
                startActivity(in);

            }
        });
        addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_category.equals("manager")){
                    Intent next = new Intent(Menu_list.this,Addmenu.class);
                    next.putExtra("name",RESNAME);
                    startActivity(next);
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.show();

        DatabaseReference resdata ;
        resdata = FirebaseDatabase.getInstance().getReference("Resturant").child(RESNAME).child("Menu");
        resdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    MENU temp = snapshot.getValue(MENU.class);

                    //adding artist to the list
                    menuList.add(temp);

                }


                MENU_ADAPTAR menuAdaptar = new MENU_ADAPTAR(Menu_list.this,  menuList);
                //set the adapter to the list view
                listView.setAdapter(menuAdaptar);
                progressDialog.hide();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

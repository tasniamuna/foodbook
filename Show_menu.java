package com.example.shabab.foodbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;

public class Show_menu extends AppCompatActivity {
    private TextView menuname, resname, price, description, offer,cat;
    RatingBar food;
    MENU menu;
    Button addreview;
    String RESNAME,user_category;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);
        Intent in = getIntent();
        menu = (MENU) in.getSerializableExtra("list");
        RESNAME = menu.getResname();
        user_category = in.getStringExtra("category");

        food = (RatingBar) findViewById(R.id.ratingBar1);
        food.setClickable(false);
        food.setFocusable(false);
        food.setIsIndicator(true);
        food.setRating((float) menu.getRating());

        //Toast.makeText(this, "aise", Toast.LENGTH_SHORT).show();
        menuname = (TextView) findViewById(R.id.menu_name);
        resname = (TextView) findViewById(R.id.r_value);
        price = (TextView) findViewById(R.id.p_value);
        description = (TextView) findViewById(R.id.description);
        offer = (TextView) findViewById(R.id.o_value);
        addreview = (Button) findViewById(R.id.review);
        cat=(TextView) findViewById(R.id.cat_value);
        if(user_category.equals("manager")){
            addreview.setText("");
            addreview.setVisibility(View.INVISIBLE);
        }


        description.setText(menu.getItems());
        menuname.setText(menu.getMenuname());
        resname.setText(menu.getResname());
        price.setText(String.valueOf(menu.getPrice()));
        offer.setText(menu.getOffer());
        cat.setText(menu.getCategory());

    }

}


package com.example.shabab.foodbook;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.MENU;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Addmenu extends AppCompatActivity implements View.OnClickListener{
    EditText name,item,offer,price;
    TextView imagemenu;
    Button butt;
    String res;
    String imagelist;
    Spinner spin;
    ByteArrayOutputStream stream;
    Bitmap bitmap;
    byte[] byteFormat;
    private  static  final  int SELECTED_PIC = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stream = new ByteArrayOutputStream();
       setContentView(R.layout.activity_addmenu);
            butt = (Button) findViewById(R.id.addbutton);
        name = (EditText) findViewById(R.id.menuname);
        item = (EditText) findViewById(R.id.menuitems);
        offer = (EditText) findViewById(R.id.menuoffer);
        price = (EditText) findViewById(R.id.menuprice);
        imagemenu= (TextView) findViewById(R.id.menuimage);
        spin = (Spinner) findViewById(R.id.addmenuspinner);
       // imagelist = new ArrayList<>();

        imagemenu.setOnClickListener(this);

        res = getIntent().getStringExtra("name");
        butt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == butt){
            Addmenutodatabase();
            return;
        }
        if(view==imagemenu){
            getimage();
            return;
        }
    }

    private void Addmenutodatabase() {
        String nam = name.getText().toString();
        String it= item.getText().toString();
        if(TextUtils.isEmpty(price.getText().toString())){
            Toast.makeText(this, "Please Enter Price", Toast.LENGTH_SHORT).show();
            return;
        }
        int dam = Integer.parseInt(price.getText().toString());
        String off ;

        if(TextUtils.isEmpty(nam)){
            Toast.makeText(this, "Please Enter Menu Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(it)){
            Toast.makeText(this, "Please Enter Manu Description", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(offer.getText().toString())){
            off = "None";
        }
        else off=  offer.getText().toString();


        MENU m = new MENU(it,res,off,nam,dam,spin.getSelectedItem().toString());
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Resturant").child(res);
        data.child("Menu").child(nam).setValue(m);
        Toast.makeText(Addmenu.this, "Menu Added"+res, Toast.LENGTH_SHORT).show();
        return;

    }
    private  void  getimage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SELECTED_PIC);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECTED_PIC){
//            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                String [] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                cursor.moveToFirst();
                int columnindex = cursor.getColumnIndex(projection[0]);
                String filepath = cursor.getString(columnindex);
                imagemenu.setText("HOISE");
                cursor.close();
                imagemenu.setText(filepath);
                bitmap = BitmapFactory.decodeFile(filepath);
//                Drawable drawable = new BitmapDrawable(bitmap);
//                image.setImageDrawable(drawable);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteFormat = stream.toByteArray();
                String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP); ///Ei STRING DATABASE E JABE
                imagelist=encodedImage;
                Toast.makeText(Addmenu.this, "IMAGE ADDED", Toast.LENGTH_SHORT).show();
//
//            }
        }
    }
}

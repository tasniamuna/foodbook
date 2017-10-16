package com.example.shabab.foodbook;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shabab.foodbook.Databse.MENU;
import com.example.shabab.foodbook.Databse.Rsturant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shabab on 8/8/17.
 */

public class MENU_ADAPTAR extends ArrayAdapter<MENU> {

    private Activity context;
    List<MENU> menulist;
    Button button;
    MENU menu;
    public MENU_ADAPTAR(Activity context, List<MENU> menulist) {
        super(context, R.layout.menu_layout,menulist);
        this.context = context;
        this.menulist=menulist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View lisviewitem = inflater.inflate(R.layout.menu_layout,null,true);
        TextView txtview = (TextView) lisviewitem.findViewById(R.id.largetxt);
        TextView txtview2 = (TextView) lisviewitem.findViewById(R.id.smalltxt);
        TextView txt = (TextView) lisviewitem.findViewById(R.id.adaptarrating);
        menu = menulist.get(position);
        txtview.setText(menu.getMenuname());
        txtview2.setText(menu.getItems());
        txt.setText(String.valueOf(menu.getRating()));
        return  lisviewitem;
    }

}

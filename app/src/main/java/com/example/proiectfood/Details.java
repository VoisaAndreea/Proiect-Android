package com.example.proiectfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView instructionsView= (TextView) findViewById(R.id.instructionsView);
        Bundle bundle = getIntent().getExtras(); //se extrage parametrii transmisi din activitatea anterioara
        textView.setText(bundle.getString("title"));


        //Pentru afisarea imaginii in ultima activitate, am folosit o clasa predefinita
        Picasso.with(Details.this)
                .load(bundle.getString("image")) //se specifica ce imagine sa se afiseze
                .into(imageView); //unde se va afisa

        instructionsView.setText(bundle.getString("lista"));
    }
}
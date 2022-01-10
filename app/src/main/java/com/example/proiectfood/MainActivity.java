package com.example.proiectfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    TextView textView;
    ImageView imageView;
    JSONObject json;

    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.idTitle);
        imageView = (ImageView) findViewById(R.id.imageFood);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        foodArrayList = new ArrayList<Food>();

        new AsyncCaller().execute();
    }



    private class AsyncCaller extends AsyncTask<Void, Void, ArrayList<Food>> implements RecyclerAdapter.ItemClickListener {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
        }
        @Override
        protected ArrayList<Food>  doInBackground(Void... params) {
            Log.d("###","starting test ...");
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            //Se accesează Tasty API
            Request request = new Request.Builder()
                    .url("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes")
                    .get()
                    .addHeader("x-rapidapi-host", "tasty.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "c7d470355bmsh2b9797d80a242d8p1ba0abjsnece6c600aa71")
                    .build();
            Response response = null;
            String r=null;
            try {
                response = client.newCall(request).execute();
                r = response.body().string(); //se va returna un json

                json = new JSONObject(r);

                //Se va parsa JSON-ul obtinut
                JSONArray food = json.getJSONArray("results");
                for(int i=0; i<food.length() ; i++){
                    if(i == 14)
                        continue;
                    else{
                        JSONObject f=food.getJSONObject(i);

                        //Se accesează campurile pe care ne interesează din JSON
                        String titlu =f.getString("name");
                        String  url = f.getString("thumbnail_url");

                        JSONArray js = f.getJSONArray("instructions");
                        String preparare ="";
                        for(int k = 0; k < js.length() ; k++){
                            JSONObject df = js.getJSONObject(k);
                            preparare += "S " + k + ": " + df.getString("display_text") + "\n";
                            Log.d("TAG", "doInBackground: "+ preparare);
                        }
                        foodArrayList.add(new Food(titlu,url,preparare));
                    }
                }
                return foodArrayList;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Food>  result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            recyclerAdapter = new RecyclerAdapter (MainActivity.this, result);
            Log.d("MainActivity.java","  FII ATENTA AICI" + foodArrayList.size());
            //se face aranjarea elementelor din lista
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerAdapter);

            recyclerAdapter.setClickListener(this);

        }

        @Override
        public void onItemClick(View view, int position) {
            //Toast.makeText(getApplicationContext().getApplicationContext(),"Elementul:"+recyclerAdapter.getItem(position) +"S-a selectat pozitia:" + position, Toast.LENGTH_SHORT).show();
            //se face trimiterea de la aceasta activitate la cea urmatoare
            Intent intent = new Intent(MainActivity.this, Details.class);

            //se extrage din lista și se trimite spre activitatea urmatoare - unde se va afisa
            intent.putExtra("title", foodArrayList.get(position).getTitle());
            intent.putExtra("image",foodArrayList.get(position).getImg());
            intent.putExtra("lista", foodArrayList.get(position).getInstructions());
            Log.d("MainActivity.java", "Titlul:  " + foodArrayList.get(position).getTitle());
            Log.d("MainActivity.java", "Titlul:  " + foodArrayList.get(position).getInstructions());

            startActivity(intent); //se deschide o noua activitate
        }
    }
}
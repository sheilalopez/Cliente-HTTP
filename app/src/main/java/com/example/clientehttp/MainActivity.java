package com.example.clientehttp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private TrackAPI trackAPI;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        trackAPI = retrofit.create(TrackAPI.class);
        getTracks();
        //createTrack(" la macarena" ,"los del rio");


    }
    private void createTrack ( String title,String singer){
        Call<Track> call =  trackAPI.createTrack (new Track(title ,singer));
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (!response.isSuccessful()){
                    list.add(("Code:").concat(Integer.toString(response.code())));
                } else {

                    Track track = response.body();
                    String content = "";
                    content += "Code:" + response.code() + "\n";
                    content += "ID:" + track.getId() + "\n";
                    content += "Title" + track.getTitle() + "\n";
                    content += "Singer" + track.getSinger() + "\n\n";
                    list.add(content);
                }
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);

            }


            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);

            }
        });
    }
    private void getTracks(){
        Call<List<Track>> call = trackAPI.getTracks();

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (!response.isSuccessful()){
                    list.add(("Code:").concat(Integer.toString(response.code())));
                } else {
                    List<Track> tracks = response.body();
                    for (Track track : tracks){
                        String content = "";
                        content += "ID:" + track.getId() + "\n";
                        content += "Title" + track.getTitle() + "\n";
                        content += "Singer" + track.getSinger() + "\n\n";
                        list.add(content);
                    }

                }
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);


            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);

            }
        });
    }
}





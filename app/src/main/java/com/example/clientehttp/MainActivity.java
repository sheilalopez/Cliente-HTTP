package com.example.clientehttp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        textViewResult = findViewById(R.id.text_view_result);

        Restrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class)
                call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public  void onResponse(call<List<Post>> call, Response<List<Post>> response){
               if (!response.isSuccessful()){
                   textViewResult.setText("Code: " + response.code());
                   return;
               }
               List<Post> posts = response.body();
               for (Post posts: posts){
                   String content = "";
                   content += "ID:" + posts.getId() + "\n";
                   content += "User ID:" + posts.getUserId() + "\n";
                   content += "Title:" + posts.getTitle() + "\n";
                   content += "Text:" + posts.getText() + "\n";

                   textViewResult.append(content);


               }
            }
            @Override
            public void onFailure(call<List<Post>> call, Throwable t){
                textViewResult.setText(t.getLocalizedMessage());
                //error 404
            }
        }
    }
}





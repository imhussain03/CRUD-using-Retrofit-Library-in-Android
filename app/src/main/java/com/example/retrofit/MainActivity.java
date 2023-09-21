package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    String url = "http://192.168.0.106/API/";

    FloatingActionButton fab;

    Retrofit retrofit;
    myapi api;

    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerViewId);
        fab = findViewById(R.id.fabAddId);
        fab.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        api = retrofit.create(myapi.class);

        Call<List<model>> call = api.getModels();

        call.enqueue(new Callback<List<model>>() {
            @Override
            public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                List<model> data = response.body();
                adapter ad = new adapter(data);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ad);
//                for (int i = 0; i<data.size(); i++){
//                    //Simply i'm showing the JSON data in textView , if you want , you can show the data in recyclerView
//                    textView.append("ID : "+data.get(i).getId()+"\nName : "+data.get(i).getName()+"\nCity : "+data.get(i).getCity() +"\n\n\n");
//                }
            }

            @Override
            public void onFailure(Call<List<model>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddId){
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.input_dialog_design);


            EditText name = dialog.findViewById(R.id.EditText_name);
            EditText city = dialog.findViewById(R.id.EditText_city);
            Button insert = dialog.findViewById(R.id.button_insert);

            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uname = name.getText().toString().trim();
                    String ucity = city.getText().toString().trim();

                    Call<model> call = api.addData(uname , ucity);
                    call.enqueue(new Callback<model>() {
                        @Override
                        public void onResponse(Call<model> call, Response<model> response) {
                            name.setText("");
                            city.setText("");
                            Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<model> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            dialog.show();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout((7 * width)/7, (3 * height)/9);
        }
    }
}
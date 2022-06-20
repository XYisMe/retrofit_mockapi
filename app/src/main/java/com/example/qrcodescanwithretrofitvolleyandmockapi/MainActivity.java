package com.example.qrcodescanwithretrofitvolleyandmockapi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button checkInScan;

    String responseString;
    RecyclerView rv;
    HistoryListAdapter adapter;
    List<History> historyList = new ArrayList<History>();
    //List<History> historyList = new LinkedList<History>();
    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recyclerview);
        adapter = new HistoryListAdapter(new HistoryListAdapter.HistoryDiff());
        rv.setAdapter(adapter);
        //set linear layout
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //loadHistoriesFromAPI();

        scan();

    }

    private void loadHistoriesFromAPI() {
        String baseUrl = "https://62b02995e460b79df03e8f23.mockapi.io/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<List<History>> call = retrofitApi.getHistories();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    historyList = response.body();
                    adapter.submitList(historyList);
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                responseString = "Error: " + t.getMessage();
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    private void scan() {
        checkInScan = findViewById(R.id.btnScan);
        checkInScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scanner -> qr code
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);

                //set properties of the scan
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(true);

                integrator.initiateScan();
            }
        });

    }
//
//
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if(result != null){
            //check content
            if(result.getContents() != null){
                Toast.makeText(this, "Cancelled scan",Toast.LENGTH_LONG).show();
            }else{
                String location = result.getContents(); //  QR code decoded text
                String date = java.time.LocalDate.now().toString();
                String time = java.time.LocalTime.now().toString();

                //call retrofit function to insert history
                postHistory (location, date, time);

                History history = new History(location, date, time);

                int historyListSize = historyList.size(); //size used to notify and know the difference
                historyList.add(history);

                rv.getAdapter().notifyItemInserted(historyListSize);
                rv.getAdapter().notifyDataSetChanged();

                rv.smoothScrollToPosition(0);




            }
        }else{
            ///do something in rv(update)
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void postHistory(String location, String date, String time) {
        String baseUrl = "https://62b02995e460b79df03e8f23.mockapi.io/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        History history = new History(location,date,time);

        Call<History> call = retrofitApi.insertHistory(history);
        call.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                History responseFromAPI = response.body();

                responseString = "Response Code : " + response.code() +
                        "\nDate : " + responseFromAPI.getDate() +
                        "\n" + "Time : " + responseFromAPI.getTime() +
                        "\n" + "Location : " + responseFromAPI.getLocation();
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                responseString = "Error: " + t.getMessage();
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
            }
        });

    }


}
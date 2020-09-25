package com.example.recylerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;

List<Asia>dataList =new ArrayList<>();
RoomDb database;

    //private MyAdapter adapter1;

    MainAdapter adapterP;

    String myResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        setTitle("Asian Continent");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // listItems=new ArrayList<>();
        database=RoomDb.getInstance(this);
////Store database Value in data list;
        dataList=database.mainDao().getAll();
        adapterP=new MainAdapter(dataList,MainActivity.this);
        recyclerView.setAdapter(adapterP);
        // database.mainDao().reset(dataList);

        //Checking Network Connection

        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
if (networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable())
{
    //internet is inactive
    Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog);
        //outside touch
        dialog.setCanceledOnTouchOutside(true);
        //width and height
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT
        ,WindowManager.LayoutParams.WRAP_CONTENT);
        //Set transparent background
dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//animation
dialog.getWindow().getAttributes().windowAnimations=android.R.style.Animation_Dialog;
    Button btTryAgain=dialog.findViewById(R.id.bt_try_again);
    btTryAgain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recreate();
        }
    });
dialog.show();
}
else{
    //INTERNET ACTIVE
    OkHttpClient client = new OkHttpClient();
    final ProgressDialog progressDialog=new ProgressDialog(this);
    progressDialog.setMessage("Please Wait!\nLoading Data");
    progressDialog.show();
    okhttp3.Request request=new okhttp3.Request.Builder()
            .url("https://restcountries.eu/rest/v2/region/asia")
            .get()
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
            if (response.isSuccessful())
            {

                myResponse=response.body().string();
                progressDialog.dismiss();
                try {

                    JSONArray root =new JSONArray(myResponse);


                    for(int i=0;i<root.length();i++)
                    {

                        JSONObject info=root.getJSONObject(i);
                        JSONArray languages=info.getJSONArray("languages");

                        JSONObject languagesname=languages.getJSONObject(0);
                        Asia item=new Asia(
                                info.getString("name"),//name
                                info.getString("capital"),//cap
                                info.getString("flag"),//flag
                                info.getString("region"),//region
                                info.getString("subregion"),//subregion
                                info.getString("population"),//popuation
                                info.getString("borders"),//borders
                                languagesname.getString("name")//language
                        );
                        dataList.clear();
                        dataList.add(item);

           database.mainDao().insert(item);
           dataList.addAll(database.mainDao().getAll());


                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         adapterP.notifyDataSetChanged();
                            adapterP=new MainAdapter(dataList, getApplicationContext());

                            recyclerView.setAdapter(adapterP);


                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    });

}





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.action_search);
      //  android.widget.SearchView searchView=(SearchView)searchitem.getActionView();
        SearchView searchView=(SearchView)searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterP.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
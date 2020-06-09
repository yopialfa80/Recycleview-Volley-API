package com.github.volley.get;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.volley.R;
import com.github.volley.RecycleAdapter;
import com.github.volley.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class VolleyGet {

    public Activity activity;
    RecyclerView recyclerView;
    public RecycleAdapter adapter;
    public ArrayList<User> listUser;

    public VolleyGet(Activity _activity){
        this.activity = _activity;
        recyclerView = (RecyclerView) this.activity.findViewById(R.id.recycleview);
    }

    public void volleyRequest(final Context context) {
        listUser = new ArrayList<>();
        adapter = new RecycleAdapter(context, listUser);

        String URL = "http://192.168.100.4:8080/GitHub/Volley/get.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                ArrayList<User> items = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<User>>() {
                                }.getType());

                                listUser.clear();
                                listUser.addAll(items);
                            }

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }
        };

        queue.add(stringRequest);
    }
}

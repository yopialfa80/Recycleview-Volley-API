package com.github.volley.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class VolleyPost {

    String name;
    String stats;
    String namaPicture;
    Bitmap bitmap;

    public void getText(String name, String stats, String namaPicture, Bitmap bitmap){
        this.name = name;
        this.stats = stats;
        this.namaPicture = namaPicture;
        this.bitmap = bitmap;
    }

    public void volleyRequest(final Context context) {

        String URL = "http://192.168.100.4:8080/GitHub/Volley/post.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                      Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String picture = getStringImage(bitmap);

                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("stats", stats);
                params.put("namaPicture", namaPicture);
                params.put("picture", picture);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}

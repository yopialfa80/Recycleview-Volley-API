package com.github.volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.volley.get.VolleyGet;
import com.github.volley.post.VolleyPost;
import com.github.volley.post_get.VolleyPostAndGet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView recyclerView;
    VolleyGet volleyGet;
    String name , stats , namaPicture;
    EditText edName, edStats;
    ImageView imgUserPicture;
    private static final int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);

        volleyGet = new VolleyGet(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        Button get = (Button) findViewById(R.id.get);
        get.setOnClickListener(this);

        Button post = (Button) findViewById(R.id.post);
        post.setOnClickListener(this);

        Button postandget = (Button) findViewById(R.id.postandget);
        postandget.setOnClickListener(this);

    }

    public void showBottomSheetDialog(final Context context) {
        View view = getLayoutInflater().inflate(R.layout.bottom_post, null);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        edName = view.findViewById(R.id.name);
        edStats = view.findViewById(R.id.stats);
        imgUserPicture = view.findViewById(R.id.userPicture);

        Button send = view.findViewById(R.id.send);

        imgUserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edName.getText().toString();
                stats = edStats.getText().toString();

                VolleyPost volleyPost = new VolleyPost();
                volleyPost.getText(name, stats, namaPicture, bitmap);
                volleyPost.volleyRequest(context);

                edName.setText("");
                edStats.setText("");
                imgUserPicture.setImageBitmap(null);

            }
        });


        dialog.show();
    }

    public void showBottomSheetDialogLast(final Context context) {
        View view = getLayoutInflater().inflate(R.layout.bottom_post, null);

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        edName = view.findViewById(R.id.name);
        edStats = view.findViewById(R.id.stats);
        imgUserPicture = view.findViewById(R.id.userPicture);

        Button send = view.findViewById(R.id.send);

        imgUserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edName.getText().toString();
                stats = edStats.getText().toString();

                VolleyPostAndGet volleyPostAndGet = new VolleyPostAndGet(MainActivity.this);
                volleyPostAndGet.getText(name, stats, namaPicture, bitmap);
                volleyPostAndGet.volleyRequest(context);

                edName.setText("");
                edStats.setText("");
                imgUserPicture.setImageBitmap(null);

            }
        });


        dialog.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == MainActivity.RESULT_OK && data != null && data.getData() != null) {

        Uri filePath = data.getData();
        try {
            String getImageRequestName = String.valueOf(data); // Nama Default
            String compressImage = (getImageRequestName.substring(getImageRequestName.lastIndexOf("/") + 1)); // Ambil Setelah Slash ('/')
            compressImage = (compressImage.substring(0,compressImage.lastIndexOf("."))); // Ambil Sebelum Titik ('.')
                namaPicture = compressImage;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                imgUserPicture.setImageBitmap(bitmap);
            }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.get:
                volleyGet.volleyRequest(this);
                break;

            case R.id.post:
                showBottomSheetDialog(this);
                break;
//
            case R.id.postandget:
                showBottomSheetDialogLast(this);
                break;

            default:
                break;
        }
    }
}
package com.example.appmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listeRestos = (ListView) findViewById(R.id.ListViewRestos);
        ArrayList<Resto> lesRestos = new ArrayList<>();

       // Request requestRestos = new Request.Builder().url("http://10.15.12.156:80/android/apiAppEdf/getAllRestosJSON.php").build();
        Request requestRestos = new Request.Builder().url("http://192.168.1.24:80/android/apiAppEdf/getAllRestosJSON.php").build();
        OkHttpResto httpResto = new OkHttpResto();
        httpResto.newCall(requestRestos).enqueue(new Callback() {
            //si la requête echoue
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                e.printStackTrace();
                Log.i("erreur1", "erreur requête getAllRestoJSON.php");

            }

            @Override
            //si la requête réussie
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //on va traiter la réponse
                final String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // on crée un objet JSON à partir de notre réponse.
                        JSONObject jsonObjectlesRestos = null;
                        try {
                            jsonObjectlesRestos = new JSONObject(myResponse);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //on transforme cet objet JSON en array d'objet Resto sous forme JSON
                        JSONArray jsonArray = jsonObjectlesRestos.optJSONArray("Restos");
                        //on parcours cette collection d'objet Restos pour ajouter chaque Resto dans notre liste d'objet Resto
                        lesRestos.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                                String nomResto = jsonObject.getString("nomR");
                                String villeResto = jsonObject.getString("villeR");
                                Resto unResto = new Resto(nomr, villeR);
                                //on ajoute le Resto à la collection lesRestos
                                lesRestos.add(unResto);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        ArrayAdapter<Resto> dataAdapter = new ArrayAdapter<Resto>(MainActivity.this, android.R.layout.simple_list_item_1, lesRestos);
                        listeRestos.setAdapter(dataAdapter);
                    }
                });

//               listeRestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//on récupère un Resto à la position sélectionnée dans la liste
//                     Resto unResto  = (Resto) listeRestos.getItemAtPosition(i);
//                        Intent intent = new Intent(MainActivity.this);
//                        intent.putExtra("EXTRA_ID",l);
//                        intent.putExtra("EXTRA_NOM", unResto.getNomR());
//                        intent.putExtra("EXTRA_VILLE", unResto.getVilleR());
//                        startActivity(intent);
//                    }
                });

            }
        });
    }
}
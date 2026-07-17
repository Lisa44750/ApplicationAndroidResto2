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

        ListView listeClients = (ListView) findViewById(R.id.ListViewClients);
        ArrayList<Client> lesClients = new ArrayList<>();

       // Request requestClients = new Request.Builder().url("http://10.15.12.156:80/android/apiAppEdf/getAllClientsJSON.php").build();
        Request requestClients = new Request.Builder().url("http://192.168.1.24:80/android/apiAppEdf/getAllClientsJSON.php").build();
        OkHttpClient httpclient = new OkHttpClient();
        httpclient.newCall(requestClients).enqueue(new Callback() {
            //si la requête echoue
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                e.printStackTrace();
                Log.i("erreur1", "erreur requête getAllClientsJSON.php");

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
                        JSONObject jsonObjectlesclients = null;
                        try {
                            jsonObjectlesclients = new JSONObject(myResponse);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //on transforme cet objet JSON en array d'objet client sous forme JSON
                        JSONArray jsonArray = jsonObjectlesclients.optJSONArray("clients");
                        //on parcours cette collection d'objet clients pour ajouter chaque client dans notre liste d'objet client
                        lesClients.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                                String nomCli = jsonObject.getString("NomClient");
                                String prenomCli = jsonObject.getString("PrenomClient");
                                String telCli = jsonObject.getString("Tel");
                                Client unclient = new Client(nomCli, prenomCli, telCli);
                                //on ajoute le client à la collection lesClients
                                lesClients.add(unclient);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        ArrayAdapter<Client> dataAdapter = new ArrayAdapter<Client>(MainActivity.this, android.R.layout.simple_list_item_1, lesClients);
                        listeClients.setAdapter(dataAdapter);
                    }
                });

                listeClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//on récupère un client à la position sélectionnée dans la liste
                     Client unclient  = (Client) listeClients.getItemAtPosition(i);
                        Intent intent = new Intent(MainActivity.this, ModifierClientActivity.class);
                        intent.putExtra("EXTRA_ID",l);
                        intent.putExtra("EXTRA_NOM", unclient.getNomCli());
                        intent.putExtra("EXTRA_PRENOM", unclient.getPrenomCli());
                        intent.putExtra("EXTRA-TEL", unclient.getTelCli());
                        startActivity(intent);
                    }
                });

            }
        });
    }
}
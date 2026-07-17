package com.example.appmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModifierClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_client);

        Intent intent = getIntent();
        if (intent != null) {
            long id = intent.getLongExtra("EXTRA_ID", 0);
            String nom = intent.getStringExtra("EXTRA_NOM");
            String prenom = intent.getStringExtra("EXTRA_PRENOM");
            String tel = intent.getStringExtra("EXTRA-TEL");

            TextView idlu = findViewById(R.id.textViewId);
            EditText nomlu = findViewById(R.id.editTextNom);
            EditText prenomlu = findViewById(R.id.editTextPrenom);
            EditText tellu = findViewById(R.id.editTextTel);

            //on affiche les infos
            int numcli = (int) (id+1);
            idlu.setText(Long.toString(id+1));
            nomlu.setText(nom);
            prenomlu.setText(prenom);
            tellu.setText(tel);

            //on programme le bouton pour qu'il enregistre la modification
            Button buttonModifier = findViewById(R.id.buttonModifier);
            View.OnClickListener ecouteur = new View.OnClickListener() {
                //on implémente la méthode onclick
                @Override
                public void onClick(View v) {

                    String newnom = nomlu.getText().toString();
                    String newprenom = prenomlu.getText().toString();
                    String newtel = tellu.getText().toString();
                    enregistrerModificationClient(numcli, newnom, newprenom, newtel);
                }
            };
            buttonModifier.setOnClickListener(ecouteur);

        }
    }

    public void enregistrerModificationClient(int id, String newnom, String newprenom, String newtel) {
        //on va modifier le client via une requête update
        Request requestClientmodifie = new Request.Builder().url("http://10.15.11.18:80/android/apiAppEdf/updateUnClient.php?id="+id+"&nom="+newnom+"&prenom="+newprenom+"&tel="+newtel+"").build();
        OkHttpClient clientmodifie = new OkHttpClient();
        clientmodifie.newCall(requestClientmodifie).enqueue(new Callback() {
            //si la requête echoue
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                e.printStackTrace();
                Log.i("erreur2", "erreur requête updateUnClient.php");

            }

            @Override
            //si la requête réussie
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //on va traiter la réponse
                
                finish();

            }
        });
    }
}
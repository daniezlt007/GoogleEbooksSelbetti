package com.deasystem.googlebooksselbetti;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.deasystem.googlebooksselbetti.model.Ebook;

import com.deasystem.googlebooksselbetti.utils.Common;
import com.deasystem.googlebooksselbetti.view.EbookAdapter;
import com.deasystem.googlebooksselbetti.view.FavoritosAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ViewHolder mViewHolder = new ViewHolder();
    private Context mContext;
    private EditText searchText;
    private ProgressDialog load;
    private static ArrayList<Ebook> lista;
    private static ArrayList<Ebook> favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        button = findViewById(R.id.searchBtn);
        searchText = findViewById(R.id.searchText);

        this.mContext = this;

        mViewHolder.mRecyclerEbook = findViewById(R.id.list);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GetJson download = new GetJson();
                try {
                    lista = download.execute().get();
                    if (lista != null) {
                        load.dismiss();
                    }

                    EbookAdapter favoritosListAdapter = new EbookAdapter(MainActivity.this, lista);
                    mViewHolder.mRecyclerEbook.setAdapter(favoritosListAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    mViewHolder.mRecyclerEbook.setLayoutManager(linearLayoutManager);
                    download.isCancelled();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                download.isCancelled();

            }
        });
    }

    private static class ViewHolder {
        RecyclerView mRecyclerEbook;
    }

    private class GetJson extends AsyncTask<Void, Void, ArrayList<Ebook>> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected ArrayList<Ebook> doInBackground(Void... params) {
            String url = searchText.getText().toString();
            return Common.getInformacao(Common.BASE_URL + url);
        }

        @Override
        protected void onPostExecute(ArrayList<Ebook> ebooks) {
            load.dismiss();
        }
    }

}
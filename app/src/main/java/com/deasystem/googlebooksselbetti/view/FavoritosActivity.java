package com.deasystem.googlebooksselbetti.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.deasystem.googlebooksselbetti.MainActivity;
import com.deasystem.googlebooksselbetti.R;
import com.deasystem.googlebooksselbetti.model.Ebook;

import java.util.ArrayList;

import io.paperdb.Paper;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView listaFavoritos;
    private ViewHolder mViewHolder = new ViewHolder();
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        Paper.init(FavoritosActivity.this);
        ArrayList<Ebook> list = Paper.book().read("favoritos");

        listaFavoritos = findViewById(R.id.listaFavoritos);


        final FavoritosAdapter favoritosListAdapter = new FavoritosAdapter(FavoritosActivity.this, list);
        listaFavoritos.setAdapter(favoritosListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoritosActivity.this);
        listaFavoritos.setLayoutManager(linearLayoutManager);
        favoritosListAdapter.notifyDataSetChanged();


    }



    private static class ViewHolder {
        RecyclerView mRecyclerEbook;
    }

}

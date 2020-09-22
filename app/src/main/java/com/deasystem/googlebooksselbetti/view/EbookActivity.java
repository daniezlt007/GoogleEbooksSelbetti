package com.deasystem.googlebooksselbetti.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deasystem.googlebooksselbetti.R;
import com.deasystem.googlebooksselbetti.model.Ebook;
import com.deasystem.googlebooksselbetti.utils.Common;

public class EbookActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtTitle;
    private TextView txtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        imageView = findViewById(R.id.imagem_ebook);
        txtTitle = findViewById(R.id.text_title);
        txtDescricao = findViewById(R.id.text_description);

        Intent intent = getIntent();
        Ebook ebook = (Ebook) getIntent().getExtras().get("ebook");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bd_grey);
        requestOptions.error(R.drawable.bd_grey);

        Glide.with(EbookActivity.this)
                .load(ebook.getFoto())
                .apply(requestOptions)
                .into(imageView);

        txtTitle.setText(ebook.getmTitle());
        txtDescricao.setText(ebook.getmDescription());

    }
}

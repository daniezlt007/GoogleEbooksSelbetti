package com.deasystem.googlebooksselbetti.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.deasystem.googlebooksselbetti.R;
import com.deasystem.googlebooksselbetti.controller.HttpService;
import com.deasystem.googlebooksselbetti.model.Ebook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Common {

    public static final String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=";
    public static final String METHOD_GET = "GET";

    public static final String CHAVE_ID = "ID";

    public static ArrayList<Ebook> getInformacao(String end){
        String json;
        ArrayList<Ebook> retorno;
        json = HttpService.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = converterJsonEmObjetoEbook(json);
        return retorno;
    }

    public static ArrayList<Ebook> converterJsonEmObjetoEbook(String json) {
        ArrayList<Ebook> ebooks = new ArrayList<>();
        String id = "";
        String title = "";
        String description = "";
        String foto = "";
        try {
            JSONObject objServerResponse = new JSONObject(json);
            JSONArray arrayDados = objServerResponse.getJSONArray("items");
            for (int i = 0; i < arrayDados.length(); i++) {
                JSONObject item = arrayDados.getJSONObject(i);
                id = item.getString("id");
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                try{
                    title = volumeInfo.getString("title");
                    description = volumeInfo.getString("description");
                    JSONObject objFoto = volumeInfo.getJSONObject("imageLinks");
                    foto = objFoto.getString("smallThumbnail");
                }catch (Exception ex){

                }

                ebooks.add(new Ebook(id, title, description, foto));
            }
            return ebooks;
        }catch (JSONException ex){
            Log.e("ErroJson", "Erro ParseJson: " + ex.getMessage());
        }
        return null;
    }

    private static Bitmap baixarImagem(String url) {
        try{
            URL endereco = new URL(url);
            InputStream inputStream = endereco.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            Log.e("BITMAP","Erro: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void showMsgConfirm(final Activity activity, String titulo, String txt, DialogInterface.OnClickListener listener) {
        int theme = 0, icone = 0;
        theme = R.style.Theme_AppCompat_Light_DarkActionBar;
        icone = R.drawable.ic_warning;

        final AlertDialog alertDialog = new AlertDialog.Builder(activity, theme).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(txt);
        alertDialog.setIcon(icone);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "SIM", listener);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(alertDialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.show();
        alertDialog.getWindow().setAttributes(params);
    }

}

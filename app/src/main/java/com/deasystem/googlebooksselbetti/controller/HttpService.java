package com.deasystem.googlebooksselbetti.controller;

import android.os.AsyncTask;

import com.deasystem.googlebooksselbetti.utils.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class HttpService extends AsyncTask<Void, Void, String> {

    private final String ebook;

    public HttpService(String ebook) {
        this.ebook = ebook;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return getJSONFromAPI(Common.BASE_URL + this.ebook);
        /*StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL(Common.BASE_URL + this.ebook);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(Common.METHOD_GET);
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(7000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNext()){
                resposta.append(scanner.next());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resposta.toString();*/
    }

    //Responsavel por carregar o Objeto JSON
    public static String getJSONFromAPI(String url){
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod(Common.METHOD_GET);
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return retorno;
    }

    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}

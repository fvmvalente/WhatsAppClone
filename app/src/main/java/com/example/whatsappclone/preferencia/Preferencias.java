package com.example.whatsappclone.preferencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    private String ARQUIVO_ZAP = "ZAP_ANDROID";

    private String CHAVE_NOME = "nome";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN = "token";

    public Preferencias(Context context) {
        this.contexto = context;
        preferencias = context.getSharedPreferences(ARQUIVO_ZAP, Context.MODE_PRIVATE);
        editor = preferencias.edit();
    }

    public void salvarPreferenciasUsuario(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getPreferenciasUsuario(){
        HashMap<String, String> dados = new HashMap<>();
        dados.put(CHAVE_NOME, preferencias.getString(CHAVE_NOME, null));
        dados.put(CHAVE_TELEFONE, preferencias.getString(CHAVE_TELEFONE, null));
        dados.put(CHAVE_TOKEN, preferencias.getString(CHAVE_TOKEN, null));
        return dados;
    }
}

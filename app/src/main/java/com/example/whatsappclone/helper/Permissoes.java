package com.example.whatsappclone.helper;

import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissoes {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if(android.os.Build.VERSION.SDK_INT >= 23){
            List<String> listaPermissoes = new ArrayList<String>();

            for (String permissao : permissoes){
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == android.content.pm.PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) {
                    listaPermissoes.add(permissao);
                }
            }
            if (listaPermissoes.isEmpty()){
                return true;
            }
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }
}

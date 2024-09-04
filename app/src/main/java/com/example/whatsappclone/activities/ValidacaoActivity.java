package com.example.whatsappclone.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.ActivityValidacaoBinding;
import com.example.whatsappclone.preferencia.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidacaoActivity extends AppCompatActivity {

    private ActivityValidacaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityValidacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Preferencias preferencias = new Preferencias(ValidacaoActivity.this);
        HashMap<String,String> dados = preferencias.getPreferenciasUsuario();

        EditText codigoSMS = binding.codigoSMS;
        Button btnValidarSMS = binding.btnValidar;

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(codigoSMS, simpleMaskFormatter);
        codigoSMS.addTextChangedListener(maskTextWatcher);

        btnValidarSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codigoSMS.getText().toString().equals(dados.get("token"))){
                    Toast.makeText(ValidacaoActivity.this, "Token Válido!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidacaoActivity.this, "Token Inválido!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
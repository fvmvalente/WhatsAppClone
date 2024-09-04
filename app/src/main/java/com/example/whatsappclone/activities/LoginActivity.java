package com.example.whatsappclone.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.whatsappclone.R;
import com.example.whatsappclone.config.ConfigFirebase;
import com.example.whatsappclone.databinding.ActivityLoginBinding;
import com.example.whatsappclone.helper.Permissoes;
import com.example.whatsappclone.preferencia.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding = null;
    private String[] permissoesRequeridas = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Configurar firebase
        databaseReference = ConfigFirebase.init();

//        Permissoes.validaPermissoes(1, this, permissoesRequeridas);
//
//        SimpleMaskFormatter smfCodigoPais = new SimpleMaskFormatter("+NN");
//        SimpleMaskFormatter smfCodigoDdd = new SimpleMaskFormatter("NN");
//        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
//
//        MaskTextWatcher mtwCodigoPais = new MaskTextWatcher(binding.codigoPais, smfCodigoPais);
//        MaskTextWatcher mtwCodigoDdd = new MaskTextWatcher(binding.codigoDdd, smfCodigoDdd);
//        MaskTextWatcher mtwTelefone = new MaskTextWatcher(binding.telefone, smfTelefone);
//
//        binding.codigoPais.addTextChangedListener(mtwCodigoPais);
//        binding.codigoDdd.addTextChangedListener(mtwCodigoDdd);
//        binding.telefone.addTextChangedListener(mtwTelefone);

//        binding.btnCadastrar.setOnClickListener(view -> {
//            String nome = binding.username.getText().toString();
//            String enderecoCompleto = binding.codigoPais.getText().toString() + binding.codigoDdd.getText().toString() + binding.telefone.getText().toString();
//            String telefoneSemFormatacao = enderecoCompleto.replace("+", "");
//            String telefone = telefoneSemFormatacao.replace("-", "");
//
//            Random random = new Random();
//            int numeroAleatorio = random.nextInt(9000) + 1000;
//            String token = String.valueOf(numeroAleatorio);
//            String SMSmensagem = "Seu código de verificação é: " + token;
//
//            Preferencias preferencias = new Preferencias(LoginActivity.this);
//            preferencias.salvarPreferenciasUsuario(nome, telefone, token);
////            HashMap<String,String> usuario = preferencias.getPreferenciasUsuario();
//
//            telefone = "5554";
//            boolean sms = enviarSMS("+" + telefone, SMSmensagem);
//            if(sms){
//                Intent intent = new Intent(LoginActivity.this, ValidacaoActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                Toast.makeText(this, "Erro ao enviar o SMS, por favor tente novamente.", Toast.LENGTH_LONG).show();
//            }
//
//        });

        binding.btnLogin.setOnClickListener(view -> {
            navegacao();
        });
    }

    public void navegacao(){
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean enviarSMS(String telefone, String mensagem) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            Log.i("ENVIO_SMS", "SMS enviado com sucesso para: " + telefone);
            return true;
        }catch (Exception e){
            Log.i("ERRO_ENVIO_SMS", e.getMessage());
            return false;
        }
    }

    private void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado : grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setPositiveButton("CONFIRMAR", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent,1);
            finish();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
package com.gutotech.atmconsultoria.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gutotech.atmconsultoria.R;

import mehdi.sakout.aboutpage.AboutPage;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sobre);

        String description = "A ATM Consultoria tem como missão apoiar as organizações que desejam alcançar o sucesso através da excelência em gestão e da busca pela Qualidade.\n\n" +
                "Nosso trabalho é dar suporte às empresas que desejam se certificar em padrões de qualidade ou investir no desenvolvimento e evolução de sua gestão, por meio da otimização dos processos e da disseminação dos Fundamentos e Críterios de Excelência.";
        View aboutPage = new AboutPage(this)
                .setImage(R.drawable.logo)
                .setDescription(description)
                .addGroup("Fale conosco")
                .addEmail("atmconsultoria@gmail.com", "Envie um email")
                .addWebsite("https://www.google.com.br", "Acesse nosso site")
                .addGroup("Acesse nossas redes sociais")
                .addFacebook("facebook")
                .addTwitter("google")
                .addYoutube("google")
                .addGitHub("Guto-Alves")
                .addInstagram("google")
                .create();

        setContentView(aboutPage);
    }
}

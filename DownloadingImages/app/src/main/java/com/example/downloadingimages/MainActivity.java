package com.example.downloadingimages;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {
    private static final SecureRandom random = new SecureRandom();

    private final String[] imagesUrl = {
            "https://www.celsonunes.com.br/wp-content/uploads/2018/05/java-logo.png",
            "https://www.pngkey.com/png/full/346-3466483_spring-logo-spring-framework.png",
            "https://cdn.iconscout.com/icon/free/png-512/flutter-2038877-1720090.png",
            "https://dwglogo.com/wp-content/uploads/2018/03/Dart_logo.png",
            "https://logodownload.org/wp-content/uploads/2015/05/android-logo-7-1.png",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/1200px-Kotlin-logo.svg.png",
            "https://seeklogo.com/images/P/python-logo-C50EED1930-seeklogo.com.png",
            "https://git-scm.com/images/logos/downloads/Git-Icon-1788C.png",
            "https://image.flaticon.com/icons/png/512/25/25231.png"
    };

    private Button button;
    private ImageView imageView;
    private boolean isDownloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
    }

    public void downloadImage(View view) {
        if (isDownloading) {
            return;
        }

        isDownloading = true;

        ImageDownloadTask imageDownloader = new ImageDownloadTask((bitmap -> {
            button.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                imageView.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
            }, 1000);

            isDownloading = false;
        }));

        String url = imagesUrl[random.nextInt(imagesUrl.length)];

        imageDownloader.execute(url);
    }
}
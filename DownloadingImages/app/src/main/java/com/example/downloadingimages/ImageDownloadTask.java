package com.example.downloadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
    ImageDownloaderListener mListener;

    public ImageDownloadTask(ImageDownloaderListener listener) {
        mListener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(params[0]);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            try (InputStream inputStream = connection.getInputStream()) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mListener.onDownloaded(bitmap);
    }

    interface ImageDownloaderListener {
        void onDownloaded(Bitmap bitmap);
    }
}

package com.android.kt.services;

import android.graphics.drawable.Drawable;
import java.io.InputStream;
import java.net.URL;

public class ImageStream {
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    /*class DownloadImage extends AsyncTask<Void, Void, Drawable>{
        @Override
        protected Drawable doInBackground(Void... params) {
            return Util.getImageFromURL(imageURL);
        }

        @Override
        protected void onPostExecute( Drawable d ) {
            getImageIcon().setImageDrawable(d);
        }

    }
    new DownloadImage().execute();*/
}

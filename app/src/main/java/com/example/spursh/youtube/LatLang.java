package com.example.spursh.youtube;

import android.app.IntentService;
import android.content.Intent;
import android.location.Geocoder;

import java.util.Locale;

public class LatLang extends IntentService {
        public LatLang(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    }
}

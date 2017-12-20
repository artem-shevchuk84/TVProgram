package ua.tools.escondido.tvprogram.services.impl;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.services.IAdvertizable;


public class Advertise implements IAdvertizable {
    @Override
    public void initBanner(Context context) {
        MobileAds.initialize(context, "ca-app-pub-3940256099942544/6300978111");
        //MobileAds.initialize(context, "ca-app-pub-6844948173266807/4223070918");

        AdView banner = (AdView) ((Activity) context).findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
    }
}

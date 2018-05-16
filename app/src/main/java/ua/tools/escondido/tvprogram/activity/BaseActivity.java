package ua.tools.escondido.tvprogram.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.services.IAdvertizable;
import ua.tools.escondido.tvprogram.utils.Navigate;


public class BaseActivity extends Activity implements IAdvertizable{

    Button backBtn;
    Button homeBtn;
    Button notifBtn;

    private DrawerLayout drawerLayout;

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(R.layout.main);
        homeBtn = (Button) findViewById(R.id.toolbar_home);
        backBtn = (Button) findViewById(R.id.toolbar_btn_back);
        notifBtn = (Button) findViewById(R.id.toolbar_btn_add_to_notif);
        backBtn.setVisibility(View.GONE);
        notifBtn.setVisibility(View.GONE);
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout body = (LinearLayout)findViewById(R.id.body);
        body.addView(layoutInflater.inflate(layoutId, null));
        initBanner();

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Navigate.setupNavigationDrawer(this, drawerLayout, navigationView);
    }

    @Override
    public void initBanner(Context context) {

    }

    @Override
    public void initBanner() {
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        MobileAds.initialize(this, "ca-app-pub-6844948173266807/4223070918"); //prod

        AdView banner = (AdView) (this).findViewById(R.id.adView);
        if(banner != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            banner.loadAd(adRequest);
        }
    }
}

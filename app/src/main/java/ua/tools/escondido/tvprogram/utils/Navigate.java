package ua.tools.escondido.tvprogram.utils;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import ua.tools.escondido.tvprogram.R;
import ua.tools.escondido.tvprogram.activity.ChannelActivity;
import ua.tools.escondido.tvprogram.activity.HomeActivity;
import ua.tools.escondido.tvprogram.activity.HowToActivity;
import ua.tools.escondido.tvprogram.activity.NewsActivity;
import ua.tools.escondido.tvprogram.activity.OnLineTvListActivity;
import ua.tools.escondido.tvprogram.activity.ProgramCategoryActivity;
import ua.tools.escondido.tvprogram.activity.SettingsActivity;

public class Navigate {

    public static void goHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void goBack(Context baseContext, Class to, String activityToBack, String channelName) {
        Intent intent = new Intent(baseContext, to);
        intent.putExtra(Constants.CHANNEL_NAME, channelName);
        intent.putExtra(Constants.BACK_ACTIVITY, activityToBack);
        baseContext.startActivity(intent);
    }

    public static void goBack(Context baseContext, Class to) {
        Intent intent = new Intent(baseContext, to);
        baseContext.startActivity(intent);
    }

    public static void setupNavigationDrawer(final Context baseContext, final DrawerLayout drawerLayout, NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent = null;
                        CharSequence title = menuItem.getTitle();
                        if(title.equals(baseContext.getResources().getString(R.string.title_channels))){
                            intent = new Intent(baseContext, ChannelActivity.class);
                        }
                        else if(title.equals(baseContext.getResources().getString(R.string.title_categories))){
                            intent = new Intent(baseContext, ProgramCategoryActivity.class);
                        }
                        else if(title.equals(baseContext.getResources().getString(R.string.title_online))){
                            intent = new Intent(baseContext, OnLineTvListActivity.class);
                        }
                        else if(title.equals(baseContext.getResources().getString(R.string.title_news))){
                            intent = new Intent(baseContext, NewsActivity.class);
                        }
                        else if(title.equals(baseContext.getResources().getString(R.string.title_settings))){
                            intent = new Intent(baseContext, SettingsActivity.class);
                        }
                        else if(title.equals(baseContext.getResources().getString(R.string.title_about))){
                            intent = new Intent(baseContext, HowToActivity.class);
                        }
                        if(intent != null) {
                            baseContext.startActivity(intent);
                        }
                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }
}

package ua.tools.escondido.tvprogram.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ua.tools.escondido.tvprogram.activity.HomeActivity;

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
}

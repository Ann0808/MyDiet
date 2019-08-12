package com.pisk.mydiet;

import android.content.Intent;
import android.net.Uri;

public class MenuClick {

    public static Intent share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");

        sendIntent.setType("text/plain");

        return sendIntent;
    }

    public static Intent send() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://telegram.me/proporcii"));
        final String appName = "org.telegram.messenger";
        i.setPackage(appName);
        return i;
    }

}

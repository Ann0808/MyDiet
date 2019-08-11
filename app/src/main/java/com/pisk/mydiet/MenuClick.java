package com.pisk.mydiet;

import android.content.Intent;
import android.net.Uri;

public class MenuClick {

    public static Intent share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "lj;dhsgk ;sjhg "+"https://play.google.com/store/apps/details?id=se.feomedia.quizkampen.de.lite");

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

package com.pisk.mydiet;

import android.content.Intent;

public class MenuClick {

    public static Intent share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "lj;dhsgk ;sjhg "+"https://play.google.com/store/apps/details?id=se.feomedia.quizkampen.de.lite");

        sendIntent.setType("text/plain");

        return sendIntent;
    }

}

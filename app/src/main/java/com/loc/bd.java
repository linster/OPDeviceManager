package com.loc;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class bd implements OnClickListener {
    final /* synthetic */ aB rC;

    bd(aB aBVar) {
        this.rC = aBVar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }
}

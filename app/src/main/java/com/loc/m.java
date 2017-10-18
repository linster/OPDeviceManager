package com.loc;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class m implements OnClickListener {
    final /* synthetic */ aB kM;

    m(aB aBVar) {
        this.kM = aBVar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.kM.pT();
        dialogInterface.cancel();
    }
}

package net.oneplus.odm;

import com.oneplus.config.ConfigObserver.ConfigUpdater;
import org.json.JSONArray;

class o implements ConfigUpdater {
    final /* synthetic */ n cD;

    o(n nVar) {
        this.cD = nVar;
    }

    public void updateConfig(JSONArray jSONArray) {
        this.cD.cM(jSONArray);
    }
}

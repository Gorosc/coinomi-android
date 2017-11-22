package com.coinomi.core.bitwage.data;

import com.coinomi.core.bitwage.Bitwage;

import org.json.JSONObject;

/**
 * Created by gkoro on 05-Sep-17.
 */

public abstract class BitwageBase {
        final public String errorMessage;
        final public boolean isError;

        protected BitwageBase(){
            this.errorMessage = null;
            this.isError = false;
        }

        protected BitwageBase(JSONObject data) {
            this(data.optString("error", null));
        }

        protected BitwageBase(String errorMessage) {
            this.errorMessage = errorMessage;
            isError = errorMessage != null;
        }
}

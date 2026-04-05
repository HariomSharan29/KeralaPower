package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;

public interface LoadAllocationArgument {
    void onLoadAllocationArgReceived(JsonObject jsonObject);
}

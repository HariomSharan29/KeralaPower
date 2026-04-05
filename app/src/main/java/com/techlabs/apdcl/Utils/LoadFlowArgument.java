package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;

import java.util.List;


public interface LoadFlowArgument {
    void onJsonObjectReceived(JsonObject jsonObject, JsonObject dashBoardJsonObject, List<String> list);
}

package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;

import java.util.List;

public interface ShortCircuitArgument {
    void onShortCircuitArgReceived(JsonObject jsonObject, List<String> list);
}

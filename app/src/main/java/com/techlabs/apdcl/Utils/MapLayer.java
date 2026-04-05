package com.techlabs.apdcl.Utils;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;

public class MapLayer {

    public static OnlineTileSourceBase Map() {
        OnlineTileSourceBase openStreetMapHybrid = new OnlineTileSourceBase("OpenStreetMap Hybrid", 0, 19, 256, "",
                new String[]{"https://a.tile.openstreetmap.org/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + ".png";
            }
        };
        return openStreetMapHybrid;
    }

}

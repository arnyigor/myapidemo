package com.arny.myapidemo.maps.ymaps;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

public class OverlayRectItem extends OverlayItem {

    ArrayList<GeoPoint> geoPoint = new ArrayList<GeoPoint>();
    ArrayList<ScreenPoint> screenPoint = new ArrayList<ScreenPoint>();
    
    public OverlayRectItem(GeoPoint geoPoint, Drawable drawable) {
        super(geoPoint, drawable);
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}

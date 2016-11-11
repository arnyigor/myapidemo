package com.arny.myapidemo.maps.ymaps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.arny.myapidemo.R;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.map.MapLayer;

import java.util.List;

public class YandexMapTest extends Activity{

	private static final String TAG = "LOG_TAG";
	private MapController mMapController;
	private MapView mapView;
	LinearLayout mView;
	Context cnx = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yandex_map);
		initYandexMapView();
		mView = (LinearLayout)findViewById(R.id.lay);
		List<MapLayer> list = mMapController.getListMapLayer();
		for(final MapLayer mapLayer : list)
		{
			final MapLayer mapLayer1 = mapLayer;
			final Button btn = new Button(this);
			btn.setText(mapLayer.name);
			btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			btn.setGravity(Gravity.CENTER);
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					mMapController.setCurrentMapLayer(mapLayer1);
					Log.i(TAG, "onClick mapLayer = " + mapLayer.toString());
				}
			});
			mView.addView(btn);
		}
	}

	private void initYandexMapView() {
		mapView = (MapView) findViewById(R.id.map);
		mapView.showBuiltInScreenButtons(true);
		mMapController = mapView.getMapController();
		mMapController.setHDMode(true);
		mMapController.setZoomCurrent(13);
	    mMapController.getOverlayManager().getMyLocation().setEnabled(true);

		OverlayManager mOverlayManager = mMapController.getOverlayManager();
		mOverlayManager.addOverlay(new MyPathOverLay(mMapController, mapView));
		mOverlayManager.getMyLocation().setEnabled(false);


//		OverlayRect overlayRect = new OverlayRect(mMapController);
//		mMapController.getOverlayManager().addOverlay(overlayRect);
	}

}
package com.arny.myapidemo.maps.ymaps;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.utils.ScreenPoint;
public class MyPathOverLay extends Overlay {

	MapView mMmapView;

	public MyPathOverLay(MapController arg0, MapView mapView) {
		super(arg0);
		mMmapView = mapView;
		this.setIRender(new MyOverlayIRender(this));
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}


	@Override
	public boolean onLongPress(float x, float y) {
		/*OverlayItem m = new OverlayItem(
				getMapController().getGeoPoint(new ScreenPoint(x, y)),
				mMmapView.getContext().getResources().getDrawable(R.drawable.ymk_ya_logo));
		m.setOffsetY(-23);
		this.addOverlayItem(m);*/
		getMapController().setPositionNoAnimationTo(getMapController().getGeoPoint(new ScreenPoint(x, y)));
		return true;
	}
}

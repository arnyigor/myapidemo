package com.arny.myapidemo.maps.ymaps;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayIRender;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

import java.util.List;
public class MyOverlayIRender extends OverlayIRender {

	Overlay mOverlay;

	public MyOverlayIRender(Overlay overlay) {
		super();
		mOverlay = overlay;
	}

	@Override
	public void draw(Canvas canvas, OverlayItem arg1) {
		super.draw(canvas, arg1);
		List<OverlayItem> oi = mOverlay.getOverlayItems();
		if (oi.size() < 2)
			return;
		Paint mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(2);
		mPaint.setAntiAlias(true);
		for (int i = 0; i < oi.size() - 1; i++) {
			Path path = new Path();
			ScreenPoint p1 = mOverlay.getMapController().getScreenPoint(
					oi.get(i).getGeoPoint());
			ScreenPoint p2 = mOverlay.getMapController().getScreenPoint(
					oi.get(i + 1).getGeoPoint());
			float angle = (float) (Math.atan2(p2.getY() - p1.getY(),
					p2.getX()- p1.getX()) * 180 / 3.14);
			path.moveTo(p2.getX(), p2.getY());
			path.lineTo(p1.getX(), p1.getY());
			canvas.drawPath(path, mPaint);
			canvas.save();
			canvas.rotate(angle + 90, p2.getX(), p2.getY());
			Path path2 = new Path();
			path2.moveTo(p2.getX(), p2.getY());
			path2.lineTo(p2.getX() + 5, p2.getY() + 8.66f);
			path2.lineTo(p2.getX() - 5, p2.getY() + 8.66f);
			path2.close();
			canvas.drawPath(path2, mPaint);
			canvas.restore();
		}
	}
}

package it.polimi.secomobile.data;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapMarker extends ItemizedOverlay<OverlayItem> {

	private ArrayList<GeoPoint> points;

	public MapMarker(Drawable defaultMarker, ArrayList<GeoPoint> points) {
		super(defaultMarker);
		this.points = points;
		boundCenterBottom(defaultMarker);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {

		OverlayItem item = new OverlayItem(points.get(i), null, null);
		
		return item;
	}

	@Override
	public int size() {

		return points.size();
	}

}

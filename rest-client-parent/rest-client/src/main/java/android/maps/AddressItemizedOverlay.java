package android.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class AddressItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
	private Context context;

	public AddressItemizedOverlay(Context context, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	public void addOverlay(OverlayItem overlay) {
		overlayItems.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlayItems.get(i);
	}

	@Override
	public int size() {
		return overlayItems.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = overlayItems.get(index);
		GeoPoint position = item.getPoint();
		double latitude = (double) position.getLatitudeE6() / (double) 1e6;
		double longitude = (double) position.getLongitudeE6() / (double) 1e6;
		String url = "google.navigation:q=" + latitude + "," + longitude;
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(i);
		return true;
	}
}

package android.maps;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class RestClientActivity extends MapActivity {
	private MapView mapView;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_search:
			onSearchRequested();
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		((RestClientApplication) getApplication()).setRestClientActivity(this);
		mapView.getOverlays().add(new ClickOverlay());
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void setDestination(GeoPoint geoPoint) {
		mapView.getController().setCenter(geoPoint);
		showMarkerAt(geoPoint);
	}

	private void showMarkerAt(GeoPoint geoPoint) {
		if (mapView.getOverlays().size() > 1) {
			mapView.getOverlays().remove(1);
		}
		OverlayItem item = new OverlayItem(geoPoint, "Destination",
				"Cliquez pour lancer la navigation");
		AddressItemizedOverlay addressOverlay = new AddressItemizedOverlay(
				this, getResources().getDrawable(R.drawable.marker_red));
		addressOverlay.addOverlay(item);
		mapView.getOverlays().add(addressOverlay);
	}

	private class ClickOverlay extends Overlay {
		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			Geocoder geoCoder = new Geocoder(RestClientActivity.this);
			double latitude = (double) p.getLatitudeE6() / (double) 1e6;
			double longitude = (double) p.getLongitudeE6() / (double) 1e6;
			try {
				List<Address> adresses = geoCoder.getFromLocation(latitude,
						longitude, 1);
				if (adresses.size() > 0) {
					Toast.makeText(RestClientActivity.this,
							adresses.get(0).toString(), Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(RestClientActivity.this, "No address found",
							Toast.LENGTH_LONG).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showMarkerAt(p);
			return super.onTap(p, mapView);
		}
	}
}

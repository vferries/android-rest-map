package android.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;

public class SearchAddressActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      
	      doMySearch(query);
	    }
	}

	private void doMySearch(String query) {
		List<Address> addresses = new ArrayList<Address>();
		Geocoder coder = new Geocoder(this);
		try {
			addresses.addAll(coder.getFromLocationName(query, 10));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setListAdapter(new SearchResultAdapter(query, addresses));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Address address = (Address) l.getItemAtPosition(position);
		int latitude = Double.valueOf(address.getLatitude() * 1e6).intValue();
		int longitude = Double.valueOf(address.getLongitude() * 1e6).intValue();
		GeoPoint geoPoint = new GeoPoint(latitude, longitude);
		((RestClientApplication)getApplication()).getRestClientActivity().setDestination(geoPoint);
		finish();
	}
}

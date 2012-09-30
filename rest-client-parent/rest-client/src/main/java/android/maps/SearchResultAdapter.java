package android.maps;

import java.util.List;

import android.location.Address;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {
	private String searchedString;
	private List<Address> addresses;

	public SearchResultAdapter(String searchedString, List<Address> addresses) {
		this.searchedString = searchedString;
		this.addresses= addresses;
	}

	@Override
	public int getCount() {
		if (addresses.size() == 0) {
			return 2;
		}
		return addresses.size() + 1;
	}

	@Override
	public Address getItem(int position) {
		if (position <= 0 || position > addresses.size()) {
			return null;
		}
		return addresses.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(parent.getContext());
		if (position == 0) {
			textView.setText("Votre recherche = " + searchedString);
		} else if (position == 1 && addresses.size() == 0) {
			textView.setText("Aucun résultat à afficher");
		} else {
			Address address = getItem(position);
			String displayedName = "";
			int i = 0;
			String line;
			while ((line = address.getAddressLine(i)) != null) {
				displayedName += line + " ";
				i++;
			}
			textView.setText(displayedName);
		}
		return textView;
	}
}

package android.maps;

import android.app.Application;

public class RestClientApplication extends Application {
	private RestClientActivity restClientActivity;

	public RestClientActivity getRestClientActivity() {
		return restClientActivity;
	}

	public void setRestClientActivity(RestClientActivity restClientActivity) {
		this.restClientActivity = restClientActivity;
	}
}

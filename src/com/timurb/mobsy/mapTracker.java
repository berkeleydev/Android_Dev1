package com.timurb.mobsy;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;


public class mapTracker extends MapActivity {

	double latid;
	double longid;
	double accuracyd;
	TextView myTextView;
	LinearLayout linearLayout;
	MapView mapView;
    List<Overlay> mapOverlays;
    Drawable drawable;
    CostumMapOverlay pointsOverlay;
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}  

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        //setContentView(R.la.mapview);
           
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);

        mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        pointsOverlay = new CostumMapOverlay(drawable);
       
         
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = lm.getBestProvider(criteria, true);
        //Log.d("MainWindow","provider = "+provider);
        Location mostRecentLocation = lm.getLastKnownLocation(provider);
        if(mostRecentLocation!=null){
        	latid=mostRecentLocation.getLatitude();
        	longid=mostRecentLocation.getLongitude();
        }
        //lm.requestLocationUpdates(provider, 1, 0, locationListener);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);
        GeoPoint point = new GeoPoint((int)(latid*1e6),(int)(longid*1e6));
        OverlayItem overlayitem = new OverlayItem(point, "Mobsy", "test0");
        pointsOverlay.addOverlay(overlayitem);
        mapOverlays.add(pointsOverlay);
       
    } 
 private class MyLocationListener implements LocationListener {

    	public void onLocationChanged(Location loc) {
    	   if (loc != null) {
    	    latid = loc.getLatitude();
    	    longid = loc.getLongitude();
    	    // if(loc.hasAccuracy()==true){
    	    accuracyd = loc.getAccuracy();
    	    String providershown = loc.getProvider(); 
    	    String tempString = "Lat: "+latid+" Long: "+longid;
    	    myTextView.setText(tempString+"\n\nLocation Acquired. Accuracy:"
    	      + Double.toString(accuracyd) + "m\nProvider: "+providershown);
    	    // }

    	   // userinfo=usernamevalue+"&"+Double.toString(latid)+"&"+Double.toString(longid);
    	   // submituserlocation(userinfo);
    	   }

    	  }

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    }


}

package com.timurb.mobsy;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;


public class mapTracker extends MapActivity {

	double latid;						// Geopoint latitude
	double longid;						// Geopoint longitude
	double accuracyd;					// Geopoint accuracy
	//TextView myTextView;
	//LinearLayout linearLayout;
	MapView mapView;					// Map ID on the screen
    List<Overlay> mapOverlays;			// List of overlays to display
    Drawable drawable;					// Drawable item 
    CostumMapOverlay pointsOverlay;		// Overlay class item
    String providershow;				// Provider string
	
    @Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}  

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
           
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
                   
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = lm.getBestProvider(criteria, true);
        //Log.d("MainWindow","provider = "+provider); TODO find out what's that
        Location mostRecentLocation = lm.getLastKnownLocation(provider);
        if(mostRecentLocation!=null){
        	latid=mostRecentLocation.getLatitude();
        	longid=mostRecentLocation.getLongitude();
        	accuracyd = mostRecentLocation.getAccuracy();
        	providershow = mostRecentLocation.getProvider();
        }
        //lm.requestLocationUpdates(provider, 1, 0, locationListener);
        lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, locationListener);
        lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 0, 0, locationListener);
          
        
    //set Kazan Kremlin location
        Location location1 = new Location("Kazan Kremlin");
        location1.setLatitude(55.796879);
        location1.setLongitude(49.108436);
        //double lat1=location1.getLatitude();
        //double lon1=location1.getLongitude();
        
        Location location2 = new Location("0");
        location2.setLatitude(0);
        location2.setLongitude(0);
        
     // the pop-up dialogue part
        double dist = GPSdist.distance(location1.getLatitude(), location1.getLongitude(), latid, longid);
        if(dist < 6){   
                // prepare the alert box
        	    final Context localcontext = this;
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                
                // set the message to display
                alertbox.setMessage("You're approaching a sightseeing item! Do you want more info?");

                // add a neutral button to the alert box and assign a click listener
                alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

                    // click listener on the alert box
                    public void onClick(DialogInterface arg0, int arg1) {
                        // the button was clicked
                    	AlertDialog.Builder Dialog = new AlertDialog.Builder(localcontext);
                    	Dialog.setTitle("Nearby Site");
                    	Dialog.setMessage(R.string.KazanKremlin_descr);
                    	AlertDialog dial = Dialog.show();
                    	TextView messageView = (TextView)dial.findViewById(android.R.id.message);
                    	messageView.setGravity(Gravity.CENTER);
                    }
                });

                // show it
                alertbox.show();
            }
                
        //         
        
        GeoPoint point = new GeoPoint((int)(latid*1e6),(int)(longid*1e6));
        String Popupstring = "Lat: "+Double.toString(latid)+" Long: " + Double.toString(longid)+ " w/ Acc: " + Double.toString(accuracyd) + " Provider: " + providershow + " " + Double.toString(dist);
        OverlayItem overlayitem = new OverlayItem(point, "Mobsy", "My Location is: \n" + Popupstring);
         
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.droid_overlay);
        pointsOverlay = new CostumMapOverlay(drawable,this);
       
        pointsOverlay.addOverlay(overlayitem);
        mapOverlays.add(pointsOverlay);

          
    }
 private class MyLocationListener implements LocationListener {

    	public void onLocationChanged(Location mostRecentLocation) {
    		if(mostRecentLocation!=null){
            	latid=mostRecentLocation.getLatitude();
            	longid=mostRecentLocation.getLongitude();
            	//if(mostRecentLocation.hasAccuracy() == true){}
            	accuracyd = mostRecentLocation.getAccuracy();
            	providershow = mostRecentLocation.getProvider();
            }
            GeoPoint point = new GeoPoint((int)(latid*1e6),(int)(longid*1e6));
            String Popupstring = "Lat: "+Double.toString(latid)+" Long: " + Double.toString(longid)+ " w/ Acc: " + Double.toString(accuracyd) + " Provider: " + providershow;
            OverlayItem overlayitem = new OverlayItem(point, "Mobsy", "My Location is: \n" + Popupstring);
             
            mapOverlays = mapView.getOverlays();
            //drawable = this.getResources().getDrawable(R.drawable.droid_overlay);
            //pointsOverlay = new CostumMapOverlay(drawable,this);
           
            pointsOverlay.addOverlay(overlayitem);
            mapOverlays.add(pointsOverlay);
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

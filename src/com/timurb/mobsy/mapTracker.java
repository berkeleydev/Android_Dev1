package com.timurb.mobsy;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;


public class mapTracker extends MapActivity implements TextToSpeech.OnInitListener {

	double latid;						// Geopoint latitude
	double longid;						// Geopoint longitude
	double accuracyd;					// Geopoint accuracy
	//TextView myTextView;
	//LinearLayout linearLayout;
	MapController mapControl;	 		// Map controller
	MapView mapView;					// Map ID on the screen
    List<Overlay> mapOverlays;			// List of overlays to display
    Drawable drawable;					// Drawable item 
    CostumMapOverlay pointsOverlay;		// Overlay class item
    MyLocationOverlay compass; 
    String providershow;				// Provider string
	
    private TextToSpeech mTts; 			// TTS engine
    private static final int MY_DATA_CHECK_CODE = 1234; // any value you want, its just a checksum.
    private String ttsstring;
    
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
        
        // TTS init follows
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        
        // Mapview init follows 
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
       // Location location1 = new Location("Kazan Kremlin");
        //location1.setLatitude(55.796879);
        //location1.setLongitude(49.108436);
        
        Location location1 = new Location("Berkeley");
        location1.setLatitude(37.866011);
        location1.setLongitude(-122.265601);
        
        Location location3 = new Location("Berkeley");
        location1.setLatitude(37.866011);
        location1.setLongitude(-122.265601);
        //double lat1=location1.getLatitude();
        //double lon1=location1.getLongitude();
        
        Location location2 = new Location("0");
        location2.setLatitude(0);
        location2.setLongitude(0);
        
     // the pop-up dialogue part
        double dist = GPSdist.distance(location1.getLatitude(), location1.getLongitude(), latid, longid);
        double dist3 = GPSdist.distance(location3.getLatitude(), location3.getLongitude(), latid, longid);
        ttsstring = this.getString(R.string.Berkeley_descr);
        AlertHandler.ShowAlert(this, dist, ttsstring, this); 
      
     // controls enabling + Overlay part   
        GeoPoint point = new GeoPoint((int)(latid*1e6),(int)(longid*1e6));
        String Popupstring = "Lat: "+Double.toString(latid)+" Long: " + Double.toString(longid)+ " w/ Acc: " + Double.toString(accuracyd) + " Provider: " + providershow + " " + Double.toString(dist);
        OverlayItem overlayitem = new OverlayItem(point, "Mobsy", "My Location is: \n" + Popupstring);
         
        mapControl = mapView.getController();
        mapControl.animateTo(point);
        mapControl.setZoom(13);
        
        compass = new MyLocationOverlay(this, mapView);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.droid_overlay);
        pointsOverlay = new CostumMapOverlay(drawable,this);
       
        pointsOverlay.addOverlay(overlayitem);
        mapOverlays.add(pointsOverlay);
        mapOverlays.add(compass);

         
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		compass.disableCompass();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		compass.enableCompass();
	}

	public void onInit(int i)
    {
        /*mTts.speak(ttsstring,
                TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
                null);
        */        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == MY_DATA_CHECK_CODE)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);
            }
            else
            {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
    
    @Override
    public void onDestroy()
    {
        // Don't forget to shutdown!
        if (mTts != null)
        {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
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

package com.timurb.mobsy;

import java.util.ArrayList;
import com.google.android.maps.ItemizedOverlay;
import android.content.Context;
import 	android.app.AlertDialog;
import com.google.android.maps.*;
import com.google.android.maps.OverlayItem;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;

public class CostumMapOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	   public CostumMapOverlay(Drawable defaultMarker) {
		 super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	    }
	   
	    public void addOverlay(OverlayItem overlay) {
	    	 mOverlays.add(overlay);
	    	 populate();
	    }
	    protected OverlayItem createItem(int i) {
	        // TODO Auto-generated method stub
	        return mOverlays.get(i);
	    }

	    @Override
	    public int size() {
	        // TODO Auto-generated method stub
	        return mOverlays.size();
	    }

	    @Override
	    protected boolean onTap(int index) {
	        OverlayItem item = mOverlays.get(index);
	        Context mContext = null;
	        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	        dialog.setTitle(item.getTitle());
	        dialog.setMessage(item.getSnippet());
	        dialog.show();
	        return true;
	    }
}

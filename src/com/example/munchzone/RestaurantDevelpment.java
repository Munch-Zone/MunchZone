package com.example.munchzone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class RestaurantDevelpment extends Fragment {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 }
	
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		

		
		View v = inflater.inflate(R.layout.pager_scroll_view, container, false);
		      ScrollView scrollable_contents = (ScrollView) v.findViewById(R.id.scrollableContents);
	       scrollable_contents.setFillViewport(true);
	      // System.out.println("Restaurant Development is called");
	       inflater.inflate(R.layout.restaurant_offers, scrollable_contents);
	       
			return v;
		}

}	
	
	

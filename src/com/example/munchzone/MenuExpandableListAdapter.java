package com.example.munchzone;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuExpandableListAdapter extends BaseExpandableListAdapter {
    SQLiteDatabase _db;
	private Context _context;
	private List<String> _listCatIdDataHeader; 
	private List<String> _listCatDataHeader; 
private int lastExpanded=-1;
	private HashMap<String, List<String>> _listItemNameDataChild;
	private HashMap<String, List<String>> _listItemIdDataChild;
	private HashMap<String, List<String>> _listItemPriceDataChild;

	String loc_id;
	String cus_id;
	FragmentManager fm;
	View v;
	private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
         EMPTY_STATE_SET, // 0
         GROUP_EXPANDED_STATE_SET // 1
    };
	public MenuExpandableListAdapter(View view,Context context, /*SQLiteDatabase db,*/ FragmentManager fm,List<String> _listCatIdDataHeader, List<String> _listCatDataHeader,HashMap<String, List<String>> _listItemNameDataChild,
			HashMap<String, List<String>> _listItemIdDataChild,HashMap<String, List<String>> _listItemPriceDataChild) {
		this._context = context;
	
		this._listItemPriceDataChild = _listItemPriceDataChild;
		this._listItemIdDataChild = _listItemIdDataChild;
		this._listItemNameDataChild = _listItemNameDataChild;
		this._listCatDataHeader = _listCatDataHeader;
		this._listCatIdDataHeader=_listCatIdDataHeader;
		this.v=view;
	this.fm=fm;
	
	}

	public Object getChildItemName(int groupPosition, int childPosititon) {
		
		return this._listItemNameDataChild.get(this._listCatDataHeader.get(groupPosition))
				.get(childPosititon);
	}
	public Object getChildItemPrice(int groupPosition, int childPosititon) {
	
		return this._listItemPriceDataChild.get(this._listCatDataHeader.get(groupPosition))
				.get(childPosititon);
	}
	public Object getChildItemId(int groupPosition, int childPosititon) {
	
		return this._listItemIdDataChild.get(this._listCatDataHeader.get(groupPosition))
				.get(childPosititon);
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild,  View convertView, ViewGroup parent) {
	
		final String itemName = (String) getChildItemName(groupPosition, childPosition);
		final String itemPrice = (String) getChildItemPrice(groupPosition, childPosition);
		final String itemId = (String) getChildItemId(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.rest_menu_item, null);
		}
		convertView.setClickable(true);
		
		final TextView ItemName = (TextView) convertView
				.findViewById(R.id.rest_item_name);
		final TextView ItemPrice = (TextView) convertView
				.findViewById(R.id.rest_item_price);
		final TextView ItemId = (TextView) convertView
				.findViewById(R.id.rest_item_id);
		ItemName.setText(itemName);
		ItemPrice.setText(itemPrice);
		ItemId.setText(itemId);
		convertView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( RestaurantMenu.key_img==1)
   				{
				//	System.out.println("Clicked");
					
					RestaurantMenu.key_img=0;
					RestaurantMenu.acTextView.clearFocus();
					RestaurantMenu.acTextView.setCursorVisible(false);
   					InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
   		         inputManager.hideSoftInputFromWindow(((Activity) _context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
   					
   				}
				else
				{
				v.setClickable(false);
				_db=_context.openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
				 Cursor t=_db.rawQuery("SELECT * FROM restaurant_menu where menu_id=\""+itemId+"\" AND menu_name=\""+itemName+"\"", null);
				 int price;
				 if(t.moveToFirst())
	    		 {
					  price=t.getInt(2);
	    		 }
				 t.close();
				 CartDialog dialog;
				 dialog=new CartDialog(v,_context/*, listDataChildStatus,_db*/,itemName,itemId,itemPrice);
					dialog.show( fm, "ListAlert");
					_db.close();
				}
			}
		
		}	);
		
		return convertView;
	}
	

	@Override
	public int getChildrenCount(int groupPosition) {
	
		return this._listItemNameDataChild.get(this._listCatDataHeader.get(groupPosition))
			.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return this._listCatDataHeader.get(groupPosition);
	}
	public Object getGroupCatName(int groupPosition) {
	
		return this._listCatDataHeader.get(groupPosition);
	}
	public Object getGroupCatId(int groupPosition) {
		
		return this._listCatIdDataHeader.get(groupPosition);
	}
	@Override
	public int getGroupCount() {
		return this._listCatDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView( int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		final int gp=groupPosition;
		String headerCatName = (String) getGroupCatName(groupPosition);
		String headerCatId = (String) getGroupCatName(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.rest_menu_group, null);
		}
		
		TextView restCatName = (TextView) convertView
				.findViewById(R.id.rest_cat_name);
		TextView restCatId = (TextView) convertView
				.findViewById(R.id.rest_cat_id);
		
		restCatName.setText(headerCatName);
		restCatId.setText(headerCatId);
		
		View ind = convertView.findViewById( R.id.explist_indicator);
		
			ImageView indicator = (ImageView)ind;
			
			if(isExpanded)
			{
				indicator.setImageResource(R.drawable.expander_ic_maximized);
		
				
			}
			else
			{
				indicator.setImageResource(R.drawable.expander_ic_minimized);
		
			}
	
			convertView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if( RestaurantMenu.key_img==1)
					{
					//	System.out.println("Clicked1" + RestaurantMenu.key_img);
						RestaurantMenu.key_img=0;
					//	System.out.println(RestaurantMenu.key_img);
						RestaurantMenu.acTextView.clearFocus();
						RestaurantMenu.acTextView.setCursorVisible(false);
	   					InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
	   		         inputManager.hideSoftInputFromWindow(((Activity) _context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	   					
	   				}
					else
					{
						if(isExpanded)
							RestaurantMenu.expandableListView.collapseGroup(gp);

						else
						{	
						RestaurantMenu.expandableListView.expandGroup(gp);
						if(lastExpanded!=gp)
						RestaurantMenu.expandableListView.collapseGroup(lastExpanded);
						
						RestaurantMenu.expandableListView.setSelectionFromTop(gp, 0);
						lastExpanded=gp;
						}
					}
					
				}
			});
				
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
	
		return false;
	}
	

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
	
		return true;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	
}



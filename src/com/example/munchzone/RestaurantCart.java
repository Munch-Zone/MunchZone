package com.example.munchzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.munchzone.SwipeMenu;
import com.example.munchzone.SwipeMenuCreator;
import com.example.munchzone.SwipeMenuItem;
import com.example.munchzone.SwipeMenuListView;
import com.example.munchzone.SwipeMenuLayout;
import com.example.munchzone.MainPanel.TabsAdapter;
import com.example.munchzone.SwipeMenuListView.OnMenuItemClickListener;
import com.example.munchzone.SwipeMenuListView.OnSwipeListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class RestaurantCart extends Fragment {
	ArrayList<String> _listItemId ;
	ArrayList<Integer> _listItemQty ;
	SQLiteDatabase db;
	CartListAdapter cAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		 }
	
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		

		
		View v = inflater.inflate(R.layout.pager_scroll_view, container, false);
		      ScrollView scrollable_contents = (ScrollView) v.findViewById(R.id.scrollableContents);
	       scrollable_contents.setFillViewport(true);
	       inflater.inflate(R.layout.restaurantcart, scrollable_contents);
	       SwipeMenuListView cListView= (SwipeMenuListView) v.findViewById(R.id.cartListView);
	     cAdapter= new CartListAdapter();
	       db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
	      Cursor c=db.rawQuery("SELECT * FROM restaurant_Cart", null);
	      System.out.println("Restaurant Cart is called");
	    
	     _listItemId = new ArrayList<String>();
	     _listItemQty = new ArrayList<Integer>();
	      while(c.moveToNext())
	      {
	    	  
	    	  _listItemId.add(c.getString(0));
	    	  _listItemQty.add(c.getInt(3));
	      }
	      c.close();
	        TextView ht=(TextView) v.findViewById(R.id.header_text);
	         c=db.rawQuery("SELECT * FROM selected_restaurant", null);
	         String r_id = null,r_name = null;
	         if(c.moveToFirst())
	        	 r_id=c.getString(0);
	         c.close();
	         c=db.rawQuery("SELECT * FROM restaurants where rest_id LIKE '"+r_id+"'", null);
	         if(c.moveToFirst())
	        	 r_name=c.getString(1);
	    ht.setText(r_name);
	    c.close();
	    db.close();
	   	    TextView b=(TextView) v.findViewById(R.id.back);
	       b.setVisibility(View.VISIBLE);
	       b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					MainPanel.mTabHost.getTabWidget().setCurrentTab(0);
					 MainPanel.mViewPager.setCurrentItem(MainPanel.menu_fragment_position);
				}
			});
	        cListView.setAdapter(cAdapter);
	
			SwipeMenuCreator creator = new SwipeMenuCreator() {

				@Override
				public void create(SwipeMenu menu) {
				
					SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
				
					deleteItem.setBackground(new ColorDrawable(Color.rgb(27,
							141, 17)));
					
					deleteItem.setWidth(dp2px(90));
				
					deleteItem.setIcon(R.drawable.ic_action_discard);
					
					menu.addMenuItem(deleteItem);
				}
			};
			
			cListView.setMenuCreator(creator);

			
			cListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
					String item_id = _listItemId.get(position);
					int item_qty=_listItemQty.get(position);
					 db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
					switch (index) {
					  
					case 0:
					{
						
						 db.execSQL("DELETE FROM restaurant_Cart WHERE item_id ='"+item_id+"' AND item_qty="+item_qty
						 		);
						 Cursor c=db.rawQuery("SELECT * FROM restaurant_Cart", null);
						 if(c.getCount()>0)
						 {
						 TextView txt=(TextView) MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.badge_value);
		                 
						 txt.setVisibility(View.VISIBLE);
		                 txt.setText(Integer.toString(c.getCount()));
		                
						
						Fragment myFragment = ((TabsAdapter) MainPanel.mViewPager.getAdapter()).getFragmentTag(2);
		        		
		        		FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
		        		 fragTransaction.detach(myFragment);
		        		 fragTransaction.attach(myFragment);
		        		 fragTransaction.commit();
		         		 MainPanel.mTabHost.getTabWidget().setCurrentTab(2);
		         		 MainPanel.mViewPager.setCurrentItem(2);
						 }
						 else
						 {
							 TextView txt=(TextView) MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.badge_value);
			               
							 txt.setVisibility(View.GONE);
							 MainPanel.mTabHost.getTabWidget().setCurrentTab(0);
			         		 MainPanel.mViewPager.setCurrentItem(MainPanel.menu_fragment_position);
			         		 MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
			         		 
						 }
						 c.close();
						break;
					}
					}
					
					db.close();
					return false;
				}
			});
			
		
			cListView.setOnSwipeListener(new OnSwipeListener() {
				
				@Override
				public void onSwipeStart(int position) {
					
				}
				
				@Override
				public void onSwipeEnd(int position) {
					
				}
			});

		
			cListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					Toast.makeText(getActivity(), "Swipe Right to Delete", 0).show();
					return false;
				}
			});
			db.close();
			return v;
		}

		class CartListAdapter extends BaseAdapter {

			@Override
			public int getCount() {
				return _listItemId.size();
			}

			@Override
			public String getItem(int position) {
				return _listItemId.get(position);
			}
			
			public int getItemQty(int position) {
				return _listItemQty.get(position);
			}
			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = View.inflate(getActivity(),
							R.layout.cart_item_list, null);
					new ViewHolder(convertView);
				}
				  db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
				ViewHolder holder = (ViewHolder) convertView.getTag();
				String item_id = getItem(position);
				int item_qty = getItemQty(position);
			
				 Cursor c=db.rawQuery("SELECT * FROM restaurant_Cart where item_id='"+item_id+"'AND item_qty="+item_qty, null);
				 while(c.moveToNext())
				 {
					 System.out.println("Selected Items"+ c.getString(0));
				holder.it_id.setText(c.getString(0));
				holder.it_name.setText(c.getString(1));
				holder.it_qty.setText(Integer.toString(c.getInt(3)));
				holder.it_price.setText(Integer.toString(c.getInt(3)*c.getInt(2)));
				 }
				 c.close();
				 db.close();
				return convertView;
			}

			class ViewHolder {
				TextView it_name;
				TextView it_id;
				TextView it_qty;
				TextView it_price;
			
				public ViewHolder(View view) {
					it_name = (TextView) view.findViewById(R.id.item_name);
					it_id = (TextView) view.findViewById(R.id.item_id);
					it_qty = (TextView) view.findViewById(R.id.item_qty);
					it_price = (TextView) view.findViewById(R.id.item_price);
					view.setTag(this);
				}
			}
		}
		private int dp2px(int dp) {
			return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
					getResources().getDisplayMetrics());
		}
	}
	
	

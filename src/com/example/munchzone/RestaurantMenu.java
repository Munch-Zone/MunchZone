package com.example.munchzone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




















import com.example.munchzone.MainPanel.TabsAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RestaurantMenu extends Fragment {
	private SQLiteDatabase db;
	String[] menuList;
    DrawerLayout dLayout;
    ScrollView scrollable_contents;
    public static ListView listview;
    int lk=0;
    public static ExpandableListView expandableListView;
    public  String[]  listsProId;
    public  String[]  listsProName;
    public static  AutoCompleteTextView acTextView;
      TextView b;
      TextView n;
     public static int key_img = 0;
  
     class ItemAutoTextAdapter extends CursorAdapter
	    implements android.widget.AdapterView.OnItemClickListener {

			
	public ItemAutoTextAdapter(Context context, Cursor c) {
				super(context, c);
				// TODO Auto-generated constructor stub
			}



	public ItemAutoTextAdapter() {
		super(getActivity(), null);
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * Called by the AutoCompleteTextView field when a choice has been made
	 * by the user.
	 * 
	 * @param listView
	 *            The ListView containing the choices that were displayed to
	 *            the user.
	 * @param view
	 *            The field representing the selected choice
	 * @param position
	 *            The position of the choice within the list (0-based)
	 * @param id
	 *            The id of the row that was chosen (as provided by the _id
	 *            column in the cursor.)
	 */
	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
	   
		
	   acTextView.clearFocus(); 
	   key_img=0;
	   acTextView.setCursorVisible(false);
		Cursor t,s = null;
		
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		 
		   db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
		 Cursor c=db.rawQuery("SELECT  DISTINCT menu_id,menu_name,menu_price,menu_product_category_id FROM restaurant_menu where menu_name LIKE '"+acTextView.getText()+"'", null);
				
		 PostMenuData[] listData = new PostMenuData[c.getCount()];
		PostMenuData data;
		 int i=-1;
		
		 while(c.moveToNext())
			{
			 i++;
			 data = new PostMenuData();
			 data.menu_id=c.getString(0);
					 data.menu_name=c.getString(1);
					 data.menu_price=c.getInt(2);
		
	      listData[i]=data;
	                                       
			}
		 c.close();  
		  db.close();
		 PostMenuAdapter itemAdapter = new PostMenuAdapter(getActivity(),R.layout.rest_menu_item, listData);
	    
		listview.setAdapter(itemAdapter);
		listview.setVisibility(View.VISIBLE);
		 expandableListView.setVisibility(View.GONE);
		  n.setVisibility(View.INVISIBLE);

	   
	     
	}

	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	}  
    


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
	
		
	}
	@SuppressLint("NewApi")
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		 
		View v = inflater.inflate(R.layout.restaurantmenu, container, false);
		
	       
		  MainPanel.mTabHost.getTabWidget().setVisibility(View.VISIBLE);
		 RelativeLayout l=(RelativeLayout) v.findViewById(R.id.header_menu);
		 //  l.setLayoutParams(new RelativeLayout.LayoutParams);
		   RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) l.getLayoutParams();
		 //  LayoutParams l1=(LayoutParams) scrollable_contents.getLayoutParams();
		   int density= getResources().getDisplayMetrics().densityDpi;

		   switch(density)
		   {
		   case DisplayMetrics.DENSITY_LOW:
			//   System.out.println("Low");
		      params.height=19;
		       break;
		   case DisplayMetrics.DENSITY_MEDIUM:
			//   System.out.println("Medium");
			   params.height=56 ;
		       break;
		   case DisplayMetrics.DENSITY_HIGH:
			//   System.out.println("hight");
			   params.height=72;
		       break;
		   case DisplayMetrics.DENSITY_XHIGH:
			//   System.out.println("xhigh");
			   params.height=112;
		       break;
		   }
		   
		
		
		   Bundle args =  getArguments();
	       String msgId1 = null;
	   	if (args  != null && args.containsKey("TagRestID"))
	   		msgId1 = args.getString("TagRestID");
	   	final String msgId=msgId1;
	   
		
		
		
		
		
		
		
		
		
		
		
		//  System.out.println("Restaurant Menu is called");
		   
	        expandableListView = (ExpandableListView) v.findViewById(R.id.menuLvExp);
	        
	        View headerView = View.inflate(getActivity(), R.layout.rest_info, null);
	        	  	  expandableListView.addHeaderView(headerView);
	  	  final ImageView info=(ImageView) v.findViewById(R.id.info);
	       listview=(ListView) v.findViewById(R.id.Menulistview);
	       listview.setVisibility(View.GONE);
	       TextView restName=(TextView) v.findViewById(R.id.RestName);
	       TextView restInfo=(TextView) v.findViewById(R.id.RestInfo);
	       TextView restLoc=(TextView) v.findViewById(R.id.RestAddress);
	       final TextView ht=(TextView) v.findViewById(R.id.header_text);
	       final ImageView img=(ImageView)v.findViewById(R.id.search);
		      
		    
		    final ImageView searchImg=(ImageView) v.findViewById(R.id.searchList);
	        b=(TextView) v.findViewById(R.id.back);
	        n=(TextView) v.findViewById(R.id.next);
	        info.setVisibility(View.VISIBLE);
	       b.setVisibility(View.VISIBLE);
	       n.setVisibility(View.INVISIBLE);
	        acTextView=(AutoCompleteTextView)v.findViewById(R.id.AClistview);
	        final TextView infoBack=(TextView) v.findViewById(R.id.backInfo);
		       final TextView infoText=(TextView) v.findViewById(R.id.textInfo);
		        scrollable_contents = (ScrollView) v.findViewById(R.id.scrollableContents);
		       scrollable_contents.setFillViewport(true);
		       inflater.inflate(R.layout.complete_rest_details, scrollable_contents);
		       scrollable_contents.setVisibility(View.GONE);
		       
		       TextView r_name=(TextView) v.findViewById(R.id.rest_name);
		       TextView r_add=(TextView) v.findViewById(R.id.rest_addres);
		       TextView r_type=(TextView) v.findViewById(R.id.rest_type);
		       TextView r_sunt=(TextView) v.findViewById(R.id.sundayTime);
		       TextView r_mont=(TextView) v.findViewById(R.id.mondayTime);
		       TextView r_tuest=(TextView) v.findViewById(R.id.tuesdayTime);
		       TextView r_wedt=(TextView) v.findViewById(R.id.wednesdayTime);
		       TextView r_thust=(TextView) v.findViewById(R.id.thursdayTime);
		       TextView r_frit=(TextView) v.findViewById(R.id.fridayTime);
		       TextView r_satt=(TextView) v.findViewById(R.id.satdayTime);
		       
		      
		       
		       
		       
		    
		       
		       
		       
		       
		       
		       
		       
		       db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
		       Cursor se= db.rawQuery("SELECT * FROM selected_restaurant where rest_id=\""+msgId+"\"", null);
		  	 
		       if(se.moveToFirst())
		       {
		    	  
		    	   r_type.setText(se.getString(2));
		    	    	   
			        	String r_serving_type = se.getString(3);
			            String r_allows_pickup = se.getString(4);
			            String r_has_catering = se.getString(5);
			             String r_has_delivery = se.getString(6);
			            String r_has_party_booking = se.getString(7);
			             String r_has_table_booking = se.getString(8);
			             r_name.setText(se.getString(9));
			             System.out.println(" Rest Name" +se.getString(9) );
			             System.out.println(" Rest Address" +se.getString(11) );
				    	   r_add.setText(se.getString(11));          
			             LinearLayout rootLayout = (LinearLayout) v.findViewById(R.id.LinearLayout01);
			             LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
			            		    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			             LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
			            		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			             if(r_allows_pickup.equals("true"))
			             {
			            	 //facility_available+=" Allows Pickup";
			            	 LinearLayout lin =new LinearLayout(getActivity());
			            	 lin.setLayoutParams(param1);
			            	TextView tex=new TextView(getActivity());
			            	tex.setPadding(5, 5, 5, 5);
			            	tex.setLayoutParams(param);
			            	tex.setText(" Allows Pickup");
			            	lin.addView(tex); 
			                 ImageView iv = new ImageView(getActivity());
			                 iv.setPadding(5, 5, 5, 5);
			                 iv.setLayoutParams(param);
			                 iv.setImageResource(R.drawable.take_away);
			                 lin.addView(iv); 
			                 rootLayout.addView(lin);
			             } 
			            
			             if(r_has_delivery.equals("true"))
			             {
			             //facility_available+="\n Has Delivery";
			            	 LinearLayout lin =new LinearLayout(getActivity());
			            	 lin.setLayoutParams(param1);
			            	TextView tex=new TextView(getActivity());
			            	tex.setPadding(5, 5, 5, 5);
			            	tex.setLayoutParams(param);
			            	tex.setText(" Has Delivery");
			            	lin.addView(tex); 
			            	 ImageView iv = new ImageView(getActivity());
			            	 iv.setPadding(5, 5, 5, 5);
			                 iv.setLayoutParams(param);
			                 iv.setImageResource(R.drawable.delivery);
			                 lin.addView(iv);
			                 rootLayout.addView(lin);
			             }
			             if(r_has_catering.equals("true"))
			             {
			             //facility_available+="\n Hase Catering";
			            	 LinearLayout lin =new LinearLayout(getActivity());
			            	 lin.setLayoutParams(param1);
			            	 TextView tex=new TextView(getActivity());
				            	tex.setPadding(5, 5, 5, 5);
				            	tex.setLayoutParams(param);
				            	tex.setText(" Has Catering");
				            	lin.addView(tex);
			                 ImageView iv = new ImageView(getActivity());
			                 iv.setPadding(5, 5, 5, 5);
			                 iv.setLayoutParams(param);
			                 iv.setImageResource(R.drawable.catering);
			                 lin.addView(iv);
			                 rootLayout.addView(lin);
			             }
			             if(r_has_party_booking.equals("true"))
			             {
			             //facility_available+="\n Has Party Booking";
			            	 LinearLayout lin =new LinearLayout(getActivity());
			            	 lin.setLayoutParams(param1);
			            	TextView tex=new TextView(getActivity());
			            	tex.setPadding(5, 5, 5, 5);
			            	tex.setLayoutParams(param);
			            	tex.setText("Has Party Booking");
			            	lin.addView(tex); 
			            	 ImageView iv = new ImageView(getActivity());
			            	 iv.setPadding(5, 5, 5, 5);
			                 iv.setLayoutParams(param);
			                 iv.setImageResource(R.drawable.party_booking);
			                 lin.addView(iv);
			                 rootLayout.addView(lin);
			             }
			             	if(r_has_table_booking.equals("true"));
			             	{
			             		//facility_available+="\n Has Table Booking";
			             		LinearLayout lin =new LinearLayout(getActivity());
				            	 lin.setLayoutParams(param1);
				            	TextView tex=new TextView(getActivity());
				            	tex.setPadding(5, 5, 5, 5);
				            	tex.setLayoutParams(param);
				            	tex.setText("Has Table Booking");
				            	lin.addView(tex); 
			             		ImageView iv = new ImageView(getActivity());
			             		 iv.setPadding(5, 5, 5, 5);
			                    iv.setLayoutParams(param);
			                    iv.setImageResource(R.drawable.table_booking);
			                    lin.addView(iv);
				                 rootLayout.addView(lin);
			             	} 
			             	if(r_serving_type.equals("Veg"))
			             	{
			             		//facility_available+="\n Has Table Booking";
			             		LinearLayout lin =new LinearLayout(getActivity());
				            	 lin.setLayoutParams(param1);
				            	TextView tex=new TextView(getActivity());
				            	tex.setPadding(5, 5, 5, 5);
				            	tex.setLayoutParams(param);
				            	tex.setText("Veg");
				            	lin.addView(tex);
			             		ImageView iv = new ImageView(getActivity());
			             		 iv.setPadding(5, 5, 5, 5);
			                    iv.setLayoutParams(param);
			                    iv.setImageResource(R.drawable.veg);
			                    lin.addView(iv);
				                 rootLayout.addView(lin);
			             	}
			             	else
			             	{
			             		LinearLayout lin =new LinearLayout(getActivity());
				            	 lin.setLayoutParams(param1);
				            	TextView tex=new TextView(getActivity());
				            	tex.setPadding(5, 5, 5, 5);
				            	tex.setLayoutParams(param);
				            	tex.setText("Veg and Non-veg");
				            	lin.addView(tex);
			             		ImageView iv = new ImageView(getActivity());
			            		 iv.setPadding(5, 5, 5, 5);
			                   iv.setLayoutParams(param);
			                   iv.setImageResource(R.drawable.veg_non_veg);
			                   lin.addView(iv);
				                 rootLayout.addView(lin);
			             	}
			             	
			             	
			             	r_sunt.setText( "0 to 23 hours");			  
			             	r_mont.setText( "0 to 23 hours");
			             	r_tuest.setText( "0 to 23 hours");
			             	r_wedt.setText( "0 to 23 hours");
			             	r_thust.setText( "0 to 23 hours");
			             	r_frit.setText( "0 to 23 hours");
			             	r_satt.setText( "0 to 23 hours");
			        }
		       
		       
		       se.close();
		       db.close();
		       
	       info.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				infoBack.setVisibility(View.VISIBLE);
				infoText.setVisibility(View.VISIBLE);
				b.setVisibility(View.GONE);
				n.setVisibility(View.INVISIBLE);
				ht.setVisibility(View.GONE);
				searchImg.setVisibility(View.GONE);
				info.setVisibility(View.GONE);
				expandableListView.setVisibility(View.GONE);
				 scrollable_contents.setVisibility(View.VISIBLE);
				
			}
	    	   
	       });
		
	       infoBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				infoBack.setVisibility(View.GONE);
				infoText.setVisibility(View.GONE);
				b.setVisibility(View.VISIBLE);
				  db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
			       	 Cursor temp=db.rawQuery("SELECT * FROM restaurant_cart", null);
				   	    if(temp.getCount() ==0)
				   	   n.setVisibility(View.INVISIBLE);
				   	    else
				   	   n.setVisibility(View.VISIBLE);
				   	    temp.close();
				         		db.close();
				ht.setVisibility(View.VISIBLE);
				searchImg.setVisibility(View.VISIBLE);
				info.setVisibility(View.VISIBLE);
				expandableListView.setVisibility(View.VISIBLE);
				 scrollable_contents.setVisibility(View.GONE);
			}
		});
	        acTextView.setVisibility(View.GONE);
	       
	      /*  if(getActivity().getCurrentFocus() !=null)
	        {
	    	InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	         inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	        }
	        
	        
	      */ 
	        acTextView.clearFocus();  
	        key_img=0;
	 	   acTextView.setCursorVisible(false);
	        
	      //  System.out.println("Intially is acText focusable:"+acTextView.isFocusable());
	     //   System.out.println("Intially is acText focused:"+acTextView.isFocused()); 
	      
	 
	    
	       searchImg.setVisibility(View.VISIBLE);
	       searchImg.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					ht.setVisibility(View.GONE);
				b.setVisibility(View.GONE);
				 info.setVisibility(View.GONE);
				n.setVisibility(View.GONE);
					searchImg.setVisibility(View.GONE);
					 MainPanel.mTabHost.getTabWidget().setVisibility(View.GONE);
					acTextView.setVisibility(View.VISIBLE);
					 acTextView.setFocusable(true);
					 acTextView.setFocusableInTouchMode(true);
			       	acTextView.requestFocus();
					
					// System.out.println("acTextView in search Img Click :"+acTextView.isFocused());
					InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			        inputManager.showSoftInput(acTextView, InputMethodManager.SHOW_IMPLICIT);
			         key_img = 1;
			         acTextView.setCursorVisible(true);
			      	//img.setBackgroundResource(R.drawable.cancel);
					img.setVisibility(View.VISIBLE);
					
				}
	       });
	       b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					//Fragment myFragment = ((TabsAdapter) MainPanel.mViewPager.getAdapter()).getFragmentTag(0);
					
					//FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
					// fragTransaction.detach(myFragment);
					// fragTransaction.attach(myFragment);
					// fragTransaction.commit();
					
					 db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
					   db.execSQL("DELETE  FROM restaurant_menu ");
					   db.close();
					 
					 MainPanel.mTabHost.getTabWidget().setCurrentTab(0);
					 MainPanel.mViewPager.setCurrentItem(0);
					 TextView txt=(TextView) MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.badge_value);
	                
	                 txt.setVisibility(View.GONE);
					 
				}
			});
	       ArrayList<String> _listCatDataHeader = new ArrayList<String>();
	       ArrayList<String> _listCatIdDataHeader = new ArrayList<String>();
	       HashMap<String, List<String>> _listItemNameDataChild = new HashMap<String, List<String>>();
	       HashMap<String, List<String>> _listItemIdDataChild = new HashMap<String, List<String>>();
	       HashMap<String, List<String>> _listItemPriceDataChild = new HashMap<String, List<String>>();
	      
	    acTextView.clearFocus(); 
	    key_img=0;
		   acTextView.setCursorVisible(false);
	 // System.out.println("Intially is acText focusable1:"+acTextView.isFocusable());
     // System.out.println("Intially is acText focused1:"+acTextView.isFocused()); 
	
	    
	    acTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        @Override
	        public void onFocusChange(View v, boolean hasFocus) {
	     //   System.out.println("In focus change"+hasFocus);
	            if (!hasFocus) {
	          //  System.out.println("Inside !focus");
	     	 	   acTextView.setHint("Search by Menu Name");
	     	 	 
	     	 	  
	     	
	            }
	        }
	    }); 
	    ht.setText("Menu");
	    ht.setVisibility(View.VISIBLE);
	    db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
	    Cursor c= db.rawQuery("SELECT rest_name,rest_info,rest_loc_id FROM selected_restaurant where rest_id=\""+msgId+"\"", null);
	    while(c.moveToNext())
	    {
	    	
	    	restName.setText(c.getString(0));
	    //System.out.println("Rest Name "+ restName.getText());
	    	restInfo.setText(c.getString(1));
	   	 Cursor t=db.rawQuery("SELECT loc_name FROM locations where loc_id LIKE \""+c.getString(2)+"\"", null);
         if(t.moveToFirst()){
        	 restLoc.setText(t.getString(0));
        	 // System.out.println("Rest Location "+ restLoc.getText());
         }
         t.close();
	    	restName.setVisibility(View.VISIBLE);
	    	restInfo.setVisibility(View.VISIBLE);
	    	restLoc.setVisibility(View.VISIBLE);
	    }c.close();
	    
	    c=db.rawQuery("SELECT DISTINCT pc_id,pc_name FROM restaurant_PC", null);
	    int cat_count=0;
	    Cursor temp=db.rawQuery("SELECT DISTINCT menu_id,menu_name,menu_price,menu_product_category_id FROM restaurant_menu", null);
	    if(temp.getCount() ==0)
	    {
	    	
	    	restName.setText("");
	    	restInfo.setText("");
	    	restLoc.setText("");
	    }
	    
	    listsProName=new String[temp.getCount()];
	    listsProId=new String[temp.getCount()];
	    int cur=temp.getCount();
	    temp.close();
	    int listsCount=-1;
	    while(c.moveToNext()){
	    	 _listCatIdDataHeader.add(c.getString(0));
	    	 _listCatDataHeader.add(c.getString(1));
	    	 Cursor t=db.rawQuery("SELECT DISTINCT menu_id,menu_name,menu_price,menu_product_category_id FROM restaurant_menu where menu_product_category_id=\""+c.getString(0)+"\"", null);
	    	 ArrayList<String>  Itemid = new ArrayList<String>();
    		 ArrayList<String> ItemName = new ArrayList<String>();
    		 ArrayList<String>  ItemPrice = new ArrayList<String>();
	    	 while(t.moveToNext()&& cur >0)
	    	 {
	    		 ++listsCount;
	    		 Itemid.add(t.getString(0));
	    		 ItemName.add(t.getString(1));
	    		 ItemPrice.add(Integer.toString(t.getInt(2)));
	    	//	System.out.println("Menu Product Name"+t.getString(1));
	    		
	    		 listsProName[listsCount]=t.getString(1);
	    		    listsProId[listsCount]=t.getString(0);
	    		    	 }
	    	 t.close();
	    	 _listItemIdDataChild.put(_listCatDataHeader.get(cat_count), Itemid);
	    	 _listItemNameDataChild.put(_listCatDataHeader.get(cat_count), ItemName);
	    	 _listItemPriceDataChild.put(_listCatDataHeader.get(cat_count), ItemPrice);
	    	 _listItemIdDataChild.put(_listCatIdDataHeader.get(cat_count), Itemid);
	    	 _listItemNameDataChild.put(_listCatIdDataHeader.get(cat_count), ItemName);
	    	 _listItemPriceDataChild.put(_listCatIdDataHeader.get(cat_count), ItemPrice);
	    	 cat_count++;
		   }c.close();
		   db.close();
		   acTextView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, listsProName));
		   
	  final MenuExpandableListAdapter listAdapter= new MenuExpandableListAdapter(getView(),getActivity(),/* db,*/getChildFragmentManager(),_listCatIdDataHeader,_listCatDataHeader, _listItemNameDataChild, _listItemIdDataChild,  _listItemPriceDataChild) ;
	  expandableListView.setAdapter(listAdapter);	
	  expandableListView.setOnChildClickListener(new OnChildClickListener() {
		  
		  public boolean onChildClick(ExpandableListView parent, View v,
		                                                  int groupPosition, int childPosition, long id) {

		  int index = parent.getFlatListPosition(ExpandableListView
		                     .getPackedPositionForChild(groupPosition, childPosition));
		  parent.setItemChecked(index, true);
		                                       
		  return false;
		  }
		  });

	  listview.setOnTouchListener(new OnTouchListener()
	  {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
		//	System.out.println("In listview touch");
			if(  key_img==1)
				{
				
					RestaurantMenu.acTextView.clearFocus();
					  key_img=0;
					  lk=1;
					   acTextView.setCursorVisible(false);
					InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		         inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					
				}
			else
			{
				lk=0;
			}
		
			return false;
		}
		  
	  });
	  expandableListView.setOnTouchListener(new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(  key_img==1)
			{
			//System.out.println("In ExpList Touch");
			RestaurantMenu.acTextView.clearFocus();
			  key_img=0;
			   acTextView.setCursorVisible(false);
			InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			return false;
		}
		  
	  });		
	  listview.setOnItemClickListener(new OnItemClickListener() {
		  @Override
		  public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
		//	  System.out.println("In listview item click");
				/*if( key_img==1 && lk==0)
   				{
					
					RestaurantMenu.acTextView.clearFocus();
					  key_img=0;
					  
					   acTextView.setCursorVisible(false);
   					InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
   		         inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
   					
   				}
				else
				{ */
			  if(lk==0)
			  {
				final String menu_name = ((TextView) view.findViewById(R.id.rest_item_name))
     					.getText().toString();
     				final String menu_id = ((TextView) view.findViewById(R.id.rest_item_id))
     						.getText().toString();
     				final String menu_price = ((TextView) view.findViewById(R.id.rest_item_price))
     						.getText().toString();
				 CartDialog dialog;
				 dialog=new CartDialog(getView(),getActivity()/*, listDataChildStatus,db*/,menu_name,menu_id,menu_price);
					dialog.show( getChildFragmentManager(), "ListAlert");
				}
					
			}
		});

	  
	 
      acTextView.setOnTouchListener(new View.OnTouchListener() {

          @Override
          public boolean onTouch(View v, MotionEvent event) {
       	
       	   acTextView.setHint("Search by Menu Name");
       key_img = 1;
       	//img.setBackgroundResource(R.drawable.cancel);
              acTextView.setFocusable(true);
              acTextView.setFocusableInTouchMode(true);
              acTextView.setCursorVisible(true);
              return false;
          }	
      });
       img.setOnClickListener(new OnClickListener(){

      	@Override
      	public void onClick(View v) {
      		// TODO Auto-generated method stub
      		
      		acTextView.setText("");
       	   acTextView.setHint("Search by Menu Name");
       	   acTextView.setFocusable(true);
       	   key_img=0;
       	   acTextView.setFocusableInTouchMode(true);
       		ht.setVisibility(View.VISIBLE);
       		b.setVisibility(View.VISIBLE);
       	 info.setVisibility(View.VISIBLE);
       	  db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
       	 Cursor temp=db.rawQuery("SELECT * FROM restaurant_cart", null);
	   	    if(temp.getCount() ==0)
	   	   n.setVisibility(View.INVISIBLE);
	   	    else
	   	   n.setVisibility(View.VISIBLE);
	   	    temp.close();
	         		db.close();
       		searchImg.setVisibility(View.VISIBLE);
       	 
      		acTextView.setVisibility(View.GONE);
      		img.setVisibility(View.GONE);
      		expandableListView.setVisibility(View.VISIBLE);
      		listview.setVisibility(View.GONE);
      		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            MainPanel.mTabHost.getTabWidget().setVisibility(View.VISIBLE);
      	}
      	  
      })
      ; 
    /*  if(acTextView.hasFocus())
      	  img.setBackgroundResource(R.drawable.cancel);
      else
      	  img.setBackgroundResource(R.drawable.search);
  */   
      ItemAutoTextAdapter adapter = new ItemAutoTextAdapter();
      acTextView.setOnItemClickListener(adapter);

  
      acTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      	    @Override
      	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
      	        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      	        	if(acTextView.getText().length()==0 )
      				
               	{
      	        		ht.setVisibility(View.VISIBLE);
      	         		b.setVisibility(View.VISIBLE);
      	         	 info.setVisibility(View.VISIBLE);
      	         	 db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
      	         	 Cursor temp=db.rawQuery("SELECT * FROM restaurant_cart", null);
      	   	    if(temp.getCount() ==0)
      	   	   n.setVisibility(View.INVISIBLE);
      	   	    else
      	   	   n.setVisibility(View.VISIBLE);
      	   	    temp.close();
      	         		db.close();
      	         		searchImg.setVisibility(View.VISIBLE);
      	        		acTextView.setVisibility(View.GONE);
      	        		img.setVisibility(View.GONE);
      	        		expandableListView.setVisibility(View.VISIBLE);
      	        		listview.setVisibility(View.GONE);

      	           		 
               	}
      	        	else
      	        	{
      	        		
      	       		
      	       		Cursor t,s = null;
      	       		
      	       		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      	             inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

      	       		   acTextView.clearFocus(); 
      	       	  key_img=0;
      	 	   acTextView.setCursorVisible(false);
      	       		   db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
      	       		 Cursor c=db.rawQuery("SELECT * FROM restaurant_menu where menu_name LIKE '%"+acTextView.getText()+"%'", null);
      	       				
      	       		 PostMenuData[] listData = new PostMenuData[c.getCount()];
      	       		PostMenuData data;
      	       		 int i=-1;
      	       		
      	       		 while(c.moveToNext())
      	       			{
      	       			 i++;
      	       			 data = new PostMenuData();
      	       			 data.menu_id=c.getString(0);
      	       					 data.menu_name=c.getString(1);
      	       					 data.menu_price=c.getInt(2);
      	       		
      	       	      listData[i]=data;
      	       	                                       
      	       			}
      	       		 c.close();  
      	       	   db.close();
      	       		 PostMenuAdapter itemAdapter = new PostMenuAdapter(getActivity(),R.layout.rest_menu_item, listData);
      	       	   
      	       		listview.setAdapter(itemAdapter);
      	       		listview.setVisibility(View.VISIBLE);
      	       		 expandableListView.setVisibility(View.GONE);
      	       	   
      	       	  
      	        	}
      	        	InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      	            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

      	            return true;
      	        }
      	        return false;
      	    }

      		
      	});

      
	  
	 
	  

				return v;
		
	}
	
	
	
}

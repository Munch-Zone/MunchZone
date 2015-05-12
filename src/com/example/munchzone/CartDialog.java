package com.example.munchzone;

import com.example.munchzone.MainPanel.TabsAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CartDialog extends DialogFragment {

	public static class CartAdapter extends AbstractWheelTextAdapter {
		
		private String Qty_list[];
		private	int Total_Qty;
		private	int menu_price;
		
		/**
		 * Constructor
		 */
		
		protected CartAdapter(Context context, int price) {
			super(context, R.layout.cart_layout, NO_RESOURCE);
			menu_price=price;
			Total_Qty=100;
			
			Qty_list=new String[Total_Qty];
			for(int i=0;i<Total_Qty;i++)
				Qty_list[i]=Integer.toString((i+1));
			setItemTextResource(R.id.menu_qty);
			
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			TextView price = (TextView) view.findViewById(R.id.menu_price);
			price.setText(Integer.toString((menu_price*(index+1))));
			
			return view;
		}

		public int getItemsCount() {
			return Qty_list.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return Qty_list[index];
		}
	}
	
   
	Context context;
	private	String mn;
	private	String mi;
	private	String mp;
	private String catId;
	View v;
	public CartDialog(ExpandableListAdapter listAdapter) {
		// TODO Auto-generated constructor stub

	}
	public CartDialog() {
		// TODO Auto-generated constructor stub
	
	}
	public CartDialog(View v,Context context,String mn, String mi, String mp) {
		// TODO Auto-generated constructor stub
	
		this.context=context;
		this.mn=mn;
		this.mi=mi;
		this.mp=mp;
		this.v=v;
		
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		 
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	   
	    LayoutInflater FilterListInflater =  getActivity().getLayoutInflater();
	    final View allView = FilterListInflater.inflate(R.layout.activity_cart, null);
	 
	    TextView menuName = ((TextView) allView.findViewById(R.id.menu_name));
	    menuName.setText(mn);
					
	    final WheelView cart = (WheelView) allView.findViewById(R.id.cart);
	    
		cart.setVisibleItems(100);
		cart.setViewAdapter((WheelViewAdapter) new CartAdapter(context,Integer.parseInt(mp)));
     
	    builder.setView(allView)
	    	           .setPositiveButton("Add", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	
	            		int qty=((WheelView) allView.findViewById(R.id.cart)).getCurrentItem();
	            		  MainPanel.mTabHost.getTabWidget().setVisibility(View.VISIBLE);
	                   SQLiteDatabase db;
	                   db=getActivity().openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
	                   db.execSQL("INSERT INTO restaurant_Cart VALUES(\""+mi+"\",\""+mn+"\","+Integer.parseInt(mp)+","+(qty+1)+");");
	                    Cursor c=db.rawQuery("SELECT * FROM restaurant_Cart", null);
	                   MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(true);
	                  TextView txt=(TextView) MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.badge_value);
	                
	                  txt.setVisibility(View.VISIBLE);
	                 txt.setText(Integer.toString(c.getCount()));
	                 c.close();
	                 db.close();
	                
	                 TextView n=(TextView) getParentFragment().getView().findViewById(R.id.next);
	                 MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(true);
	      	       n.setVisibility(View.VISIBLE);
	      	       n.setOnClickListener(new View.OnClickListener() {
	      				public void onClick(View v) {
	      					Fragment myFragment = ((TabsAdapter) MainPanel.mViewPager.getAdapter()).getFragmentTag(2);
	    					
	    					FragmentTransaction fragTransaction =   ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
	    					 fragTransaction.detach(myFragment);
	    					 fragTransaction.attach(myFragment);
	    					 fragTransaction.commit();
	    					
	    					 MainPanel.mTabHost.getTabWidget().setCurrentTab(2);
	    					 MainPanel.mViewPager.setCurrentItem(2);
	      				}
	      			});
	                  
	               }})
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	Log.i("Dialogos", "Confirmacion Cancel.");
	       					dialog.cancel();
	                   }
				
	           });
	          
	    
	   
	    AlertDialog dialog = builder.create();
	    dialog.show();
	    Button positive_button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);


	   
	    if (positive_button != null) {
	        positive_button.setBackgroundColor(Color.rgb(153, 202, 60));

	        positive_button.setTextColor(context.getResources()
	                        .getColor(android.R.color.white));
	    }
		return dialog;
    }


	
		
		
	
		}
		
	
	
		
	


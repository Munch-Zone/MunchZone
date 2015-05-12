package com.example.munchzone;



import java.util.ArrayList;


import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainPanel extends FragmentActivity  {
	public static  TabHost mTabHost;
    public static MyViewPager mViewPager;
    public static  TabsAdapter mTabsAdapter;
    public static int menu_fragment_position=4;
    protected OnBackPressedListener onBackPressedListener;
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_panel);

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
       
        mViewPager = (MyViewPager)findViewById(R.id.pager);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(4);
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        mTabsAdapter.addTab(mTabHost.newTabSpec("one").setIndicator(createTabIndicator("Restaurants",createTabDrawable(R.drawable.rest_menu))), RestaurantList.class,null,createTabDrawable(R.drawable.rest_list));
        mTabsAdapter.addTab(mTabHost.newTabSpec("two").setIndicator(createTabIndicator("Offers",createTabDrawable(R.drawable.rest_list))), RestaurantDevelpment.class,null,null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("three").setIndicator(createTabIndicator("Cart",createTabDrawable(R.drawable.rest_list))), RestaurantCart.class,null,null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("four").setIndicator(createTabIndicator("More",createTabDrawable(R.drawable.rest_list))), RestaurantDevelpment.class,null,null);
        mTabsAdapter.addFrag("new",RestaurantMenu.class,null,MainPanel.menu_fragment_position);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener()
        {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			      inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
        	
        });
    
        
        if (savedInstanceState != null)
        {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
        MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
          mTabHost.getTabWidget().setDividerDrawable(null);
      
        MainPanel.mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

         	 
            	  MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
         		 TextView txt=(TextView) MainPanel.mTabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.badge_value);
              
                 txt.setVisibility(View.GONE);
                 SQLiteDatabase db = openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
				   db.execSQL("DELETE  FROM restaurant_menu ");
				   db.close();
                 MainPanel.mTabHost.getTabWidget().setCurrentTab(0);
         		 MainPanel.mViewPager.setCurrentItem(0);
 }
 });
        MainPanel.mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

         	
        		Fragment myFragment = ((TabsAdapter) MainPanel.mViewPager.getAdapter()).getFragmentTag(2);
        	
        		FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        		 fragTransaction.detach(myFragment);
        		 fragTransaction.attach(myFragment);
        		 fragTransaction.commit();
        		
         		 MainPanel.mTabHost.getTabWidget().setCurrentTab(2);
         		 MainPanel.mViewPager.setCurrentItem(2);
 }
 });    
        MainPanel.mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

         	     MainPanel.mTabHost.getTabWidget().setCurrentTab(1);
         		 MainPanel.mViewPager.setCurrentItem(1);
 }
 });
        MainPanel.mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

         	
                 MainPanel.mTabHost.getTabWidget().setCurrentTab(3);
         		 MainPanel.mViewPager.setCurrentItem(3);
 }
 });
    }
    private View createTabIndicator(String label, Drawable drawable) {
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_items, mTabHost.getTabWidget(), false);

		TextView txtTitle = (TextView) tabIndicator.findViewById(R.id.text_view_tab_title);
		
		txtTitle.setText(label);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle.getLayoutParams();
	txtTitle.setLayoutParams(params);

		ImageView imgIcon = (ImageView) tabIndicator.findViewById(R.id.image_view_tab_icon);
		imgIcon.setImageDrawable(drawable);
		
		return tabIndicator;
	}
    private Drawable createTabDrawable(int resId) {
		Resources res = getResources();
		StateListDrawable states = new StateListDrawable();

		final Options options = new Options();
		options.inPreferredConfig = Config.ARGB_8888;
		
		Bitmap icon = BitmapFactory.decodeResource(res, resId, options);
		
		Bitmap unselected = TabBitmap.createUnselectedBitmap(res, icon);
		Bitmap selected = TabBitmap.createSelectedBitmap(res, icon);
		
		icon.recycle();
		
		states.addState(new int[] { android.R.attr.state_selected }, new BitmapDrawable(res, selected));
		states.addState(new int[] { android.R.attr.state_enabled }, new BitmapDrawable(res, unselected));
		
		return states;
	}
    

    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentStatePagerAdapter implements TabHost.OnTabChangeListener, MyViewPager.OnPageChangeListener
    {
    	private Map<Integer,Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();
        private final Context mContext;
       private final TabHost mTabHost;
        public  final MyViewPager mViewPager;
        private static ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    FragmentManager fm;
        static class TabInfo
        {
          
            private  Class<?> clss;
            private Bundle args;
          
            TabInfo(String _tag, Class<?> _class, Bundle _args)
            {
              
                clss = _class;
                args = _args;
              
            }
        }
    
        static class DummyTabFactory implements TabHost.TabContentFactory
        {
            private final Context mContext;

            public DummyTabFactory(Context context)
            {
                mContext = context;
            }

            public View createTabContent(String tag)
            {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, MyViewPager pager)
        {
            super(activity.getSupportFragmentManager());
            fm=activity.getSupportFragmentManager();
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mViewPager.setPagingEnabled(false);
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args,Drawable drawable)
        {
            tabSpec.setContent(new DummyTabFactory(mContext));
          
            String tag = tabSpec.getTag();
           
            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
          
            mTabHost.addTab(tabSpec);
           
            
            notifyDataSetChanged();
        }
       public void addFrag(String tag, Class<?> clss, Bundle args,int position)
        {
           
    	  
		TabInfo info = new TabInfo(tag, clss, args);
           mTabs.add(info);
           Fragment f =Fragment.instantiate(mContext, info.clss.getName());
           
           f.setArguments(info.args);
           mPageReferenceMap.put(Integer.valueOf(position), f);
            notifyDataSetChanged();
        } 
       public void ChangeBundle(Bundle arg,int position)
       {
          
   	
		TabInfo info = mTabs.get(position);
        info.args=arg;
    	
       } 
        @Override
        public int getCount()
        {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            TabInfo info = mTabs.get(position);
            
            Fragment f =Fragment.instantiate(mContext, info.clss.getName());
           
            f.setArguments(info.args);
            mPageReferenceMap.put(Integer.valueOf(position), f);
          
            return f;
            		
                  }
        @Override
        public int getItemPosition(Object object) {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }
        public void onTabChanged(String tabId)
        {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
         
        }
       
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        public void onPageSelected(int position)
        {
           
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        public void onPageScrollStateChanged(int state)
        {
        }
      
        
		public String getFragmentTag1(int currentIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public Fragment getFragmentTag(int currentIndex) {
			// TODO Auto-generated method stub
			return mPageReferenceMap.get(currentIndex);
		}
		
    }

	
}
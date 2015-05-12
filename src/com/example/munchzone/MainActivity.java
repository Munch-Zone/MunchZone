package com.example.munchzone;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_splash_screen);
		   Display display = getWindowManager().getDefaultDisplay(); 
	        int width = display.getWidth();
	        int height = display.getHeight();
	        
	        DisplayMetrics displaymetrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	        height = displaymetrics.heightPixels;
	         width = displaymetrics.widthPixels;
	       
		  check();
	}

	private void check() {
	
		SQLiteDatabase db = openOrCreateDatabase("MunchZone", Context.MODE_PRIVATE, null);
		Cursor c = null;
    	boolean tableExists = false;
    
    	try
    	{
    	    c = db.query("restaurants", null, null, null, null, null, null);
    	        tableExists = true;
    	}
    	catch (Exception e) {
    	    /* fail */
    	   
    	}

    	
    	if(!tableExists)
    	{
    		
    		System.out.println("Table Does not Exists");
    		   ConnectivityManager connec =  
                       (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    		   if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                       connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                       connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                       connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
    			 
    			   Thread mThread = new Thread() {
               	    @Override
               	    public void run() {
               	    	 RestaurantJSONParsing RJP=new RestaurantJSONParsing(MainActivity.this);
               	    	 
               	    	 int temp= RJP.populateResData();
               	    	                 	 	Intent ListDisplay = new Intent(getBaseContext(),MainPanel.class);
                   	    startActivity(ListDisplay);
                   	   
               	    }
               	    };
               	    mThread.start();
               	                 
               } else if ( 
            		   
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            	
                  Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
                  showAlert();
                 setContentView(R.layout.activity_main);
                   
               }
    		   
    	}
    	else
    	{
    		
    		System.out.println("Table Exists");
    		  setContentView(R.layout.activity_splash_screen);
    		 Thread background = new Thread() {
    	            public void run() {
    	                 
    	                try {
    	                 
    	                    synchronized(this){
    	                  sleep(1*1000);
    	                    }
    	                   
    	                    Intent ListDisplay = new Intent(getBaseContext(),MainPanel.class);
    	               	    startActivity(ListDisplay);
    	                                        
    	                   
    	                    finish();
    	                     
    	                } catch (Exception e) {
    	                 
    	                }
    	            }
    	        };
    	         
    	        // start thread
    	        background.start();
    		
    	}
	}
	 public void showAlert(){
	        MainActivity.this.runOnUiThread(new Runnable() {
	            public void run() {
	                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	                builder.setTitle(" Connection Error ");
	                builder.setMessage("Please check your internet connection")  
	                       .setCancelable(false)
	                       .setPositiveButton("OK",new DialogInterface.OnClickListener() {
	                           public void onClick(DialogInterface dialog, int id) {
	                           }
	                       });                     
	                AlertDialog alert = builder.create();
	                alert.show();               
	            }
	        });
	    }
}
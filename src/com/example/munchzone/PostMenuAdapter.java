package com.example.munchzone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class PostMenuAdapter extends ArrayAdapter<PostMenuData> {
    private Activity myContext;
    private PostMenuData[] datas;
    public PostMenuAdapter(Context context, int textViewResourceId,PostMenuData[] objects) {
        super(context, textViewResourceId, objects);
       
        myContext = (Activity) context;
        datas = objects;
      
       
    }
   
	public View getView(int position, View convertView, ViewGroup parent) {
		
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rest_menu_item, null);
        
        TextView postMenuName = (TextView) rowView
                .findViewById(R.id.rest_item_name);
        postMenuName.setText(datas[position].menu_name);
        TextView postMenuId = (TextView) rowView
                .findViewById(R.id.rest_item_id);
        postMenuId.setText(datas[position].menu_id);
        TextView postMenuPrice = (TextView) rowView
                .findViewById(R.id.rest_item_price);
        postMenuPrice.setText(Integer.toString(datas[position].menu_price));
     
return rowView;
    }

}

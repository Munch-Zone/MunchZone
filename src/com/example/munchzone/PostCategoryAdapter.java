package com.example.munchzone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class PostCategoryAdapter extends ArrayAdapter<PostCategoryData> {
    private Activity myContext;
    private PostCategoryData[] datas;
    public PostCategoryAdapter(Context context, int textViewResourceId,PostCategoryData[] objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;
        
    }
   
	public View getView(int position, View convertView, ViewGroup parent) {
		
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.category_item, null);
        
        TextView postCatName = (TextView) rowView
                .findViewById(R.id.cat_name);
        postCatName.setText(datas[position].cat_name);
        TextView postCatId = (TextView) rowView
                .findViewById(R.id.cat_id);
        postCatId.setText(datas[position].cat_id);
       
return rowView;
    }

}

package example.hfa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	
	private Integer[] mThumbIds = {
            R.drawable.ic_launcher,R.drawable.content
    };
	Context mContext;
    ImageView imageView;
	public ImageAdapter(Context c){
		mContext= c;	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mThumbIds[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		  if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            
	            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
	           
	            // imageView.setAdjustViewBounds(true);
	            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	            imageView.setPadding(5, 5, 5, 5);
	            
	            
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	}

}

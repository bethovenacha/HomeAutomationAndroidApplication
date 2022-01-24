package example.hfa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridView extends Activity {
	
	// references to our images
	private Integer[] mThumbIds = {
			R.drawable.androider_01,
			R.drawable.androider_02,
			};
	
    public class MyAdapter extends BaseAdapter {
    	
    	private Context mContext;

		public MyAdapter(Context c) {
			// TODO Auto-generated constructor stub
			mContext = c;
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
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			View grid;
			 
			if(convertView==null){
				grid = new View(mContext);
				LayoutInflater inflater=getLayoutInflater();
				grid=inflater.inflate(R.layout.row_grid, parent, false);
			}else{
				grid = (View)convertView;
			}
			
			ImageView imageView = (ImageView)grid.findViewById(R.id.imgGridImage);
			TextView textView = (TextView)grid.findViewById(R.id.tvGridRowTitle);
			imageView.setImageResource(mThumbIds[position]);
			textView.setText(String.valueOf(position));

			return grid;
		}

	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        
        GridView gridview = (GridView) findViewById(R.id.gvFacilities);
    

    }
}
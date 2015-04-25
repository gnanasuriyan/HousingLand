package land.housingland.com.housingland;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by hareef on 23/04/15.
 */
public class HighlightAdapter extends ArrayAdapter<LandPlace> {
    Context context;
    int layoutResourceId;
    Bitmap resized;
    // BcardImage data[] = null;
    ArrayList<LandPlace> data=new ArrayList<LandPlace>();
    public HighlightAdapter(MapsActivity context, int layoutResourceId, ArrayList<LandPlace> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imglist);


            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        LandPlace picture = data.get(position);
        //convert byte to bitmap take from contact class



//        byte[] outImage=picture._image;
//        ByteArrayInputStream imageStream;
//        File imgFile = new File("");
//        imageStream = new ByteArrayInputStream(imgFile.);
//        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//        resized = Bitmap.createScaledBitmap(theImage, 120, 150, true);
//
//        holder.imgIcon.setImageBitmap(resized);
        return row;

    }

    static class ImageHolder
    {
        ImageView imgIcon;


    }
}


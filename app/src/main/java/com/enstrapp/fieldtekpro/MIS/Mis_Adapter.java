package com.enstrapp.fieldtekpro.MIS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;


public class Mis_Adapter extends BaseAdapter
{

    String[] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public Mis_Adapter(Mis_Activity mainActivity, String[] osNameList, int[] osImages)
    {
        result = osNameList;
        context= mainActivity;
        imageId = osImages;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return result.length;
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    public class Holder
    {
        TextView os_text;
        ImageView os_img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.mis_adapter,null);
        holder.os_text =(TextView) rowView.findViewById(R.id.mgrid_text);
        holder.os_img =(ImageView) rowView.findViewById(R.id.mgrid_image);

        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        return rowView;
    }

}

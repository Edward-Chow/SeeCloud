package com.example.seecloud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seecloud.database.CloudServer;

import java.util.List;

/**
 * Created by monkeysmac on 2017/4/13.
 */

public class CloudsAdapter extends ArrayAdapter<CloudServer> {

    private int resourceId;

    public CloudsAdapter(Context context, int textViewResourceId, List<CloudServer> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CloudServer cloud = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cloudImage = (ImageView) view.findViewById(R.id.cloud_image);
            viewHolder.cloudName = (TextView) view.findViewById(R.id.cloud_name);
            viewHolder.cloudNo = (TextView) view.findViewById(R.id.rank_num);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.cloudImage.setImageResource(cloud.getImageID());
        viewHolder.cloudNo.setText(String.valueOf(cloud.getCloudID()+1));
        viewHolder.cloudName.setText(cloud.getName());
        return view;
    }

    class ViewHolder{

        ImageView cloudImage;

        TextView cloudNo;

        TextView cloudName;
    }
}

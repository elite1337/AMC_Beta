package com.example.pete.amc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteArrayAdapter extends ArrayAdapter<FavoriteRow> {

    public static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
    }

    public FavoriteArrayAdapter(Context context, ArrayList<FavoriteRow> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FavoriteRow note = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favorite_row, parent, false);

            viewHolder.textView = (TextView)convertView.findViewById(R.id.title);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(note.getTitle());
        viewHolder.imageView.setImageResource(note.getAssociatedDrawable());

        return convertView;
    }
}

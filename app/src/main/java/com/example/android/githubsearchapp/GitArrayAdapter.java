package com.example.android.githubsearchapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shereen on 10/21/2017.
 */

public class GitArrayAdapter extends ArrayAdapter<ItemsItem> {
  private   List<ItemsItem> items=new ArrayList<>();

    public void setItems(List<ItemsItem> items) {
        this.items = items;
    }

    @Nullable
    @Override
    public ItemsItem getItem(int position) {
        return items.get(position);
    }

    public GitArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItemsItem> objects) {
        super(context, resource, objects);
        items=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
//            the first time you create a view holder
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
//            provide the view holder's attribute with values using findViewById
            viewHolder.RepositoryName = (TextView) convertView.findViewById(R.id.repo_name_text_view);
            viewHolder.OwnerName = (TextView) convertView.findViewById(R.id.owner_name_text_view);
//save the view holder in the view's Tag
            convertView.setTag(viewHolder);
        } else {
//            if you're recycling views no need to use findViewById just get the value stored in Tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemsItem item = getItem(position);
        viewHolder.RepositoryName.setText(item.getName());
        viewHolder.OwnerName.setText(item.getOwner().getLogin());

        return convertView;
    }

    //    define this static class to prevent using findviewbyId for every getView call
    private static class ViewHolder {
        TextView RepositoryName;
        TextView OwnerName;

    }

}

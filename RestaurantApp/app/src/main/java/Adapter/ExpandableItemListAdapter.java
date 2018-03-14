package Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import day01.swomfire.restaurantapp.R;

/**
 * Created by Swomfire on 08-Mar-18.
 */

public class ExpandableItemListAdapter extends ExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private int lastExpandedGroup;

    public ExpandableItemListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        super(context, listDataHeader, listHashMap);
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (b) {
            lastExpandedGroup = i;
            view = layoutInflater.inflate(R.layout.item_tab_list_group_expanded, null);
        } else {
            view = layoutInflater.inflate(R.layout.item_tab_list_group, null);

        }
        TextView lblListHeader = (TextView) view.findViewById(R.id.lblListItemHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        switch (i % 4) {
            case 0:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor1));
                break;
            case 1:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor2));
                break;
            case 2:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor3));
                break;
            case 3:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor4));
                break;
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_tab_list_item, null);
        }

        TextView lblListChild = (TextView) view.findViewById(R.id.lblListItem);
        lblListChild.setText(childText);
        switch (i % 4) {
            case 0:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor1));
                break;
            case 1:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor2));
                break;
            case 2:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor3));
                break;
            case 3:
                view.setBackgroundColor(view.getResources().getColor(R.color.colorItemGroupColor4));
                break;
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return super.isChildSelectable(i, i1);
    }
}

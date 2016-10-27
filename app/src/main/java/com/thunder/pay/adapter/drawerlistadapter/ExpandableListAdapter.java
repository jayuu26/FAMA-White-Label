package com.thunder.pay.adapter.drawerlistadapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thunder.pay.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    private List<String> mListDataHeader; // header titles
    TypedArray iconListDataHeader;
    TypedArray selectedIconListDataHeader;
    int previousGroup = 0;
    ImageView headerIcon;
    ImageView arrow;
    TextView lblListHeader;
    OnSelectedListner listener;
    int childCount = 0;

    // child data in format of header title, child title
    private HashMap<String, List<String>> mListDataChild;
    ExpandableListView expandList;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, TypedArray iconListDataHeader, HashMap<String, List<String>> listChildData, ExpandableListView mView,OnSelectedListner listener) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
        this.iconListDataHeader = iconListDataHeader;
        selectedIconListDataHeader = mContext.getResources().obtainTypedArray(R.array.nav_sel_icon);
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (groupPosition == 3 || groupPosition == 4 || groupPosition == 5) {
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final int  position =groupPosition;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_expandable, null);
        }
        lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);
        headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        arrow = (ImageView) convertView.findViewById(R.id.down);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(mListDataHeader.get(groupPosition));

        if(groupPosition ==3 || groupPosition==4 || groupPosition==5){
            arrow.setVisibility(View.VISIBLE);
        }else{
            arrow.setVisibility(View.GONE);
        }
        try {
            headerIcon.setImageDrawable(null);
            headerIcon.setImageResource(iconListDataHeader.getResourceId(groupPosition,-1));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isExpanded) {
            headerIcon.setImageDrawable(null);
            arrow.setImageDrawable(null);
            headerIcon.setImageResource(selectedIconListDataHeader.getResourceId(groupPosition,-1));
            arrow.setImageResource(R.drawable.arrow_up);
            lblListHeader.setTextColor(Color.parseColor("#ffa200"));
        } else {
            headerIcon.setImageDrawable(null);
            headerIcon.setImageResource(iconListDataHeader.getResourceId(groupPosition,-1));
            lblListHeader.setTextColor(Color.parseColor("#A1B9CC"));
            arrow.setImageDrawable(null);
            arrow.setImageResource(R.drawable.arrow_frwd);
        }
        expandList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if(groupPosition != previousGroup)
                    expandList.collapseGroup(previousGroup);
                previousGroup = groupPosition;
                notifyDataSetChanged();
                listener.onParentItemClicked(groupPosition,0,isExpanded);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_child_expandable, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);

        expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                listener.onChildItemClicked(groupPosition,childPosition);
                return false;
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface OnSelectedListner {

         void onParentItemClicked(int groupPosition, int childPosition, boolean isExpand);
        void onChildItemClicked(int groupPosition, int childPosition);

    }
}

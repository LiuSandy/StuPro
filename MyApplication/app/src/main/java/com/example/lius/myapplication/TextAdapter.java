package com.example.lius.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lius on 2018/2/11.
 */

public class TextAdapter extends BaseAdapter {
    private List<ListData> lists;
    private Context mContext;

    private RelativeLayout layout;

    public TextAdapter(List<ListData> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (lists.get(i).getFlag() == ListData.RECEIVER) {
            layout = (RelativeLayout) inflater.inflate(R.layout.leftitem, null);
        }
        if (lists.get(i).getFlag() == ListData.SEND) {
            layout = (RelativeLayout) inflater.inflate(R.layout.rightitem, null);
        }
        TextView tv = layout.findViewById(R.id.tv);
        TextView time = layout.findViewById(R.id.time);
        tv.setText(lists.get(i).getContent());
        time.setText(lists.get(i).getTime());
        return layout;
    }
}

package com.team2.wechat.contactlist;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team2.wechat.R;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by wjsay on 2017/12/29.
 */

public class AddressAdapter extends ArrayAdapter<AddressItem> {
    private int resourceId;
    public AddressAdapter(Context context, int textViewResourceId,
                       List<AddressItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressItem address = getItem(position);// 获取当前项的AddressItem实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView tv_address_title = view.findViewById(R.id.tv_title_type);
        ImageView iv_address_icon = view.findViewById(R.id.iv_address_icon);
        TextView tv_address_name = view.findViewById(R.id.tv_address_name);
        tv_address_title.setText(address.getTitleName());
        if(address.getTitleVisible() == View.INVISIBLE) {
            tv_address_title.setVisibility(View.GONE);
        }
        iv_address_icon.setImageURI(address.getImageUri());
        tv_address_name.setText(address.getName());

        return view;
    }
}

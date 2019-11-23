package com.ampinityit.Brahmaent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    DBHelper controldb;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    //private ArrayList<String> Account = new ArrayList<String>();
    //private ArrayList<String> Branchname = new ArrayList<String>();

    public CustomAdapter(Context  context,ArrayList<String> Id/*,ArrayList<String> Account, ArrayList<String> Branchname*/)
    {
        this.mContext = context;
        this.Id = Id;
        //this.Account = Account;
        //this.Branchname = Branchname;

    }
    @Override
    public int getCount() {
        return Id.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        controldb =new DBHelper(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.txtId);
            //holder.account = (TextView) convertView.findViewById(R.id.txtAccount);
            //holder.branchname = (TextView) convertView.findViewById(R.id.txtBranchname);

            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.id.setText(Id.get(position));
        //holder.account.setText(Account.get(position));
        //holder.branchname.setText(Branchname.get(position));

        return convertView;
    }
    public class viewHolder {
        TextView id;
        //TextView account;
        //TextView branchname;

    }
}

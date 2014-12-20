package com.liferay.elasticsearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

	public UserListAdapter(Context context, int resource, List<User> users) {
		super(context, resource, users);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView)super.getView(position, convertView, parent);

		User user = getItem(position);

		view.setText(user.firstName + " " + user.lastName);

		return view;
	}
}

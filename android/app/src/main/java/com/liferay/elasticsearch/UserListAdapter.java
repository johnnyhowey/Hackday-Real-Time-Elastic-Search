package com.liferay.elasticsearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<JSONObject> {

	public UserListAdapter(
		Context context, int resource, List<JSONObject> users) {

		super(context, resource, users);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView)super.getView(position, convertView, parent);

		JSONObject user = getItem(position);

		String name = user.toString();

		try {
			name = user.getString("name");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		view.setText(name);

		return view;
	}
}

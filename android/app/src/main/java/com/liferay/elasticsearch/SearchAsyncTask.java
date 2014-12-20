package com.liferay.elasticsearch;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class SearchAsyncTask extends AsyncTask<Object, Void, List<User>> {

	public SearchAsyncTask(UserListAdapter adapter, String query) {
		_adapter = adapter;
		_query = query;
	}

	@Override
	protected List<User> doInBackground(Object... params) {
		List<User> users = new ArrayList<User>();

		try {
			JSONArray fields = new JSONArray();
			fields.put("firstName");
			fields.put("lastName");

			JSONObject child = new JSONObject();
			child.put("fields", fields);
			child.put("like_text", _query);
			child.put("max_query_terms", 12);

			JSONObject fuzzy = new JSONObject();
			fuzzy.put("fuzzy_like_this", child);

			JSONObject root = new JSONObject();
			root.put("query", fuzzy);

			DroidClientConfig clientConfig = new DroidClientConfig.Builder(
				"http://172.16.17.251:9200").build();

			JestClientFactory jestClientFactory = new JestClientFactory();
			jestClientFactory.setDroidClientConfig(clientConfig);

			JestClient client = jestClientFactory.getObject();

			Search search = new Search.Builder(root.toString())
				.addIndex("hackday")
				.build();

			SearchResult result = client.execute(search);

			users = result.getSourceAsObjectList(User.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	protected void onPostExecute(List<User> users) {
		_adapter.clear();
		_adapter.addAll(users);
	}

	private UserListAdapter _adapter;
	private String _query;

}
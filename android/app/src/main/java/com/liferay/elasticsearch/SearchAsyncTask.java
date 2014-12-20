package com.liferay.elasticsearch;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

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
			JSONObject queryString = new JSONObject();
			queryString.put("query", _query);

			JSONObject query = new JSONObject();
			query.put("query_string", queryString);

			JSONObject root = new JSONObject();
			root.put("query", query);

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
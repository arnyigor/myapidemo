package com.arny.myapidemo.view.adapters;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ParseJSON {
	public static String[] ids;
	public static String[] logins;
	public static String[] repos;

	public static final String JSON_ARRAY = "items";
	public static final String KEY_ID = "id";
	public static final String KEY_LOGIN = "login";
	public static final String KEY_REPOS = "repos_url";

	private JSONArray users = null;

	private String json;

	public ParseJSON(String json){
		this.json = json;
	}

	public void parseJSON(){
		JSONObject jsonObject=null;
		try {
			jsonObject = new JSONObject(json);
			users = jsonObject.getJSONArray(JSON_ARRAY);

			ids = new String[users.length()];
			logins = new String[users.length()];
			repos = new String[users.length()];

			for(int i=0;i<users.length();i++){
				JSONObject jo = users.getJSONObject(i);
				ids[i] = jo.getString(KEY_ID);
				logins[i] = jo.getString(KEY_LOGIN);
				repos[i] = jo.getString(KEY_REPOS);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
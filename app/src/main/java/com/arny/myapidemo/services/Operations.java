package com.arny.myapidemo.services;

import android.util.Log;

import com.arny.myapidemo.models.TestObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.arny.arnylib.network.NetworkService;
import com.arny.arnylib.network.OnStringRequestResult;
import com.arny.arnylib.service.AbstractIntentService;
import com.arny.arnylib.service.OperationProvider;
import com.arny.arnylib.utils.Params;
import com.arny.arnylib.utils.Utility;

public class Operations extends AbstractIntentService {
    public static final String API_BASE_URL = "https://pik.ru/luberecky/";
    public static final String API_URL_GEN_PLAN = "datapages?data=GenPlan";
    public static final String API_URL_SINGLE_PAGE = "singlepage?data=ChessPlan&format=json&domain=pik.ru&id=";
    public Operations() {
        super();
    }

    @Override
    protected void runOperation(OperationProvider provider, OnOperationResult operationResult) {
        int operationId = provider.getId();
        Log.d(Operations.class.getSimpleName(), "operationId: " + operationId);
        switch (operationId) {
            case 2:
                NetworkService.apiRequest(getApplicationContext(), "http://www.json-generator.com/api/json/get/cngBQrGyhu?indent=2", new JSONObject(), new OnStringRequestResult() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
	                    try {
		                    Params params = new Params(result);
		                    Log.d(Operations.class.getSimpleName(), "onSuccess: " + params.getParams());
		                    JSONObject jsonObject = new JSONObject();
		                    jsonObject.put("testparam", "1111");
		                    JSONArray jsonArray = new JSONArray();
		                    jsonArray.put(999);
		                    jsonArray.put("Strings");
		                    jsonArray.put(jsonObject);
		                    Log.d(Operations.class.getSimpleName(), "onSuccess: jsonArray = " + jsonArray);
		                    params.setParam("boom.coop.snoose", jsonObject);
//		                    Log.d(Operations.class.getSimpleName(), "onSuccess: " + params.getParams());
	                    } catch (JSONException e) {
		                    e.printStackTrace();
	                    }
	                    TestObject testObject = gson.fromJson(result, TestObject.class);
                        testObject.setTitle("new title");
                        try {
                            JSONObject checkObject = new JSONObject(gson.fromJson(result, JsonElement.class).toString());
//	                        Log.d(Operations.class.getSimpleName(), "onSuccess: checkObject = " + checkObject);
	                        ArrayList<String> checkKeys = Utility.getJsonArrayKeys(checkObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.d(Operations.class.getSimpleName(), "onError: error = " + error);
                    }
                });
                break;
        }
    }

    private void parseResultGSON(String result) {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson (result, JsonElement.class).getAsJsonObject();
        JsonElement elem = jsonObj.get("titleOfCorps");
        JsonArray sections = jsonObj.getAsJsonArray("sections");
        for (int i = 0; i < sections.size(); i++) {
            String sectionName = sections.get(i).getAsJsonObject().get("name").getAsString();
            Log.d(Operations.class.getSimpleName(), "parseResultGSON: sectionName = " + sectionName);
        }
        Log.d(Operations.class.getSimpleName(), "parseResultGson: time = " + (System.currentTimeMillis() - start));
    }

    private void parseResultJSON(String result){
        long start = System.currentTimeMillis();
        JSONObject object;
        try {
            object = new JSONObject(result);
            String elem = object.getString("titleOfCorps");
            JSONArray sections = object.getJSONArray("sections");
            JSONObject section = sections.getJSONObject(0);
            for (int i = 0; i < sections.length(); i++) {
                String sectionName = sections.getJSONObject(i).get("name").toString();
                Log.i(Operations.class.getSimpleName(), "parseResultJSON: sectionName = " + sectionName);
            }
            Log.d(Operations.class.getSimpleName(), "parseResultJSON: time = " + (System.currentTimeMillis() - start));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getKorpuses() {
       NetworkService.apiRequest(getApplicationContext(), API_BASE_URL +API_URL_GEN_PLAN , new JSONObject(), new OnStringRequestResult() {
           @Override
           public void onSuccess(String result) {
               JSONArray genPlanArray;
               try {
                   genPlanArray = new JSONArray(result);
                   JSONObject genPlanObj = new JSONObject(genPlanArray.get(0).toString()).getJSONObject("data");
                   JSONArray kopruses = new JSONArray(genPlanObj.get("sets_of_pathes").toString());
                   parseKorpuses(kopruses);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onError(String error) {

           }
       });
    }

    private void parseKorpuses(final JSONArray korpuses) throws JSONException {
        for (int i = 0; i < korpuses.length(); i++) {
            JSONObject korpusObject = new JSONObject(korpuses.get(i).toString());
            NetworkService.apiRequest(getApplicationContext(), API_BASE_URL + API_URL_SINGLE_PAGE + korpusObject.getString("id"), new JSONObject(), new OnStringRequestResult() {
                @Override
                public void onSuccess(String result) {
                    Log.i(Operations.class.getSimpleName(), "onSuccess: result = " + result.length());
                }

                @Override
                public void onError(String error) {
                    Log.d(Operations.class.getSimpleName(), "onError: error = " + error);
                }
            });
        }
    }
}

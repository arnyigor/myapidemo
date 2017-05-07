package com.arny.myapidemo.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import pw.aristos.arnylib.network.NetworkService;
import pw.aristos.arnylib.network.MyResultReceiver;
import pw.aristos.arnylib.network.OnStringRequestResult;
import pw.aristos.arnylib.service.AbstractIntentService;
import pw.aristos.arnylib.service.OperationProvider;

public class Operations extends AbstractIntentService {
    private MyResultReceiver receiver;
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
            case 1:
                String test_url ="https://pik.ru/luberecky/singlepage?data=ChessPlan&id=2b3ecc9b-bfad-e611-9fbe-001ec9d5643c&private_key=1&format=json&domain=pik.ru";
                    NetworkService.apiRequest(getApplicationContext(), test_url, new JSONObject(), new OnStringRequestResult() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d(Operations.class.getSimpleName(), "onSuccess: result = " + result.length());
                            parseResultGSON(result);
                        }

                        @Override
                        public void onError(String error) {
                            Log.d(Operations.class.getSimpleName(), "onError: error = " + error);
                        }
                    });
                break;
            case 2:
                Document doc = null;
                try {
                    doc = Jsoup.connect("http://www.dsk1.ru/novostroyki/nekrasovka/ceny-na-kvartiry/").get();
                    Elements pages = doc.getElementsByClass("pages");
                    Log.d(Operations.class.getSimpleName(), "runOperation: pages - " + pages);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

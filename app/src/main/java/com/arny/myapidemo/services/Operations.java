package com.arny.myapidemo.services;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pw.aristos.arnylib.network.ApiService;
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
                    ApiService.apiRequest(getApplicationContext(), test_url, new JSONObject(), new OnStringRequestResult() {
                        @Override
                        public void onSuccess(String result) {
//                            Log.d(Operations.class.getSimpleName(), "onSuccess: result = " + result.length());
                            receiver = new MyResultReceiver(new Handler());
                            Bundle bundle = new Bundle();
                            bundle.putString("result", "test_test_test");
                            receiver.send(Activity.RESULT_OK, bundle);
                        }

                        @Override
                        public void onError(String error) {
                            Log.d(Operations.class.getSimpleName(), "onError: error = " + error);
                        }
                    });
                break;
            case 2:
                getKorpuses();
                break;
        }
    }

    private void getKorpuses() {
       ApiService.apiRequest(getApplicationContext(), API_BASE_URL +API_URL_GEN_PLAN , new JSONObject(), new OnStringRequestResult() {
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
            ApiService.apiRequest(getApplicationContext(), API_BASE_URL + API_URL_SINGLE_PAGE + korpusObject.getString("id"), new JSONObject(), new OnStringRequestResult() {
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

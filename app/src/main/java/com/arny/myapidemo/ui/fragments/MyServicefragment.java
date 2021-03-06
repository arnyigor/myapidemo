package com.arny.myapidemo.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.arny.arnylib.service.AbstractIntentService;
import com.arny.arnylib.service.OperationProvider;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.activities.TabsActivity;
import com.arny.myapidemo.services.Operations;


public class MyServicefragment extends Fragment implements View.OnClickListener, FragmentCommunicator {

    private static final String DATA_SAVE_STATE = "data_save_update";
    private static final String TAG = MyServicefragment.class.getSimpleName();
    private Context context;
    private Button btnOper1;
    private TextView tvInfo1;
    private Button btnOper2;
    private Button btnOper3;
    private TextView tvInfo2;
    private TextView tvInfo3;
    private Intent intent;
	private ActivityCommunicator activityCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);
        context = container.getContext();
        intent = new Intent(context, Operations.class);
        initUI(rootView);
        return rootView;
    }

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		context = getActivity();
		activityCommunicator =(ActivityCommunicator)context;//send to activity
        if (getActivity() != null) {
            ((TabsActivity)getActivity()).fragmentCommunicator = this;//recieve from activity
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void initUI(View rootView) {
        Log.i(TAG, "initUI: ");
        btnOper1 = rootView.findViewById(R.id.btnOper1);
        btnOper2 = rootView.findViewById(R.id.btnOper2);
        btnOper3 = rootView.findViewById(R.id.btnOper3);
        btnOper1.setOnClickListener(this);
        btnOper2.setOnClickListener(this);
        btnOper3.setOnClickListener(this);
        tvInfo1 = rootView.findViewById(R.id.tvInfo1);
        tvInfo2 = rootView.findViewById(R.id.tvInfo2);
        tvInfo3 = rootView.findViewById(R.id.tvInfo3);
    }

    private void showDialog(String result) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(result);
        dlg.setNegativeButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = dlg.create();
        alert.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        IntentFilter filter = new IntentFilter(Operations.ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(context).registerReceiver(updateReciever, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        LocalBroadcastManager.getInstance(context).unregisterReceiver(updateReciever);
    }


    private BroadcastReceiver updateReciever = new BroadcastReceiver() {
        public boolean success,operationFinished;
        public int operation;
        public String operationResult = "";
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
	        Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras = " + extras);
	        if (extras != null) {
	            OperationProvider provider = (OperationProvider)extras.getSerializable(AbstractIntentService.EXTRA_KEY_OPERATION) ;
                operationFinished = provider.isFinished();
                success = provider.isSuccess();
                operation = provider.getId();
                operationResult = provider.getResult();
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operation= " + operation);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationFinished= " + operationFinished);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras success= " + success);
//                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras getOperationData= " + provider.getOperationData());
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: extras operationResult= " + operationResult);
                Log.i(MyServicefragment.class.getSimpleName(), "onReceive: time = " + DateTimeUtils.getDateTime());
            }
            if (operationFinished) {
                ToastMaker.toastSuccess(context,"Результат операции " + operation + ":"+ operationResult);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOper1:
                Operations.onStartOperation(intent,context,Operations.EXTRA_KEY_TYPE_ASYNC,1,null);
                break;
	        case R.id.btnOper2:
		        activityCommunicator.passDataToActivity("hello from service fragment");
                break;
        }
    }

	@Override
	public void passDataToFragment(String someValue) {
		Log.i(MyServicefragment.class.getSimpleName(), "passDataToFragment: someValue = " + someValue);
	}
}
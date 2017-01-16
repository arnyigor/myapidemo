package com.arny.myapidemo.activities;//Package name

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.Dialog1;
import com.arny.myapidemo.fragments.Dialog2;

public class DialogActivity extends Activity {
	DialogFragment dlg1;
	DialogFragment dlg2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		setTitle("dialog");
		dlg1 = new Dialog1();
		dlg2 = new Dialog2();

	}

	public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.btnDlg1:
	      dlg1.show(getFragmentManager(), "dlg1");
	      break;
	    case R.id.btnDlg2:
	      dlg2.show(getFragmentManager(), "dlg2");
	      break;
	    default:
	      break;
	    }
	}
}

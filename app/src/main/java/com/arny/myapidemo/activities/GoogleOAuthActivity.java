package com.arny.myapidemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.androidnetworking.common.Method;
import com.arny.arnylib.interfaces.OnJSONObjectResult;
import com.arny.arnylib.interfaces.OnStringRequestResult;
import com.arny.arnylib.network.NetworkService;
import com.arny.myapidemo.R;
import com.arny.myapidemo.utils.ToastMaker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleOAuthActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

	private static final int RC_AUTH_CODE = 101;
	private SignInButton signInButton;
	private GoogleApiClient mApiClient;
	private String mAccessToken;
	private String mTokenType;
	private String mRefreshToken;
	private Button button4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_oauth);
		initUI();
		initGoogleoAuthAPI();
	}

	private void initGoogleoAuthAPI() {
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestServerAuthCode(getString(R.string.server_client_id))
				.requestEmail()
				.requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
				.build();

		mApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, this)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
	}

	private void initUI() {
		signInButton = (SignInButton) findViewById(R.id.activity_button_sign_in);
		button4 = (Button) findViewById(R.id.button4);
		signInButton.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Log.d(GoogleOAuthActivity.class.getSimpleName(), "onConnectionFailed: connectionResult.getErrorMessage() = " + connectionResult.getErrorMessage());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.activity_button_sign_in:
				signIn();
				break;
			case R.id.button4:
				NetworkService.apiRequest(GoogleOAuthActivity.this, Method.GET,"https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + mAccessToken, null, new OnStringRequestResult() {
					@Override
					public void onSuccess(String result) {
						ToastMaker.toast(GoogleOAuthActivity.this,result);
					}

					@Override
					public void onError(String error) {
						ToastMaker.toast(GoogleOAuthActivity.this,error);
					}
				});
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RC_AUTH_CODE) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			Log.i(GoogleOAuthActivity.class.getSimpleName(), "onActivityResult: result = " + result);
			if (result.isSuccess()) {
				GoogleSignInAccount acct = result.getSignInAccount();
				String authCode = null;
				if (acct != null) {
					authCode = acct.getServerAuthCode();
				}
				try {
					getAccessToken(authCode);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getAccessToken(String authCode) throws JSONException {
		JSONObject params = new JSONObject();
		params.put("grant_type","authorization_code");
		params.put("client_id",getString(R.string.server_client_id));
		params.put("client_secret",getString(R.string.server_client_id));
		params.put("client_secret",getString(R.string.client_secret));
		params.put("code",authCode);
		JSONObject headers = new JSONObject();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		NetworkService.apiRequest(GoogleOAuthActivity.this, Method.POST,"https://www.googleapis.com/oauth2/v4/token", params,headers, new OnJSONObjectResult() {
			@Override
			public void onSuccess(JSONObject object) {
				try {
					mAccessToken = object.get("access_token").toString();
					mTokenType = object.get("token_type").toString();
					mRefreshToken = object.get("refresh_token").toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String error) {
				ToastMaker.toast(GoogleOAuthActivity.this,error);
			}
		});
	}

	private void getNewAccessToken() throws JSONException {
		JSONObject params = new JSONObject();
		params.put("refresh_token",mRefreshToken);
		params.put("client_id",getString(R.string.server_client_id));
		params.put("client_secret",getString(R.string.client_secret));
		params.put("grant_type","refresh_token");
		JSONObject headers = new JSONObject();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		NetworkService.apiRequest(GoogleOAuthActivity.this, Method.POST,"https://www.googleapis.com/oauth2/v4/token", params, headers, new OnJSONObjectResult() {
			@Override
			public void onSuccess(JSONObject object) {
				try {
					mAccessToken = object.get("access_token").toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String error) {
				ToastMaker.toast(GoogleOAuthActivity.this,error);
			}
		});
	}

	private void signIn() {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
		startActivityForResult(signInIntent, RC_AUTH_CODE);
	}
}

package com.ulta.core.pushnotification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ulta.core.conf.WebserviceConstants;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class PushNotificationGCMRegister {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	public RegistrationIdInterface mRegistrationIdInterface;
	
	private String PROPERTY_APP_VERSION = "appversion";
	
	private boolean mAlreadyRegistered = false;

	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context mContext;

	String regid;

	private String TAG = "GCMClientActivity";

	public PushNotificationGCMRegister(Activity context) {
		mContext = context;
	}

	public void getRegistrationId() {

		mRegistrationIdInterface = (RegistrationIdInterface) mContext;
		// Check device for Play Services APK.
		if (checkPlayServices()) {
			// If this check succeeds, proceed with normal processing.
			// Otherwise, prompt user to get valid Play Services APK.

			gcm = GoogleCloudMessaging.getInstance(mContext);
			regid = getRegistrationId(mContext);

			System.out.println("Registration ID on create :" + regid);

			if (regid.isEmpty()) {
				registerInBackground();
			} else {
				mAlreadyRegistered = true;
				mRegistrationIdInterface.OnRegistrationIdReceived(regid,mAlreadyRegistered);
			}
		}
	}

	public boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(mContext);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						(Activity) mContext, PLAY_SERVICES_RESOLUTION_REQUEST)
						.show();
			} else {
				Log.i(TAG, "This device is not supported.");
				// finish();
			}
			return false;
		}
		return true;
	}

	public String getRegistrationId(Context context) {
		final SharedPreferences prefs = mContext.getSharedPreferences(
				WebserviceConstants.REG_ID_PREF, 0);
		String registrationId = prefs.getString(
				WebserviceConstants.PUSH_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public void registerInBackground() {

		new AsyncTask<Void, Integer, String>() {

			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(mContext);
					}
					regid = gcm.register(WebserviceConstants.SENDER_ID);
					msg = regid;
					storeRegistrationId(mContext, regid);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				System.out.println("Registration Id from GCM :" + msg);
				mAlreadyRegistered = false;
				mRegistrationIdInterface.OnRegistrationIdReceived(msg,mAlreadyRegistered);
			}

		}.execute();
	}

	public void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = mContext.getSharedPreferences(
				WebserviceConstants.REG_ID_PREF, 0);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(WebserviceConstants.PUSH_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

}

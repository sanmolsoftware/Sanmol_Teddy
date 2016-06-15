package com.ulta.core.pushnotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ulta.R;
import com.ulta.core.activity.account.WebViewActivity;
import com.ulta.core.activity.product.HomeActivity;
import com.ulta.core.conf.WebserviceConstants;
import com.ulta.core.util.Utility;

public class GCMNotificationIntentService extends IntentService {
	// Sets an ID for the notification, so it can be updated
	// public static final int notifyID = 9001;
	public static final int notifyID = 3008;
	NotificationCompat.Builder builder;
	
	private SharedPreferences mPushNotificationSharedPrefs;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);
		mPushNotificationSharedPrefs = getSharedPreferences(
				WebserviceConstants.PUSH_NOTIFICATION_SHAREDPREF, MODE_PRIVATE);
		boolean isPushNotificationOn = mPushNotificationSharedPrefs
				.getBoolean(WebserviceConstants.IS_PUSH_NOTIFICATION_ON, true);
		
		if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType) && isPushNotificationOn) {
			sendNotification(" " + extras.get(WebserviceConstants.MESSAGE), " "
					+ extras.get(WebserviceConstants.TITLE),
					" " + extras.get(WebserviceConstants.PARAMETER), " "
							+ extras.get(WebserviceConstants.TYPE));
			// System.out.println(" "+extras.get(WebserviceConstants.TITLE)+""+extras.get(WebserviceConstants.PARAMETER)+extras.get(WebserviceConstants.MESSAGE)+""+extras.get(WebserviceConstants.TYPE));
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg, String title, String value,
			String type) {

		try {
			// This will fetch the intent from Utility class, where app has to
			// navigate
			if (msg != null && title != null && type != null && value != null) {
				if (!msg.trim().equalsIgnoreCase("null")
						&& !title.trim().equalsIgnoreCase("null")
						&& !type.trim().equalsIgnoreCase("null")
						&& !value.trim().equalsIgnoreCase("null")) {

					Intent resultIntent = new Intent(this, HomeActivity.class);

					// Global message
					if (type.trim().equalsIgnoreCase("url")) {

						resultIntent = new Intent(this, WebViewActivity.class);
						resultIntent.putExtra("navigateToWebView",
								WebserviceConstants.FROM_PUSHNOTIFICATION);
						resultIntent.putExtra("url", value);
						resultIntent.putExtra("title", "");

					}
					// Deep linking
					else if (type.trim().equalsIgnoreCase("section")) {
						if (value != null) {
							resultIntent = Utility.navigateToPage(this, value,
									null);
						}
					}

					PendingIntent resultPendingIntent = PendingIntent
							.getActivity(this, 0, resultIntent,
									PendingIntent.FLAG_ONE_SHOT);

					NotificationCompat.Builder mNotifyBuilder;
					NotificationManager mNotificationManager;

					mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

					// Resources res = getResources();
					mNotifyBuilder = new NotificationCompat.Builder(this)
							.setContentTitle(title).setContentText(msg);
							
					// .setLargeIcon(BitmapFactory.decodeResource(res,
					// R.drawable.ulta_icon));
					
					if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						mNotifyBuilder.setColor(getResources().getColor(R.color.accentColor));
						mNotifyBuilder.setSmallIcon(R.drawable.notification_icon);
					 } 
					else
					{
						mNotifyBuilder.setSmallIcon(R.drawable.ic_launcher);
					}

					// Set pending intent
					mNotifyBuilder.setContentIntent(resultPendingIntent);

					// Set Vibrate, Sound and Light
					int defaults = 0;
					defaults = defaults | Notification.DEFAULT_LIGHTS;
					defaults = defaults | Notification.DEFAULT_VIBRATE;
					defaults = defaults | Notification.DEFAULT_SOUND;

					mNotifyBuilder.setDefaults(defaults);
					// Set the content for Notification
					mNotifyBuilder.setContentText(msg);
					// Set autocancel
					mNotifyBuilder.setAutoCancel(true);
					// Post a notification
					mNotificationManager.notify(notifyID,
							mNotifyBuilder.build());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

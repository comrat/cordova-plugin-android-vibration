package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.engine.SystemWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibrationEffect;


/**
 * * This class provides vibration methods.
 * */
public class AndroidVibration extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("vibrate")) {
			int duration = args.getInt(0);
			this.vibrate(duration, callbackContext);
			return true;
		} else if (action.equals("hasVibrator")) {
			this.hasVibrator(callbackContext);
			return true;
		} else if (action.equals("hasAmplitudeControl")) {
			this.hasAmplitudeControl(callbackContext);
			return true;
		}
		return false;
	}

	private void hasVibrator(CallbackContext callbackContext) {
		Context context = this.cordova.getActivity().getApplicationContext();
		Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		// callbackContext.success(String.valueOf(vibrator.hasAmplitudeControl()));
		callbackContext.success(String.valueOf(vibrator.hasVibrator()));
	}

	private void hasAmplitudeControl(CallbackContext callbackContext) {
		Context context = this.cordova.getActivity().getApplicationContext();
		Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		callbackContext.success(String.valueOf(vibrator.hasAmplitudeControl()));
	}

	private void vibrate(int duration, CallbackContext callbackContext) {
		Context context = this.cordova.getActivity().getApplicationContext();
		if (Build.VERSION.SDK_INT >= 26) {
			((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
		} else {
			((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(duration);
		}
		callbackContext.success();
	}
}

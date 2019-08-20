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
		} else if (action.equals("vibrateWithPattern")) {
			JSONArray pattern = args.getJSONArray(0);
			int repeat = args.getInt(1);
			this.vibrateWithPattern(pattern, repeat, callbackContext);
			return true;
		} else if (action.equals("hasVibrator")) {
			this.hasVibrator(callbackContext);
			return true;
		} else if (action.equals("cancel")) {
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
		callbackContext.success(String.valueOf(vibrator.hasVibrator()));
	}

	private void hasAmplitudeControl(CallbackContext callbackContext) {
		Context context = this.cordova.getActivity().getApplicationContext();
		Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		callbackContext.success(String.valueOf(vibrator.hasAmplitudeControl()));
	}

	private void cancel(CallbackContext callbackContext) {
		Context context = this.cordova.getActivity().getApplicationContext();
		((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
		callbackContext.success();
	}

	private void vibrateWithPattern(JSONArray pattern, int repeat, CallbackContext callbackContext) throws JSONException {
		Context context = this.cordova.getActivity().getApplicationContext();

		if (pattern == null) {
			callbackContext.success();
			return;
		}

		long[] numbers = new long[pattern.length()];
		for (int i = 0; i < pattern.length(); ++i) {
			numbers[i] = pattern.getInt(i);
		}

		 if (Build.VERSION.SDK_INT >= 26) {
			((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createWaveform(numbers, repeat));
		} else {
			((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(numbers, repeat);
		}
		callbackContext.success();
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

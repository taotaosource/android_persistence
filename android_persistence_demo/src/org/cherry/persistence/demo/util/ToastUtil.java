package org.cherry.persistence.demo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persistence.demo.R;

public class ToastUtil {

	public static void show(Context context, String mesage) {
		if (context != null) {
			Toast toast = makeText(context, mesage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public static void show(Context context, int mesageId) {
		if (context != null) {
			show(context, context.getResources().getString(mesageId));
		}
	}

	public static Toast makeText(Context context, CharSequence text, int duration) {
		Toast result = new Toast(context);
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.transient_notification, null);
		TextView tv = (TextView) v.findViewById(R.id.message);
		tv.setText(text);
		result.setView(v);
		result.setDuration(duration);
		return result;
	}
}

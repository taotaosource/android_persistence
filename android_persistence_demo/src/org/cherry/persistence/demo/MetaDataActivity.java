package org.cherry.persistence.demo;

import org.cherry.persistence.demo.util.Logger;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.example.persistence.demo.R;

public class MetaDataActivity extends ActionBarActivity {
	private static final String TAG = "MetaDataActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		try {
			ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
			Bundle metaData = activityInfo.metaData;
			String clazz = metaData.getString("clazz");
			if (TextUtils.isEmpty(clazz)) {
				throw new NullPointerException(" clazz is null");
			}
			FragmentManager fm = getSupportFragmentManager();
			int contentId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.content : R.id.action_bar_activity_content;
			if (fm.findFragmentById(contentId) == null) {
				@SuppressWarnings("unchecked")
				Class<? extends Fragment> name = (Class<? extends Fragment>) Class.forName(clazz);
				Fragment instance = name.newInstance();
				fm.beginTransaction().add(contentId, instance).commit();
			}
		} catch (Exception e) {
			Logger.e(TAG, e);
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return true;
	}

}

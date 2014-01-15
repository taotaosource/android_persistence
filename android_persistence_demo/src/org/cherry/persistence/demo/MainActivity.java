package org.cherry.persistence.demo;

import org.cherry.persistence.demo.fragment.ContentProviderFragment;
import org.cherry.persistence.demo.fragment.DeleteFragment;
import org.cherry.persistence.demo.fragment.InsertFragment;
import org.cherry.persistence.demo.fragment.QueryFragment;
import org.cherry.persistence.demo.fragment.UpdateFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.SupportActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost.TabSpec;

import com.example.persistence.demo.R;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private SupportActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;
	private FragmentTabHost mTabHost;
 
	private static final Class<?>[] MENU_CLASS = new Class[] { 
		InsertFragment.class, 
		DeleteFragment.class, 
		UpdateFragment.class,
		QueryFragment.class,
		ContentProviderFragment.class };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.left_menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new SupportActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				setTitle(mPlanetTitles[mTabHost.getCurrentTab()]);
				//invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				setTitle(mDrawerTitle);
				//invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		for (int i = 0; i < mPlanetTitles.length; i++) {
			String tag = mPlanetTitles[i];
			TabSpec newTabSpec = mTabHost.newTabSpec(tag);
			newTabSpec.setIndicator(tag);
			mTabHost.addTab(newTabSpec, MENU_CLASS[i], null);
		}

		findViewById(android.R.id.tabs).setVisibility(View.GONE);

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		// update selected item and title, then close the drawer
		mTabHost.setCurrentTab(position);
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}

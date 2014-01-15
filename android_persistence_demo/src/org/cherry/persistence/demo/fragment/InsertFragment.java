package org.cherry.persistence.demo.fragment;

import org.cherry.persistence.Session;
import org.cherry.persistence.demo.app.PersistenceApplication;
import org.cherry.persistence.demo.model.Tiger;
import org.cherry.persistence.demo.model.User;
import org.cherry.persistence.demo.provider.DataProvider;
import org.cherry.persistence.demo.util.ToastUtil;
import org.cherry.persistence.log.LoggerManager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.persistence.demo.R;

public class InsertFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.insert_array, R.layout.list_item));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final FragmentActivity context = getActivity();
		final Session session = ((PersistenceApplication) context.getApplication()).getSession();
		switch (position) {
		case 0:// Autoincrement Id Insert
			User user = createUser();
			session.save(user);
			
			ToastUtil.show(context, " save success id : " + user.getId());
			//notifyChange
			
			context.getContentResolver().notifyChange(DataProvider.URI_USER, null);
			break;
		case 1:// UUID Id Insert
			Tiger tiger = new Tiger();
			tiger.setName("BayBay");
			tiger.setAge(80);
			session.save(tiger);
			ToastUtil.show(context, " save success id : " + tiger.getId());
			break;
		case 2:// Insert 1000 records
			
			//async insert
			final ProgressDialog dialog = new ProgressDialog(context);
			dialog.setMessage("waiting");
			dialog.show();
			new AsyncTask<Void, Void, Long>() {

				@Override
				protected Long doInBackground(Void... params) {
					long startTime = System.currentTimeMillis();
					LoggerManager loggerManager = LoggerManager.getInstance();
					
					//debug false;
					loggerManager.setDebug(false);
					try {
						session.beginTransaction();
						for ( int i = 0; i < 1000; i ++ ) {
							Tiger tiger = new Tiger();
							tiger.setName("BayBay");
							tiger.setAge(80);
 							session.save(tiger);
						}
						session.setTransactionSuccessful();
					} finally {
						session.endTransaction();
					}
					//debug true;
					loggerManager.setDebug(true);
					return System.currentTimeMillis() - startTime;
				}

				protected void onPostExecute(Long result) {
					dialog.dismiss();
					ToastUtil.show(context, " Insert 1000 records time : " + result);
				};

			}.execute();

			break;
		}
	}
	
	private User createUser() {
		User user = new User();
		user.setName("Jack");
		user.setAge(25);
		user.setUsername("---");
		user.setPassword("name");
		user.setEmail("jack@gmail.com");
		user.setGender("male");
		return user;
	}
}

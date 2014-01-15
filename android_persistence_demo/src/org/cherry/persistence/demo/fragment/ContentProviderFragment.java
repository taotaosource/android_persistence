package org.cherry.persistence.demo.fragment;

import org.cherry.persistence.Session;
import org.cherry.persistence.demo.app.PersistenceApplication;
import org.cherry.persistence.demo.model.User;
import org.cherry.persistence.demo.provider.DataProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.persistence.demo.R;

public class ContentProviderFragment extends ListFragment implements LoaderCallbacks<Cursor>, OnItemLongClickListener {

	public static final String[] PROJECTION = { "name", "_id" };
	public static final int[] TO = new int[] { android.R.id.text1 };
	private SimpleCursorAdapter mCursorAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, PROJECTION, TO,
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(mCursorAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemLongClickListener(this);
		setListShown(false);
		setEmptyText("Empty");
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
		Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Prompt");
		builder.setMessage("Delete ?");
		builder.setNegativeButton(android.R.string.cancel, null);
		builder.setPositiveButton(android.R.string.yes, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteUser(id);
			}
		}).show();
		return true;
	}

	private void deleteUser(long id) {
		User user = new User();
		user.setId((int) id);
		FragmentActivity activity = getActivity();
		((Session) ((PersistenceApplication)activity.getApplication()).getSession()).delete(user);
		activity.getContentResolver().notifyChange(DataProvider.URI_USER, null);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int loadId, Bundle args) {
		return new CursorLoader(getActivity(), DataProvider.URI_USER, PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (isResumed()) {
			setListShownNoAnimation(true);
		} else {
			setListShown(true);
		}
		mCursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mCursorAdapter.swapCursor(null);
	}

}

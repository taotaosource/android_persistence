package org.cherry.persistence.demo.fragment;

import org.cherry.persistence.Session;
import org.cherry.persistence.demo.CriteriaQueryActivity;
import org.cherry.persistence.demo.app.PersistenceApplication;
import org.cherry.persistence.demo.model.User;
import org.cherry.persistence.demo.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.persistence.demo.R;

public class QueryFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.query_array, R.layout.list_item));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final FragmentActivity context = getActivity();
		final Session session = ((PersistenceApplication) context.getApplication()).getSession();
		switch (position) {
		case 0:// Id Query
			User user = new User();
			user.setAge(48);
			user.setEmail("jack@qq.com");
			session.save(user);
			User getUser = session.get(User.class, user.getId());
			ToastUtil.show(context, " query user : " + getUser);
			break;
		case 1:// CriteriaQuery
			Intent intent = new Intent(context, CriteriaQueryActivity.class);
			startActivity(intent);
			break;
		}
	}
}

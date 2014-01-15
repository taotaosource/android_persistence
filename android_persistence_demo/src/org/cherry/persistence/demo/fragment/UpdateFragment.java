package org.cherry.persistence.demo.fragment;

import org.cherry.persistence.Session;
import org.cherry.persistence.demo.app.PersistenceApplication;
import org.cherry.persistence.demo.model.User;
import org.cherry.persistence.demo.util.ToastUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.persistence.demo.R;

public class UpdateFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.update_array, R.layout.list_item));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final FragmentActivity context = getActivity();
		final Session session = ((PersistenceApplication) context.getApplication()).getSession();
		switch (position) {
		case 0: //Update
			User user = new User();
			session.save(user);
			user.setAge(25);
			user.setName("Jack");
			user.setEmail("jack@qq.com");
			user.setAvatar("http://www.domain.com/avatar.png");
			session.update(user);
			User newUser = session.get(User.class, user.getId());
			ToastUtil.show(context, " update success : " + newUser);
			session.delete(user);
			break;
		}
	}	
}

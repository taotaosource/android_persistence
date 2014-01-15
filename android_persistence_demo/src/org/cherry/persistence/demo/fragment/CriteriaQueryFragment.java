package org.cherry.persistence.demo.fragment;

import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.Session;
import org.cherry.persistence.criterion.Criterion;
import org.cherry.persistence.criterion.Example;
import org.cherry.persistence.criterion.Order;
import org.cherry.persistence.criterion.Restrictions;
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

public class CriteriaQueryFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.criteria_query_array, R.layout.list_item));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final FragmentActivity context = getActivity();
		final Session session = ((PersistenceApplication) context.getApplication()).getSession();

		User user = new User();
		user.setAge(25);
		user.setGender("male");
		user.setUsername("jack");
		user.setPassword("kcaj");
		user.setEmail("taotao@qq.com");
		user.setName("haitao");
		user.setAvatar("http://www.domain.com/avatar.png");
		session.save(user);
		Criteria criteria = session.createCriteria(User.class);
		boolean uniqueResult = false;
		switch (position) {
		case 0:// Id equal
			criteria.add(Restrictions.idEq(user.getId()));
			uniqueResult = true;
 			break;
		case 1:// Example query
			User example = new User();
			example.setUsername("jack");
			example.setPassword("kcaj");
			Example create = Example.create(example);
			create.excludeZeroes();
			criteria.add(create);
			break;
		case 2:// Property equal
			criteria.add(Restrictions.eq("name", "haitao"));
			break;
		case 3:// Property not equal
			criteria.add(Restrictions.ne("name", "taotao"));
			break;
		case 4:// Property like
			criteria.add(Restrictions.like("name", "%tao%"));
			break;
		case 5:// Property greater than
			criteria.add(Restrictions.gt("age", 23));
			break;
		case 6:// Property less than
			criteria.add(Restrictions.lt("age", 26));
			break;
		case 7:// Property greater than or equal
			criteria.add(Restrictions.ge("age", 25));
			break;
		case 8:// Property less than or equal
			criteria.add(Restrictions.le("age", 25));
			break;
		case 9:// Property between
			criteria.add(Restrictions.between("age", 24, 26));
			break;
		case 10:// Property in
			criteria.add(Restrictions.in("username", new Object[] { "jack", "taotao" }));
			break;
		case 11:// Criterion and
			Criterion lhs = Restrictions.eq("username", "jack");
			Criterion rhs = Restrictions.eq("password", "kcaj");
			criteria.add(Restrictions.and(lhs, rhs));
			break;
		case 12:// Criterion multiple and
			Criterion criterion1 = Restrictions.eq("username", "jack");
			Criterion criterion2 = Restrictions.eq("password", "kcaj");
			Criterion criterion3 = Restrictions.eq("name", "haitao");
			criteria.add(Restrictions.and(criterion1, criterion2, criterion3));
			break;
		case 13:// Criterion or
			criteria.add(Restrictions.or(
					Restrictions.eq("name", "haitao"),
					Restrictions.eq("name", "jack")));
			break;
		case 14:// Criterion multiple or
			criteria.add(Restrictions.or(
					Restrictions.eq("name", "haitao"),
					Restrictions.eq("name", "jack"),
					Restrictions.eq("name", "taotao")));
			break;
		case 15:// Criterion sqlRestriction
			criteria.add(Restrictions.sqlRestriction("name = 'haitao'"));
			break;
		case 16:// Paging query
			criteria.setFirstResult(0);
			criteria.setMaxResults(1);
			break;
		case 17:// Property order
			criteria.addOrder(Order.asc("name")).addOrder(Order.desc("id"));
			break;
		}

		Object result  = null;
		if (uniqueResult) {
			User resultUser = (User) criteria.uniqueResult();
			result = resultUser;
		} else {
			@SuppressWarnings("unchecked")
			List<User> list = criteria.list();
			result =  list;
		}
				
		ToastUtil.show(context, " result  = " + result);
		session.delete(user);
	}
}

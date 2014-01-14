package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cherry.persistence.Criteria;
import org.cherry.persistence.Session;
import org.cherry.persistence.SessionFactory;
import org.cherry.persistence.cfg.Configuration;
import org.cherry.persistence.criterion.Example;
import org.cherry.persistence.criterion.Restrictions;
import org.cherry.persistence.log.Log;
import org.cherry.persistence.log.Logger;
import org.cherry.persistence.log.LoggerManager;

import android.database.sqlite.SQLiteDatabase;


public class Test {
	public static SQLiteDatabase sqLiteDatabase;

	public static void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase) {
		Test.sqLiteDatabase = sqLiteDatabase;
	}

	public static void main(final String[] args) throws InterruptedException {
		LoggerManager instance = LoggerManager.getInstance();
		instance.setDebug(true);
 		instance.setLog(new Log() {
			
			@Override
			public void print(int priority, String tag, String msg) {
				if (args.length > 0) {
					android.util.Log.d("Persitence", msg);
				} else {
					System.out.println( "priority " + priority + " tag " + tag + " msg " + msg);
				}
 			}
		});
	
 		
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Empty.class);
		configuration.setSQLiteDatabase(sqLiteDatabase);
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Serializable serializable = null;
		
		long currentTime = System.currentTimeMillis();
		 
		System.out.println(serializable + " transaction " + session.inTransaction());
		User object = new User("!");
		object.setPrice(500000);
		//object.setGender("男");
		object.setEmial("taotao@qq.com");
		object.setAge("1");
		object.setPassword("liuhaitao");
		object.setName("taotao");
		//object.setPost("110000");
		object.setAvatar("http://www.baidu.com");
		for (int i = 0; i < 10000; i ++) {
			serializable = session.save(object);
		} 
		System.out.println("exec time  = " + (System.currentTimeMillis() - currentTime) + " transaction : " + session.inTransaction());
		System.out.println(" id = " + serializable  + " user " + object );
		Object object2 = session.get(User.class, 1);
		Empty object3 = new Empty();
		session.save(object3);
		 
		/*{
			Criteria criteria = session.createCriteria(User.class);
			Example example = Example.create(object);
			example.excludeZeroes();
			List<User> list =  criteria.list();
			System.out.println(list);
		}
		 
		{
			User user = new User("!");
			user.setId(2 + "");
			user.setAvatar("baidu.com");
			user.setGender("323232");
			user.setPrice(1.53222222222222222222f);
			user.setPassword("hello");
			session.update(user);
			User user2 = new User("!");
			user2.setId(0 + "");
			session.delete(user2);
		}
	
		{
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.idEq(2));
			System.out.println(criteria.uniqueResult());
		}

		{
			long startTime = System.currentTimeMillis();
			System.out.println(session.get(User.class, 2));
			System.out.println("query time " + (System.currentTimeMillis() - startTime));
		}*/
		session.setTransactionSuccessful();
		session.endTransaction();
	}
}

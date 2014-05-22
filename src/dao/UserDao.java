package dao;



import java.util.List;

import javax.annotation.Resource;

import model.Page;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository(value="userDao")
public class UserDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	//验证用户的信息时否存在
	public  User getInfo(User user) {
		
		String password = user.getPassword();
		String authority = user.getAuthority();
		String username = user.getUsername();
		Session session = sessionFactory.getCurrentSession();

		String hql = "from User u where u.username=:username and u.password=:password and u.authority=:authority";
		User user1 = (User)session.createQuery(hql).setParameter("username",username).setParameter("password",password).setParameter("authority",authority).uniqueResult();
	
		return user1;
		
	}

	public User checkusername(String username) {
	
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User u where u.username=:username";
		User user1 = (User)session.createQuery(hql).setParameter("username",username).uniqueResult();
		return user1;
	}

	
	/*保存用户信息*/
	public void saveUser(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(user);

	}

	
	/*删除用户信息*/
	public void deleteUser(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
		
	}

	public User getUserInfo(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User u where u.username=:username";
		User user1 = (User)session.createQuery(hql).setParameter("username",username).uniqueResult();
		return user1;
		
	}

	public long queryRecordAmount(String keyWord) {
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "select count(*) from User b where b.username like '%"+keyWord+"%'";
		Query q = session.createQuery(hql);
		long count = (Long) q.uniqueResult();//获得记录的总数
		return count;
	}

	public List<User> queryUserByUsername(Page pageInfo, String keyWord) {
		
		int items = (pageInfo.getShowPage()-1)*pageInfo.getPageRecord();
		int pageSize = pageInfo.getPageRecord();
	
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User u where u.username like '%"+keyWord+"%'";
		Query q = session.createQuery(hql);
		q.setMaxResults(pageSize);
		q.setFirstResult(items);
		List<User> users = q.list();
		
		return users;
	}

	public void updateUser(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
		
	}


	

}
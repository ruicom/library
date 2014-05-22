package dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import model.Book;
import model.BorrowBook_tbl;
import model.Page;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component(value="borrowBookDao")

public class BorrowBookDao {
	
	SessionFactory sessionFactory;
	
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public void BookBook(Book book, User user) {
		// TODO Auto-generated method stub
		BorrowBook_tbl borrowBook = new BorrowBook_tbl();
		
		//获得时间的存储
		 Calendar c1 = Calendar.getInstance();
		 Calendar c2= Calendar.getInstance();
		 c2.add(Calendar.DATE, 60);
		 //将时间转换为date型
		 Date d1 = c1.getTime();
		 Date d2 =c2.getTime();
		 //将时间格式化
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.SIMPLIFIED_CHINESE);
		 String borrowTime = sdf.format(d1);
		 String returnTime =sdf.format(d2);
		
		 borrowBook.setBorrowTime(borrowTime);
		 borrowBook.setReturnTime(returnTime);
		 borrowBook.setUser(user);
		 borrowBook.setBook(book);
	
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.save(borrowBook);
	
	
	}

	
	public long queryBookOfUser(User user) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.getCurrentSession();
		
		String username=user.getUsername();
		
		String hql = "select count(*) from BorrowBook_tbl b where b.user.username=:username";
		Query q = session.createQuery(hql).setParameter("username",username);
		long count = (Long) q.uniqueResult();//获得记录的总数

		return count;
	}

	public List<BorrowBook_tbl> queryBookOfUser(User user, Page pageInfo) {
		
		int items = (pageInfo.getShowPage()-1)*pageInfo.getPageRecord();//第一条记录的位置
		int pageSize = pageInfo.getPageRecord();//根据每页的记录数返回相应的记录
		
		Session session = sessionFactory.getCurrentSession();
		
		String username=user.getUsername();
		String hql = "from BorrowBook_tbl b where b.user.username=:username";
		Query q = session.createQuery(hql).setParameter("username", username);
		q.setMaxResults(pageSize);
		q.setFirstResult(items);
		List<BorrowBook_tbl> borrowBook = q.list();
		
		return borrowBook;
	}

	public void returnBook(String bookid1,User user) {
		// TODO Auto-generated method stub
		int bookid = Integer.parseInt(bookid1);
		String username = user.getUsername();
		
		Session session = sessionFactory.getCurrentSession();

		
		String hql = "delete BorrowBook_tbl as p where p.book.id=:bookid and p.user.username=:username";
		String hql1= "from Book b where b.id =:bookid";
		Book book = (Book) session.createQuery(hql1).setParameter("bookid", bookid).uniqueResult();
		Query q = session.createQuery(hql).setParameter("bookid",bookid).setParameter("username",username);
		q.executeUpdate();
		
		//更新书本的储存量
		int id = book.getId();
		int remainer1 = Integer.parseInt(book.getRemainer()) + 1;
		String remainer = String.valueOf(remainer1);
		org.hibernate.Query q1 = session.createQuery("update Book b set b.remainer ='"+remainer+"' where b.id='"+id+"'");
		q1.executeUpdate();
		
		
	}
	
	
}

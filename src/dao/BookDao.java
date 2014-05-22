package dao;

import java.util.List;

import javax.annotation.Resource;

import model.Book;
import model.Page;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value="bookDao")

public class BookDao {

	SessionFactory sessionFactory;

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addBook(Book book) {
		
		book.setRemainer(book.getTotalAmount());
		Session session = sessionFactory.getCurrentSession();
		session.save(book);
		
	}

	//通过id和书名检查id信息
	public Book checkBook(Book book) {
		
		Session session = sessionFactory.getCurrentSession();
		
		int bookid = book.getId();
		String hql = "from Book b where b.id=:bookid";
		Book book1 = (Book)session.createQuery(hql).setParameter("bookid",bookid).uniqueResult();
		
		return book1;
	}

	//删除图书信息
	public void deleteBook(Book book) {
	
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.delete(book);
	}

	
	//通过id检查是否有这本书存在
		public Book checkBookById(Book book) {
			// TODO Auto-generated method stub
		
			Session session = sessionFactory.getCurrentSession();
			int bookid = book.getId();
			System.out.println(bookid);
			String hql = "from Book b where b.id=:bookid";
			Book book1 = (Book)session.createQuery(hql).setParameter("bookid",bookid).uniqueResult();
			return book1;
		}
			
	
	public void updateBook(Book book) {
		
		book.setRemainer(book.getTotalAmount());
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		session.update(book);
	}

	public long queryRecordAmount(String keyWord) {
		
	
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "select count(*) from Book b where b.bookname like '%"+keyWord+"%'";
		Query q = session.createQuery(hql);
		long count = (Long) q.uniqueResult();//获得记录的总数

		return count;
	}

	
	//获得显示某一页所需的记录
	public List<Book> queryBook(Page pageInfo, String keyWord) {
		
		int items = (pageInfo.getShowPage()-1)*pageInfo.getPageRecord();
		int pageSize = pageInfo.getPageRecord();
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		String hql = "from Book b where b.bookname like '%"+keyWord+"%'";
		Query q = session.createQuery(hql);
		q.setMaxResults(pageSize);
		q.setFirstResult(items);
		List<Book> books = q.list();
		return books;
	}


	public void updateBookAmount(Book book1) {
	
		
		org.hibernate.Session session = sessionFactory.getCurrentSession();

		String remainer = book1.getRemainer();
		int id = book1.getId();
		org.hibernate.Query q = session.createQuery("update Book b set b.remainer ='"+remainer+"' where b.id='"+id+"'");
		q.executeUpdate();
	
	}

	

	
}
	


		
		
		
		
	

	

	
	

	
	
	
	


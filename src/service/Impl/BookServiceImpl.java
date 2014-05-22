package service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import model.Book;
import model.Page;
import model.User;
import service.BookService;

@Component(value="bookServiceImpl")
@Transactional(propagation=Propagation.REQUIRED)
public class BookServiceImpl implements BookService {
	
	private BookDao bookDao;
	
	@Resource(name = "bookDao")
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}


	/*�����鼮*/
	public void addBook(Book book) {
		
		bookDao.addBook(book);
		
	}
	
	
	/*ɾ���鼮*/
	@Override
	public String deleteBook(Book book) {
		
		Book book1 = bookDao.checkBook(book);
		if (book1 == null) {
			return "deleteFail";
		} else {
			bookDao.deleteBook(book);
			return "deleteSuccess";
		}
		
	}


	/*����bookname��bookidѡ��Ҫ�޸ĵ��鼮*/
	@Override
	public Book selectBook(Book book) {
		
		Book book1 = bookDao.checkBook(book);
		return book1;
		
	}
	
	
	/*�����鼮*/
	public String updateBook(Book book) {
		
		Book book1 = bookDao.checkBookById(book);
		if (book1 == null) {
			return "updateFail";
		} else {
			bookDao.updateBook(book);
			return "updateSuccess";
		}

	}

	
	/*��ѯ����Ҫ���ڷ�ҳ��¼����Ŀ*/
	public long queryRecordAmount(String keyWord) {
			return bookDao.queryRecordAmount(keyWord);
		
	}
	
	
	/*��ѯ����Ҫ���鱾��Ϣ*/
	public List<Book> queryBook(Page pageInfo, String keyWord) {
		List<Book> list = bookDao.queryBook(pageInfo, keyWord);
		return list;

	}




	


	

}

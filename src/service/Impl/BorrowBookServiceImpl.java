package service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import dao.BorrowBookDao;
import model.Book;
import model.BorrowBook_tbl;
import model.Page;
import model.User;
import service.BorrowBookService;

@Component(value="borrowBookServiceImpl")
@Transactional(propagation=Propagation.REQUIRED)
public class BorrowBookServiceImpl implements BorrowBookService{

	BorrowBookDao borrowBookDao ;
	
	BookDao bookDao;
	
	@Resource(name="borrowBookDao")
	public void setBorrowBookDao(BorrowBookDao borrowBookDao) {
		this.borrowBookDao = borrowBookDao;
	}

	@Resource(name="bookDao")
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}


	public String bookBook(Book book, User user) {
		
		/*������������û���Ȿ��*/
		Book book1 = bookDao.checkBook(book);
		
		if (book1 == null) {
			return "bookFail";
		} else {
			if (book1.getRemainer().equals("0")) {
				return "bookOver";
			} else {
				int remainer1 = Integer.parseInt(book1.getRemainer()) - 1;
				String remainer = String.valueOf(remainer1);
				book1.setRemainer(remainer);
				borrowBookDao.BookBook(book1,user);
				return "bookSuccess";
			}
		}
	}
	
	
	/*��ö�������ͼ�����Ŀ*/
	@Override
	public long queryBookOfUser(User user) {
		return borrowBookDao.queryBookOfUser(user);
	}
	
	
	/*����ǻ���Ѿ��赽��ͼ��*/
	@Override
	public List<BorrowBook_tbl> queryBookOfUser(User user, Page pageInfo) {
		
		return borrowBookDao.queryBookOfUser(user,pageInfo);
	}

	
	/*�黹ͼ��*/
	public void returnBook(String bookid,User user) {
		
		borrowBookDao.returnBook(bookid,user);
		
	}


}

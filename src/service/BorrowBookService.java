package service;

import java.util.List;

import model.Book;
import model.BorrowBook_tbl;
import model.Page;
import model.User;

public interface BorrowBookService {

	String bookBook(Book book, User user);

	long queryBookOfUser(User user);

	List<BorrowBook_tbl> queryBookOfUser(User user, Page pageInfo);

	void returnBook(String bookid, User user);

}

package service;

import java.util.List;

import model.Book;
import model.Page;
import model.User;

public interface BookService {
	
	/*增加书籍*/
	public void addBook(Book book);
	
	/*删除书籍*/
	public String deleteBook(Book book);
	
	/*根据bookname和bookid选择要修改的书籍*/
	public Book selectBook(Book book);

	String updateBook(Book book);

	public long queryRecordAmount(String keyWord1);

	public List<Book> queryBook(Page pageInfo, String keyWord1);

	
}

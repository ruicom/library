package service;

import java.util.List;

import model.Book;
import model.Page;
import model.User;

public interface BookService {
	
	/*�����鼮*/
	public void addBook(Book book);
	
	/*ɾ���鼮*/
	public String deleteBook(Book book);
	
	/*����bookname��bookidѡ��Ҫ�޸ĵ��鼮*/
	public Book selectBook(Book book);

	String updateBook(Book book);

	public long queryRecordAmount(String keyWord1);

	public List<Book> queryBook(Page pageInfo, String keyWord1);

	
}

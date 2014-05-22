package service;

import java.util.List;

import model.Book;
import model.BorrowBook_tbl;
import model.Page;
import model.User;

//登录所应该提供的服务
public interface LoginService {
	 
	//public User packUser(String username,String password,String authority);//将用户的数据放到User对象里面
	
	public List<String> verfy(User user);//验证功能；

	public String verfyData(User user);//

	public String register(User user);

	public String deleteUser(User user);

	public User getUserInfo(String username);

	public void addBook(Book book);

	public String deleteBook(Book book);

	public Book selectBook(Book book);

	public String updateBook(Book book);

	public List<Book> queryBook(Page pageInfo,String keyWord);

	public long queryRecordAmount(String keyWord,String kind);

	public List<User> queryUser(Page pageInfo, String keyWord);

	public String bookBook(Book book, User user);

	public long queryBookOfUser(User user);

	public List<BorrowBook_tbl> queryBookOfUser(User user, Page pageInfo);

	public void returnBook(String bookid, User user);

	public String updateUser(User user);

	public User queryUser(String keyWord);

	

	
	
	
	
}

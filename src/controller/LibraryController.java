package controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Book;
import model.BorrowBook_tbl;
import model.Page;
import model.User;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import service.BookService;
import service.BorrowBookService;
import service.LoginService;
import service.UserService;
import service.Impl.BookServiceImpl;
import service.Impl.BorrowBookServiceImpl;
import service.Impl.UserServiceImpl;

import util.PageImfo;

@Controller
public class LibraryController {
	
	UserService userService;
	
	BookService bookService;
	
	BorrowBookService borrowBookService;
	
	/*依赖注入*/
	@Resource(name="userServiceImpl")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource(name ="bookServiceImpl")
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Resource(name="borrowBookServiceImpl")
	public void setBorrowBookService(BorrowBookService borrowBookService) {
		this.borrowBookService = borrowBookService;
	}

	/*
	 * 
	 * 
	 * 登录模块
	 * 
	 * 
	 * 
	 **/
	
	
	/*登录信息验证*/
	@RequestMapping(value="/loginSolver",method = RequestMethod.POST)
	public String login(@ModelAttribute User user,Model model,HttpServletRequest request){
		//获得用户填写的用户名和密码
		
		HttpSession session  = request.getSession();
		session.setAttribute("user", user);
		
		String authority = user.getAuthority();
		String username = user.getUsername();
	
		//验证用户名，密码，权限格式是否正确
		List<String> verfyResult=new ArrayList<String>();
		verfyResult=userService.verfy(user);
		model.addAttribute("verfy",verfyResult);
		if(verfyResult.isEmpty()==false) {
			return "login";
		}
		
		//验证数据与数据库是否一致
				String verfyResult1 = "";
				
				verfyResult1 = userService.verfyData(user);
				
				if("loginfail".equals(verfyResult1)) {
					return "loginfail";//跳转到失败提示页面
				}
				else if("loginsuccess".equals(verfyResult1)&&"user".equals(authority)) 
				{
					return "userIndex";//跳转到普通用户界面
				}
				else {
					return "adminIndex";//跳转到管理员界面
				}  		
	}
	
	
	//注销登录
	@RequestMapping(value="/logOut",method=RequestMethod.GET)
	public String logOut(HttpServletRequest request) {
		HttpSession session  = request.getSession();
		session.invalidate();
		return "login";
	}
	
		
	
	/*
	 * 
	 * 
	 * 用户管理模块
	 * 
	 * 
	 * */
	
	/*进行注册*/
	@RequestMapping(value="/registerSolver",method=RequestMethod.POST) 
	public String register(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String gender,@RequestParam String number,Model model) {
		//获得所有参数
		User user= new User(username,password,"user",gender,number);
		String registerResult = "";
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","两次输入的密码不一致");
			return "register";
		}
		else {		
				registerResult = userService.register(user);
	
				if("success".equals(registerResult)) {
					return "operateSuccess";
				}
				else {
					model.addAttribute("fail","註冊失敗，該用戶名已經存在");
					return "register";
				}
		}
		
	}
		

	/*删除用户*/
	@RequestMapping(value="/deleteUserSolver",method=RequestMethod.POST)
	public String deleteUser(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,Model model) {
		
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","两次输入的密码不一致");
			return "deleteUser";
		}
		else {
				User user = new User(username,password,"user");
				String deleteResult ="";
				deleteResult = userService.deleteUser(user);
				
				if("deleteFail".equals(deleteResult)) {
					model.addAttribute("fail", "删除信息填写有误");
					return "deleteUser";
				}
				else {
					return "operateSuccess1";
				}
		}
	}
		
	
	/*修改用户信息模块*/
	/*获得要修改的用户的信息*/
	@RequestMapping(value="/modifyUserSolver",method = RequestMethod.POST) 
	public String modify(@RequestParam String username,Model model) {
		User user = new User();
		
		user = userService.getUserInfo(username);
		if(user==null) {
			model.addAttribute("fail","用户名不存在");
			return "modifyUser";//返回到原来的界面
		}
		else {
			model.addAttribute("user",user);
			return "modifyUserEdit";
		}	
	}
	
	/*填写要修改的用户信息(管理员修改)*/
	@RequestMapping(value="/modifyUserEditSolver",method = RequestMethod.POST) 
	public String modifyUserEdit(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String authority,@RequestParam String gender,@RequestParam String number,Model model) {
		
		User user= new User(username,password,authority,gender,number);
		String modifyUserResult = "";
		
		//检查密码是否一致
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","两次输入的密码不一致");
			return "modifyUserEdit";
		}
		else {	
				modifyUserResult=userService.updateUser(user);
				if("updateFail".equals(modifyUserResult)) {
					model.addAttribute("fail","该用户不存在");
					return "modifyUserEdit";
				}
				else {
			
					return "operateSuccess1";
				}		
		}	
	}
	
	
	/*模糊查询用户信息*/
	@RequestMapping(value="/queryUser",method=RequestMethod.GET)
	public String queryUser(@RequestParam String showPage,@RequestParam String keyWord,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		
		Page pageInfo= new Page();
		pageInfo.setShowPage(showPage);//设置要显示的页数
		pageInfo.setPageRecord(3);//设置每页的记录数目
		String keyWord1=new String(keyWord.getBytes("ISO8859_1"),"UTF-8");
	
		long recordAmount = userService.queryRecordAmount(keyWord1);
		pageInfo.setPageNum(recordAmount);
		pageInfo.showPageCheck();
		
		List<User> list = userService.queryUser(pageInfo,keyWord1);
		model.addAttribute("users",list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("keyWord",keyWord1);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if("admin".equals(user.getAuthority()))
		{
			return "queryUserResult";
		}
		else {
			return "privateInfo";
		}
	
	}
	
	
	/*所属功能：个人信息修改
	实现：向个人信息修改的编辑页面提供原先未修改的原始值*/
	@RequestMapping(value="updatePrivateInfo")
	public String updatePrivateInfo(@RequestParam String keyWord,Model model) throws UnsupportedEncodingException {
		
		String keyWord1=new String(keyWord.getBytes("ISO8859_1"),"UTF-8");
		User user =userService.queryUser(keyWord1);
		model.addAttribute("user",user);
		return "updatePrivateInfo";
	}

	/*所属功能:修改个人信息(普通用户修改)
	实现:将经过修改后的个人信息写进数据库里面,覆盖原来的信息
	标签：写*/
	@RequestMapping(value="updatePrivateInfo",method=RequestMethod.POST)
	public String updatePrivateInfo(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String gender,@RequestParam String number,Model model) {
		
		User user= new User(username,password,"user",gender,number);
		String modifyUserResult = "";
		//检查密码是否一致
				if(password.equals(repassword)==false) {
					model.addAttribute("fail","两次输入的密码不一致");
					return "updatePrivateInfo";
				}
				else {	
						modifyUserResult=userService.updateUser(user);
						if("updateFail".equals(modifyUserResult)) {
							model.addAttribute("fail","该用户不存在");
							return "updatePrivateInfo";
						}
						else{
							return "operateSuccess2";
						}		
				}
	}
	
	
	/*
	 * 
	 * 书籍管理模块
	 * 
	 * 
	 * */
	
	/*增加书籍*/
	@RequestMapping(value="/addBookSolver",method=RequestMethod.POST)
	public String addBook(Book book,Model model){
		
		bookService.addBook(book);
		return "operateSuccess1";
	}
	
	
	/*	删除书籍*/
	@RequestMapping(value="/deleteBookSolver",method=RequestMethod.POST) 
	public String deleteBook(@RequestParam String bookid,Model model) {
		int bookId1=Integer.parseInt(bookid); 
		Book book = new Book();
		book.setId(bookId1);
		
		String deleteResult=bookService.deleteBook(book);
		if("deleteFail".equals(deleteResult)) {
			model.addAttribute("fail","不存在对应的书籍");
			return "deleteBook";
		}
		else {
			return "operateSuccess1";
		}
	}
	
	
	/*根据bookid和bookname寻找要修改的书籍*/
	@RequestMapping(value="/modifyBookSelectSolver",method=RequestMethod.POST)
	public String modifyBook(@RequestParam String bookid,@RequestParam String bookname,Model model) {
		
		Book book = new Book();
		int bookId1=Integer.parseInt(bookid); 
		book.setId(bookId1);
		book.setBookname(bookname);
		
		Book book1 = bookService.selectBook(book);
		
		if(book1 == null) {
			model.addAttribute("fail","该书不存在");
			return "modifyBookSelect";
		}
		else {
			model.addAttribute("book",book1);
			return "modifyBook";
		} 
	}

	
	/*修改图书信息*/
	@RequestMapping(value="/modifyBook",method=RequestMethod.POST)
	public String modifyBook(@RequestParam String bookid,@RequestParam String bookname,@RequestParam String authod,@RequestParam String publishCompany,@RequestParam String publishTime,@RequestParam String totalAmount,Model model){
		
		int bookId=Integer.parseInt(bookid);
	
		Book book = new Book(bookId,bookname,authod,publishCompany,publishTime,totalAmount);
		String updateResult = bookService.updateBook(book);
		if("updateFail".equals(updateResult)) {
			model.addAttribute("fail","这本书不存在");
			return "modifyBook";
		}
		else {
			return "operateSuccess1";
		}
		
	}
	
	
	/*模糊查询图书的信息*/
	@RequestMapping(value="/queryBook",method=RequestMethod.GET)
	public String query(@RequestParam String keyWord,@RequestParam String showPage,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		Page pageInfo = new Page();
		String keyWord1=new String(keyWord.getBytes("ISO8859_1"),"UTF-8");
		pageInfo.setShowPage(showPage);//设置要显示的页数
		pageInfo.setPageRecord(2);//设置每一页的记录数
		
		long recordAmount = bookService.queryRecordAmount(keyWord1);//获得总的记录数
		pageInfo.setPageNum(recordAmount);
		pageInfo.showPageCheck();//对错误的showPage进行异常处理
		List<Book> list = bookService.queryBook(pageInfo, keyWord1);
		model.addAttribute("books",list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("keyWord",keyWord1);
		
		HttpSession session  = request.getSession();
		String authority = ((User)session.getAttribute("user")).getAuthority();
		
		
		if("admin".equals(authority)) {
			return "queryBookResult";
		}
		else {
			return "queryBookResultUser";
		}
		
	}
	
	
	
	
	/*
	 * 
	 * 
	 * 借书还书模块
	 * 
	 * 
	 * */
	
	/*预约图书*/
	@RequestMapping(value="/bookBookSolver",method=RequestMethod.POST)
	public String borrowBook(@RequestParam String bookid,HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");//获得借书用户的信息
		
		Book book = new Book();//获得书本的信息，进行封装
		int bookId1=Integer.parseInt(bookid); 
		book.setId(bookId1);
		
		String bookResult=borrowBookService.bookBook(book,user);
		if("bookFail".equals(bookResult)) {
			model.addAttribute("fail","该书不存在");
			return "bookBook";
		}
		else if("bookSuccess".equals(bookResult)) {
			return "operateSuccess2";
		}
		else {
			 model.addAttribute("fail","该书已经被借完");
			 return "bookBook";
		}
	}
	
	
	/*形成归还图书页面*/
	@RequestMapping(value ="/returnBookPage")
	public String returnBookPage(@RequestParam String showPage,HttpServletRequest request,Model model) {
		
		Page pageInfo = new Page();
		pageInfo.setShowPage(showPage);
		pageInfo.setPageRecord(3);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		long recordAmount =borrowBookService.queryBookOfUser(user);//这个是为了获得总的记录数目
		pageInfo.setPageNum(recordAmount);
		pageInfo.showPageCheck();
		
		List<BorrowBook_tbl> list = borrowBookService.queryBookOfUser(user,pageInfo);
		model.addAttribute("borrowBooks",list);
		model.addAttribute("pageInfo",pageInfo);
		
		return "returnBookPage";
	}
	
	
	/*归还图书*/
	@RequestMapping(value="returnBook")
	public String returnBook(@RequestParam String bookid,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		borrowBookService.returnBook(bookid,user);
		return "operateSuccess2";
		
	}	
}
	
	
	
		
		
	
		
			
	



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
	
	/*����ע��*/
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
	 * ��¼ģ��
	 * 
	 * 
	 * 
	 **/
	
	
	/*��¼��Ϣ��֤*/
	@RequestMapping(value="/loginSolver",method = RequestMethod.POST)
	public String login(@ModelAttribute User user,Model model,HttpServletRequest request){
		//����û���д���û���������
		
		HttpSession session  = request.getSession();
		session.setAttribute("user", user);
		
		String authority = user.getAuthority();
		String username = user.getUsername();
	
		//��֤�û��������룬Ȩ�޸�ʽ�Ƿ���ȷ
		List<String> verfyResult=new ArrayList<String>();
		verfyResult=userService.verfy(user);
		model.addAttribute("verfy",verfyResult);
		if(verfyResult.isEmpty()==false) {
			return "login";
		}
		
		//��֤���������ݿ��Ƿ�һ��
				String verfyResult1 = "";
				
				verfyResult1 = userService.verfyData(user);
				
				if("loginfail".equals(verfyResult1)) {
					return "loginfail";//��ת��ʧ����ʾҳ��
				}
				else if("loginsuccess".equals(verfyResult1)&&"user".equals(authority)) 
				{
					return "userIndex";//��ת����ͨ�û�����
				}
				else {
					return "adminIndex";//��ת������Ա����
				}  		
	}
	
	
	//ע����¼
	@RequestMapping(value="/logOut",method=RequestMethod.GET)
	public String logOut(HttpServletRequest request) {
		HttpSession session  = request.getSession();
		session.invalidate();
		return "login";
	}
	
		
	
	/*
	 * 
	 * 
	 * �û�����ģ��
	 * 
	 * 
	 * */
	
	/*����ע��*/
	@RequestMapping(value="/registerSolver",method=RequestMethod.POST) 
	public String register(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String gender,@RequestParam String number,Model model) {
		//������в���
		User user= new User(username,password,"user",gender,number);
		String registerResult = "";
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","������������벻һ��");
			return "register";
		}
		else {		
				registerResult = userService.register(user);
	
				if("success".equals(registerResult)) {
					return "operateSuccess";
				}
				else {
					model.addAttribute("fail","�]��ʧ����ԓ�Ñ����ѽ�����");
					return "register";
				}
		}
		
	}
		

	/*ɾ���û�*/
	@RequestMapping(value="/deleteUserSolver",method=RequestMethod.POST)
	public String deleteUser(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,Model model) {
		
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","������������벻һ��");
			return "deleteUser";
		}
		else {
				User user = new User(username,password,"user");
				String deleteResult ="";
				deleteResult = userService.deleteUser(user);
				
				if("deleteFail".equals(deleteResult)) {
					model.addAttribute("fail", "ɾ����Ϣ��д����");
					return "deleteUser";
				}
				else {
					return "operateSuccess1";
				}
		}
	}
		
	
	/*�޸��û���Ϣģ��*/
	/*���Ҫ�޸ĵ��û�����Ϣ*/
	@RequestMapping(value="/modifyUserSolver",method = RequestMethod.POST) 
	public String modify(@RequestParam String username,Model model) {
		User user = new User();
		
		user = userService.getUserInfo(username);
		if(user==null) {
			model.addAttribute("fail","�û���������");
			return "modifyUser";//���ص�ԭ���Ľ���
		}
		else {
			model.addAttribute("user",user);
			return "modifyUserEdit";
		}	
	}
	
	/*��дҪ�޸ĵ��û���Ϣ(����Ա�޸�)*/
	@RequestMapping(value="/modifyUserEditSolver",method = RequestMethod.POST) 
	public String modifyUserEdit(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String authority,@RequestParam String gender,@RequestParam String number,Model model) {
		
		User user= new User(username,password,authority,gender,number);
		String modifyUserResult = "";
		
		//��������Ƿ�һ��
		if(password.equals(repassword)==false) {
			model.addAttribute("fail","������������벻һ��");
			return "modifyUserEdit";
		}
		else {	
				modifyUserResult=userService.updateUser(user);
				if("updateFail".equals(modifyUserResult)) {
					model.addAttribute("fail","���û�������");
					return "modifyUserEdit";
				}
				else {
			
					return "operateSuccess1";
				}		
		}	
	}
	
	
	/*ģ����ѯ�û���Ϣ*/
	@RequestMapping(value="/queryUser",method=RequestMethod.GET)
	public String queryUser(@RequestParam String showPage,@RequestParam String keyWord,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		
		Page pageInfo= new Page();
		pageInfo.setShowPage(showPage);//����Ҫ��ʾ��ҳ��
		pageInfo.setPageRecord(3);//����ÿҳ�ļ�¼��Ŀ
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
	
	
	/*�������ܣ�������Ϣ�޸�
	ʵ�֣��������Ϣ�޸ĵı༭ҳ���ṩԭ��δ�޸ĵ�ԭʼֵ*/
	@RequestMapping(value="updatePrivateInfo")
	public String updatePrivateInfo(@RequestParam String keyWord,Model model) throws UnsupportedEncodingException {
		
		String keyWord1=new String(keyWord.getBytes("ISO8859_1"),"UTF-8");
		User user =userService.queryUser(keyWord1);
		model.addAttribute("user",user);
		return "updatePrivateInfo";
	}

	/*��������:�޸ĸ�����Ϣ(��ͨ�û��޸�)
	ʵ��:�������޸ĺ�ĸ�����Ϣд�����ݿ�����,����ԭ������Ϣ
	��ǩ��д*/
	@RequestMapping(value="updatePrivateInfo",method=RequestMethod.POST)
	public String updatePrivateInfo(@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@RequestParam String gender,@RequestParam String number,Model model) {
		
		User user= new User(username,password,"user",gender,number);
		String modifyUserResult = "";
		//��������Ƿ�һ��
				if(password.equals(repassword)==false) {
					model.addAttribute("fail","������������벻һ��");
					return "updatePrivateInfo";
				}
				else {	
						modifyUserResult=userService.updateUser(user);
						if("updateFail".equals(modifyUserResult)) {
							model.addAttribute("fail","���û�������");
							return "updatePrivateInfo";
						}
						else{
							return "operateSuccess2";
						}		
				}
	}
	
	
	/*
	 * 
	 * �鼮����ģ��
	 * 
	 * 
	 * */
	
	/*�����鼮*/
	@RequestMapping(value="/addBookSolver",method=RequestMethod.POST)
	public String addBook(Book book,Model model){
		
		bookService.addBook(book);
		return "operateSuccess1";
	}
	
	
	/*	ɾ���鼮*/
	@RequestMapping(value="/deleteBookSolver",method=RequestMethod.POST) 
	public String deleteBook(@RequestParam String bookid,Model model) {
		int bookId1=Integer.parseInt(bookid); 
		Book book = new Book();
		book.setId(bookId1);
		
		String deleteResult=bookService.deleteBook(book);
		if("deleteFail".equals(deleteResult)) {
			model.addAttribute("fail","�����ڶ�Ӧ���鼮");
			return "deleteBook";
		}
		else {
			return "operateSuccess1";
		}
	}
	
	
	/*����bookid��booknameѰ��Ҫ�޸ĵ��鼮*/
	@RequestMapping(value="/modifyBookSelectSolver",method=RequestMethod.POST)
	public String modifyBook(@RequestParam String bookid,@RequestParam String bookname,Model model) {
		
		Book book = new Book();
		int bookId1=Integer.parseInt(bookid); 
		book.setId(bookId1);
		book.setBookname(bookname);
		
		Book book1 = bookService.selectBook(book);
		
		if(book1 == null) {
			model.addAttribute("fail","���鲻����");
			return "modifyBookSelect";
		}
		else {
			model.addAttribute("book",book1);
			return "modifyBook";
		} 
	}

	
	/*�޸�ͼ����Ϣ*/
	@RequestMapping(value="/modifyBook",method=RequestMethod.POST)
	public String modifyBook(@RequestParam String bookid,@RequestParam String bookname,@RequestParam String authod,@RequestParam String publishCompany,@RequestParam String publishTime,@RequestParam String totalAmount,Model model){
		
		int bookId=Integer.parseInt(bookid);
	
		Book book = new Book(bookId,bookname,authod,publishCompany,publishTime,totalAmount);
		String updateResult = bookService.updateBook(book);
		if("updateFail".equals(updateResult)) {
			model.addAttribute("fail","�Ȿ�鲻����");
			return "modifyBook";
		}
		else {
			return "operateSuccess1";
		}
		
	}
	
	
	/*ģ����ѯͼ�����Ϣ*/
	@RequestMapping(value="/queryBook",method=RequestMethod.GET)
	public String query(@RequestParam String keyWord,@RequestParam String showPage,Model model,HttpServletRequest request) throws UnsupportedEncodingException {
		Page pageInfo = new Page();
		String keyWord1=new String(keyWord.getBytes("ISO8859_1"),"UTF-8");
		pageInfo.setShowPage(showPage);//����Ҫ��ʾ��ҳ��
		pageInfo.setPageRecord(2);//����ÿһҳ�ļ�¼��
		
		long recordAmount = bookService.queryRecordAmount(keyWord1);//����ܵļ�¼��
		pageInfo.setPageNum(recordAmount);
		pageInfo.showPageCheck();//�Դ����showPage�����쳣����
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
	 * ���黹��ģ��
	 * 
	 * 
	 * */
	
	/*ԤԼͼ��*/
	@RequestMapping(value="/bookBookSolver",method=RequestMethod.POST)
	public String borrowBook(@RequestParam String bookid,HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");//��ý����û�����Ϣ
		
		Book book = new Book();//����鱾����Ϣ�����з�װ
		int bookId1=Integer.parseInt(bookid); 
		book.setId(bookId1);
		
		String bookResult=borrowBookService.bookBook(book,user);
		if("bookFail".equals(bookResult)) {
			model.addAttribute("fail","���鲻����");
			return "bookBook";
		}
		else if("bookSuccess".equals(bookResult)) {
			return "operateSuccess2";
		}
		else {
			 model.addAttribute("fail","�����Ѿ�������");
			 return "bookBook";
		}
	}
	
	
	/*�γɹ黹ͼ��ҳ��*/
	@RequestMapping(value ="/returnBookPage")
	public String returnBookPage(@RequestParam String showPage,HttpServletRequest request,Model model) {
		
		Page pageInfo = new Page();
		pageInfo.setShowPage(showPage);
		pageInfo.setPageRecord(3);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		long recordAmount =borrowBookService.queryBookOfUser(user);//�����Ϊ�˻���ܵļ�¼��Ŀ
		pageInfo.setPageNum(recordAmount);
		pageInfo.showPageCheck();
		
		List<BorrowBook_tbl> list = borrowBookService.queryBookOfUser(user,pageInfo);
		model.addAttribute("borrowBooks",list);
		model.addAttribute("pageInfo",pageInfo);
		
		return "returnBookPage";
	}
	
	
	/*�黹ͼ��*/
	@RequestMapping(value="returnBook")
	public String returnBook(@RequestParam String bookid,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		borrowBookService.returnBook(bookid,user);
		return "operateSuccess2";
		
	}	
}
	
	
	
		
		
	
		
			
	



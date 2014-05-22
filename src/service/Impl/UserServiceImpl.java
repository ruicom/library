package service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import dao.UserDao;

import model.Page;
import model.User;
import service.UserService;

@Service(value="userServiceImpl")

public class UserServiceImpl implements UserService {
	
		UserDao userDao;
	
		@Resource(name="userDao")
		public void setUserDao(UserDao userDao) {
			this.userDao = userDao;
		}

		@Override
		 /*这个方法提供格式的验证*/
		@Transactional(propagation=Propagation.REQUIRED)
		public List<String> verfy(User user) {
			String username = user.getUsername();
			String password = user.getPassword();
		
			List<String> list = new ArrayList<String>();
			
			if (username == null || username.length() < 6 || username.length() > 15) {
				list.add("用户名的长度不对");
			}
			if (password == null || password.length() < 6 || password.length() > 15) {
				list.add("密码的的长度不对");
			}	
			return list;
		}

		
		
		/*这个是用来验证数据库是否存在密码和用户名的*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public String verfyData(User user) {

			User user1 = new User();
			user1 = userDao.getInfo(user);
			if (user1 == null) {
				return "loginfail";
			} 
			else {
				return "loginsuccess";
			}
		}
		
		
		/*注册*/
		@Transactional(propagation=Propagation.REQUIRED)
		public String register(User user) {
			
			String username = user.getUsername();
			
			User user1 = userDao.checkusername(username);
			
			if (user1 != null) {
				return "fail";
			} else {
				userDao.saveUser(user);
				return "success";
			}
		}
		
		
		/*删除用户*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public String deleteUser(User user) {
			
			User user1 = new User();
			user1 = userDao.getInfo(user);
			
			if (user1 == null) {
				return "deleteFail";
			} else {
				
				userDao.deleteUser(user1);
				return "deleteSuccess";
			}
		}

		
		/*根据用户名获用户的信息*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public User getUserInfo(String username) {
			User user1 = new User();
			user1 = userDao.getUserInfo(username);
			return user1;
		}

	
		/*更新用户信息的服务*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public String updateUser(User user) {
			String username=user.getUsername();
			User user1 = userDao.checkusername(username);
			if(user1 == null) {
				return "updateFail";
			}
			else {
				userDao.updateUser(user);
				return "updateSuccess";
			}
		}
	
		
		/*查询所需要用于分页记录的数目*/
		@Transactional(propagation=Propagation.REQUIRED)
		public long queryRecordAmount(String keyWord) {
			return userDao.queryRecordAmount(keyWord);	
		}

		
		@Override
		/*获得用于查询书籍结果分页的记录*/
		@Transactional(propagation=Propagation.REQUIRED)
		public List<User> queryUser(Page pageInfo, String keyWord) {
			return userDao.queryUserByUsername(pageInfo, keyWord);
		}
		
		
		/*（user使用）查询用户信息，用于修改用户信息，*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public User queryUser(String keyWord) {
			return userDao.checkusername(keyWord);	
		}

}



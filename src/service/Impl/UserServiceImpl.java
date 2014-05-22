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
		 /*��������ṩ��ʽ����֤*/
		@Transactional(propagation=Propagation.REQUIRED)
		public List<String> verfy(User user) {
			String username = user.getUsername();
			String password = user.getPassword();
		
			List<String> list = new ArrayList<String>();
			
			if (username == null || username.length() < 6 || username.length() > 15) {
				list.add("�û����ĳ��Ȳ���");
			}
			if (password == null || password.length() < 6 || password.length() > 15) {
				list.add("����ĵĳ��Ȳ���");
			}	
			return list;
		}

		
		
		/*�����������֤���ݿ��Ƿ����������û�����*/
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
		
		
		/*ע��*/
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
		
		
		/*ɾ���û�*/
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

		
		/*�����û������û�����Ϣ*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public User getUserInfo(String username) {
			User user1 = new User();
			user1 = userDao.getUserInfo(username);
			return user1;
		}

	
		/*�����û���Ϣ�ķ���*/
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
	
		
		/*��ѯ����Ҫ���ڷ�ҳ��¼����Ŀ*/
		@Transactional(propagation=Propagation.REQUIRED)
		public long queryRecordAmount(String keyWord) {
			return userDao.queryRecordAmount(keyWord);	
		}

		
		@Override
		/*������ڲ�ѯ�鼮�����ҳ�ļ�¼*/
		@Transactional(propagation=Propagation.REQUIRED)
		public List<User> queryUser(Page pageInfo, String keyWord) {
			return userDao.queryUserByUsername(pageInfo, keyWord);
		}
		
		
		/*��userʹ�ã���ѯ�û���Ϣ�������޸��û���Ϣ��*/
		@Override
		@Transactional(propagation=Propagation.REQUIRED)
		public User queryUser(String keyWord) {
			return userDao.checkusername(keyWord);	
		}

}



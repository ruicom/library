package service;

import java.util.List;

import model.Page;
import model.User;

public interface UserService {
	
	/*��֤��д���ϵĸ�ʽ*/
	public List<String> verfy(User user);
	
	/*��֤�û������Ƿ����*/
	public String verfyData(User user);
	
	/*����ע��*/
	public String register(User user);
	
	/*ɾ���û�*/
	public String deleteUser(User user);

	/*����û�������û���Ϣ*/
	public User getUserInfo(String username);
	
	/*�޸��û���Ϣ*/
	public String updateUser(User user);
	
	/*����û�������*/
	public long queryRecordAmount(String keyWord);

	/*��ѯ�û���Ϣ*/
	List<User> queryUser(Page pageInfo, String keyWord);
	
	/*��userʹ�ã���ѯ�û���Ϣ�������޸��û���Ϣ��*/
	public User queryUser(String keyWord);
	
	
}

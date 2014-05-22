package service;

import java.util.List;

import model.Page;
import model.User;

public interface UserService {
	
	/*验证填写资料的格式*/
	public List<String> verfy(User user);
	
	/*验证用户资料是否存在*/
	public String verfyData(User user);
	
	/*进行注册*/
	public String register(User user);
	
	/*删除用户*/
	public String deleteUser(User user);

	/*获得用户名获得用户信息*/
	public User getUserInfo(String username);
	
	/*修改用户信息*/
	public String updateUser(User user);
	
	/*获得用户的总数*/
	public long queryRecordAmount(String keyWord);

	/*查询用户信息*/
	List<User> queryUser(Page pageInfo, String keyWord);
	
	/*（user使用）查询用户信息，用于修改用户信息，*/
	public User queryUser(String keyWord);
	
	
}

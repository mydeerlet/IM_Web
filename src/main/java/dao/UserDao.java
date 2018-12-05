package dao;

import bean.User;

/**
 * 用户数据操作
 * @author Administrator
 *
 */

public interface UserDao {

		
	/**
	 * 根据用户名密码进行匹配
	 * @param userName
	 * @return
	 */
	User findUser(String  imCode);
	

	/**
	 * 修改密码
	 * @param user
	 */
	void updataUser(User user);
	
	/**
	 * 新用户注册
	 * @param user
	 */
	void regiser(User user);
	
}

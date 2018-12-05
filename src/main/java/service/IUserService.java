package service;

import bean.User;

public interface IUserService {

	/**
	 * 登录接口 返回User 对象 
	 * @param user
	 * @return
	 */
	User login(User user);
	
	/**
	 * 修改密码接口
	 * @param user
	 */
	void updataUser(User user);
	
	/**
	 * 修改密码接口
	 * @param user
	 */
	void regiser(User user);
}

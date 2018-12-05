package service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bean.User;
import dao.UserDao;
import execption.DataResponseException;
import service.IUserService;

@Service
public class UserService implements IUserService {

	@Resource
	private UserDao userDao;
	
	/**
	 * 登录，用数据库做处理，根据用户名密码查询数据
	 * 匹配成功则返回数据
	 */
	public User login(User luser) {
		
		User user = userDao.findUser(luser.getImCode());
		
		if(user==null) {
			throw new DataResponseException("用户名不存在");
		}else if(!luser.getPassword().equals(user.getPassword())) {
			throw new DataResponseException("用户名或密码错误");
		}
		return user;
	}

	
	/**
	 * 修改密码
	 */
	public void updataUser(User user) {
		
		
	}

	/**
	 * 注册用户
	 */
	public void regiser(User user) {		
		User ruser = userDao.findUser(user.getImCode());		
		if(ruser == null) {
			try {
				userDao.regiser(user);
			}catch (Exception e) {
				throw new DataResponseException("服务器正在升级请稍后再试");
			}
			
		}else {
			throw new DataResponseException("用户已存在");
		}
		
		
	}

}

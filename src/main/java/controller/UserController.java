package controller;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.ResponseResult;
import bean.User;
import execption.DataResponseException;
import service.IUserService;
import utils.SocketService;



@Controller
@RequestMapping("/app")
public class UserController {

	
    @Resource 
	private IUserService userService;
    
	//异步提交注册
	@RequestMapping("/register.do")
	@ResponseBody
	public ResponseResult<Void> register(User user){

		ResponseResult<Void> rr = null;

		try{
			//注册
			userService.regiser(user);
			rr = new ResponseResult<Void>(0000,"注册成功");
		}catch(DataResponseException ex){
			rr = new ResponseResult<Void>(1001,ex.getMessage());
		}	
		return rr;
	}

	//异步提交登录
	@RequestMapping("/login.do")
	@ResponseBody
	public ResponseResult<User> login(User user){

		ResponseResult<User> rr = null;
		
		try{
			//2.调用业务层方法：login(username,password)返回user对象
			User resoutUser = userService.login(user);
			
			resoutUser.setPassword("");
			//4.rr设置 state：1 message：登录成功
			rr = new ResponseResult<User>(1000,"登录成功",resoutUser);
		}catch(DataResponseException ex){
			//5.rr设置 state：0 message：ex.getMessage();
			rr = new ResponseResult<User>(1001,ex.getMessage());
		}		
		return rr;
	}
	
	@RequestMapping("/startSocket.do")
	@ResponseBody
	public String startSocket(HttpServletRequest req) { 
		
		
		SocketService 	socketService =new SocketService();
		
		req.getSession().getServletContext().setAttribute("key","value");
		
		return "服务器启动成功";
	}
	
	
	@RequestMapping("/userList.do")
	@ResponseBody
	public ResponseResult<List<String>> userList(HttpServletRequest req) { 

	
		List<String> list=null;
		list = (List<String>) req.getSession().getServletContext().getAttribute("userKey");
		
		if(list==null) {
			return new ResponseResult<List<String>>(1001,"没有用户在线");
		}else {
			return new ResponseResult<List<String>>(1001,"成功",list);
		}
	
		
		
		

	}
	
	@ResponseBody
	@RequestMapping("/removeUserKey.do")
	public String removeUserKey(HttpServletRequest req,String index) { 		

		if(index==null)return "kong";
		List<String> list=null;
		list = (List<String>) req.getSession().getServletContext().getAttribute("userKey");
		
		if(list!=null) {
			list.remove(index);
			req.getSession().getServletContext().setAttribute("userKey",list);
		}
		
		String msg=null;
		for(String s :list) {
			msg= msg+s;
			System.out.println(s);
		}
		
		return msg;
	
	}
	
	@ResponseBody
	@RequestMapping("/addUserKey.do")
	public String addUserKey(HttpServletRequest req,String userKey) { 
		
		if(userKey==null)return"请求数据不能为空";
		List<String> list=null;
		list = (List<String>) req.getSession().getServletContext().getAttribute("userKey");		
		if(list==null) {
			list =new ArrayList<String>();
		}
		
		list.add(userKey);
		
		req.getSession().getServletContext().setAttribute("userKey",list);
		
		return userKey;
		
		
	}
	
	
	
	
	
	
	
	

	
	
	
}

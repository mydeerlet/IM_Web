package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import bean.ResponseResult;
import bean.User;



@Controller
public class LoginController {
	

	
	static Map<String,Socket> map = new HashMap<String, Socket>();
	
    //服务器端口
    public static final int port = 8888;
    //服务器主机名
    public static final String address = "localhost";
    //服务器接收消息，以日志形式记录，动态
    private static Logger log = Logger.getLogger("server");
    
    static Socket so=null; 
	
    
    
    
	//登录页面	
	@RequestMapping("/login.do")
	public String Login() {
		
		service();
		return "index";
	}
	
	

	//异步提交登录
	@RequestMapping("/adminLogin.do")
	@ResponseBody
	public ResponseResult<Void> loginAdmin(String userName,String password){

		ResponseResult<Void> rr = new ResponseResult<Void>(1,"登录成功");

		return rr;
	}
	

	private void service() {


		
		try {
	        //1.创建一个服务器端的Socket,即ServerSocket，指定绑定的端口
	        ServerSocket ss= new ServerSocket(8888);
	        
	        
	        log.log(Level.INFO, "服务器开启监听，端口：" + port);
	      
	       
	        
	        //记录客户端的数量
	        int count=0;
	        //循环侦听等待客户端的连接
	       
	        
	        while(true){
	            //2.调用accept方法开始监听，等待客户端的连接
	             so=ss.accept();//accept方法返回Socket实例 //阻塞监听	          
	             SocketDoWith st=new SocketDoWith(so);//创建线程对其进行操作
	             st.start(); //启动线程，执行与客户端的交互//注意是start不是run
	             
//	             clientSocketList.add(so);//将链接添加到，集合中，用以群发
	          	             
	             count++;
	             System.out.println("此时客户端数量为："+count);
	             InetAddress add=so.getInetAddress();
	             System.out.println("当前客户端的ip地址为"+add.getHostAddress());	            
	        }
	        
	        
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	 //服务器处理客户端Socket消息线程
    static class SocketDoWith extends Thread {
    
    	Logger log = Logger.getLogger("server");
    	
        private Socket socket = null;
    	
        public SocketDoWith(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
        	 if (socket == null) return;
        	 try {
				InputStream is = socket.getInputStream();
//				OutputStream os =socket.getOutputStream();
				
				byte[] bytes;
				String msg ="";
	            while(true) {
	            	
	            	int first = is.read();         
	            	if(first==-1) {
	            		break;
	            	}
	            	int second = is.read();
	            	int length =(first <<8)+second;
	            	bytes  = new byte[length];
	            	is.read(bytes);
	            	
	            	msg=new String( bytes,"utf-8");
	            	
	            	
	            	try {
	            		Gson gson = new Gson();
	            		User  user = gson.fromJson(msg, User.class);
	            		map.put(user.getUserName(), so);
	            		
	            	}catch (Exception e) {
						// TODO: handle exception
					}
	            	
	            	 //把信息输出到，当前连接的所有客户端。
                    for ( String key : map.keySet()) {
                    	
                        if (!map.get(key).isClosed()) {//防止发现送消息给，，断连客户端。
                        	
                        	byte[] sendBytes;                        
                        	OutputStream os =map.get(key).getOutputStream();
                        	sendBytes = msg.getBytes("utf-8");
	           				//然后将消息的长度优先发送出去
	           			    os.write(sendBytes.length >>8);
	           			    os.write(sendBytes.length);
	           			    //然后将消息再次发送出去
	           			    os.write(sendBytes);
	           			    os.flush();
                      
                            log.log(Level.INFO, "服务器发送内容：" +key+":"+ msg);//+ client.toString() + "  "
                        } else {//断开的Socket就移除
                            map.remove(key);//移除
                        }
                    }
	            }
				
				
				
				
			}catch (SocketException e) {
				  log.log(Level.INFO, "服务器断开连接！！！");	
			}
        	 
        	 catch (IOException e) {
			
				e.printStackTrace();
				 log.log(Level.INFO, "IO 异常");
			}
        	 
        	 
        	 
        }
    
    }
	
}

package utils;

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

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import bean.HeadMsg;
import bean.User;

public class SocketService {

	Map<String, Socket> map = new HashMap<String, Socket>();
	// 服务器端口
	final int port = 8888;
	// 服务器主机名
	static final String address = "localhost";
	// 服务器接收消息，以日志形式记录，动态
	Logger log = Logger.getLogger("server");
	Socket so = null;
	Gson gson = new Gson();

	public SocketService() {
			

		try {
			// 1.创建一个服务器端的Socket,即ServerSocket，指定绑定的端口
			ServerSocket ss = new ServerSocket(8888);
			log.log(Level.INFO, "服务器开启监听，端口：" + port);
			// 记录客户端的数量
			int count = 0;
			// 循环侦听等待客户端的连接
			while (true) {
				// 2.调用accept方法开始监听，等待客户端的连接
				so = ss.accept();// accept方法返回Socket实例 //阻塞监听
				SocketDoWith st = new SocketDoWith(so);// 创建线程对其进行操作
				st.start(); // 启动线程，执行与客户端的交互//注意是start不是run
				count++;
				System.out.println("此时客户端数量为：" + count);
				System.out.println("当前客户端的ip地址为" + so.getInetAddress().getHostAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Map<String, Socket> getSocketMap() {
		return map;
	}
	
	

	 //服务器处理客户端Socket消息线程
     class SocketDoWith extends Thread {
    
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
	            	//map socket 加上key imCode
	            	try {
	            		User  user = gson.fromJson(msg, User.class);
	            		map.put(user.getImCode(), so);
	            	
	            					   
	            	}catch (Exception e) {
					}
	            	
	            	
	            	try {
	            		Gson gson = new Gson();
	            		HeadMsg  headMsg = gson.fromJson(msg, HeadMsg.class);
	            		
	            		 String key =headMsg.getImCode();
	            		 String sendMsg= headMsg.getMsg();
	            		
	            		 if (!map.get(key).isClosed()) {//防止发现送消息给，，断连客户端。
	                        	byte[] sendBytes;                        
	                        	OutputStream os =map.get(key).getOutputStream();
	                        	sendBytes = sendMsg.getBytes("utf-8");
		           				//然后将消息的长度优先发送出去
		           			    os.write(sendBytes.length >>8);
		           			    os.write(sendBytes.length);
		           			    //然后将消息再次发送出去
		           			    os.write(sendBytes);
		           			    os.flush();                      
	                            log.log(Level.INFO, "服务器发送内容："+ sendMsg);//+ client.toString() + "  "
	                        } else {//断开的Socket就移除
	                            map.remove(key);//移除
	                         
					
	                            
	                        }
	            		
	            	}catch (Exception e) {}
	            }
				
				
				
				
			}catch (SocketException e) {
				  log.log(Level.INFO, "服务器断开连接！！！");	
			}catch (IOException e) {
				e.printStackTrace();
				 log.log(Level.INFO, "IO 异常");
			}
        	 
        }
    
    }
}

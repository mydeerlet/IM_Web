import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bean.User;
import dao.UserDao;



public class t {

	@Test
	public void selectAll() {
		
		AbstractApplicationContext ac =
				new ClassPathXmlApplicationContext(
						"application-dao.xml");

		
		UserDao d  = ac.getBean("userDao",UserDao.class);
		

	    User user =	d.findUser("123");
		
	    
		System.out.println(user.getImCode()+"===");	
	    
	    
	}
}

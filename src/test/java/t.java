import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



import bean.LoveBean;
import dao.LoveDao;





public class t {

	@Test
	public void selectAll() {
		
		AbstractApplicationContext ac =
				new ClassPathXmlApplicationContext(
						"application-dao.xml");

		
		LoveDao d  = ac.getBean("loveDao",LoveDao.class);
		LoveBean bean = new LoveBean();
		Date dd = new Date(System.currentTimeMillis());
		bean.setResult("的");
		
		
		bean.setCrateDate(dd);
		

		d.getAll();
		
		
	    
	
	    
	    
	}
}

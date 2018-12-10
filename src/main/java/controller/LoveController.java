package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import bean.LoveBean;
import service.ILoveService;

@Controller
@RequestMapping(value="/app")
public class LoveController {

	
	//对象实例化	
	@Resource ILoveService loveService;
	
	
	/**
	 * 插入数据
	 */
	@RequestMapping(value="/save.do")
	@ResponseBody
	public void save(LoveBean loveBean) {
		loveService.save(loveBean);
	}
	
	
	/**
	 * 查询所有
	 */
	@RequestMapping(value="/getAll.do")
	public String getAll(ModelMap map) {
		List<LoveBean> listFT = loveService.getAll();
		map.addAttribute("listDT", listFT);
		
		for(LoveBean b:listFT) {
			
			System.out.println(b.toString());
		}
		
		return "love";
	}
}

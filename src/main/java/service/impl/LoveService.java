package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bean.LoveBean;
import dao.LoveDao;
import service.ILoveService;

@Service
public class LoveService implements ILoveService {

	
	@Resource
	private LoveDao loveDao;

	
	
	/**
	 * 插入数据
	 */

	public void save(LoveBean loveBean) {
		loveDao.save(loveBean);
		
	}


	/**
	 * 查出所有数据
	 */
	public List<LoveBean> getAll() {

		return loveDao.getAll();
	}
	
	




}

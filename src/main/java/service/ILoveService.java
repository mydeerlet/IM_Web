package service;

import java.util.List;

import bean.LoveBean;

public interface ILoveService {

	/**
	 * 插入数据
	 */
	void save(LoveBean loveBean);
	
	/**
	 * 查询所有数据
	 * @return
	 */
	List<LoveBean> getAll();
}

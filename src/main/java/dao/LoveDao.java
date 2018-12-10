package dao;

import java.util.List;

import bean.LoveBean;



public interface LoveDao {

	/**
	 * 保存用户数据
	 * @param loveBean
	 */
	void save(LoveBean loveBean);
	
	
	/**
	 * 查看全部
	 * @return
	 */
	List<LoveBean> getAll();
}

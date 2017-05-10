package com.cmcc.medicalcare.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.cmcc.medicalcare.inter.Pager;

/**
 * 
 * @author siming.wu
 *@date 2016-11-04 上午10:13:19
 */

@SuppressWarnings("unchecked")
public class SlavebatisDAO extends SqlSessionDaoSupport{
	/**
	 * mapperName
	 */
	private String mapperName;
	
	/**
	 * @param mapperName the mapperName to set
	 */
	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public <T> T findById(Object id) {

		return (T)getSqlSession().selectOne(mapperName +".getById",id);
	}
	/**
	 * 根据参数查找
	 * @param mapperId
	 * @param params
	 * @return
	 */
	public <T> T findByParam(String mapperId,Object params){
		return (T)getSqlSession().selectOne(mapperName+"."+mapperId,params);
	}
	/**
	 * 查找
	 * @param mapperId
	 * @param params
	 * @return
	 */
	public Object findUnique(String mapperId,Object params){
		return getSqlSession().selectOne(mapperName+"."+mapperId,params);
	}
	
	/**
	 * 根据配置的指定查询语句查询实体列表
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public <T> List<T> getList(Object params){
		return getSqlSession().selectList(mapperName+".getList", params);
	}
	/**
	 * 获取列表
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> getList(Object params,int pageIndex ,int pageSize){
		pageIndex=pageIndex<=0?1:pageIndex;
		pageSize=pageSize<=0?10:pageSize;
		int offset = (pageIndex-1)*pageSize;
		RowBounds row = new RowBounds(offset,pageSize);
		return getSqlSession().selectList(mapperName+".getList", params,row);
	}
	/**
	 * 获取列表
	 * @param mapperId
	 * @param pageIndex
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public <T> Pager<T> getList(String mapperId, int pageIndex ,int pageSize ,Object params){
		Pager<T> pager = new Pager<T>();
		if(pageSize <= 0){
			pager.setQueryAll(true);
		}else{
			pager.setPageSize(pageSize);
		}
		pager.setPageIndex(pageIndex <= 0 ? 1 : pageIndex);
		List<T> list = getSqlSession().selectList(mapperName+"."+mapperId, params,pager);
		pager.setList(list);
		return pager;
	}
	/**
	 * 全局插入
	 * @param entity
	 * @return
	 */
	public <E> int globalInsert(E entity){
		return getSqlSession().insert(entity.getClass().getName()+".insert", entity);
	}
	/**
	 * 全局更新
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public <E> int globalUpdate(String mapperId ,E entity){
		return getSqlSession().insert(entity.getClass().getName()+"."+mapperId, entity);
	}
	/**
	 * 插入
	 * @param entity
	 * @return
	 */
	public <T> int insert(T entity){
		return getSqlSession().insert(mapperName+".insert", entity);
	}
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	public <T> int update(T entity){
		return getSqlSession().update(mapperName+".update", entity);
	}
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	public <T> int delete(T entity){
		return getSqlSession().update(mapperName+".delete", entity);
	}
	/**
	 * 插入
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public <T> int insert(String mapperId,T entity){
		return getSqlSession().insert(mapperName+"."+mapperId, entity);
	}
	/**
	 * 更新
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public <T> int update(String mapperId,T entity){
		return getSqlSession().update(mapperName+"."+mapperId, entity);
	}
	/**
	 * 删除
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public <T> int delete(String mapperId,T entity){
		return getSqlSession().update(mapperName+"."+mapperId, entity);
	}
	/**
	 * 获取列表
	 * @param mapperId
	 * @param params
	 * @return
	 */
	public <T> List<T> getList(String mapperId,Object params){
		return getSqlSession().selectList(mapperName+"."+mapperId, params);
	}
	/**
	 * 获取列表
	 * @param mapperId
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> getList(String mapperId,Object params,int pageIndex ,int pageSize){
		pageIndex=pageIndex<=0?1:pageIndex;
		pageSize=pageSize<=0?10:pageSize;
		int offset = (pageIndex-1)*pageSize;
		RowBounds row = new RowBounds(offset,pageSize);
		return getSqlSession().selectList(mapperName+"."+mapperId, params,row);
	}
	
	/**
	 * 获取列表
	 * @param mapperId
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String mapperId,Object params){
		return getSqlSession().selectList(mapperName+"."+mapperId, params);
	}
	/**
	 * 获取列表
	 * @param mapperId
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String mapperId,Object params,int pageIndex ,int pageSize){
		pageIndex=pageIndex<=0?1:pageIndex;
		pageSize=pageSize<=0?10:pageSize;
		int offset = (pageIndex-1)*pageSize;
		RowBounds row = new RowBounds(offset,pageSize);
		return getSqlSession().selectList(mapperName+"."+mapperId, params,row);
	}
	/**
	 * 获取列表
	 * @param mapperId
	 * @param pageIndex
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Pager<List<Map<String, Object>>> queryForList(String mapperId, int pageIndex ,int pageSize ,Object params){
		Pager<List<Map<String, Object>>> pager = new Pager<List<Map<String, Object>>>();
		if(pageSize <= 0){
			pager.setQueryAll(true);
		}else{
			pager.setPageSize(pageSize);
		}
		pager.setPageIndex(pageIndex <= 0 ? 1 : pageIndex);
		List<List<Map<String, Object>>> list = getSqlSession().selectList(mapperName+"."+mapperId, params,pager);
		pager.setList(list);
		return pager;
	}
	
	
}

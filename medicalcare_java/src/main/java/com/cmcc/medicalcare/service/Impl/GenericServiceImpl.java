package com.cmcc.medicalcare.service.Impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmcc.medicalcare.dao.MybatisDAO;
import com.cmcc.medicalcare.dao.SlavebatisDAO;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.service.GenericService;
import com.cmcc.medicalcare.utils.GenericsUtils;

/**
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <ID>
 */
@Service(value = "genericService")

public class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {
	/**
	 * persistentClass
	 */
	private Class<T> persistentClass; // T类对应的持久类
	/**
	 * mybatisDAO
	 */
	protected MybatisDAO mybatisDAO;
	/**
	 * slavebatisDAO
	 */
	protected SlavebatisDAO slavebatisDAO;


	/**
	 * GenericServiceImpl
	 */
	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
//		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.persistentClass = GenericsUtils.getSuperClassGenricType(getClass(),0);
	}

	/**
	 * @param mybatisDAO the mybatisDAO to set
	 */
	
	/**
	 * setMybatisDAO
	 * @param mybatisDAO
	 */
	@Resource
	public void setMybatisDAO(MybatisDAO mybatisDAO) {
		mybatisDAO.setMapperName(persistentClass.getName());
		this.mybatisDAO = mybatisDAO;
	}
	/**
	 * @return the slavebatisDAO
	 */
	public SlavebatisDAO getSlavebatisDAO() {
		return slavebatisDAO;
	}

	/**
	 * @param slavebatisDAO the slavebatisDAO to set
	 */
	@Resource
	public void setSlavebatisDAO(SlavebatisDAO slavebatisDAO) {
		slavebatisDAO.setMapperName(persistentClass.getName());
		this.slavebatisDAO = slavebatisDAO;
	}

	/**
	 * findById
	 */
	public T findById(String mapperId, ID id) {
		return slavebatisDAO.findByParam(mapperId, id);
	}
	/**
	 * findByParam
	 */
	public T findByParam(String mapperId, Object param) {
		return slavebatisDAO.findByParam(mapperId, param);
	}
	/**
	 * getList
	 */
	public List<T> getList(String mapperId, Object params) {
		return slavebatisDAO.getList(mapperId, params);
	}
	/**
	 * getList
	 */
	public List<T> getList(String mapperId, Object params,int pageIndex,int pageSize) {
		return slavebatisDAO.getList(mapperId, params, pageIndex, pageSize);
	}
	/**
	 * getList
	 */
	public Pager<T> getList(String mapperId, int pageIndex, int pageSize,
			Object params) {
		return slavebatisDAO.getList(mapperId, pageIndex, pageSize, params);
	}
	/**
	 * insert
	 */
	public int insert(T entity) {
		return mybatisDAO.insert(entity);
	}
	/**
	 * insert
	 */
	public int insert(String mapperId,T entity) {
		return mybatisDAO.insert(mapperId, entity);
	}
	/**
	 * update
	 */
	public int update(T entity) {
		return mybatisDAO.update(entity);
	}
	/**
	 * update
	 */
	public int update(String mapperId,T entity) {
		return mybatisDAO.update(mapperId, entity);
	}
	/**
	 * delete
	 */
	public int delete(T entity) {
		return mybatisDAO.delete(entity);
	}
	/**
	 * delete
	 */
	public int delete(String mapperId,T entity) {
		return mybatisDAO.delete(mapperId, entity);
	}
	/**
	 * count
	 */
	public int count(String mapperId, Object ob) {
		return slavebatisDAO.findByParam(mapperId, ob);
	}
	/**
	 * findById
	 */
	public T findById(ID id)  {
		return slavebatisDAO.findById(id);
	}
	/**
	 * getList
	 */
	public List<T> getList(Object params) {
		return slavebatisDAO.getList(params);
	}
	/**
	 * getList
	 */
	public List<T> getList(Object params, int pageIndex, int pageSize) {
		return slavebatisDAO.getList(params, pageIndex, pageSize);
	}
	/**
	 * findObjectByParam
	 */
	public Map<String, Object> findObjectByParam(String mapperId, Object param) {
		// TODO Auto-generated method stub
		return slavebatisDAO.findByParam(mapperId, param);
	}
	/**
	 * queryForList
	 */
	public List<Map<String, Object>> queryForList(String mapperId,Object params) {
		return slavebatisDAO.queryForList(mapperId,params);
	}
	/**
	 * queryForList
	 */
	public List<Map<String, Object>> queryForList(String mapperId,Object params, int pageIndex,int pageSize) {
		return slavebatisDAO.queryForList(mapperId,params,pageIndex,pageSize);
	}
	/**
	 * queryForList
	 */
	public Pager<List<Map<String, Object>>> queryForList(String mapperId,
			int pageIndex, int pageSize, Object params) {
		return slavebatisDAO.queryForList(mapperId,pageIndex,pageSize,params);
	}
	
}

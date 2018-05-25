package com.cmcc.medicalcare.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cmcc.medicalcare.inter.Pager;

public interface GenericService<T, ID extends Serializable> {

	/**
	 * 根据实体ID查找。<br>
	 * 参数mapperId的取值为：<br>
	 * 假如MyBatis Mapper配置文件中有如下：<br>
	 * &lt;mapper namespace="com.sunchis.demo.model.User"&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;select id="getUserById" parameterType="int" resultMap="userMap"&gt;...<br>
	 * &lt;/mapper&gt;<br>
	 * 那么mapperId就是“getUserById”了。其中namespace会自动加上
	 * 
	 * @param mapperId  MyBatis支持的完全限定ID名
	 * @param id  数据库中表的主键值
	 * @return 实体
	 * @throws Exception 
	 */
	public T findById(ID id);
	/**
	 * findById
	 * @param mapperId
	 * @param id
	 * @return
	 */
	public T findById(String mapperId, ID id);
	/**
	 * findByParam
	 * @param mapperId
	 * @param param
	 * @return
	 */
	public T findByParam(String mapperId, Object param);
	/**
	 * findObjectByParam
	 * @param mapperId
	 * @param param
	 * @return
	 */
	public Object findObjectByParam(String mapperId, Object param);
	
	
	
	/**
	 * 根据配置的指定查询语句查询实体列表
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public List<T> getList(Object params);
	/**
	 * getList
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> getList(Object params,int pageIndex ,int pageSize);
	/**
	 * getList
	 * @param mapperId
	 * @param params
	 * @return
	 */
	public List<T> getList(String mapperId, Object params);
	/**
	 * getList
	 * @param mapperId
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> getList(String mapperId, Object params,int pageIndex ,int pageSize);
	
	/**
	 * 用指定的mapperId进行分页查询，如果pageSize<=0则不分页
	 * @param mapperId
	 * @param pageIndex
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Pager<T> getList(String mapperId, int pageIndex ,int pageSize , Object params);
	

	/**
	 * 新增实体对象，并将新增的实体序列ID设置到实体中<br>
	 * 其配置在xml文件中的语句名必须为insert,例如：<br>
	 * &lt;insert id="insert" parameterType="com.isoftstone.demo.model.User"&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;selectKey keyProperty="id" resultType="long" order="BEFORE"&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;select SEQ_T_USER.nextVal from dual <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/selectKey&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;INSERT INTO T_USER (ID, NAME ,...) <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;VALUES (#{id, jdbcType=long}, #{name, jdbcType=VARCHAR}, ....)<br>
	 * &lt;/insert&gt;<br>
	 * @param entity
	 * 
	 * @return 返回操作影响的记录数
	 */
	public int insert(T entity);
	/**
	 * insert
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public int insert(String mapperId,T entity);
	/**
	 * 更新实体对象<br>
	 * 其配置在xml文件中的语句名必须为update,例如：<br>
	 * &lt;update id="update" parameterType="com.isoftstone.demo.model.User"&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;UPDATE T_USER SET <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;NAME=#{name, jdbcType=VARCHAR},<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp; ....<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;WHERE ID = #{id}<br>
	 * &lt;/update&gt;<br>
	 * @param entity
	 * 
	 * @return 返回操作影响的记录数
	 */
	public int update(T entity);
	/**
	 * update
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public int update(String mapperId,T entity);
	
	/**
	 * 删除指定的实体对象<br>
	 * 其配置在xml文件中的语句名必须为delete,例如：<br>
	 * &lt;delete id="delete"&gt;<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;DELETE FROM T_USER WHERE ID = #{id}<br>
	 * &lt;/delete&gt;<br>
	 * 项目中可能使用假删除形式，则改为UPDATE语句<br>
	 * @param entity
	 * 
	 * @return 返回操作影响的记录数
	 */
	public int delete(T entity);
	/**
	 * delete
	 * @param mapperId
	 * @param entity
	 * @return
	 */
	public int delete(String mapperId,T entity);
	// ……(用户可以继续增加自己的方法)
	/**
	 * 计数统计
	 * @param mapperId
	 * @param ob
	 * @return
	 */
	public int count(String mapperId,Object ob);
	
	/**
	 * queryForList
	 * @param mapperId
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryForList(String mapperId,Object params);
	/**
	 * queryForList
	 * @param mapperId
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> queryForList(String mapperId,Object params,int pageIndex ,int pageSize);
	/**
	 * queryForList
	 * @param mapperId
	 * @param pageIndex
	 * @param pageSize
	 * @param params
	 * @return
	 */
	Pager<List<Map<String, Object>>> queryForList(String mapperId, int pageIndex ,int pageSize ,Object params);
	
	
}

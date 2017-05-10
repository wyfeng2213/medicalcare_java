package com.cmcc.medicalcare.inter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.jdbc.support.JdbcUtils;

import com.cmcc.medicalcare.utils.ReflectUtil;

/**
 * @author mikel
 * @date 2013-7-17 上午9:42:51
 * @description 
 */
@Intercepts( {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagableInterceptor implements Interceptor{
	/**
	 * dialect
	 */
	private String dialect = "mysql";
	
	public Object intercept(Invocation invocation) throws Throwable {
		if(invocation.getTarget() instanceof RoutingStatementHandler){  
            RoutingStatementHandler statement = (RoutingStatementHandler)invocation.getTarget();  
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getFieldValue(statement, "delegate");  
            if(null==delegate) {
            	return null;
            }
            RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(delegate,
    				"rowBounds");
            if(null==rowBounds) {
            	return null;
            }
            if (rowBounds instanceof Pager) {
            	Pager<?> page = (Pager<?>) rowBounds;
            	if(!page.isQueryAll()){
	            	MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement"); 
	            	if(null==mappedStatement) {
	            		return null;
	            	}
	                BoundSql boundSql = delegate.getBoundSql(); 
	                Object parameterObject = boundSql.getParameterObject();
	                Connection connection = (Connection) invocation.getArgs()[0];  
	                String sql = boundSql.getSql();  
	                String countSql = "select count(0) from (" + sql+ ") temp"; //记录统计  
	                BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,
							boundSql.getParameterMappings(),parameterObject);  
	                int count = 0;
	                PreparedStatement stmt = null;
	                ResultSet rs = null;
					try {
						stmt = connection.prepareStatement(countSql);  
						setParameters(stmt,mappedStatement,countBS,parameterObject);
						rs = stmt.executeQuery();  
						if (rs.next()) {  
						    count = rs.getInt(1);  
						}  
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(null!=rs) {
							rs.close();
						}
						if(null!=stmt/*&&!stmt.isClosed()*/) {
							stmt.close();
						}
					}
					page.setTotalCount(count);
	                String pageSql = generatePageSql(sql,page); 
	                ReflectUtil.setFieldValue(boundSql, "sql", pageSql);//将分页sql语句反射回BoundSql. 
	                page.setQueryAll(true);
            	}
            }else if (rowBounds.getLimit() > 0
    				&& rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
    			BoundSql boundSql = statement.getBoundSql();
    			String sql = boundSql.getSql();
    			ReflectUtil.setFieldValue(boundSql, "sql", generatePageSql(sql,rowBounds));
    			ReflectUtil.setFieldValue(rowBounds, "offset", RowBounds.NO_ROW_OFFSET);
    			ReflectUtil.setFieldValue(rowBounds, "limit", RowBounds.NO_ROW_LIMIT);
    		}
		}
		return invocation.proceed();
	}

	/** 
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler 
     * @param stmt 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement stmt,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {  
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));  
                        }  
                    } else {  
                        value = metaObject == null ? null : metaObject.getValue(propertyName);  
                    }  
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();  
                    if (typeHandler == null) {  
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());  
                    }  
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) 
                    	jdbcType = configuration.getJdbcTypeForNull();
                    typeHandler.setParameter(stmt, i + 1, value, parameterMapping.getJdbcType());  
                }  
            }  
        }  
    }  
      
    /** 
     * 根据数据库方言，生成特定的分页sql 
     * @param sql 
     * @param rowBounds 
     * @return 
     */  
    private String generatePageSql(String sql,RowBounds rowBounds){  
    	StringBuilder sqlBuffer = new StringBuilder(sql);
        if(rowBounds != null){  
            if("oracle".equals(dialect)){  
                return OracleDialect.DEFAULT.getLimitString(
                		sqlBuffer.toString(), rowBounds.getOffset(), rowBounds.getLimit());  
            }  
            if ("mysql".equalsIgnoreCase(dialect)) {  
                return getMysqlPageSql((Pager<?>) rowBounds, sqlBuffer); 
        }
        return sqlBuffer.toString();
    }
        return null;
    }   
        
    /** 
     * 获取Mysql数据库的分页查询语句 
     * @param page 分页对象 
     * @param sqlBuffer 包含原sql语句的StringBuffer对象 
     * @return Mysql数据库分页语句 
     */  
    private String getMysqlPageSql(Pager<?> page, StringBuilder sqlBuffer) {  
       //计算第一条记录的位置，Mysql中记录的位置是从0开始的。  
       int offset = (page.getPageIndex() - 1) * page.getPageSize();  
       sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());  
       return sqlBuffer.toString();  
    }    
	/**
	 * plugin
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

	/**
	 * @param dialect the dialect to set
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
}

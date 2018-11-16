package com.wen.utils;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
 
 
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.mysql.jdbc.Connection;
//拦截StatementHandler中参数类型为Connection的prepare方法
@Intercepts(@Signature(type=StatementHandler.class,method="prepare",args={Collection.class}))
public class MyInterceptor implements Interceptor{
	
	private final Logger logger=LoggerFactory.getLogger(MyInterceptor.class);
 
	
	/**
	 * 插件运行的代码，它将代替原有的方法，要重写最重要的intercept方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if(invocation.getTarget() instanceof StatementHandler){
			//这里我们有一个设定  如果查询方法含有Page 就进行分页 其他方法无视  
            //所以就要获取方法名  
			StatementHandler statementHandler=(StatementHandler)invocation.getTarget();
			//通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性
			MetaObject metaObject=SystemMetaObject.forObject(statementHandler);
			 //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
			MappedStatement mappedStatement=(MappedStatement)metaObject.getValue("delegate.mappedStatement");
			// 配置文件中SQL语句的ID
			String selectId=mappedStatement.getId();
			if(selectId.matches(".*Page$")){//需要拦截的ID(正则匹配)
				BoundSql boundSql=(BoundSql)metaObject.getValue("delegate.BoundSql");
				// 原始的SQL语句
				String sql=boundSql.getSql();
				HashMap<String,Object> hashMap=(HashMap<String, Object>)(boundSql.getParameterObject());
				MyPage myPage=(MyPage)hashMap.get("page");
				//重写SQL
				// 查询总条数的SQL语句
				String countSql="select count(0) from "+sql;
				String pageSql=sql+"limit "+myPage.getPageBegin()+","+myPage.getPageSize();
				
				Connection connection=(Connection)invocation.getArgs()[0];
				
				PreparedStatement cStatement=null;
				ResultSet rs=null;
				int totalCount=0;
				try {
					cStatement=connection.prepareStatement(countSql);
					setParameters(cStatement,mappedStatement,boundSql,boundSql.getParameterObject());
					rs=cStatement.executeQuery();
					if(rs.next()){
						totalCount=rs.getInt(1);
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}finally {
					try {    
                        rs.close();    
                        cStatement.close();    
                    } catch (Exception e) {    
                       logger.error("SQLException", e);  
                    }   
				}
				metaObject.setValue("delegate.boundSql.sql", pageSql);
				
				myPage.setNumCount(totalCount);
			}
			
			
		}
		
		return invocation.proceed();
	}
	
	
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
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
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());  
                }  
            }  
        }  
		
	}
 
	@Override
	public Object plugin(Object arg0) {
		// 
		return null;
	}
 
	@Override
	public void setProperties(Properties arg0) {
		// 
		
	}
 
}
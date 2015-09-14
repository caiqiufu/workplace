package com.unieap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;

import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;

/**
 * <p>
 * Description: [业务基类]
 * </p>
 * 
 * @author <a href="mailto: xxx@neusoft.com">蔡秋伏</a>
 * @version $Revision: 1.2 $
 */
public abstract class BaseBO {
	/**
	 * <p>
	 * 描述:
	 * </p>
	 */
	public final Log log = LogFactory.getLog(BaseBO.class);
	
	//abstract public Object PojoToVo(Object po, Object vo);

	//abstract public Object VoToPojo(Object vo, Object po);
	
	public Map<String,String> result(String result,String message){
		Map<String, String> model = new HashMap<String, String>();
		model.put(result,message);
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public void getPaginationDataByDetachedCriteria(DetachedCriteria detachedCriteria,Projection projection,PaginationSupport ps){
		int totalCount = getCountByDetachedCriteria(detachedCriteria,projection,ps.getDsName());
		ps.setTotalCount(totalCount);
		ps.setItems(DBManager.getHT(ps.getDsName()).findByCriteria(detachedCriteria, ps.getStartIndex(),ps.getPageSize()));
	}
	@SuppressWarnings("unchecked")
	public void getPaginationDataByDetachedCriteria(DetachedCriteria detachedCriteria,Projection projection,PaginationSupport ps,Class vo, Class po) throws InstantiationException, IllegalAccessException{
		int totalCount = getCountByDetachedCriteria(detachedCriteria,projection,ps.getDsName());
		ps.setTotalCount(totalCount);
		List<Object> datas = DBManager.getHT(ps.getDsName()).findByCriteria(detachedCriteria, ps.getStartIndex(),ps.getPageSize());
		if(datas!=null & datas.size()>0){
			List<Class> vdatas = new ArrayList<Class>();
			for(Object data:datas){
				BeanUtils.copyProperties(data,vo.newInstance());
				vdatas.add(vo);
			}
			ps.setItems(datas);
		}else{
			ps.setItems(datas);
		}
	}
	@SuppressWarnings("unchecked")
	public void getPaginationDataByDetachedCriteria(DetachedCriteria detachedCriteria,PaginationSupport ps){
		int totalCount = getCountByDetachedCriteria(detachedCriteria,null,ps.getDsName());
		ps.setTotalCount(totalCount);
		if(StringUtils.isNotEmpty(ps.getDir())){
			if(StringUtils.equalsIgnoreCase(ps.getDir(),ps.DESC)){
				detachedCriteria.addOrder(Order.desc(ps.getSort()));
			}else{
				detachedCriteria.addOrder(Order.asc(ps.getSort()));
			}
		}
		List<Object> datas = DBManager.getHT(ps.getDsName()).findByCriteria(detachedCriteria,ps.getStartIndex(),ps.getPageSize());
		ps.setItems(datas);
	}
	/**
	 * 针对sql预计，只参与分页数据组装
	 * @param className
	 * @param sql
	 * @param totalSql
	 * @param parameters
	 * @param ps
	 */
	public void getPaginationDataByMysql(Class className,String sql,String totalSql, Object[] parameters,PaginationSupport ps){
		sql = sql + " limit "+ps.getStartIndex()+","+ps.getPageSize();
		int totalCount = DBManager.getJT(null).queryForInt(totalSql,parameters);
		ps.setTotalCount(totalCount);
		List<?> items =  DBManager.getJT(null).query(sql, parameters, new EntityRowMapper(className));
		ps.setItems(items);
	}
	@SuppressWarnings("unchecked")
	public int getCountByDetachedCriteria(DetachedCriteria detachedCriteria,Projection projection,String dsName) {
		if(projection == null){
			detachedCriteria.setProjection(Projections.rowCount());
			List datas = DBManager.getHT(dsName).findByCriteria(detachedCriteria);
			detachedCriteria.setProjection(null);
			return ((Integer)datas.get(0)).intValue();
		}else{
			detachedCriteria.setProjection(Projections.rowCount());
			List datas = DBManager.getHT(dsName).findByCriteria(detachedCriteria);
			detachedCriteria.setProjection(projection);
			return ((Integer)datas.get(0)).intValue();
		}
	}
	public Integer getSequence(String dsName,String serialName){
		return UnieapConstants.getSequence(dsName, serialName);
	}
	public String getCurrentTime(String dsName){
		return UnieapConstants.getCurrentTime(dsName,UnieapConstants.TIMEFORMAT);
	}
	public Date getDateTime(String dsName){
		return UnieapConstants.getDateTime(dsName);
	}
	@SuppressWarnings("unchecked")
	public void setCriteria(DetachedCriteria criteria,Object bean) throws Exception{
		if(bean!=null){
			/*Map<String,PropertyDescriptor> poprops = CacheMgt.getBeanProps(bean.getClass().getName());
			if(poprops==null){
				poprops = new HashMap<String,PropertyDescriptor> ();
				BeanInfo poInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] props = poInfo.getPropertyDescriptors();
				for (int i = 0; i < props.length; i++){
					if (!"class".equals(props[i].getName())){
						poprops.put(props[i].getName(), props[i]);
					}
				}
				CacheMgt.setBeanProps(bean.getClass().getName(), poprops);
			}*/
			Map<String,PropertyDescriptor> beanprops = CacheMgt.getBeanProps(bean.getClass().getName());
			if(beanprops==null){
				cacheBeanprops(bean);
				beanprops = CacheMgt.getBeanProps(bean.getClass().getName());
			}
			Iterator<String> iter = beanprops.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(beanprops.containsKey(key)){
					PropertyDescriptor prop = beanprops.get(key);
					Method getter = prop.getReadMethod();
					Object value = getter.invoke(bean, null);
					if (value!=null&&StringUtils.isNotEmpty(value.toString())){
						Property pro = Property.forName(key);
						Class<?> retType = getter.getReturnType();
						if(String.class==retType){
							criteria.add(pro.like(value.toString(), MatchMode.START));
						}else{
							criteria.add(pro.eq(value));
						}
					}
				}
			}
		}
	}
	public void cacheBeanprops(Object bean) throws IntrospectionException{
		Map<String,PropertyDescriptor> beanprops = new HashMap<String,PropertyDescriptor>();
		BeanInfo poInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] props = poInfo.getPropertyDescriptors();
		for (int i = 0; i < props.length; i++){
			if (!"class".equals(props[i].getName())){
				Method getter = props[i].getReadMethod();
				if(getter!=null){
					beanprops.put(props[i].getName(), props[i]);
				}
			}
		}
		CacheMgt.setBeanProps(bean.getClass().getName(), beanprops);
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> checkExist(String displayMsg,String fieldName,String value,Class object,String dsName){
		DetachedCriteria criteria = DetachedCriteria.forClass(object);
		Property code = Property.forName(fieldName);
		criteria.add(code.eq(value));
		List resdatas = DBManager.getHT(dsName).findByCriteria(
				criteria);
		if(resdatas!=null&&resdatas.size()>0){
			Map<String, String> map = result(UnieapConstants.RETURNMESSAGE,displayMsg);
			map.put(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
			return map;
		}else{
			return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		}
	}
	public Map<String, String> checkExistForUpdate(String displayMsg,String IdName,Integer idValue,String fieldName,String filedValue,Class object,String dsName){
		DetachedCriteria criteria = DetachedCriteria.forClass(object);
		Property id = Property.forName(IdName);
		criteria.add(id.ne(idValue));
		Property code = Property.forName(fieldName);
		criteria.add(code.eq(filedValue));
		List resdatas = DBManager.getHT(dsName).findByCriteria(
				criteria);
		if(resdatas!=null&&resdatas.size()>0){
			Map<String, String> map = result(UnieapConstants.RETURNMESSAGE,displayMsg);
			map.put(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
			return map;
		}else{
			return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		}
	}
	public void deleteById(Class<?> entityName, java.lang.Integer id, String dsName){
		DBManager.getHT(dsName).delete(findById(entityName,id,dsName));
	}
	/**
	 * @param entityName
	 * @param id
	 * @param dsName
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object findById(Class entityName, java.lang.Integer id, String dsName) {
		Object instance = DBManager.getHT(dsName).get(entityName, id);
		return instance;
	}
}

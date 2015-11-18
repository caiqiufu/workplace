package com.unieap.base.dao;

// default package

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;

import com.unieap.db.DBManager;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
@Service("baseHDAO")
public class BaseHibernateDAO1 {
	private final Log logger = LogFactory.getLog(BaseHibernateDAO1.class);

	/**
	 * @param entityClass
	 * @param dsName
	 */
	public void deleteAll(List<?> entityClass, String dsName) {
		DBManager.getHT(dsName).deleteAll(entityClass);
	}

	/**
	 * @param persistentInstance
	 * @param dsName
	 */
	public void delete(Object persistentInstance, String dsName){
		DBManager.getHT(dsName).delete(persistentInstance);
	}
	/**
	 * @param entityName
	 * @param id
	 * @param dsName
	 */
	public void deleteById(Class<?> entityName, java.lang.Long id, String dsName){
		DBManager.getHT(dsName).delete(findById(entityName,id,dsName));
	}
	/**
	 * @param entityName
	 * @param id
	 * @param dsName
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object findById(Class entityName, java.lang.Long id, String dsName) {
		Object instance = DBManager.getHT(dsName).get(entityName, id);
		return instance;
	}

	/**
	 * @param instance
	 * @param dsName
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<?> findByExample(Object instance, String dsName){
		List results = DBManager.getHT(dsName).getSessionFactory().getCurrentSession().createCriteria("BizLog")
		.add(Example.create(instance)).list();
		logger.debug("find by example successful, result size: " + results.size());
		return results;
	}

	/**
	 * @param propertyName
	 * @param value
	 * @param dsName
	 * @return List
	 */
	public List<?> findByProperty(String propertyName, Object value, String dsName){
		String queryString = "from Object as model where model." + propertyName + "= ?";
		Query queryObject = DBManager.getHT(dsName).getSessionFactory().getCurrentSession()
		.createQuery(queryString);
		queryObject.setParameter(0, value);
		return queryObject.list();
	}

	/**
	 * @param entityName
	 * @param dsName
	 * @return List
	 */
	public List<?> findAll(Class<?> entityName, String dsName){
		String queryString = "from " + entityName;
		Query queryObject = DBManager.getHT(dsName).getSessionFactory().getCurrentSession()
		.createQuery(queryString);
		return queryObject.list();
	}

	/**
	 * @param detachedInstance
	 * @param dsName
	 * @return Object
	 */
	public Object merge(Object detachedInstance, String dsName){
		Object result = (Object) DBManager.getHT(dsName).getSessionFactory().getCurrentSession().merge(
				detachedInstance);
		logger.debug("merge successful");
		return result;
	}

	/**
	 * @param instance
	 * @param dsName
	 */
	public void attachDirty(Object instance, String dsName){
		DBManager.getHT(dsName).getSessionFactory().getCurrentSession().saveOrUpdate(instance);
	}

	/**
	 * @param instance
	 * @param dsName
	 */
	public void attachClean(Object instance, String dsName){
		DBManager.getHT(dsName).getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
	}

	/**
	 * @param transientInstance
	 * @param dsName
	 */
	public void save(Object transientInstance, String dsName){
		DBManager.getHT(dsName).save(transientInstance);
	}

	/**
	 * @param transientInstance
	 * @param dsName
	 */
	public void update(Object transientInstance, String dsName){
		DBManager.getHT(dsName).update(transientInstance);
	}

	/**
	 * @param transientInstance
	 * @param lockMode
	 * @param dsName
	 */
	public void update(Object transientInstance, LockMode lockMode, String dsName){
		DBManager.getHT(dsName).update(transientInstance);
	}
}
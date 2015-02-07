package vn.vivas.core.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import vn.vivas.core.dao.GenericDAO;
import vn.vivas.core.dao.QueryParameter;
import vn.vivas.core.dao.hibernate.GenericHibernateDAO;
import vn.vivas.core.dao.hibernate.HibernateSessionFactory;

public class GenericHibernateService<T> implements GenericService<T> {
	protected Logger log = Logger.getLogger(this.getClass());
	protected GenericDAO<T> genericDao;

    public GenericHibernateService(Class<T> entityType) {
		super();
		genericDao = new GenericHibernateDAO<T>(entityType);
	}
    
	public Transaction beginTransaction() {
		return HibernateSessionFactory.getSession().beginTransaction();
	}

	@Override
	public void save(T transientInstance) {
    	Transaction trans = beginTransaction();
    	genericDao.save(transientInstance);
    	trans.commit();
    }
    
    public T merge(T detachedInstance) {
    	Transaction trans = beginTransaction();
    	T t = genericDao.merge(detachedInstance);
    	trans.commit();
    	return t;
    }
    
    @Override
	public void delete(T persistentInstance) {
    	Transaction trans = beginTransaction();
    	genericDao.delete(persistentInstance);
    	trans.commit();
    }
  
	@Override
	public T findById(long id) {
		return genericDao.findById(id); 
	}

    @Override
    public List<T> findByProperty(String propertyName, Object value) {
    	Transaction trans = beginTransaction();
    	List<T> result = genericDao.findByProperty(propertyName, value);
    	trans.commit();
    	return result;
    }
		
    @Override
    public List<T> findAll() {
    	Transaction trans = beginTransaction();
    	List<T> result = genericDao.findAll();
    	trans.commit();
    	return result;
    }

	@Override
	public long countAll() {
		return genericDao.countAll();
	}

	@Override
	public long countByProperty(String propertyName, Object value) {
		return genericDao.countByProperty(propertyName, value);
	}

	@Override
	public List<T> findByParameter(List<QueryParameter> params) {
		return genericDao.findByParameter(params);
	}

	@Override
	public List<T> countByParameter(List<QueryParameter> params) {
		return genericDao.countByParameter(params);
	}

	@Override
	public List<T> findAllWithPaging(int pageNumber, int pageSize) {
		return genericDao.findAllWithPaging(pageNumber, pageSize);
	}

	@Override
	public List<T> findByPropertyWithPaging(String propertyName, Object value,
			int pageNumber, int pageSize) {
		return genericDao.findByPropertyWithPaging(propertyName, value, pageNumber, pageSize);
	}

	@Override
	public List<T> findByParameterWithPaging(List<QueryParameter> params,
			int pageNumber, int pageSize) {
		return genericDao.findByParameterWithPaging(params, pageNumber, pageSize);
	}

	@Override
	public List<T> findByCustomQuery(String hqlQueryString,
			List<QueryParameter> params) {
		return genericDao.findByCustomQuery(hqlQueryString, params);
	}

	@Override
	public List<T> findByCustomQueryWithPaging(String hqlQueryString,
			List<QueryParameter> params, int pageNumber, int pageSize) {
		return genericDao.findByCustomQueryWithPaging(hqlQueryString, params, pageNumber, pageSize);
	}

	@Override
	public List findByNativeSqlQuery(String queryString, Class entity) {
		return genericDao.findByNativeSqlQuery(queryString, entity);
	}
}

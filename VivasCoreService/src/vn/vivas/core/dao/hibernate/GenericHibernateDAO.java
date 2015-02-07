package vn.vivas.core.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import vn.vivas.core.dao.GenericDAO;
import vn.vivas.core.dao.QueryParameter;
import vn.vivas.core.exception.DAOException;

public class GenericHibernateDAO<T> implements GenericDAO<T> {
	protected Logger log = Logger.getLogger(this.getClass());
	private String entityName;

	public GenericHibernateDAO(Class<T> entityType) {
		entityName = entityType.getName();
	}

	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	@Override	
	public void save(T transientInstance) {
		log.debug("saving Project instance");
		try {
			getSession().save(transientInstance);
			//			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw new DAOException("save() " + re.getMessage(),re);
		}
	}

	@Override
	public void delete(T persistentInstance) {
		log.debug("deleting instance");
		try {
			getSession().delete(persistentInstance);
			//			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw new DAOException("delete() " + re.getMessage(),re);
		}
	}

	@Override
	public T findById(long id) {
		log.debug("getting instance with id: " + id);
		try {
			T instance = (T) getSession()
					.get(entityName, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw new DAOException("findById() " + re.getMessage(),re);
		}
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		log.debug("finding instance by property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from " + entityName + " as model where model." 
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw new DAOException("findByProperty()" + re.getMessage(), re);
		}
	}

	@Override
	public List<T> findAll() {
		log.debug("finding all instances");
		try {
			String queryString = "from " + entityName;
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw new DAOException("findAll() " + re.getMessage(),re);
		}
	}

	@Override
	public T merge(T detachedInstance) {
		log.debug("merging instance");
		try {
			T result = (T) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw new DAOException("merge() " + re.getMessage(),re);
		}
	}

	@Override
	public long countByProperty(String propertyName, Object value) {
		log.debug("couting instance by property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "select count(*) from " + entityName + " as model where model." 
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return (Long)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("count by property name failed", re);
			throw new DAOException("countByProperty() " + re.getMessage(),re);
		}
	}

	@Override
	public long countAll() {
		log.debug("couting all instances");
		try {
			String queryString = "select count(*) from " + entityName;
			Query queryObject = getSession().createQuery(queryString);
			return (Long)queryObject.uniqueResult();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw new DAOException("countAll() " + re.getMessage(),re);
		}
	}

	public List findAllWithPaging(int pageNumber, int pageSize) {
		log.debug("finding one page of all instances");
		try {

			String queryString = "from " + entityName;
			Query selectQuery  = getSession().createQuery(queryString);
			if (pageSize >0) {
				selectQuery.setFirstResult((pageNumber - 1) * pageSize);
				selectQuery.setMaxResults(pageSize);
			}
			return selectQuery.list();

		} catch (RuntimeException re) {
			log.error("findByPropertyWithPaging", re);
			throw new DAOException("findAllWithPaging() " + re.getMessage(),re);
		}   	
	}

	public List findByPropertyWithPaging(String propertyName, Object value, int pageNumber, int pageSize) {
		log.debug("finding one page of instances: " + propertyName
				+ ", value: " + value);
		try {

			String queryString = "from " + entityName + " as model where model." 
					+ propertyName + "= ?";
			Query selectQuery  = getSession().createQuery(queryString);
			selectQuery.setParameter(0, value);
			if (pageSize >0) {
				selectQuery.setFirstResult((pageNumber - 1) * pageSize);
				selectQuery.setMaxResults(pageSize);
			}
			return selectQuery.list();

		} catch (RuntimeException re) {
			log.error("findByPropertyWithPaging", re);
			throw new DAOException("findByPropertyWithPaging" + re.getMessage(),re);
		}   	
	}

	public List countByParameter(List<QueryParameter> params) {
		log.debug("counting by paramter list");
		try {
			String queryString = "select count(*) from " + entityName + " as model where model.";

			Query selectQuery  = HibernateUtil.buildQuery(getSession(), queryString, params);

			return selectQuery.list();
		} catch (RuntimeException re) {
			log.error(" failed", re);
			throw new DAOException("countByParameter() " + re.getMessage(),re);
		}
	}

	public List findByParameter(List<QueryParameter> params) {
		log.debug("finding instances by paramter list");
		try {
			String queryString = "from " + entityName + " as model where model.";

			Query selectQuery  = HibernateUtil.buildQuery(getSession(), queryString, params);

			return selectQuery.list();
		} catch (RuntimeException re) {
			log.error(" failed", re);
			throw new DAOException("findByParameter() " + re.getMessage(),re);
		}
	}

	public List findByParameterWithPaging(List<QueryParameter> params, int pageNumber, int pageSize) {
		log.debug("finding one page of instances by paramter list");
		try {
			String queryString = "from " + entityName + " as model where model.";

			Query selectQuery  = HibernateUtil.buildQuery(getSession(), queryString, params);
			if (pageSize >0) {
				selectQuery.setFirstResult((pageNumber - 1) * pageSize);
				selectQuery.setMaxResults(pageSize);
			}

			return selectQuery.list();
		} catch (RuntimeException re) {
			log.error(" failed", re);
			throw new DAOException("findByParameterWithPaging() " + re.getMessage(),re);
		}
	}

	public List findByCustomQuery(String hqlQueryString, List<QueryParameter> params) {
		log.debug("finding instances by custom query");
		try {
			Query selectQuery  = HibernateUtil.buildQuery(getSession(), hqlQueryString, params);
			return selectQuery.list();
		} catch (Exception re) {
			log.error(" failed", re);
			throw new DAOException("findByCustomQuery() " + re.getMessage(),re);
		}
	}

	public List findByCustomQueryWithPaging(String hqlQueryString, List<QueryParameter> params, int pageNumber, int pageSize) {
		log.debug("find one page of instances by custom query:  pageNumber=" + pageNumber + " - pageSize=" + pageSize);
		try {
			Query selectQuery  = HibernateUtil.buildQuery(getSession(), hqlQueryString, params);
			if (pageSize >0) {
				selectQuery.setFirstResult((pageNumber - 1) * pageSize);
				selectQuery.setMaxResults(pageSize);
			}
			return selectQuery.list();
		} catch (RuntimeException re) {
			log.error(" failed", re);
			throw new DAOException("findByCustomQueryWithPaging() " + re.getMessage(),re);
		}
	}

	public List findByNativeSqlQuery(String sqlQuery, Class entity) {
		log.debug("find by native SQL query");
		try {
			SQLQuery query = getSession().createSQLQuery(sqlQuery);
			query.addEntity(entity);
			return query.list();
		} catch (RuntimeException re) {
			throw new DAOException("findByNativeSqlQuery() " + re.getMessage(),re);
		}
	}
}

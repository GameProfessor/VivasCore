package vn.vivas.core.service;

import java.util.List;

import vn.vivas.core.dao.QueryParameter;

public interface GenericService<T> {
	public void save(T transientInstance);
	
    public T merge(T detachedInstance);
    
	public void delete(T persistentInstance);
    
    public T findById(long id);
        
	public List<T> findAll();
    public List<T> findByProperty(String propertyName, Object value);
    public List<T> findByParameter(List<QueryParameter> params);
    
    public long countAll();   
    public long countByProperty(String propertyName, Object value);
    public List<T> countByParameter(List<QueryParameter> params);

	public List<T> findAllWithPaging(int pageNumber, int pageSize);
    public List<T> findByPropertyWithPaging(String propertyName, Object value, int pageNumber, int pageSize);
    public List<T> findByParameterWithPaging(List<QueryParameter> params, int pageNumber, int pageSize);
    
    public List<T> findByCustomQuery(String queryString, List<QueryParameter> params);
    public List<T> findByCustomQueryWithPaging(String queryString, List<QueryParameter> params, int pageNumber, int pageSize);
    
    public List findByNativeSqlQuery(String queryString, Class entity);
}

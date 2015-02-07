package vn.vivas.core.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import vn.vivas.core.dao.QueryParameter;

public class HibernateUtil {
	public static Query buildQuery(Session session, String query, List<QueryParameter> params) {
		StringBuffer queryString = new StringBuffer(query);

		int i = 0;
		for (QueryParameter param: params) {
			if ((param.getValue()==null)||(param.getPropertyName()==null)||(param.getOperator()==null)) continue;
			if (i > 0) {
				queryString.append(" ").append(param.getCondition());
				if (param.getPropertyName().indexOf(".")<0)
					queryString.append(" model.");
			}
			queryString.append(param.getPropertyName())
			.append(" ")
			.append(param.getOperator())
			.append(" ")
			.append("?");
			i++;
		}

		Query queryObject = session.createQuery(queryString.toString());

		i=0;
		for (QueryParameter param: params) {
			queryObject.setParameter(i, param.getValue());
			i++;
		}
		
		return queryObject;
	}
}

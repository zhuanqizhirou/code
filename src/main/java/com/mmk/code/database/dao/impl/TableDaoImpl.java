package com.mmk.code.database.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.mmk.code.database.dao.TableDao;
import com.mmk.code.database.model.DbTable;
import com.mmk.gene.dao.impl.SpringDataQueryDaoImpl;



/**
*@Title: TableDaoImpl
*@Description: 表 数据持久层接口实现
*@author code generator
*@version 1.0
*@date 2016-07-19 17:15:43
*/
@Repository
public class TableDaoImpl extends SpringDataQueryDaoImpl<DbTable> implements TableDao {
    
    private Log log = LogFactory.getLog(this.getClass());
    
    public TableDaoImpl(){
        super(DbTable.class);
    }
    
    
    @Override
	public List<Map<String,Object>> findAll(String db) {
		StringBuilder sql = new StringBuilder();
		sql.append("select TABLE_NAME,TABLE_SCHEMA ,true as isNode from information_schema.TABLES where TABLE_SCHEMA = ?1 ");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		query.setParameter(1, db);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> showColumn(String db,String table) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" TABLE_NAME, ");
		sql.append(" lower(COLUMN_NAME)  columnName, ");
		sql.append(" DATA_TYPE , ");
		sql.append(" CASE IS_NULLABLE WHEN 'YES' THEN 'Y' ELSE 'N' END nullable, ");
		sql.append(" CASE COLUMN_KEY WHEN 'PRI'  THEN 'Y' ELSE 'N' END isPk, ");
		sql.append(" CASE EXTRA WHEN 'auto_increment' THEN 'Y' ELSE 'N' END autoincrement, ");
		sql.append(" CASE WHEN CHARACTER_MAXIMUM_LENGTH  is NULL and data_type='date'  ");
		sql.append(" 	then 0 when CHARACTER_MAXIMUM_LENGTH  is NULL and DATA_TYPE!='date'  ");
		sql.append(" 	then NUMERIC_PRECISION ELSE CHARACTER_MAXIMUM_LENGTH END length, ");
		sql.append(" COLUMN_COMMENT comment, ");
		sql.append(" 'mysql' as DB_TYPE ");
		sql.append(" FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?1 and  TABLE_SCHEMA= ?2 ");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		query.setParameter(1, table);
		query.setParameter(2, db);
		return query.getResultList();
	}

	@Override
	public Map<String, Object> tableInfo(String db, String table) {
		String sql = "select TABLE_NAME,TABLE_COMMENT,TABLE_ROWS,AUTO_INCREMENT from information_schema.`TABLES` where TABLE_SCHEMA = ?1 and TABLE_NAME =?2 ";
		
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		query.setParameter(1, db);
		query.setParameter(2, table);
		List<Map<String, Object>> resultList = query.getResultList();
		return  (resultList.isEmpty() ? null : resultList.get(0));
	}
    
    
}
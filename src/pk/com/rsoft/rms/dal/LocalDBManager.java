package pk.com.rsoft.rms.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.persistence.Member;
import org.persistence.Plot;
import org.persistence.PlotCategory;
import org.persistence.PlotSize;
import org.persistence.PlotStatus;
import org.persistence.User;

import pk.com.rsoft.util.DBManager;
import pk.com.rsoft.util.Field;
import pk.com.rsoft.util.Field.FIELD_TYPE;
import pk.com.rsoft.util.IllegalOperationException;
import pk.com.rsoft.util.Query;

public class LocalDBManager extends DBManager {

    private static ArrayList<Class<?>> AllEntityClasses = new ArrayList<>();
    static {
    	loadAllEntityClasses();
    }
	public LocalDBManager() {
		// TODO Auto-generated constructor stub
	}
	public <T> ArrayList<T> getObjectsAsList(T obj, int pageNumber,int pageSize)
	{
		return getAll(obj,pageNumber, pageSize);
	}

	public ArrayList<?> getObjectsByType(OBJECT_TYPE type, int pageNumber,int pageSize)
	{
		switch(type)
		{
		case PLOT: 
			return getObjectsAsList(new Plot(), pageNumber, pageSize);
		case USER: 
			return getObjectsAsList(new User(), pageNumber, pageSize);
		case PLOT_CATEGORY:
			return getObjectsAsList(new PlotCategory(), pageNumber, pageSize);
		case PLOT_SIZE:
			return getObjectsAsList(new PlotSize(), pageNumber, pageSize);
		case PLOT_STATUS:
			return getObjectsAsList(new PlotStatus(),pageNumber, pageSize);
		case MEMBER:
			return getObjectsAsList(new Member(),pageNumber, pageSize);
		default: 
			break;
		}
		return null;
	}
	
	public ArrayList<?> getObjectsByType(String type, int pageNumber,int pageSize)
	{
			return getObjectsAsList(getEntityClass(type,false),pageNumber, pageSize);

	}
	
	
	public void beginTransection()
	{
		getEntityManager().getTransaction().begin();
	}
	
	public void commit()
	{
		getEntityManager().getTransaction().commit();
	}
	
	public void rollBack()
	{
		getEntityManager().getTransaction().rollback();
	}
	
	public <T> void persist(T object)
	{
		getEntityManager().persist(object);
		
	}
	
	public <T> void persistAndCommit(T object)
	{
		beginTransection();
		persist(object);
		commit();
	}
	
	public <T> void merge(T object)
	{
		getEntityManager().merge(object);		
	}
	
	public <T> void mergeAndCommit(T object)
	{
		getEntityManager().getTransaction().begin();
		getEntityManager().merge(object);
		getEntityManager().getTransaction().commit();
	}
	
	
	public <T> long getAllCount(Class<T> cls, ArrayList<Field> fieldList)
	{
		return this.getAll(cls, fieldList).size();
	}
	public <T> ArrayList<T> getAll(Class<T> cls, ArrayList<Field> fieldList)
	{
		Query query = new Query(cls);
		List<T> retList = new ArrayList<>();
		try {

			boolean first = true;
			for(Field field : fieldList)
			{
				if(first)
				{
					query.where(field);
					first =false;
				}
				else 
				{
					query.and(field);
				}			
			}

			javax.persistence.Query qry = createQuery(query.getJPAQueryString());
			for(Field f:fieldList)
			{
				
				if(f.getFieldType()==FIELD_TYPE.STRING)
				{
					qry.setParameter(f.getName(), "%"+f.getValue()+"%");
				}
				else if(f.getFieldType()==FIELD_TYPE.LONG)
				{
					long val = Long.parseLong(f.getValue());
					qry.setParameter(f.getName(), val);
				}
				else if(f.getFieldType()==FIELD_TYPE.BOOLEAN)
				{
					boolean bool = Boolean.parseBoolean(f.getValue());
					qry.setParameter(f.getName(), bool);
				}
				else if (f.getFieldType()==FIELD_TYPE.INTEGER)
				{
					int val = Integer.parseInt(f.getValue());
					qry.setParameter(f.getName(), val);
				}
				
			}
			retList =  qry.getResultList();
			 
		} catch (IllegalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//End Test
		return new ArrayList<>(retList) ;
	}
	
	private static void loadAllEntityClasses()
	{
		DBManager dbmgr = new DBManager();
		EntityManager emgr = dbmgr.getEntityManager();
		Set<EntityType<?>> s =emgr.getMetamodel().getEntities();
		System.out.println("All JPA Domain Entities:-");
		for(EntityType<?> e : s)
		{
			System.out.println(e.getName());
			AllEntityClasses.add(e.getJavaType());
		}
		System.out.println("Finished listing...!");
//		emgr.close();
//		dbmgr = null;
		
	}
	
	public Class<?> getEntityClass(String entityName,boolean caseSensitive)
	{
		if (caseSensitive) {
			for (Class<?> cls : AllEntityClasses) {
				if (cls.getSimpleName().equals(entityName)) {
					return cls;
				}
			}
		}
		else
		{
			for (Class<?> cls : AllEntityClasses) {
				if (cls.getSimpleName().equalsIgnoreCase(entityName)) {
					return cls;
				}
			}			
		}
		return null;
	}
}

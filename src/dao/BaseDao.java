package dao;

public interface BaseDao<T> {
	
	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
}

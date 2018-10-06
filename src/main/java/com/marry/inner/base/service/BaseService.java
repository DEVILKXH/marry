package com.marry.inner.base.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.marry.inner.base.entity.BaseEntity;

import tk.mybatis.mapper.entity.Example;

public interface BaseService<T extends BaseEntity>  {
	public List<T> select(T entity);
	public List<T> selectAll();
	public T selectByPrimaryKey(String uuid);
	public List<T> selectByExample(T entity);
	public List<T> selectByExample(Example example);
	public int selectCount(T entity);
	public int selectCountByExample(T entity);
	public int selectCountByExample(Example example);
	public T selectOne(T entity);
	public T selectOneByExample(T entity);
	public int insert(T entity);
	public int insertSelective(T entity);
	public int updateByPrimaryKey(T entity);
	public int updateByPrimaryKeySelective(T entity);
	public int updateByExample(T entity);
	public int updateByExample(T entity, Example example);
	public int updateByExampleSelective(T entity);
	public int updateByExampleSelective(T entity, Example example);
	public int delete(T entity);
	public int deleteByExample(T entity);
	public int deleteByExample(Example example);
	public int deleteByPrimaryKey(T entity);
	public Page<T> getQueryByPage(T entity, Page<T> page);
}

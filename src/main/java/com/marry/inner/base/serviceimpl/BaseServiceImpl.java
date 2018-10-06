package com.marry.inner.base.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.marry.inner.base.entity.BaseEntity;
import com.marry.inner.base.mapper.BaseMapper;
import com.marry.inner.base.service.BaseService;
import com.marry.inner.util.ExampleUtil;

import tk.mybatis.mapper.entity.Example;



public class BaseServiceImpl<T extends BaseEntity,E extends BaseMapper<T>> implements BaseService<T>{

	@Autowired
	protected E mapper;
	
	@Autowired
	protected ExampleUtil exampleUtil;

	@Override
	public List<T> select(T entity) {
		return mapper.select(entity);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public T selectByPrimaryKey(String uuid) {
		return mapper.selectByPrimaryKey(uuid);
	}

	@Override
	public List<T> selectByExample(T entity) {
		return mapper.selectByExample(exampleUtil.getExample(entity));
	}

	@Override
	public int selectCount(T entity) {
		return mapper.selectCount(entity);
	}

	@Override
	public int selectCountByExample(T entity) {
		return mapper.selectCountByExample(exampleUtil.getExample(entity));
	}

	@Override
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}

	@Override
	public T selectOneByExample(T entity) {
//		return mapper.selectOneByExample(exampleUtil.getExample(entity));
		return null;
	}

	@Override
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	@Override
	public int insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateByExample(T entity) {
		return mapper.updateByExample(entity, exampleUtil.getExample(entity));
	}

	@Override
	public int updateByExampleSelective(T entity) {
		return mapper.updateByExampleSelective(entity, exampleUtil.getExample(entity));
	}

	@Override
	public int delete(T entity) {
		return mapper.delete(entity);
	}

	@Override
	public int deleteByExample(T entity) {
		return mapper.deleteByExample(entity);
	}

	@Override
	public int deleteByPrimaryKey(T entity) {
		return mapper.deleteByPrimaryKey(entity);
	}

	@Override
	public Page<T> getQueryByPage(T entity, Page<T> page) {
		return null;
	}

	@Override
	public List<T> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}

	@Override
	public int selectCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	@Override
	public int updateByExample(T entity, Example example) {
		return mapper.updateByExample(entity, example);
	}

	@Override
	public int deleteByExample(Example example) {
		return mapper.deleteByExample(example);
	}

	@Override
	public int updateByExampleSelective(T entity, Example example) {
		return mapper.updateByExampleSelective(entity, example);
	}

}


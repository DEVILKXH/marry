/**
 * @author kexiaohong
 * @version 1.0 2018年1月26日
 *
 */
package com.marry.inner.vo;

import java.util.ArrayList;
import java.util.List;

public class Example {
	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Example() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        
        public Criteria andIsNull(String param) {
            addCriterion(param + " is null");
            return (Criteria) this;
        }

        public Criteria andIsNotNull(String param) {
            addCriterion(param + " is not null");
            return (Criteria) this;
        }

        public Criteria andEqualTo(String param, Object value) {
            addCriterion(param + " =", value, getParam(param));
            return (Criteria) this;
        }

		public Criteria andNotEqualTo(String param, Object value) {
            addCriterion(param + " <>", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andGreaterThan(String param, Object value) {
            addCriterion(param + " >", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andGreaterThanOrEqualTo(String param, Object value) {
            addCriterion(param + " >=", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andLessThan(String param, Object value) {
            addCriterion(param + " <", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andLessThanOrEqualTo(String param, Object value) {
            addCriterion(param + " <=", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andLike(String param, Object value) {
            addCriterion(param + " like", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andNotLike(String param, Object value) {
            addCriterion(param + " not like", value, getParam(param));
            return (Criteria) this;
        }

        public Criteria andIn(String param, List<Object> values) {
            addCriterion(param + " in", values, getParam(param));
            return (Criteria) this;
        }

        public Criteria andNotIn(String param, List<Object> values) {
            addCriterion(param + " not in", values, getParam(param));
            return (Criteria) this;
        }

        public Criteria andBetween(String param, Object value1, Object value2) {
            addCriterion(param + " between", value1, value2, getParam(param));
            return (Criteria) this;
        }

        public Criteria andNotBetween(String param, Object value1, Object value2) {
            addCriterion(param + " not between", value1, value2, getParam(param));
            return (Criteria) this;
        }
        
        private String getParam(String param) {
        	String value = param.toLowerCase();
        	String []val = value.split("_");
        	String _value = "";
        	for(String v: val){
        		_value += (Character.toUpperCase(v.charAt(0)) + v.substring(1));
        	}
			return (Character.toUpperCase(_value.charAt(0)) + _value.substring(1));
		}
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}

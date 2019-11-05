package com.water.irrigation.entity.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 请求返回实体类
 * 2019年03月7日,下午1:54
 * {@link ResultEntity}
 * @author yangj080
 * @version 1.0.0
 */
public class ResultEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**状态（1:成功,0:失败）*/
	private Integer code;
	/**返回信息*/
	private String msg;		
	/**总数*/
	private Long count;
	/**当前记录数*/
	private Integer datacount; 
	/**是否存在下一页*/
	private Integer pagestate; 
	/**每页记录数*/
	private Integer pagesize;
	/**返回内容*/
	private Object content; 
	
	/**当前页码*/
	private Integer pageno;
	
	private List<Long> list = new ArrayList<>();
	
	public Object getData() {
//		Map<Object,Object> jsonObject = new HashMap<>(16);
//		if(content instanceof List) {
//			@SuppressWarnings("unchecked")
//			List<Object> objects= (List<Object>) content;
//			for(Object obj : objects) {
//				if(obj instanceof BaseEntity) {
//					BaseEntity entity = (BaseEntity)obj;
//					jsonObject.put(entity.getIndocno(), obj);
//				}else {
//					jsonObject.put("obj", content);
//				}
//			}
//		}else {
//			if(content instanceof BaseEntity) {
//				BaseEntity entity = (BaseEntity)content;
//				jsonObject.put(entity.getIndocno(), content);
//			}else if(content instanceof String){
//				jsonObject.put("value", content);
//			}else {
//				jsonObject.put("obj", content);
//			}
//		}
//		return jsonObject;
		if(content instanceof  List) {
		@SuppressWarnings("unchecked")
			List<Object> objects= (List<Object>) content;
			return objects;
		}else{
			return content;
		}
	}
/*	public List<Long> getList(){
		if(content instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> objects= (List<Object>) content;
			for(Object obj : objects) {
				if(obj instanceof BaseEntity) {
					BaseEntity entity = (BaseEntity)obj;
					this.list.add(entity.getIndocno());
				}
			}
		}else {
			if(content instanceof BaseEntity) {
				BaseEntity entity = (BaseEntity) content;
				this.list.add(entity.getIndocno());
			}
		}
		return this.list;
	}*/
	
	public Long getCount(){
//		Map<String,Object> maps = new HashMap<>(16);
//		maps.put("count", this.count);
//		maps.put("pageSize", this.pagesize);
//		maps.put("pageNo", this.pageno);
		return this.count;
	}
	
	public ResultEntity() {}
	public ResultEntity(Integer code,Object content) {
		super();
		this.code = code;
		this.content = content;
	}
	public ResultEntity(Integer code, String msg,Object content) {
		super();
		this.code = code;
		this.msg = msg;
		this.content = content;
	}
	public ResultEntity(Integer code, String msgno, String msg,Object content) {
		super();
		this.code = code;
		this.msg = msg;
		this.content = content;
	}
	public ResultEntity(Integer code, String msg,Object content,Long count,Integer pagesize) {
		super();
		this.code = code;
		this.msg = msg;
		this.content = content;
		this.count = count;
		this.pagesize = pagesize;
	}
	public ResultEntity(Integer code, String msgno, String msg, Long count, Integer datacount, Integer pagestate,
			Integer pagesize, Object content) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.datacount = datacount;
		this.pagestate = pagestate;
		this.pagesize = pagesize;
		this.content = content;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	@JsonIgnore
	public Long getCount1() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@JsonIgnore
	public Integer getDatacount() {
		return datacount;
	}
	public void setDatacount(Integer datacount) {
		this.datacount = datacount;
	}
	@JsonIgnore
	public Integer getPagestate() {
		return pagestate;
	}
	public void setPagestate(Integer pagestate) {
		this.pagestate = pagestate;
	}
	@JsonIgnore
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	
	@JsonIgnore
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	
	@JsonIgnore
	public Integer getPageno() {
		return pageno;
	}
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}


	public enum StatusCode{
		/**失败*/
		FAILURE(1),
		/**成功*/
		SUCCESS(0);
		private Integer code;
		private StatusCode(Integer code) {
			this.code = code;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
	}
}

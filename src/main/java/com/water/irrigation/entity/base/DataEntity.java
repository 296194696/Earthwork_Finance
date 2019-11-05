package com.water.irrigation.entity.base;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础实体类
 * 2019年03月7日,下午1:54
 * {@link DataEntity}
 * @author yangj080
 * @version 1.0.0
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=true)
public class DataEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否刪除状态0-未删除;1-删除
	 */
	private Integer idel;
}

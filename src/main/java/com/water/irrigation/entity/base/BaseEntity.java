package com.water.irrigation.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 基础实体类
 * 2019年03月7日,下午1:54
 * {@link BaseEntity}
 * @author yangj080
 * @version 1.0.0
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long indocno;
	
	/** 创建人id */
	private String sregid;
	
	/** 创建时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dregt = new Date();
}

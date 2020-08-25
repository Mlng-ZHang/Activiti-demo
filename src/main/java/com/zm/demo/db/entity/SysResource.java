package com.zm.demo.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangming
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysResource implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String resourceKey;

    private String resourceName;


}

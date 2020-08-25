package com.zm.demo.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangming
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    @TableField("userId")
    private Long userId;

    @TableField("roleId")
    private Long roleId;


}

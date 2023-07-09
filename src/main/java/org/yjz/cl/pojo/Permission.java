package org.yjz.cl.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class Permission {
    @TableId(value = "permission_id",type= IdType.AUTO)
    private Integer permissionId;
    private String permissionIcon;
    private String permissionIndex;
    private String label;
    private String permissionUrl;
    private Integer parentId;
    private Integer nodeId;
    // @TableField 用户非主键的其他属性上，指明映射关系以及一些规则 如果满足驼峰命名 不要指定映射关系
    // exist = false 表示当前值修饰的属性不属于数据库中的字段
    @TableField(exist = false)
    private List<Permission> childrenList;
}

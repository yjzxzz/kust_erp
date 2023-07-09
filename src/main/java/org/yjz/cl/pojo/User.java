package org.yjz.cl.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // 提供 set、get等方法
@AllArgsConstructor // 全参构造函数
@NoArgsConstructor // 无参构造函数
@TableName("user") // 指定实体类对应的数据库表的名字，默认类名小写
public class User implements Serializable {// 实现Serializable 序列化
    // 指定主键自增策略：value，指定数据库表中的字段名，type = IdType.AUTO 表示主键自增策略
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private String roleName;
}

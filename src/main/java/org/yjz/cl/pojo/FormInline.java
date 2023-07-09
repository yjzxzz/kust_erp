package org.yjz.cl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // 提供 set、get等方法
@AllArgsConstructor // 全参构造函数
@NoArgsConstructor // 无参构造函数
public class FormInline {
    private String username;
    private String password;
    private String rePassword;
}

package org.yjz.cl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 封装响应工具类
public class ResponseMessage {
    private Integer status;
    private String msg;
    private Object data;
}

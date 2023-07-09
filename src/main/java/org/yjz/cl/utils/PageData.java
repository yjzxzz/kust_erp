package org.yjz.cl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData {
    private Integer page; // 当前页
    private Integer size; // 当前页显示的条数
    private String param; // 模糊查询参数

}

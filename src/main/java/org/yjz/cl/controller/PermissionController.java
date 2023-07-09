package org.yjz.cl.controller;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjz.cl.mapper.PermissionMapper;
import org.yjz.cl.pojo.Permission;
import org.yjz.cl.utils.ResponseMessage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/permissionList")
    public ResponseMessage permissionList(String username){
        List<Permission> permissionList = permissionMapper.permissionList(username);
        List<Permission> dataList = new ArrayList<>();

        for (Permission parentPermission : permissionList){
            // 定义集合 用来存放每一次遍历得到的二级菜单信息
            List<Permission> childrenList = new ArrayList<>();
            if (parentPermission.getParentId().equals(0)){
                for (Permission childrenPermission : permissionList){
                    if (childrenPermission.getParentId().equals(parentPermission.getNodeId())){
                        childrenList.add(childrenPermission);
                    }
                }
                parentPermission.setChildrenList(childrenList);
                dataList.add(parentPermission);
            }
        }
        return new ResponseMessage(200,"permission list",dataList);
    }

    @RequestMapping("/permissionTableList")
    public ResponseMessage permissionTableList(String username){
        List<Permission> permissionList = permissionMapper.permissionList(username);
        return new ResponseMessage(200,"permission list",permissionList);
    }

}

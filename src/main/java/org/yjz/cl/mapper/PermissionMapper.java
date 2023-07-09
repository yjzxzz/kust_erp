package org.yjz.cl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.yjz.cl.pojo.Permission;

import java.util.List;

// 操作表permission的dao接口
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT " +
            "p.* " +
            "FROM " +
            "`permission` p " +
            "LEFT JOIN `user_permission_rel` upr ON upr.permission_id = p.permission_id " +
            "LEFT JOIN `user` u ON u.role_id = upr.role_id " +
            "WHERE " +
            "u.username = #{username}")
    List<Permission> permissionList(String username);
}

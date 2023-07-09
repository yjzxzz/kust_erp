package org.yjz.cl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yjz.cl.mapper.UserMapper;
import org.yjz.cl.pojo.User;
import org.yjz.cl.pojo.FormInline;
import org.yjz.cl.utils.PageData;
import org.yjz.cl.utils.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Objects;

@RestController //把当前类注入IOC容器当中 返回数据json
@RequestMapping("/user") // 定义请求的地址，作用在类上
public class UserController {
    // java中，接口不能直接用来创建对象
    // 使用mybatis-plus框架生成对应实现类 需要在启动类注解接口包路径
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    // 登录方法，接受前端请求参数验证是否存在
    // 登录方法访问 本地8080端口
    @RequestMapping("/login")
    public ResponseMessage login(String username,String password){
        // 1，使用mybatis-plus selectOne方法登录
        // sql设计条件语句 需要构建条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 等于关系 对应函数调用eq函数
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        // 2，执行查询 得到结果
        User user = userMapper.selectOne(queryWrapper); //得到方法返回值快捷键
        // 3，判断返回的是否有数据
        if (Objects.isNull(user))
            return new ResponseMessage(500,"login fail",null);
        // 4，把当前用户存入redis中
        // set方法 key：value
        redisTemplate.opsForValue().set(username, user);
        return new ResponseMessage(200,"login success",null);
    }

    // 列表方法 接收前端请求参数
    @RequestMapping("/userList")
    public ResponseMessage userList(PageData pageData){
        // 1,创建分页对象 参数为当前页 和 当前每页显示的条数
        Page<User> page = new Page<>(pageData.getPage(),pageData.getSize());
        // 2，构造条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 3，设置条件
        queryWrapper.eq("role_name","用户");
        // 取出pageData中的param
        String param = pageData.getParam();
        // 模糊查询使用like方法
        queryWrapper.like(param!=null && !"".equals(param),"username",param);
        //
        Page<User> userPage = userMapper.selectPage(page,queryWrapper);
        return new ResponseMessage(200,"user list",userPage);
    }

    @RequestMapping("/userSelf")
    public ResponseMessage userSelf(String username){
        // 取出redis中的数据 返回object类型
        User user = (User) redisTemplate.opsForValue().get(username);
        return new ResponseMessage(200,"user self",user);
    }
    // 退出登录，接收前端请求参数 通过username去redis中删除对应的username
    //请求地址 'user/logout'
    @RequestMapping("/logout")
    public void logout(String username){
        redisTemplate.delete(username);
    }

//    @RequestMapping("/userPermission")
//    public ResponseMessage userPermission(String username){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",username);
//        User user = userMapper.selectOne(queryWrapper);
//        return new ResponseMessage(200,"user permission",user);
//    }

    @RequestMapping("/updatePassword")
    public ResponseMessage updatePassword(FormInline formInline){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 等于关系 对应函数调用eq函数
        queryWrapper.eq("username",formInline.getUsername());
        User user = new User();
        user.setPassword(formInline.getPassword());
        userMapper.update(user,queryWrapper);
        return new ResponseMessage(200,"update password success",null);
    }

    @RequestMapping("/deleteUser")
    public ResponseMessage deleteUser(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        userMapper.delete(wrapper);
        return new ResponseMessage(200,"delete User success",null);
    }
}

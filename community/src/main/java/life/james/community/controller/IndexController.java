package life.james.community.controller;


import life.james.community.mapper.UserMapper;
import life.james.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//允许这个类去接受前端的一个请求
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    //显示主页
    public String index(HttpServletRequest request)
    {
        //拿到服务器的cookie,先去cookie中寻找token,再拿得到的token与数据库中的token做比较
        //如果有那么就存入session,没有则跳转到登录页面
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";//去template找
    }

}

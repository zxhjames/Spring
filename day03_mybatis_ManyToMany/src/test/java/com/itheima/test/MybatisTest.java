package com.itheima.test;

import com.itheima.dao.IRoleDao;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 *
 * 测试mybatis的crud操作
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IRoleDao roleDao;


    @Before//用于在测试方法执行之前执行
    public void init() throws Exception {
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象

        //1第一种方法
        userDao = sqlSession.getMapper(IUserDao.class);
//        userDao = new UserDaoImpl(factory);
        roleDao = sqlSession.getMapper(IRoleDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destroy() throws Exception {
//        //提交事务
        sqlSession.commit();
//        //6.释放资源
        sqlSession.close();
        in.close();
    }


    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        //5.执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getRoles());
        }

    }

    /***
     * 根据用户的编号返回一个用户信息
     */
    @Test
    public void testgetUserById(){
        Integer id = 4;
        User user = userDao.getUserById(id);
        if(user!=null){
            System.out.println(user.toString());
        }
    }

    /******************************************/


    @Test
    public void testFindAllRole(){
        List<Role> roles = roleDao.findAll();
        for (Role role:roles){
            System.out.println("每个角色的信息");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }


}
package factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 一个创建Bean对象的工厂
 *
 * Bean：在计算机英语中，有可重用组件的含义。
 * JavaBean >> 实体类  用Java语言编写的可重用组件
 *
 * 第一：需要一个配置文件来配置我们的service和dao
 *        配置的内容：唯一标识=全限定类名（key=value）
 * 第二：通过读取配置文件中配置的内容，反射创建对象
 *
 * 配置文件可以是xml和properties
 */

public class BeanFactory {
    //定义一个Properties对象
    private static Properties properties;

    //定义一个Map，用于存放我们要创建的对象，称之为容器
    private static Map<String,Object> beans;

    //使用静态代码块对Properties对象赋值
    static {
        try {
        //实例化对象
        properties=new Properties();
        //获取properties文件的刘对象
        InputStream inputStream=BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            properties.load(inputStream);
        //实例化容器
            beans=new HashMap<String,Object>();
        //取出配置文件中所有的key
            Enumeration keys=properties.keys();
        //遍历枚举
        while(keys.hasMoreElements()){
            //取出每个key
            String key=keys.nextElement().toString();
            //根据key获取value
            String beanPath=properties.getProperty(key);
            //反射创建对象
            Object value=Class.forName(beanPath).newInstance();
            //把key和value存入容器中
            beans.put(key,value);

        }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败！");
        }
    }

    /**
     * 根据bean的名称获取bean对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){

        return beans.get(beanName);
    }
    /**
     * 根据bean的名称获取bean对象
     * @param beanName
     * @return

    public static Object getBean(String beanName){

        Object bean=null;
        try {
            String beanPath = properties.getProperty(beanName);
//            System.out.println(beanPath);
            bean = Class.forName(beanPath).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }*/
}

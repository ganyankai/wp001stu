package com.catt.oil.settlement.service.impl.stationPrice;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.util.bean.BeanUtil;
import com.catt.oil.repository.dao.customerMgr.CusInfoDao;
import com.catt.oil.repository.dao.customerMgr.impl.CusInfoDaoImpl;
import com.catt.oil.repository.data.CusInfoVo;
import com.catt.oil.service.customerMgr.CusInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

@Service("testStart")
//最好是在配置文件名中配置懒加载 容器启动构造器就会执行
public class TestStart {
    @Resource(name = "cusInfoServiceImpl")
    private CusInfoService cusInfoService;

    private static final Logger LOG = LoggerFactory.getLogger(TestPrintServiceImpl.class);

    public TestStart() {
//        System.out.println("no xml");
        System.out.println("has xml");
        System.out.println("TestStart 执行了");
        //放在这里会有空指针异常
//        CusInfoVo info = cusInfoService.getCusInfoById((long)42329);
//        System.out.println(info.getId());

    }

    public void execute() {
        LOG.info("testStart.execute 方法开始执行");
        System.out.println("testStart.execute");

//        CusInfoVo info = cusInfoService.getCusInfoById((long)42329);
//        System.out.println(info.getId()+":"+info.getNickName());

        LOG.info("testStart.execute方法执行完毕");
    }

    public static void main(String[] args) {
        //2019年8月19日 - 目前取不到数据
//        String property = System.getProperty("rmi.registryPort");
//        System.out.println(property);

        String[] paths = new String[2];
        paths[0] = "applicationContext.xml";
        paths[1] = "applicationContext-project.xml";

        //ok
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths);
        CusInfoDao cusInfoDao =(CusInfoDao) applicationContext.getBean("cusInfoDaoImpl");
        Page<Map> info = cusInfoDao.getCusInfoById((long) 42329);
//        System.out.println("flag:1");

        //ok
        List<Map> pageList = info.getContent();
        CusInfoVo cusInfoVo = new CusInfoVo();
        BeanUtil.copyProperties(cusInfoVo, pageList.get(0));
        System.out.println(cusInfoVo.getId()+":"+cusInfoVo.getNickName());

        //no
//        EntityManager manager = applicationContext.getBean(EntityManager.class);
        EntityManagerFactory factory = (EntityManagerFactory)applicationContext.getBean("entityManagerFactory");
//        System.out.println("flag:2");
        System.out.println(factory);

//        System.out.println("flag:3");
        System.out.println(factory.createEntityManager());

    }

}

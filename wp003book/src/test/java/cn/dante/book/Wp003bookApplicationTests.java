package cn.dante.book;

import cn.dante.book.entity.Book;
import cn.dante.book.mapper.BookMapper;
//import cn.dante.entity.Stu;
//import cn.dante.mapper.StuMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Wp003bookApplication.class)
//@MapperScan({"cn.dante.mapper"})
@MapperScan({"cn.dante.book.mapper"})		//这个注解最好主动配置一下
//@MapperScan({"cn.dante"})		//这个注解竟然会报错
public class Wp003bookApplicationTests {

	//	@Autowired
	//	private StuMapper stuMapper;
	//	@Autowired
	//	private LoadBalancerClient loadBalancerClient;
	//客户端通过 LoadBalancerClient 来获取应用名
	//获得所有父爷节点的数量




	@Test
	//	MapperFactoryBean
	public void test4() {


	}

	@Autowired
	private BookMapper bookMapper;

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void test1() {
//		List<Stu> list = stuMapper.list();
//		System.out.println(list);
//	}

	@Test
//	MapperFactoryBean
	public void test2() {
		List<Book> list = bookMapper.list();
		System.out.println(list);
	}

	@Test
	//通过restTemplate访问,无需导入项目依赖
//	MapperFactoryBean
	public void test3() {
		RestTemplate restTemplate = new RestTemplate();
		//不知道泛型是什么类型
		List list = restTemplate.getForObject("http://localhost:8088/stu/list", List.class);
		System.out.println(list);

//		Object obj = restTemplate.postForObject("http://192.168.0.142:8392/hotPlace/page","", Object.class);
//		HashMap<String, String> map = new HashMap<>();
//		map.put("type", type);
//
//		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/saveByType/{type}", userEntity, String.class,map);
//		String body = responseEntity.getBody();
//		Object obj = restTemplate.postForObject("http://localhost:8392/hotPlace/page","", Object.class);
//		System.out.println(obj);
	}



}

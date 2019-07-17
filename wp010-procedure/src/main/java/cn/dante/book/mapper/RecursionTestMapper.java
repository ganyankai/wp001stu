package cn.dante.book.mapper;

import cn.dante.book.entity.RecursionTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper  //可包扫描也可以单个配置,两个同时使用好像也可以
public interface RecursionTestMapper {
    @Select("select * from recursion_test")
    public List<RecursionTest> list();

    @Select("select count(id) from recursion_test where id = #{pid}")
    public Integer getParentCount(@Param("pid") Integer pid);

    @Select("select count(id) from recursion_test where pid = #{id}")
    public Integer getSonCount(@Param("id") Integer id);

    @Select("select pid from recursion_test where id = #{id}")
    public Integer getParentId(@Param("id") Integer id);

    @Select("select id,count(id) as count from recursion_test where pid = #{id} group by id")
    public List<Map<String,Object>> getSonIdandCount(@Param("id") Integer id);
//    public Map<Integer,Integer> getSonIdandCount(@Param("id") Integer id);
}

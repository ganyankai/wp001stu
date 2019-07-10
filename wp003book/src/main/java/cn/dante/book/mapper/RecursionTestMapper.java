package cn.dante.book.mapper;

import cn.dante.book.entity.RecursionTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper  //可包扫描也可以单个配置,两个同时使用好像也可以
public interface RecursionTestMapper {
    @Select("select * from recursion_test")
    public List<RecursionTest> list();

    @Select("select count(id) from recursion_test where id = #{pid}")
    public Integer getParentCount(@Param("pid") Integer pid);

    @Select("select count(id) from recursion_test where pid = #{pid}")
    public Integer getSonCount(@Param("pid") Integer pid);

    @Select("select pid from recursion_test where id = #{id}")
    public Integer getParentId(@Param("id") Integer id);


}

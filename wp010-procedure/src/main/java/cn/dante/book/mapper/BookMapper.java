package cn.dante.book.mapper;

import cn.dante.book.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper  //可包扫描也可以单个配置,两个同时使用好像也可以
public interface BookMapper {
    public List<Book> list();

    @Select("call p12(#{id})")
    @Options(statementType= StatementType.CALLABLE )
    public List<Book> getBook(@Param("id") int id);

}

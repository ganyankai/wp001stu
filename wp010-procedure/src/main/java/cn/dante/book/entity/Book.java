package cn.dante.book.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:	StuService接口
 * @Author:			传智播客 java学院
 * @Company:		http://java.itcast.cn
 * @CreateDate:		2019-6-17 17:17:05
 */
@Entity
@Data
public class Book extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1936L;

	/**主键，自增*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;			
	private BigDecimal price;


}

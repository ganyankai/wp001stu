package cn.gan.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * 
 * 
 *
 */
@Entity
@Table(name = "skill")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "cid")
	private Integer cid;
	@Column(name = "degree")
	private Integer degree;
	@Column(name = "pid")
	private Integer pid;
	@Column(name = "remark")
	private String remark;
	@Column(name = "detail")
	private String detail;
	@Column(name = "manual_id")
	private Integer manualId;
	@Column(name = "level")
	private String level;
	@Column(name = "keyword")
	private String keyword;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public Integer getCid(){
		return cid;
	}
	public void setCid(Integer cid){
		this.cid = cid;
	}
	public Integer getDegree(){
		return degree;
	}
	public void setDegree(Integer degree){
		this.degree = degree;
	}
	public Integer getPid(){
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getDetail(){
		return detail;
	}
	public void setDetail(String detail){
		this.detail = detail;
	}
	public Integer getManualId(){
		return manualId;
	}
	public void setManualId(Integer manualId){
		this.manualId = manualId;
	}
	public String getLevel(){
		return level;
	}
	public void setLevel(String level){
		this.level = level;
	}
	public String getKeyword(){
		return keyword;
	}
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	public Date getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	public Date getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	
}
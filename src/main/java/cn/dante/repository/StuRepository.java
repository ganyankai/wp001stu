package cn.dante.repository;

import cn.dante.entity.Stu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StuRepository extends JpaRepository<Stu,Integer>{


}

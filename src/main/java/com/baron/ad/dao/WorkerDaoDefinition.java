package com.baron.ad.dao;

import com.baron.ad.entity.Worker;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 @package com.baron.ad.dao
 @author Baron
 @create 2020-09-14-5:57 PM
 */
@RepositoryDefinition(domainClass = Worker.class, idClass = Long.class)
public interface  WorkerDaoDefinition {


    Worker findByName(String name);

    @Query("SELECT w FROM Worker w where id = (SELECT MAX(id) FROM Worker)")
    Worker getMaxIdWorker();


    @Query("SELECT w from Worker w where name = ?1 and salary >= ?2")
    List<Worker> findWorkerByFirstParam(String name, Long salary);

    @Query("select w from Worker w where name = :name and  salary >= :salary")
    List<Worker> findWorkerBySecondParam(@Param("name") String name, @Param("salary") Long salary);

    @Query("select new Worker (w.name, w.salary) from Worker w")
    List<Worker> getWorkerNameAndSalaryInfo();

    @Modifying
    @Transactional(readOnly = false)
    @Query("update Worker set salary = :salary where name = :name")
    int updateSalaryByName(@Param("salary") Long salary, @Param("name") String name);
}

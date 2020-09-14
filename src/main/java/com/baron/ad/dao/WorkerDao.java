package com.baron.ad.dao;

import com.baron.ad.entity.Worker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-14-5:54 PM
 */
@SuppressWarnings("all")
public interface WorkerDao extends JpaRepository<Worker, Long> {

    Worker findByName(String name);
    Worker getByName(String name);
    Worker readByName(String name);
    Worker queryByName(String name);
    Worker streamByName(String name);

    Worker findByNameAndCity(String name, String city);
    List<Worker> findByCity(String city);

    Worker findByNameAndSalaryGreaterThanEqual(String name, Long salary);
    Worker getByNameAndSalaryGreaterThanEqual(String name, Long salary);


    @Query("SELECT w FROM Worker w where id = (SELECT MAX(id) FROM Worker)")
    Worker getMaxIdWorker();


    @Query(value = "select * from worker", nativeQuery = true)
    List<Worker> findAllNativeQuery();


    @Query(value = "select w.name, w.salary from worker w", nativeQuery = true)
    List<Map<String, Object>> getWorkerNameAndSalaryInfoByNativeQuery();

    List<Worker> findAllBySalaryGreaterThanEqual(Long salary, Sort sort);

    List<Worker> findAllBySalaryGreaterThanEqual(Long salary, Pageable pageable);

}

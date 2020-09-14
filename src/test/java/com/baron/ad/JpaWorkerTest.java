package com.baron.ad;

import com.alibaba.fastjson.JSON;
import com.baron.ad.dao.WorkerDao;
import com.baron.ad.dao.WorkerDaoDefinition;
import com.baron.ad.entity.Worker;
import com.baron.ad.utils.Map2Entity;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-14-6:22 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaWorkerTest {

    @Autowired
    private WorkerDao dao;
    @Autowired
    private WorkerDaoDefinition definition;


    @Test
    public void testOpWorkerWithName() {
        log.info("{1}", JSON.toJSONString(dao.findByName("baron")));
        log.info("{2}", JSON.toJSONString(dao.getByName("baron")));
        log.info("{3}", JSON.toJSONString(dao.readByName("baron")));
        log.info("{4}", JSON.toJSONString(dao.streamByName("baron")));
        log.info("{5}", JSON.toJSONString(dao.queryByName("baron")));

        log.info("{6}", JSON.toJSONString(definition.findByName("baron")));
    }


    @Test
    public void testMoreConditionQuery() {
        log.info("{1}", JSON.toJSONString(dao.findByNameAndCity("baron", "上海市")));

        log.info("{2}", JSON.toJSONString(dao.findByNameAndSalaryGreaterThanEqual("baron", Long.valueOf(200))));

    }

    @Test
    public void testOpQuery(){
        log.info("{1}", JSON.toJSONString(dao.getMaxIdWorker()));
        log.info("{2}", JSON.toJSONString(definition.getMaxIdWorker()));
    }

    @Test
    public void testParamQuery() {
        log.info("{}", JSON.toJSONString(definition.findWorkerByFirstParam("baron", 1000L)));
        log.info("{}", JSON.toJSONString(definition.findWorkerBySecondParam("baronW", 10000L)));
    }

    @Test
    public void testNameAndSalaryQuery() {
        log.info("{}", JSON.toJSONString(definition.getWorkerNameAndSalaryInfo()));
    }

    @Test
    public void testNativeQueryOp() throws Exception{
        log.info("{}", JSON.toJSONString(dao.findAllNativeQuery()));
        List<Map<String, Object>> result = dao.getWorkerNameAndSalaryInfoByNativeQuery();

        List<Worker> workers = new ArrayList<>(result.size());
        for (Map<String, Object> worker : result) {
            workers.add(Map2Entity.map2Entity(worker, Worker.class));
        }
        log.info("{}", workers.size());
        log.info("{}", workers);
    }
    
    @Test
    public void testModify() {
        Worker worker = dao.getMaxIdWorker();
        worker.setSalary(20000L);
        dao.save(worker);
    }

    @Test
    public void testModifySalary() {
        Worker worker = dao.getMaxIdWorker();
       log.info("{}", definition.updateSalaryByName(20001L, "baron"));

    }


    @Test
    public void testSortAndPageable(){
//        Sort sort01 = new Sort(Sort.Direction.DESC, "id");
//        Sort sort02 = new Sort(Sort.Direction.DESC,  "salary", "id");
//        Sort sort03 = new Sort(Sort.Direction.DESC, Arrays.asList("salary","id"));
//
//        System.out.println(dao.findAll(sort01));
//        System.out.println(dao.findAll(sort02));
//
//        System.out.println(dao.findAllBySalaryGreaterThanEqual(100L, sort01));
//        System.out.println(dao.findAllBySalaryGreaterThanEqual(100L, sort03));


//        Pageable pageable = new PageRequest(int page, int size, Sort sort);

        Pageable pageable = PageRequest.of(0,2,Sort.by(Sort.Order.asc("salary")));
        Page<Worker> workers = dao.findAll(pageable);
        log.info("总页数 {}", workers.getTotalElements());
        log.info("记录总数 {}", workers.getTotalPages());
        log.info("当前索引页 {}", workers.getNumber());
        log.info("查询结果 {}", workers.getContent());
        log.info("当前页元素 {}", workers.getTotalElements());
        log.info("页大小 {}", workers.getSize());
        log.info("排序参数 {}", workers.getSort());
        log.info("是否有内容 {}", workers.hasContent());
        log.info("有下一页 {}", workers.hasNext());
        log.info("有上一页 {}", workers.hasPrevious());
        log.info("是否最后 {}", workers.isLast());
        log.info("上一页 {}", workers.previousPageable());
        log.info("下一页 {}", workers.nextPageable());
    }
}

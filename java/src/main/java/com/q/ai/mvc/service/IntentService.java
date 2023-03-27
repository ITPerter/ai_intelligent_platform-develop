package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Robot2Intent;
import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.IntentDao;
import com.q.ai.mvc.dao.Robot2IntentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class IntentService {


    @Autowired
    IntentDao intentDao;
    @Autowired
    Robot2IntentDao robot2IntentDao;
    @Autowired
    RequestContext requestContext;


    public Intent getById(int id) {
        return intentDao.getById(id);
    }

    public Intent getByNumber(String number) {
        return intentDao.getByNumber(number);
    }

    public List<Intent> getList(Page page) {
        page.setTotal(intentDao.getCount());
        return intentDao.getList(page.getOffset(), page.getLimit());
    }

    public List<Intent> getListByRobotId(int robotId,Page page) {
        page.setTotal(intentDao.getListByRobotIdCount(robotId));
        return intentDao.getListByRobotId(robotId,page.getOffset(), page.getLimit());
    }


    /**
     * 新建处理关联关系，编辑不处理
     * @param intent
     * @param robotId
     * @return
     */
    public Intent saveWithRobotId(Intent intent,Integer robotId) {
        if (intent.getId() != 0) {//编辑
            intent.setUpdateTime(LocalDateTime.now());
            intentDao.update(intent);
        } else {//新增
            intent.setCreator(requestContext.getSession().getUserId());
            intent.setCreateTime(LocalDateTime.now());
            intent.setUpdateTime(LocalDateTime.now());
            intentDao.insert(intent);

            if(null != robotId){
                this.add2Robot(Collections.singletonList(intent.getId()),robotId);
            }
        }

        return intent;
    }

    public int add2Robot(List<Integer> intentIdList,int robotId){

        List<Robot2Intent> robot2IntentList = new ArrayList<>();
        for (int intentId:intentIdList){
            Robot2Intent robot2Intent = new Robot2Intent();
            robot2Intent.setCreateTime(LocalDateTime.now());
            robot2Intent.setIntentId(intentId);
            robot2Intent.setRobotId(robotId);
            robot2Intent.setCreator(requestContext.getSession().getUserId());
            robot2Intent.setCreateTime(LocalDateTime.now());
            robot2IntentList.add(robot2Intent);
        }

        return robot2IntentDao.inserts(robot2IntentList);

    }


    public int removeIntentIdsFromRobot(List<Integer> intentIdList,int robotId){
        return robot2IntentDao.delByIntentIdListAndRobotId(intentIdList,robotId);
    }

    public int delByIdList(List<Integer> idList){
        return intentDao.delByIdList(idList);
    }

}

package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Intention;
import com.q.ai.mvc.dao.po.Robot2Intent;
import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.IntentDao;
import com.q.ai.mvc.dao.Robot2IntentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class IntentService {
    @Resource
    IntentDao intentDao;
    @Resource
    Robot2IntentDao robot2IntentDao;
    @Resource
    RequestContext requestContext;


    /**
     * 通过意图id获取意图
     * @param id
     * @return
     */
    public Intent getById(int id) {
        return intentDao.getById(id);
    }

    /**
     * 通过编码获取意图
     * @param number
     * @return
     */
    public Intent getByNumber(String number) {
        return intentDao.getByNumber(number);
    }

    /**
     * 获取意图分页数据
     * @param page      分页数据
     * @return
     */
    public List<Intent> getList(Page page) {
        page.setTotal(intentDao.getCount());
        return intentDao.getList(page.getOffset(), page.getLimit());
    }

    /**
     * 通过机器人id获取 机器人意图 分页数据
     * @param robotId
     * @param page
     * @return
     */
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

    /**
     * 为一个机器人添加意图
     * @param intentIdList  意图列表
     * @param robotId   机器人id
     * @return
     */
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

    /**
     * 删除意图与机器人的所有关联（针对意图与机器人关联表）
     * @param intentIdList  意图列表
     * @param robotId   机器人id
     * @return
     */
    public int removeIntentIdsFromRobot(List<Integer> intentIdList,int robotId){
        return robot2IntentDao.delByIntentIdListAndRobotId(intentIdList,robotId);
    }

    /**
     * 通过意图id删除意图数据（可连续删除多条数据，带循环）
     * @param idList
     * @return
     */
    public int delByIdList(List<Integer> idList){
        return intentDao.delByIdList(idList);
    }

    /**
     *
     * @param intention
     * @return  返回添加的意图的id
     */
    public Long addIntention(Intention intention) {
        intention.setCreateTime(LocalDateTime.now());
        intention.setCreator(requestContext.getSession().getUserId());
        intentDao.addIntention(intention);
        return intention.getId();
    }
}

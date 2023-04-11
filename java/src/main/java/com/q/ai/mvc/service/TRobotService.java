package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.TRobotDao;
import com.q.ai.mvc.dao.po.TRobot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class TRobotService {
    @Resource
    private TRobotDao tRobotDao;
    @Autowired
    RequestContext requestContext;


    public List<TRobot> getRobotList(int number, int size) {
        return tRobotDao.getRobotList(number,size);
    }

    public int getRobotCount() {
        return tRobotDao.getRobotCount();
    }

    public TRobot getRobotById(int id) {
        return tRobotDao.getRobotById(id);
    }

    public int save(TRobot tRobot) {
        int i = 0;
        if (tRobot.getId() != null) {//编辑
            tRobot.setUpdateTime(LocalDateTime.now());
            i = tRobotDao.updateRobot(tRobot);
        } else {//新增
            long min = 100000000L; // 最小值为100000000
            long max = 999999999L; // 最大值为999999999
            // 为关系随机生成一个id
            long FID = (long)(Math.random() * (max - min + 1)) + min;
            tRobot.setId(FID);
            int userId = requestContext.getSession().getUserId();
            tRobot.setCreatorId(Long.valueOf(String.valueOf(userId)));
            tRobot.setCreateTime(LocalDateTime.now());
            i = tRobotDao.insertRobot(tRobot);
        }
        return i;
    }

    public int deleteRobotIds(List<Long> robotIdList) {
        return tRobotDao.deleteRobotIds(robotIdList);
    }
}

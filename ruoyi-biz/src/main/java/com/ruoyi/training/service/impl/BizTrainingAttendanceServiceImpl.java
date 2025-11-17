package com.ruoyi.training.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.training.domain.BizTrainingAttendance;
import com.ruoyi.training.domain.BizTrainingRecords;
import com.ruoyi.training.mapper.BizTrainingAttendanceMapper;
import com.ruoyi.training.mapper.BizTrainingRecordsMapper;
import com.ruoyi.training.service.IBizTrainingAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员参训记录Service业务层处理
 *
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizTrainingAttendanceServiceImpl implements IBizTrainingAttendanceService {
    @Autowired
    private BizTrainingAttendanceMapper bizTrainingAttendanceMapper;

    @Autowired
    private BizTrainingRecordsMapper bizTrainingRecordsMapper;

    /**
     * 查询人员参训记录
     *
     * @param id 人员参训记录主键
     * @return 人员参训记录
     */
    @Override
    public BizTrainingAttendance selectBizTrainingAttendanceById(Long id) {
        return bizTrainingAttendanceMapper.selectBizTrainingAttendanceById(id);
    }

    /**
     * 查询人员参训记录列表
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 人员参训记录
     */
    @Override
    public List<BizTrainingAttendance> selectBizTrainingAttendanceList(BizTrainingAttendance bizTrainingAttendance) {
        return bizTrainingAttendanceMapper.selectBizTrainingAttendanceList(bizTrainingAttendance);
    }

    /**
     * 新增人员参训记录
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 结果
     */
    @Override
    public int insertBizTrainingAttendance(BizTrainingAttendance bizTrainingAttendance) {
        initData(bizTrainingAttendance);
        return bizTrainingAttendanceMapper.insertBizTrainingAttendance(bizTrainingAttendance);
    }

    private void initData(BizTrainingAttendance bizTrainingAttendance) {
        BizTrainingRecords bizTrainingRecords = bizTrainingRecordsMapper.selectBizTrainingRecordsById(bizTrainingAttendance.getTrainingId());
        if (StringUtils.isNull(bizTrainingRecords)) {
            throw new RuntimeException("没有找到对应的培训");
        }
        if (StringUtils.isNull(bizTrainingRecords.getChoiceId())) {
            throw new RuntimeException("该培训没有项目,请选择项目！！！");
        }
        bizTrainingAttendance.setChoiceId(bizTrainingRecords.getChoiceId());
        bizTrainingAttendance.setTrainingName(bizTrainingRecords.getTrainingName());
    }

    /**
     * 修改人员参训记录
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 结果
     */
    @Override
    public int updateBizTrainingAttendance(BizTrainingAttendance bizTrainingAttendance) {
        initData(bizTrainingAttendance);
        return bizTrainingAttendanceMapper.updateBizTrainingAttendance(bizTrainingAttendance);
    }

    /**
     * 批量删除人员参训记录
     *
     * @param ids 需要删除的人员参训记录主键
     * @return 结果
     */
    @Override
    public int deleteBizTrainingAttendanceByIds(Long[] ids) {
        return bizTrainingAttendanceMapper.deleteBizTrainingAttendanceByIds(ids);
    }

    /**
     * 删除人员参训记录信息
     *
     * @param id 人员参训记录主键
     * @return 结果
     */
    @Override
    public int deleteBizTrainingAttendanceById(Long id) {
        return bizTrainingAttendanceMapper.deleteBizTrainingAttendanceById(id);
    }
}

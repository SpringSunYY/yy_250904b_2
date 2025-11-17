package com.ruoyi.prepare.mapper;

import java.util.List;
import com.ruoyi.prepare.domain.BizToolInventory;
import com.ruoyi.statistics.domain.ro.BizStatisticsToolRo;
import org.apache.ibatis.annotations.Param;

/**
 * 工器具检查Mapper接口
 *
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizToolInventoryMapper
{
    /**
     * 查询工器具检查
     *
     * @param toolId 工器具检查主键
     * @return 工器具检查
     */
    public BizToolInventory selectBizToolInventoryByToolId(Long toolId);

    /**
     * 查询工器具检查列表
     *
     * @param bizToolInventory 工器具检查
     * @return 工器具检查集合
     */
    public List<BizToolInventory> selectBizToolInventoryList(BizToolInventory bizToolInventory);

    /**
     * 新增工器具检查
     *
     * @param bizToolInventory 工器具检查
     * @return 结果
     */
    public int insertBizToolInventory(BizToolInventory bizToolInventory);

    /**
     * 修改工器具检查
     *
     * @param bizToolInventory 工器具检查
     * @return 结果
     */
    public int updateBizToolInventory(BizToolInventory bizToolInventory);

    /**
     * 删除工器具检查
     *
     * @param toolId 工器具检查主键
     * @return 结果
     */
    public int deleteBizToolInventoryByToolId(Long toolId);

    /**
     * 批量删除工器具检查
     *
     * @param toolIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizToolInventoryByToolIds(Long[] toolIds);

    /**
     * 工器具检查统计
     * @param choiceId
     * @param dateType
     * @param startDate
     * @param endDate
     * @return
     */
    List<BizStatisticsToolRo> selectToolInventoryStatistics(@Param("choiceId") Long choiceId, @Param("dateType") String dateType, @Param("startDate") String startDate, @Param("endDate") String endDate);
}

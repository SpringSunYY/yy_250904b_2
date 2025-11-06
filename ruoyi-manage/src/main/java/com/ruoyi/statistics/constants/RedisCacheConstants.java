package com.ruoyi.statistics.constants;

/**
 * redis缓存
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-11-01  19:41
 * @Version: 1.0
 */
public class RedisCacheConstants {
    /**
     * 缓存时间
     */
    public static final int REDIS_TIMEOUT = 5 * 60;
    /**
     * 设备点检完成率checkRate
     */
    public static final String STATISTICS_CHECK_RATE = "statistics:check:rate";
    /**
     * 设备运行时间runTime
     */
    public static final String STATISTICS_RUN_TIME = "statistics:run:time";
    /**
     * 设备润环率lubricateRate
     */
    public static final String STATISTICS_LUBRICATE_RATE = "statistics:lubricate:rate";
    /**
     * 设备隐患整改率defectEliminate
     */
    public static final String STATISTICS_DEFECT_ELIMINATE = "statistics:defect:eliminate";
    /**
     * 设备泄露率leakageRate
     */
    public static final String STATISTICS_LEAKAGE_RATE = "statistics:leakage:rate";
    /**
     * 设备缺陷消除率defectEliminateRate
     */
    public static final String STATISTICS_DEFECT_ELIMINATE_RATE = "statistics:defect:eliminate:rate";
}

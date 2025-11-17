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
     * trainingRate
     */
    public static final String STATISTICS_TRAINING_RATE = "statistics:training:rate";
    /**
     * toolInventoryRate
     */
    public static final String STATISTICS_TOOL_INVENTORY_RATE = "statistics:tool:inventory:rate";
    /**
     * securityAgreementRate
     */
    public static final String STATISTICS_SECURITY_AGREEMENT_RATE = "statistics:security:agreement:rate";
    /**
     * qualificationRate
     */
    public static final String STATISTICS_QUALIFICATION_RATE = "statistics:qualification:rate";
}

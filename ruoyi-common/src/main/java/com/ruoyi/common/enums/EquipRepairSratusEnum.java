package com.ruoyi.common.enums;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 设备报修状态 枚举
 * 对应字典类型：equip_repair_sratus
 */
public enum EquipRepairSratusEnum {

    /** 草稿 */
    EQUIP_REPAIR_SRATUS_0("0", "草稿"),

    /** 审批中 */
    EQUIP_REPAIR_SRATUS_1("1", "审批中"),

    /** 已通过 */
    EQUIP_REPAIR_SRATUS_2("2", "已通过"),

    /** 已驳回 */
    EQUIP_REPAIR_SRATUS_3("3", "已驳回"),

    /** 进行中 */
    EQUIP_REPAIR_SRATUS_4("4", "进行中"),

    /** 待验收 */
    EQUIP_REPAIR_SRATUS_5("5", "待验收");

    private static final Map<String, EquipRepairSratusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (EquipRepairSratusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    EquipRepairSratusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应的枚举
     *
     * @param value 枚举的值
     * @return 对应的枚举对象，如果没有找到则返回 Optional.empty()
     */
    public static Optional<EquipRepairSratusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}

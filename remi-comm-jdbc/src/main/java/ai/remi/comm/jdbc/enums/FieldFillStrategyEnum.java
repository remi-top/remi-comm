package ai.remi.comm.jdbc.enums;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc 字段填充策略枚举
 */
public enum  FieldFillStrategyEnum {

    /**
     * 插入时填充字段
     */
    INSERT,
    /**
     * 更新时填充字段
     */
    UPDATE,
    /**
     * 插入和更新时填充字段
     */
    INSERT_UPDATE
}

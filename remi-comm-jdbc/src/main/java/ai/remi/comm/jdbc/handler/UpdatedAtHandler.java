package ai.remi.comm.jdbc.handler;

import ai.remi.comm.jdbc.config.FieldFillConfig;
import ai.remi.comm.jdbc.enums.FieldFillStrategyEnum;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.TimestampValue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc 更新时间字段填充拦截器
 */
public class UpdatedAtHandler extends AbstractFieldFillHandler {

    public UpdatedAtHandler(FieldFillConfig fieldFillConfig) {
        super(FieldFillStrategyEnum.INSERT_UPDATE, fieldFillConfig.getUpdateAtIntercept());
    }


    @Override
    protected Expression doGetFieldFillValue() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        return new TimestampValue(date);
    }

    @Override
    protected String getDefaultColumn() {
        return "updated_at";
    }

}
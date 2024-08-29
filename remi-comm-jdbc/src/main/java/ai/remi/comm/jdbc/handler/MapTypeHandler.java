package ai.remi.comm.jdbc.handler;

import java.util.Map;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc Map类型转换器
 */
public class MapTypeHandler extends JsonTypeHandler<Map> {
    public MapTypeHandler() {
        super(Map.class);
    }
}

package ai.remi.comm.jdbc.handler;

import java.util.List;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc List类型转换器
 */
public class ListTypeHandler extends JsonTypeHandler<List> {
    public ListTypeHandler() {
        super(List.class);
    }
}

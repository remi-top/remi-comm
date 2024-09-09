package ai.remi.comm.file.domian;

import lombok.Data;

import java.util.List;

@Data
public class DirTree {
    /**
     * dtree自定义图标
     */
    private String iconClass;

    /**
     * dtree开启复选框
     */
    private String checkArr = "0";

    /**
     * dtree节点名称
     */
    private String title;

    /**
     * 是否展开节点
     */
    private Boolean spread = true;

    /**
     * 子集合
     */
    private List<DirTree> children;
}

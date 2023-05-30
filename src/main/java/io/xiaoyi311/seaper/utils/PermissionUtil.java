package io.xiaoyi311.seaper.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具
 * @author Xiaoyi311
 */
public class PermissionUtil {
    /**
     * 超级管理员
     */
    public static List<String> SuperAdmin = List.of("*");

    /**
     * 普通用户
     */
    public static List<String> Default = List.of();


    /**
     * 检查权限
     * @param userPermissions 用户权限
     * @param targetPermissions 检查权限
     * @return 是否通过
     */
    public static boolean checkPermissions(List<String> userPermissions, List<String> targetPermissions) {
        //获取重合数据
        List<String> allHave = new ArrayList<>(userPermissions);
        allHave.retainAll(targetPermissions);

        //移除重合数据
        targetPermissions.removeAll(allHave);

        //是否已经满足
        if (targetPermissions.size() == 0){
            return true;
        }

        //遍历拥有的权限，并获取拥有的权限节点
        List<String> havaNodes = new ArrayList<>();
        for (String userPermission : userPermissions) {
            //若带有泛指标志
            if(userPermission.contains("*")){
                //获取对应拥有的权限节点
                havaNodes.add(userPermission.replace(".*", ""));
            }
        }

        //遍历需要的权限
        boolean isSuccessful = true;
        for (String targetPermission : targetPermissions){
            boolean isFind = false;
            //遍历拥有的权限
            for (String haveNode : havaNodes){
                //查找目前权限是否用允许的节点开头
                if (targetPermission.startsWith(haveNode)) {
                    isFind = true;
                    break;
                }
            }

            //是否未找到匹配权限
            if (!isFind) {
                isSuccessful = false;
                break;
            }
        }
        return isSuccessful;
    }
}

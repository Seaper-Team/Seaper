package io.xiaoyi311.seaper.interceptor;

import io.xiaoyi311.seaper.annotation.Permission;
import io.xiaoyi311.seaper.exception.AccessDeniedException;
import io.xiaoyi311.seaper.exception.DataFileException;
import io.xiaoyi311.seaper.model.User;
import io.xiaoyi311.seaper.service.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * 权限注解处理拦截器
 * @author Xiaoyi311
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    /**
     * 拦截处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        //在方法上寻找注解
        if(permission == null){
            permission = method.getDeclaringClass().getAnnotation(Permission.class);
        }

        //如果找不到注释跳过
        if(permission == null){
            return true;
        }

        //检查权限
        String[] checkPermissions = permission.value();
        if(checkPermissions(request.getSession(), checkPermissions)){
            return true;
        }

        throw new AccessDeniedException(checkPermissions);
    }

    /**
     * 检测权限
     * @return 是否通过
     */
    public static boolean checkPermissions(HttpSession session, String[] checkPermissions) throws DataFileException {
        //获取用户
        User user = UserManager.getBySession(session);
        return user != null && UserManager.checkPermissions(user, checkPermissions);
    }
}

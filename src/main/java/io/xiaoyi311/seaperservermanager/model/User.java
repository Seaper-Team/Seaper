package io.xiaoyi311.seaperservermanager.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 用户实体
 * @author Xiaoyi311
 * @apiDefine UserData
 * @apiError UsernameNotBlank 用户名不能为空
 * @apiError PasswordNotBlank 密码不能为空
 * @apiError PasswordNotMd5 密码不是 Md5
 * @apiErrorExample {json} 错误-用户名不能为空
 *  HTTP/1.1 400 Bad Request
 *  {
 *      "status":400,
 *      "time":0,
 *      "data":"Username cant be null!"
 *  }
 * @apiErrorExample {json} 错误-密码不能为空
 *  HTTP/1.1 400 Bad Request
 *  {
 *      "status":400,
 *      "time":0,
 *      "data":"Password cant be null!"
 *  }
 * @apiErrorExample {json} 错误-密码不是 Md5
 *  HTTP/1.1 400 Bad Request
 *  {
 *      "status":400,
 *      "time":0,
 *      "data":"Password need md5!"
 *  }
 */
@Getter
@Setter
@NoArgsConstructor
public class User {
    /**
     * UUID
     */
    public String uuid;
    /**
     * 用户名
     */
    @NotBlank(message = "user.usernameNull")
    public String username;
    /**
     * 密码
     */
    @NotBlank(message = "user.passwordNull")
    @Size(min = 32, message = "user.passwordNotMd5", max = 32)
    public String password;
    /**
     * 注册时间
     */
    public long registerTime;
    /**
     * 权限
     */
    public List<String> permissions;

    /**
     * 实例化
     */
    public User(String uuid, String username, String password, List<String> permissions){
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.registerTime = System.currentTimeMillis();
        this.permissions = permissions;
    }
}

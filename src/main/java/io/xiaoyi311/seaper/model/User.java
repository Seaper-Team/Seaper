package io.xiaoyi311.seaper.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户实体
 * @author Xiaoyi311
 * @apiDefine UserData
 * @apiBody {String} username 要注册的用户名
 * @apiBody {String} password 要注册的密码 (应为MD5)
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
@Data
@NoArgsConstructor
public class User {
    /**
     * UUID
     */
    public String uuid = UUID.randomUUID().toString();

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
    public long registerTime = System.currentTimeMillis();

    /**
     * 权限
     */
    public List<String> permissions = new ArrayList<>();

    /**
     * 实例化
     */
    public User(String uuid, String username, String password, List<String> permissions){
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }
}

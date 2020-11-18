package proxytest;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/17 0017 16:41
 */
public interface UserManager {

    /**
     *
     *新增用户抽象方法
     */
    void addUser(String userName,String password);

    /**
     * 删除用户抽象方法
     */
    void delUser(String userName);

}

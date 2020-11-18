package proxytest;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/17 0017 16:42
 */
public class UserManagerImpl implements UserManager {

    @Override
    public void addUser(String userName, String password) {
        System.out.println("user新增，userName:" + userName + ",passWord:" + password);
    }

    @Override
    public void delUser(String userName) {
        System.out.println("user删除，userName:" + userName);
    }
}

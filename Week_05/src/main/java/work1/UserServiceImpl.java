package work1;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 10:18
 */
public class UserServiceImpl implements UserService {

    @Override
    @EnableProxy
    public void addUser(String name, String password) {
        System.out.println("user新增，userName:" + name + ",passWord:" + password);
    }

    @Override
    public String getUserName() {
        System.out.println("获取user成功，userName: yangxing");
        return "yangxing";
    }

    @Override
    @EnableProxy
    public void deleteUser(String name) {
        System.out.println("user删除成功，userName:" + name);
    }

}

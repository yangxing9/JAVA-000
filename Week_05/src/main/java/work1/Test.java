package work1;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 11:43
 */
public class Test {

    public static void main(String[] args) {
        UserService service = MyProxyFactory.getService(UserServiceImpl.class,new MyAspectImpl());
        UserService service2 = MyProxyFactory.getService("work1.UserServiceImpl",new MyAspectImpl());
        UserService service3 = MyProxyFactory.getService(new UserServiceImpl(),new MyAspectImpl());

//        service3.addUser("yangxing","password");
        service3.getUserName();
//        service3.deleteUser("yangxing");
    }
}

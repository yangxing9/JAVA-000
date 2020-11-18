package work1;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 10:16
 */
public interface UserService {

    void addUser(String name,String password);

    String getUserName();

    void deleteUser(String name);


}

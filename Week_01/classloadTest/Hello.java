package classloadTest;

public class Hello {

    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("代码块");
    }

  public void hello(){
     System.out.println("Hello, classLoader!");
  }

}
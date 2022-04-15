package test.singleinstance;

/**
 * Created by C on 2018/11/16.
 */
public class StaticInner {
    static {
        System.out.println("init outer static code block...");
    }

    private StaticInner() {}

    private static class Instance {
        static {
            System.out.println("init inner static code block...");
        }
        private static final StaticInner instance = new StaticInner();
    }

    public static final StaticInner getInstance() {
        System.out.println("before get instance");
        return Instance.instance;
    }

}

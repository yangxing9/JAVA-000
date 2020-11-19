package mycacheaop.cacheimpl;


import mycacheaop.MyCache;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 15:56
 */
public class CacheUtils {

    private static CacheUtils instance = new CacheUtils();

    private CacheUtils(){}

    public static CacheUtils getInstance(){
        return instance;
    }

    /**
     *  读取现有缓存
     * @param key 实际key,非key表达式
     * @param myCacheable 是否刷新存活时间
     * @return
     */
    public Object getCache(String key, MyCache myCacheable){
        Object res = null;
        switch (myCacheable.cacheStrategy()){
            case REDIS:
                res = RedisUtils.getInstance().getCache(key,myCacheable.state(),myCacheable.ttl());
                break;
            case EHCACHE:
                break;
            case MEMORYCACHE:
                break;
            default:
                System.out.println("===");
        }
        return res;
    }


    public Object putKey(String key, Object value, MyCache myCacheable){
        Object res = null;
        switch (myCacheable.cacheStrategy()){
            case REDIS:
                res = RedisUtils.getInstance().putValue(key,value,myCacheable.cacheNull(),myCacheable.ttl());
                break;
            case EHCACHE:
                break;
            case MEMORYCACHE:
                break;
            default:
                System.out.println("===");
        }
        return res;
    }
}

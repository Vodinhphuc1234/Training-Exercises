/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cache;


import ThriftConnection.User;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 *
 * @author vodinhphuc
 */
public class CacheHelper{
    private CacheManager cacheManager;
    private Cache<Long, User> myCache;
    public CacheHelper (){
        cacheManager = CacheManagerBuilder
          .newCacheManagerBuilder().build();
        
        cacheManager.init();
       
       myCache =  (Cache<Long, User>) cacheManager
          .createCache("User", CacheConfigurationBuilder
            .newCacheConfigurationBuilder(
              Long.class, User.class,
              ResourcePoolsBuilder.heap(10)));
        
    }
    public Cache<Long, User> getCache (String name){
        return (Cache) cacheManager.getCache(name, Long.class, User.class);
    }
    
}

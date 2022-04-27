/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cache;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vodinhphuc
 */
public class Cache<K, V> {
    
    private final int defaultExpireTime = 1200;
    
    private HashMap<K, V> pairs = new HashMap<>();
    
    private int maxSize;
    
    private LinkedHashMap<K, LocalDateTime> inputTimes = new LinkedHashMap<>();
    
    public void putValue (K key, V value){
        if (pairs.size()>= maxSize)
        {
            pairs.remove(this.getEldestElement());
            inputTimes.remove(this.getEldestElement());
        }
        inputTimes.put(key, LocalDateTime.now());
        pairs.put(key, value);
    }
    
    public V getValue (K key){
        
        V result = pairs.get(key);
        return result;
    }
    
    public void cleanExpiredElement(){
        for (Map.Entry e: inputTimes.entrySet()){
            if (ChronoUnit.MINUTES.between((Temporal) e.getValue(), LocalDateTime.now()) 
                    < defaultExpireTime)
            {
                pairs.remove(e.getKey());
                inputTimes.remove(e.getKey());
            }
        }
    }
    
    public K getEldestElement (){
        
        List <Map.Entry<K, LocalDateTime>> list;
        list = new ArrayList<Map.Entry<K, LocalDateTime>>(inputTimes.entrySet());
        
        return list.get(0).getKey();
    }
    
    public void remove (K key){
        pairs.remove(key);
        inputTimes.remove(key);
    }
}

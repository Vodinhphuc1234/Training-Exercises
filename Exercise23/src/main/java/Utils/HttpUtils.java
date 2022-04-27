/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author vodinhphuc
 */
public class HttpUtils<T> {
    public T getModel (BufferedReader br, Class<T> tClass) throws JsonProcessingException, IOException{
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line=br.readLine())!=null){
            sb.append(line);
        }
        
        T result = new ObjectMapper().readValue(sb.toString(), tClass);
        return result;
    }
}

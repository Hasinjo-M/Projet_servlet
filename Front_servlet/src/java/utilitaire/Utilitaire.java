/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import annotation.URLannotation;
import etu1789.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Hasinjo
 */
public class Utilitaire {
    
    public String[] splitURL(HttpServletRequest request){
        String urldefault = request.getPathInfo();
        String[] taburl = urldefault.split("/");
        return taburl;
    }
    
    public HashMap<String , Mapping> set_allMethodAnnotation( String path, File directori , HashMap<String, Mapping> mappingUrls) throws ClassNotFoundException{
        for (File file_details : directori.listFiles()) {
            if(file_details.isDirectory() == true){
                mappingUrls = set_allMethodAnnotation(path, file_details, mappingUrls);
            }else{
                if(file_details.getName().contains(".class")){
                    String name_class = file_details.toString().split("\\.")[0].replace(path+"WEB-INF\\classes\\" , "").replace("\\" , ".");
                    Method[] List_functions = Class.forName(name_class).getDeclaredMethods() ;
                    for (Method method : List_functions) {
                        if(method.isAnnotationPresent(URLannotation.class)){
                            URLannotation annotation = method.getAnnotation(URLannotation.class);
                            mappingUrls.put(annotation.url(), new Mapping(name_class,method.getName()));
                        }
                    }
                }
            }
        }
        return mappingUrls;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import annotation.Scop;
import annotation.URLannotation;
import etu1789.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Hasinjo
 */
public class Utilitaire {
    /****** Sprint 10********/
    private boolean  cheackannoationScop(Class clazz, String scop){
        Annotation[] annotations = clazz.getAnnotations();
        //System.out.println(clazz.toString());
        for (Annotation annotation1 : annotations) {
            if( annotation1 instanceof Scop){
                Scop s = (Scop) annotation1;
                if(s.scop().equals(scop)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public HashMap<String , Object> get_class_method(Mapping mapping, HashMap<String, Object> obj_singleton) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        String clazz = mapping.getClassName();
        Class c = (Class.forName(clazz));
        HashMap<String , Object> rep = null;
        if(cheackannoationScop(c, "singleton")== true){
            Object obj = obj_singleton.get(clazz);
            if(obj == null){
                obj = c.newInstance();
                obj_singleton.remove(clazz);
                obj_singleton.put(clazz, obj);
            }
            System.out.println("Metyyyy b bbb");
            rep = get_class_method_singleton(mapping, obj_singleton);
            rep.put("object", obj);
            return rep;
        }
        Object obj = c.newInstance();
        rep = get_class_method(mapping);
        rep.put("object", obj);
        return rep;
   }
    
    private HashMap<String , Object> get_class_method_singleton(Mapping mapping, HashMap<String, Object> obj_singleton) throws ClassNotFoundException{
        String clazz = mapping.getClassName();
        HashMap<String , Object> rep = null;
        for (Map.Entry<String, Object> dt : obj_singleton.entrySet()) {
            String key = dt.getKey();
            if(key.equals(clazz)){
                rep = new HashMap<>();
                Class class_utiliser = Class.forName(clazz);
                rep.put("class", class_utiliser);
                Method[] liste_method = class_utiliser.getDeclaredMethods();
                for (Method method : liste_method) {
                    if(method.getName().equals(mapping.getMethod())){
                        rep.put("method", method);
                        break;
                    }
                }
                return rep;
            }
        }
        return  rep;
    } 
    /***********************/
    
    /*** faire un majiscule d'un mot
     * @param word
     * @return  **/
    public  String capitalize(String word) {
    if (word == null || word.isEmpty()) {
        return word;
    }
    return word.substring(0, 1).toUpperCase() + word.substring(1);
}

    /**
     *
     * @param request
     * @return
     */
    public String[] splitURL(HttpServletRequest request){
        String urldefault = request.getPathInfo();
        String[] taburl = urldefault.split("/");
        return taburl;
    }
    
    /**
     *
     * @param path
     * @param directori
     * @param mappingUrls
     * @return
     * @throws ClassNotFoundException
     */
    public HashMap<String , Mapping> set_allMethodAnnotation( String path, File directori , HashMap<String, Mapping> mappingUrls, HashMap<String, Object> obj_singleton) throws ClassNotFoundException{
        for (File file_details : directori.listFiles()) {
            if(file_details.isDirectory() == true){
                mappingUrls = set_allMethodAnnotation(path, file_details, mappingUrls, obj_singleton);
            }else{
                if(file_details.getName().contains(".class")){
                    String name_class = file_details.toString().split("\\.")[0].replace(path+"WEB-INF\\classes\\" , "").replace("\\" , ".");
                    Class clazz = Class.forName(name_class);
                    Method[] List_functions = clazz.getDeclaredMethods() ;
                    boolean cheak_scoop = cheackannoationScop(clazz, "singleton");
                    if(cheak_scoop == true){
                        obj_singleton.put(clazz.toString(), clazz);
                    }
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
    
    
    /***
     * chelck Mapping
     * 
     * @param liste_mapping
     * @param request
     * @return 
     */
    public Mapping get_mapping(HashMap<String , Mapping> liste_mapping, HttpServletRequest request) {
        String[] splits = splitURL(request);
        String annotation = splits[1];
        for (Map.Entry<String, Mapping> entry :  liste_mapping.entrySet()) {
                String key = entry.getKey();
                if(key.equals(annotation)){
                    return entry.getValue();
                }
        }
        return null;
    }
    
    /*** appel du class et le method deu fonction
     * @param mapping
     * @return 
     * @throws java.lang.ClassNotFoundException ***/
    
    public  HashMap<String , Object> get_class_method(Mapping mapping) throws ClassNotFoundException{
        HashMap<String , Object> rep = new HashMap<>();
        Class class_utiliser = Class.forName(mapping.getClassName());
        rep.put("class", class_utiliser);
        Method[] liste_method = class_utiliser.getDeclaredMethods();
        for (Method method : liste_method) {
            if(method.getName().equals(mapping.getMethod())){
                rep.put("method", method);
                break;
            }
        }
        return rep;
    } 
    
    
    
}

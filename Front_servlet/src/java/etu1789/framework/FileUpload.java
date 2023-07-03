/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1789.framework;

import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Hasinjo
 */
public class FileUpload {
    private String nomFichier;
    private byte[] fichier;

    /*** Fonction qui traite le fichier insert par le formulaire
     * @param req
     * @throws java.lang.Exception **/
   
    public void transform_fileupload(Part req)throws Exception{
        this.setNomFichier(this.getFileName(req));
        try {
            InputStream fileContent = req.getInputStream();  
            byte[] bytes = readAllBytes(fileContent);
        } catch (Exception e) {
            throw new Exception("Impossible d'enregistre le fichier");
        }
             
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        
        return outputStream.toByteArray();
    }
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    public  void uploadFile(String uploadDirectory) throws Exception{ 
          FileOutputStream outputStream = null;
        try{
            File uploadedFile = new File(uploadDirectory, this.nomFichier);
            outputStream = new FileOutputStream(uploadedFile);
            outputStream.write(this.fichier);
        }catch (Exception e) {
           throw e;
        }
        
    }

    public FileUpload(String nomFichier, byte[] fichier) {
        this.nomFichier = nomFichier;
        this.fichier = fichier;
    }

    public FileUpload() {
    }

       
    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }
    
    
    
}

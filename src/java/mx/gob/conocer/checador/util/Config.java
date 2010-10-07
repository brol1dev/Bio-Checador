package mx.gob.conocer.checador.util;

/**
 *
 * @author Ironbit
 */

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;


public class Config {

    private Properties props = new Properties();
    private FileInputStream fsFile;

    public void loadFile(String pstrFilename) {
    try {


    fsFile = new FileInputStream(pstrFilename);

    //System.out.print("Archivo: " + filename + "\n");
    props.load(fsFile);

    } catch (Exception e) {
    System.out.print("Error en Config.loadFile " + e.toString() + "\n");
    }

    }
    public void closeFile() {
        try {
                fsFile.close();
        } catch (Exception e) {
                System.out.print("Error al cerrar el archivo. " + e.toString() + "\n");
        }
    }

    public String getProperty(String pstrPropiedad) {
        try {
            String val = props.getProperty(pstrPropiedad);
            /*
            if (val==null) {
                    System.out.print("Key '"+pstrPropiedad+"' not found.\n");
            } else {
                    System.out.print(pstrPropiedad+"=" + val + "\n");
            }
            */
            return val;
        } catch (Exception e) {
            System.out.print("Error en Config.getProperty " + e.toString() + "\n");
            return null;
        }
    }


     private String getPath() {
        String cn = "/" + this.getClass().getName();
        cn = cn.replace('.', '/');
        cn += ".class";
        URL url = this.getClass().getResource(cn);
        String path = url.getPath();
      /*  log.debug("cambio");
        log.debug("separadorSystem:" + System.getProperty("file.separator"));
        log.debug("separadorFile:" + File.separator);
        log.debug(path);*/
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.replaceAll("%20", " ");
        //log.debug(path);


        return path;

    }
}

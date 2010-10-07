package mx.gob.conocer.checador.util;

import java.io.File;
import java.net.URL;

/**
 *
 * @author Ironbit
 */
public class getPath<T> {

    private final Class<T> clas;

    public getPath(Class<T> aClass) {
        this.clas = aClass;
    }

    public String ver() {
        String a = "";
        String path = "";
        if (File.separator.equals("\\")) {
            a = "/" + clas.getName();
            a = a.replace(".", "/");
            a += ".class";
            URL url = clas.getResource(a);
            path = url.getPath();
            /* path = path.substring(0, path.lastIndexOf("/"));
            path = path.replaceAll("%20", "/");
             *
             */
            path = path.substring(0, path.lastIndexOf("/"));
            path = path.replace("/", "\\");
            path = path.replace("%20", " ");
            System.out.println(" *** PATH PARA WINDOWS" + path);
        } else if (File.separator.equals("/")) {
            a = "/" + clas.getName();
            a = a.replace(".", "/");
            a += ".class";
            URL url = clas.getResource(a);

            path=  url.getPath();
            System.out.println(path);
            System.out.println(System.getProperty("file.separator"));
            System.out.println(File.separator);

            path = path.substring(0, path.lastIndexOf("/"));
            path = path.replaceAll("%20", "/");



        }
        return path;
    }
}
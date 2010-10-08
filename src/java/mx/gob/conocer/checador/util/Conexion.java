package mx.gob.conocer.checador.util;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 *
 * @author Ironbit
 */
public class Conexion {

    /** Conexi�n */
    private Connection conn;
    /** Nombre del Usuario en la base de datos */
    private String strUser = "";
    /** Contrase�a del Usuario en la base de datos */
    private String strPassWord = "";
    private String strDriver = "";
    private String strsqlservername = "";
    private String strDBName = "";
    private String strPort = "";
    private String portNo = "";

    private static final Logger bitacora = Logger.getLogger(Conexion.class);
    /** Creates a new instance of Conexion */
    public Conexion() {
        Config objConfig = new Config();
        String path = new getPath<Conexion>(Conexion.class).ver();
        path = path + File.separator + "jdbc.properties";
        objConfig.loadFile(path);
        //strDriver = objConfig.getProperty("driverType");
        strsqlservername = objConfig.getProperty("sqlservername");
        strDBName = objConfig.getProperty("sqlserverDbname");
        strPort = objConfig.getProperty("sqlserverPort");
        strUser = objConfig.getProperty("sqlserverusr");
        strPassWord = objConfig.getProperty("sqlserverpwd");

        objConfig.closeFile();
        conn = null;
    }

    public Connection AbreConexion(){

        try {
            SQLServerDataSource ods = new SQLServerDataSource();
            //Fijando el tipo de driver

            //Fijando el Host Name, se carga del archivo de propiedades
            ods.setServerName(strsqlservername);
            //Fijando el nombre de la instancia, se carga del archivo de propiedades
            ods.setDatabaseName(strDBName);
            //Fijando el numero de puerto, se carga del archivo de propiedades
            ods.setPortNumber(Integer.parseInt(strPort));
            //Fijando el nombre de usuario, se carga del archivo de propiedades
            ods.setUser(strUser);
            //Fijando el password, se carga del archivo de propiedades
            ods.setPassword(strPassWord);
            conn = ods.getConnection();
            
            return conn;
        }
        catch(SQLException sqle) {
            if(bitacora.isInfoEnabled())bitacora.info("Error en Conexi�n " + sqle.toString());
            return null;
        }
        catch(Exception e){
            if(bitacora.isInfoEnabled())bitacora.info("Error en Conexi�n " + e.toString());
            return null;
        }
    }

    public void cierraConexion(Connection conexion) {

        try{
            if(conn != null) conn.close();
        }
        catch(Exception error){
            if(bitacora.isInfoEnabled())bitacora.info("Error al cerrar Conexi�n " + error.toString());
        }
    }

}

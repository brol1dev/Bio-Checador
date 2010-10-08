package mx.gob.conocer.checador.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.conocer.checador.model.Status;
import mx.gob.conocer.checador.util.Conexion;

/**
 *
 * @author Ironbit
 */
public class StatusDAO {

     private final static String SP_OBTENER_STATUS = "{ call SpObtenerStatus() }";
     private Conexion conexion;
     private Connection conn;
     private CallableStatement proc;

     public List<Status> obtenerEstatus() throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_OBTENER_STATUS);

          ResultSet rs = proc.executeQuery();
          List<Status> listStatus = new ArrayList<Status>();
          while(rs.next()) {
               Status status = new Status();
               status.setId(rs.getInt(1));
               status.setStatus(rs.getString(2));
               listStatus.add(status);
          }
          rs.close();
          conexion.cierraConexion(conn);

          return listStatus;
     }
}

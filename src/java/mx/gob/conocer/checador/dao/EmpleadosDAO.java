package mx.gob.conocer.checador.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mx.gob.conocer.checador.model.Empleado;
import mx.gob.conocer.checador.util.Conexion;

/**
 *
 * @author Ironbit
 */
public class EmpleadosDAO {

     private final static String SP_OBTENER_EMPLEADO_CODIGO = "{ call SpObtenerEmpleadoPorCodigo(?, ?, ?) }";
     private final static String SP_REGISTRAR_HORA = "{ call SpRegistrarHora(?, ?, ?, ?, ?) }";
     private Conexion conexion;
     private Connection conn;
     private CallableStatement proc;

     public Map<String, Object> obtenerEmpleadoPorCodigo(String codigo, int status) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_OBTENER_EMPLEADO_CODIGO);
          proc.setString("codigo", codigo);
          proc.setInt("idStatus", status);
          proc.registerOutParameter("notStatus", java.sql.Types.INTEGER);

          Map<String, Object> resultado = new HashMap<String, Object>();
          ResultSet rs = proc.executeQuery();
          while(rs.next()) {
               Empleado empleado = new Empleado();
               empleado.setIdRegistro(rs.getInt(1));
               empleado.setCodigo(rs.getString(2));
               empleado.setNombre(rs.getString(3));
               empleado.setArea(rs.getString(4));
               empleado.setNumEmpleado(rs.getInt(5));
               empleado.setRfc(rs.getString(6));
               resultado.put("empleado", empleado);
          }
          Integer usoStatus = proc.getInt(3);
          resultado.put("usoStatus", usoStatus);
          rs.close();
          conexion.cierraConexion(conn);

          return resultado;
     }

     public Map<String, Object> registrarHora(int idEmpleado, int idStatus, String comentario) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_REGISTRAR_HORA);
          proc.setInt("idEmpleado", idEmpleado);
          proc.setInt("idStatus", idStatus);
          proc.setString("comentario", comentario);
          proc.registerOutParameter("fechaActual", java.sql.Types.TIMESTAMP);
          proc.registerOutParameter("usoStatus", java.sql.Types.BIGINT);
          proc.execute();

          Map<String, Object> resultado = new HashMap<String, Object>();
          Date date = proc.getTimestamp("fechaActual");
          Integer usoStatus = proc.getInt(5);
          resultado.put("fechaActual", date);
          resultado.put("usoStatus", usoStatus);

          conexion.cierraConexion(conn);

          return resultado;
     }

}

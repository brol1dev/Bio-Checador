package mx.gob.conocer.checador.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.conocer.checador.model.Empleado;
import mx.gob.conocer.checador.util.Conexion;

/**
 *
 * @author Ironbit
 */
public class EmpleadosDAO {

     private final static String SP_OBTENER_EMPLEADO_CODIGO = "{ call SpObtenerEmpleadoPorCodigo(?) }";
     private Conexion conexion;
     private Connection conn;
     private CallableStatement proc;

     public Empleado obtenerEmpleadoPorCodigo(String codigo) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_OBTENER_EMPLEADO_CODIGO);
          proc.setString("codigo", codigo);

          Empleado empleado = null;
          ResultSet rs = proc.executeQuery();
          while(rs.next()) {
               empleado = new Empleado();
               empleado.setIdRegistro(rs.getInt(1));
               empleado.setCodigo(rs.getString(2));
               empleado.setNombre(rs.getString(3));
               empleado.setArea(rs.getString(4));
               empleado.setNumEmpleado(rs.getInt(5));
               empleado.setRfc(rs.getString(6));
          }
          rs.close();
          conexion.cierraConexion(conn);

          return empleado;

     }
}

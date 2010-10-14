package mx.gob.conocer.checador.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.conocer.checador.model.Usuario;
import mx.gob.conocer.checador.util.Conexion;

/**
 *
 * @author Ironbit
 */
public class UsuariosDAO {

     private final static String SP_OBTENER_USUARIO = "{ call SpObtenerUsuario(?, ?) }";
     private Conexion conexion;
     private Connection conn;
     private CallableStatement proc;

     public String obtenerUsuario(Usuario usuario) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_OBTENER_USUARIO);
          proc.setString("usuario", usuario.getUsuario());
          proc.setString("password", usuario.getPassword());

          String nombre = null;
          ResultSet rs = proc.executeQuery();
          while (rs.next()) {
               nombre = rs.getString(1);
          }
          rs.close();
          conexion.cierraConexion(conn);

          return nombre;
     }
}

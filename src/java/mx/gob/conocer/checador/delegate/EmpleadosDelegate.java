package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import mx.gob.conocer.checador.dao.EmpleadosDAO;

/**
 *
 * @author Ironbit
 */
public class EmpleadosDelegate {

     public Map<String, Object> obtenerEmpleadoPorCodigo(String codigo, int status) throws SQLException {
          return new EmpleadosDAO().obtenerEmpleadoPorCodigo(codigo, status);
     }

     public Map<String, Object> registrarHora(int idEmpleado, int idStatus, String comentario) throws SQLException {
          return new EmpleadosDAO().registrarHora(idEmpleado, idStatus, comentario);
     }
}

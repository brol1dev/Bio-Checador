package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import mx.gob.conocer.checador.dao.EmpleadosDAO;
import mx.gob.conocer.checador.model.Empleado;

/**
 *
 * @author Ironbit
 */
public class EmpleadosDelegate {

     public Empleado obtenerEmpleadoPorCodigo(String codigo) throws SQLException {
          return new EmpleadosDAO().obtenerEmpleadoPorCodigo(codigo);
     }

     public void registrarHora(int idEmpleado, int idStatus) throws SQLException {
          new EmpleadosDAO().registrarHora(idEmpleado, idStatus);
     }
}

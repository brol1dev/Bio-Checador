package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import java.util.List;
import mx.gob.conocer.checador.dao.StatusDAO;
import mx.gob.conocer.checador.model.Status;

/**
 *
 * @author Ironbit
 */
public class StatusDelegate {

     public List<Status> obtenerStatus() throws SQLException {
          return new StatusDAO().obtenerEstatus();
     }

     public Status obtenerStatusPorId(int idStatus) throws SQLException {
          return new StatusDAO().obtenerStatusPorID(idStatus);
     }
}

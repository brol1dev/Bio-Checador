package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import mx.gob.conocer.checador.dao.ReportesDAO;

/**
 *
 * @author Ironbit
 */
public class ReportesDelegate {

     public Map obtenerReporteIndividual(int inicio, int fin,
             String codigo, Date fchInicio, Date fchFinal)
             throws SQLException {

          return new ReportesDAO().obtenerReporteIndividual(inicio, fin, codigo, fchInicio, fchFinal);
     }
}

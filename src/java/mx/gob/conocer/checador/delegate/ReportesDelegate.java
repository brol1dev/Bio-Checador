package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.gob.conocer.checador.dao.ReportesDAO;
import mx.gob.conocer.checador.model.Reporte;
import mx.gob.conocer.checador.reportes.ReportesBuilder;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Ironbit
 */
public class ReportesDelegate {

     public Map obtenerReporteIndividual(int inicio, int fin,
             String codigo, Date fchInicio, Date fchFinal)
             throws SQLException {

          return new ReportesDAO().obtenerReporteIndividual(inicio, fin,
                  codigo, fchInicio, fchFinal);
     }

     public Map obtenerReporteDiario(int inicio, int fin,
             Date fecha) throws SQLException {
          return new ReportesDAO().obtenerReporteDiario(inicio, fin, fecha);
     }

     public Map obtenerReportePeriodo(int inicio, int fin,
             Date fchInicial, Date fchFinal) throws SQLException {
          return new ReportesDAO().obtenerReportePeriodo(inicio, fin, fchInicial, fchFinal);
     }

     public byte[] generarReporteXls(List<Reporte> registros) throws JRException {
          return new ReportesBuilder().generarReporteXls(registros);
     }
}

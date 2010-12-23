package mx.gob.conocer.checador.reportes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import mx.gob.conocer.checador.model.Reporte;
import mx.gob.conocer.checador.util.getPath;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Ironbit
 */
public class ReportesBuilder {

     public byte[] generarReporteXls(List<Reporte> registros) throws JRException {
          String path = new getPath<ReportesBuilder>(ReportesBuilder.class).ver();
          String fileName = path + File.separator + "reporteExcel.jasper";

          registros.add(0, null); // La posición 0 en la lista nunca se imprime en el reporte.
          
          JasperPrint print = JasperFillManager.fillReport(fileName, null, new JRBeanCollectionDataSource(registros));

          ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
          JRXlsExporter exporter = new JRXlsExporter();

          exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
          exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
          exporter.exportReport();

          return byteArray.toByteArray();
     }
}

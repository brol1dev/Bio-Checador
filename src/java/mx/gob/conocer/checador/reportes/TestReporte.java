package mx.gob.conocer.checador.reportes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.gob.conocer.checador.dao.ReportesDAO;
import mx.gob.conocer.checador.model.Reporte;
import mx.gob.conocer.checador.util.getPath;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Ironbit
 */
public class TestReporte {

     public static void main(String[] args) {

          String path = new getPath<TestReporte>(TestReporte.class).ver();
          String fileName = path + File.separator + "reporteIndividual.jasper";
          String outFileName = "reporteIndividual.pdf";

          try {
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               Date fInicial = dateFormat.parse("2010-10-08");
               Date fFinal = dateFormat.parse("2010-10-28");
               Map map = new ReportesDAO().obtenerReporteIndividual(0, 0, "1098gags", fInicial, fFinal);
               List<Reporte> lista = (List<Reporte>) map.get("registros");
               JasperPrint print = JasperFillManager.fillReport(fileName, null, new JRBeanCollectionDataSource(lista));

               OutputStream outputStream = new FileOutputStream(new File("/root/NetBeansProjects/bio-checador/excel.xls"));
               ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
               //JRExporter exporter = new JRPdfExporter();
               JRXlsExporter exporter = new JRXlsExporter();

               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
               exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);

               exporter.exportReport();
               
               outputStream.write(byteArray.toByteArray());
               outputStream.flush();
               outputStream.close();

          } catch(JRException ex) { ex.printStackTrace(); }
          catch(Exception ex) { ex.printStackTrace(); }
     }
}

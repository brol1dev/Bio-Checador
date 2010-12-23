package mx.gob.conocer.checador.struts.action;

import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import mx.gob.conocer.checador.delegate.ReportesDelegate;
import mx.gob.conocer.checador.model.Reporte;
import net.sf.jasperreports.engine.JRException;
import net.sf.json.JSONObject;

/**
 *
 * @author Ironbit
 */
public class ReportesAction extends DispatchAction {

          public ActionForward generarReporteIndividual(ActionMapping mapping,
                  ActionForm form,
                  HttpServletRequest request,
                  HttpServletResponse response) throws Exception {

               String codigo = request.getParameter("codigo");
               String fchInicial = request.getParameter("fchInicial");
               String fchFinal = request.getParameter("fchFinal");
               int inicio = Integer.parseInt(request.getParameter("start"));
               int fin = Integer.parseInt(request.getParameter("limit"));

               HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
               Writer out = responseWrapper.getWriter();
               JSONObject jsonResponse = null;
               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

               try {
                    Date fInicial = dateFormat.parse(fchInicial);
                    Date fFinal = dateFormat.parse(fchFinal);
                    Map mapaDatos = new ReportesDelegate().obtenerReporteIndividual(inicio, fin, codigo, fInicial, fFinal);
                    jsonResponse = JSONObject.fromObject(mapaDatos);
               } catch(SQLException ex) {
                    ex.printStackTrace();
               } catch(Exception ex) { ex.printStackTrace(); }
               finally {
                    out.write(jsonResponse.toString());
                    out.close();
               }

               return null;
          }

          public ActionForward generarReporteDiario(ActionMapping mapping,
                  ActionForm form,
                  HttpServletRequest request,
                  HttpServletResponse response) throws Exception {

               String fecha = request.getParameter("fchInicial");
               int inicio = Integer.parseInt(request.getParameter("start"));
               int fin = Integer.parseInt(request.getParameter("limit"));

               HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
               Writer out = responseWrapper.getWriter();
               JSONObject jsonResponse = null;
               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

               try {
                    Date dFecha = dateFormat.parse(fecha);
                    Map mapaDatos = new ReportesDelegate().obtenerReporteDiario(inicio, fin, dFecha);
                    jsonResponse = JSONObject.fromObject(mapaDatos);
               } catch(SQLException ex) {
                    ex.printStackTrace();
               } catch(Exception ex) { ex.printStackTrace(); }
               finally {
                    out.write(jsonResponse.toString());
                    out.close();
               }

               return null;
          }

          public ActionForward generarReportePeriodo(ActionMapping mapping,
                  ActionForm form,
                  HttpServletRequest request,
                  HttpServletResponse response) throws Exception {

               String fchInicial = request.getParameter("fchInicial");
               String fchFinal = request.getParameter("fchFinal");
               int inicio = Integer.parseInt(request.getParameter("start"));
               int fin = Integer.parseInt(request.getParameter("limit"));

               HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
               Writer out = responseWrapper.getWriter();
               JSONObject jsonResponse = null;
               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

               try {
                    Date fInicial = dateFormat.parse(fchInicial);
                    Date fFinal = dateFormat.parse(fchFinal);
                    Map mapaDatos = new ReportesDelegate().obtenerReportePeriodo(inicio, fin, fInicial, fFinal);
                    jsonResponse = JSONObject.fromObject(mapaDatos);
               } catch(SQLException ex) {
                    ex.printStackTrace();
               } catch(Exception ex) { ex.printStackTrace(); }
               finally {
                    out.write(jsonResponse.toString());
                    out.close();
               }

               return null;
          }

          public ActionForward exportarReporteXls(ActionMapping mapping,
                  ActionForm form,
                  HttpServletRequest request,
                  HttpServletResponse response) throws Exception {

               String codigo = request.getParameter("codigo");
               String fechaInicial = request.getParameter("fechaInicial");
               String fechaFinal = request.getParameter("fechaFinal");

               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
               ReportesDelegate delegate = new ReportesDelegate();
               byte[] byteArray = null;

               // Reporte Diario
               if (codigo.equals("undefined") && fechaFinal.equals("undefined")) {
                    try {
                         Date fecha = dateFormat.parse(fechaInicial);

                         Map map = delegate.obtenerReporteDiario(0, 0, fecha);
                         List<Reporte> registros = (List<Reporte>) map.get("registros");
                         byteArray = delegate.generarReporteXls(registros);

                         response.setHeader("Content-disposition", "inline; filename=reporteDiario.xls");

                    } catch (SQLException e) {
                         e.printStackTrace();
                    } catch (JRException e) {
                         e.printStackTrace();
                    } catch (Exception e) {
                         e.printStackTrace();
                    }

                    // Reporte por Periodo
               } else if (codigo.equals("undefined") && !fechaFinal.equals("undefined")) {
                    try {
                         Date fInicial = dateFormat.parse(fechaInicial);
                         Date fFinal = dateFormat.parse(fechaFinal);

                         Map map = delegate.obtenerReportePeriodo(0, 0, fInicial, fFinal);
                         List<Reporte> registros = (List<Reporte>) map.get("registros");
                         byteArray = delegate.generarReporteXls(registros);

                         response.setHeader("Content-disposition", "inline; filename=reportePeriodo.xls");

                    } catch (SQLException e) {
                         e.printStackTrace();
                    } catch (JRException e) {
                         e.printStackTrace();
                    } catch (Exception e) {
                         e.printStackTrace();
                    }

                    // Reporte Individual
               } else {
                    try {
                         Date fInicial = dateFormat.parse(fechaInicial);
                         Date fFinal = dateFormat.parse(fechaFinal);

                         Map map = delegate.obtenerReporteIndividual(0, 0, codigo, fInicial, fFinal);
                         List<Reporte> registros = (List<Reporte>) map.get("registros");
                         byteArray = delegate.generarReporteXls(registros);

                         response.setHeader("Content-disposition", "inline; filename=reporteIndividual.xls");

                    } catch (SQLException e) {
                         e.printStackTrace();
                    } catch (JRException e) {
                         e.printStackTrace();
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }

               response.setHeader("Cache-control", "no-cache");
               response.setContentType("application/vnd.ms-excel");
               response.setContentLength(byteArray.length);
               ServletOutputStream outputStream = response.getOutputStream();
               outputStream.write(byteArray);
               outputStream.flush();
               outputStream.close();
               
               return null;
          }

}

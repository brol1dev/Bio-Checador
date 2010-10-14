package mx.gob.conocer.checador.struts.action;

import java.io.Writer;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import mx.gob.conocer.checador.delegate.EmpleadosDelegate;
import mx.gob.conocer.checador.model.Empleado;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author Ironbit
 */
public class EmpleadosAction extends DispatchAction {

     public ActionForward obtenerEmpleado(ActionMapping mapping,
             ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
             throws Exception {

          String codigo = request.getParameter("codigo");
          int razon = Integer.parseInt(request.getParameter("razon"));
          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          String jsonResponse = "{success:true, ";

          try {
               Map resultado = new EmpleadosDelegate().obtenerEmpleadoPorCodigo(codigo, razon);
               Empleado empleado = (Empleado) resultado.get("empleado");
               int usoStatus = (Integer) resultado.get("usoStatus");
               if (usoStatus >= 0) {
                    if (empleado != null) {
                         jsonResponse += "empleado:{id:'" + empleado.getIdRegistro() + "', ";
                         jsonResponse += "codigo:'" + empleado.getCodigo() + "', ";
                         jsonResponse += "nombre:'" + empleado.getNombre() + "', ";
                         jsonResponse += "numero:'" + empleado.getNumEmpleado() + "', ";
                         jsonResponse += "rfc:'" + empleado.getRfc() + "', ";
                         jsonResponse += "area:'" + empleado.getArea() + "', ";
                         jsonResponse += "razon:'" + razon + "'}} ";
                    } else {
                         throw new NullPointerException("Empleado inexistente. Intentelo de nuevo.");
                    }
               } else {
                    String errorStatus = obtenerMensajeStatus(usoStatus);
                    if (errorStatus == null) {
                         errorStatus = "Status desconocido";
                    }
                    jsonResponse += "empleado:{error:'" + errorStatus + "'}}";
               }
          } catch (NullPointerException ex) {
               jsonResponse += "empleado:{error:'" + ex.getMessage() + "'}}";
          } catch (SQLException ex) {
               jsonResponse += "empleado:{error:'Error en conexión con base de datos. Intentelo de nuevo más tarde.'}}";
               //ex.printStackTrace();
          } finally {
               out.write(jsonResponse);
               out.close();
          }

          return null;
     }

     public ActionForward registrarHora(ActionMapping mapping,
             ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
             throws Exception {

          int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
          int idStatus = Integer.parseInt(request.getParameter("idStatus"));

          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          String jsonResponse = null;

          try {
               Map resultado = new EmpleadosDelegate().registrarHora(idEmpleado, idStatus);
               Date date = (Date) resultado.get("fechaActual");
               int usoStatus = (Integer) resultado.get("usoStatus");
               if (usoStatus > 0) {
                    String falla = "Ya se ha registrado a este empleado con la razón solicitada. Intentelo de nuevo con una razón diferente.";
                    jsonResponse = "{success: false, error:'" + falla + "'} ";
               } else {
                    DateFormat fechaFormat = new SimpleDateFormat("dd MMMM, yyyy");
                    DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
                    String fecha = fechaFormat.format(date);
                    String hora = horaFormat.format(date);

                    String exito = "Registro realizado a las " + hora + " horas, con fecha del " + fecha;
                    jsonResponse = "{success: true, respuesta:'" + exito + "'} ";
               }
          } catch(SQLException ex) {
               //TODO: Registro fallido. ERROR SQL
               ex.printStackTrace();
          } finally {
               out.write(jsonResponse);
               out.close();
          }

          return null;
     }

     private String obtenerMensajeStatus(int status) {
          String msg = null;
          switch (status) {
               case -1:
                    msg = "El empleado solicitado no ha hecho su registro de entrada el día de hoy.";
                    break;
               case -3:
                    msg = "El empleado solicitado no ha hecho su registro de salida a comer el día de hoy.";
                    break;
          }
          return msg;

     }
}

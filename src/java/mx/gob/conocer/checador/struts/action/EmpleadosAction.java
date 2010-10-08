package mx.gob.conocer.checador.struts.action;

import java.io.Writer;
import java.sql.SQLException;
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
          Empleado empleado = null;
          
          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          String jsonResponse = "{success:true, ";

          try {
               empleado = new EmpleadosDelegate().obtenerEmpleadoPorCodigo(codigo);
               if (empleado != null) {
                    jsonResponse += "empleado:{id:'" + empleado.getIdRegistro() + "', ";
                    jsonResponse += "codigo:'" + empleado.getCodigo() + "', ";
                    jsonResponse += "nombre:'" + empleado.getNombre() + "', ";
                    jsonResponse += "numero:'" + empleado.getNumEmpleado() + "', ";
                    jsonResponse += "rfc:'" + empleado.getRfc() + "', ";
                    jsonResponse += "area:'" + empleado.getArea() + "'}}";
               } else {
                    throw new NullPointerException("Empleado inexistente. Intentelo de nuevo.");
               }
          } catch (NullPointerException ex) {
               jsonResponse += "empleado:{error:'" + ex.getMessage() + "'}}";
          } catch (SQLException ex) {
               jsonResponse += "empleado:{error:'Error en conexión con base de datos. Intentelo de nuevo más tarde.'}}";
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
               new EmpleadosDelegate().registrarHora(idEmpleado, idStatus);
               String exito = "Registro realizado!";
               jsonResponse = "{success: true, respuesta: '" + exito + "'}";
          } catch(SQLException ex) {
               //TODO: Registro fallido. ERROR SQL
               ex.getStackTrace();
          } finally {
               out.write(jsonResponse);
               out.close();
          }

          return null;
     }
}

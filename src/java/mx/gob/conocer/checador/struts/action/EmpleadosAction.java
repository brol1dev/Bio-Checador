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

          try {
               empleado = new EmpleadosDelegate().obtenerEmpleadoPorCodigo(codigo);
               if (empleado != null) {
                    out.write("{success:true, empleado:{id:'" + empleado.getNombre() + "'}}");
               } else {
                    throw new NullPointerException("Empleado inexistente. Intentelo de nuevo.");
               }
          } catch (NullPointerException ex) {
               System.out.println(ex.getMessage());
               out.write("{success:true, empleado:{id:'" + ex.getMessage() + "'}}");
          } catch (SQLException ex) {
               System.out.println("Error en conexión con base de datos: " + ex.getMessage());
          } finally {
               out.close();
          }

          return null;
     }
}

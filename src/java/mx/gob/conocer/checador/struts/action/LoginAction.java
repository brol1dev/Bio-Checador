/*
 * Esta clase ya no se usa.
 * Fue sustituida por Spring Security.
 */
package mx.gob.conocer.checador.struts.action;

import java.io.Writer;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import mx.gob.conocer.checador.delegate.UsuariosDelegate;
import mx.gob.conocer.checador.model.Usuario;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Ironbit
 */
public class LoginAction  extends Action {

     @Override
     public ActionForward execute (ActionMapping mapping, ActionForm form,
             HttpServletRequest request, 
             HttpServletResponse response) throws Exception {

          String nombre = request.getParameter("usuario");
          String password = request.getParameter("password");
          Usuario usuario = new Usuario();
          usuario.setUsuario(nombre);
          usuario.setPassword(password);

          String usuarioRespuesta = null;

          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();

          try {
               usuarioRespuesta = new UsuariosDelegate().obtenerUsuario(usuario);
               if (usuarioRespuesta == null) {
                    throw new NullPointerException("El nombre de usuario o contraseña es incorrecto. Favor de intentarlo de nuevo.");
               }
               out.write("{success: true}");
               out.close();
          } catch (NullPointerException ex) {
               out.write("{success: false, errors: {reason: '" + ex.getMessage() + "'}}");
               out.close();
          } catch (SQLException ex) {
               out.write("{success: false, errors: {reason: 'El servidor de base de datos es inalcanzable. Comuniquese con el administrador.'}}");
               out.close();
          }

          return mapping.findForward("index");
     }
}

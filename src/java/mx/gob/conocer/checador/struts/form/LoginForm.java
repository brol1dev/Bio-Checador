package mx.gob.conocer.checador.struts.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author Ironbit
 */
public class LoginForm extends ActionForm {

     private String usuario;
     private String password;

     public String getPassword() {
          return password;
     }

     public String getUsuario() {
          return usuario;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public void setUsuario(String usuario) {
          this.usuario = usuario;
     }

}

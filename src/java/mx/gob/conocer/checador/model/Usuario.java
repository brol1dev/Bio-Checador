/*
 * Esta clase ya no se usa.
 * Fue sustituida por Spring Security.
 */
package mx.gob.conocer.checador.model;

/**
 *
 * @author Ironbit
 */
public class Usuario {

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

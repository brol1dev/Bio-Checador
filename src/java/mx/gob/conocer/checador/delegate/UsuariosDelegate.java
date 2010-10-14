package mx.gob.conocer.checador.delegate;

import java.sql.SQLException;
import mx.gob.conocer.checador.dao.UsuariosDAO;
import mx.gob.conocer.checador.model.Usuario;

/**
 *
 * @author Ironbit
 */
public class UsuariosDelegate {

     public String obtenerUsuario(Usuario usuario) throws SQLException {
          return new UsuariosDAO().obtenerUsuario(usuario);
     }
}

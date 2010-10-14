package mx.gob.conocer.checador.struts.action;

import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import mx.gob.conocer.checador.delegate.StatusDelegate;
import mx.gob.conocer.checador.model.Status;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author Ironbit
 */
public class StatusAction extends DispatchAction {

     public ActionForward obtenerStatusParaCombo(ActionMapping mapping,
             ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
             throws Exception {

          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          List<Status> listStatus = null;
          String jsonResponse = null;

          try {
               listStatus = new StatusDelegate().obtenerStatus();
               int index = 1;
               jsonResponse = "{total:'" + listStatus.size() + "', registros:[";
               for (Status status : listStatus) {
                    jsonResponse += "{id:'" + status.getId() + "',";
                    jsonResponse += "status:'" + status.getStatus() + "'}";

                    if (index != listStatus.size()) {
                         jsonResponse += ",";
                    }
                    ++index;
               }
               jsonResponse += "]}";
          } catch (NullPointerException ex) {
               //TODO: Excepcion si la lista esta vacia
          } catch (SQLException ex) {
               //TODO: Excepcion si hay error en SQL
          } finally {
               out.write(jsonResponse);
               out.close();
          }
          
          return null;
     }

     public ActionForward obtenerStatusParaRadio(ActionMapping mapping,
             ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
             throws Exception {

          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          List<Status> listStatus = null;
          String jsonResponse = null;

          try {
               listStatus = new StatusDelegate().obtenerStatus();
               int index = 1;
               jsonResponse = "{xtype: 'radiogroup', id: 'rbgRazones', horizontal: true, columns: 2, width: 380, items: [";
               for (Status status : listStatus) {
                    jsonResponse += "{boxLabel:'" + status.getStatus() + "', name:'radios', ";
                    if (status.getId() == 1) {
                         jsonResponse += "checked: true, ";
                    }
                    jsonResponse += "inputValue: '" + status.getId() + "'}";

                    if (index != listStatus.size()) {
                         jsonResponse += ",";
                    }
                    ++index;
               }
               jsonResponse += "]}";
          } catch (NullPointerException ex) {
               //TODO: Excepcion si la lista esta vacia
          } catch (SQLException ex) {
               //TODO: Excepcion si hay error en SQL
          } finally {
               out.write(jsonResponse);
               out.close();
          }

          out.write(jsonResponse);
          out.close();
          return null;
     }
}

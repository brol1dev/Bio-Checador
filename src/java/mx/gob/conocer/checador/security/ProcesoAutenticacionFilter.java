package mx.gob.conocer.checador.security;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Ironbit
 */
public class ProcesoAutenticacionFilter extends UsernamePasswordAuthenticationFilter {

     @Override
     protected void successfulAuthentication(HttpServletRequest request,
             HttpServletResponse response, Authentication authResult)
             throws IOException, ServletException {
          SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
          this.setAuthenticationSuccessHandler(successHandler);
          successHandler.setRedirectStrategy(new RedirectStrategy() {

               @Override
               public void sendRedirect(HttpServletRequest hsr,
                       HttpServletResponse hsr1, String string) throws IOException {
                    // no redireccionar a ningun lugar.
                    // necesario para que regrese respuesta a login.
               }
          });

          super.successfulAuthentication(request, response, authResult);

          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          System.out.println("autenticado");
          out.write("{success:true}");
          out.close();
     }


     @Override
     protected void unsuccessfulAuthentication(HttpServletRequest request,
             HttpServletResponse response, AuthenticationException failed)
             throws IOException, ServletException {
          SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
          this.setAuthenticationFailureHandler(failureHandler);
          failureHandler.setRedirectStrategy(new RedirectStrategy() {

               @Override
               public void sendRedirect(HttpServletRequest hsr,
                       HttpServletResponse hsr1, String string) throws IOException {
                    // no redireccionar a ningun lugar.
                    // necesario para que regrese respuesta a login.
               }
          });
          super.unsuccessfulAuthentication(request, response, failed);
          HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
          Writer out = responseWrapper.getWriter();
          System.out.println("fallo en login");
          out.write("{success:false, errors: {reason:" +failed.getMessage() + "}}");
          out.close();
     }
}

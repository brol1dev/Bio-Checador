package mx.gob.conocer.checador.model;

/**
 *
 * @author Ironbit
 */
public class Empleado {

     private int idRegistro;
     private String codigo;
     private int numEmpleado;
     private String nombre;
     private String rfc;
     private String area;

     public String getArea() {
          return area;
     }

     public String getCodigo() {
          return codigo;
     }

     public int getIdRegistro() {
          return idRegistro;
     }

     public String getNombre() {
          return nombre;
     }

     public int getNumEmpleado() {
          return numEmpleado;
     }

     public String getRfc() {
          return rfc;
     }

     public void setArea(String area) {
          this.area = area;
     }

     public void setCodigo(String codigo) {
          this.codigo = codigo;
     }

     public void setIdRegistro(int idRegistro) {
          this.idRegistro = idRegistro;
     }

     public void setNombre(String nombre) {
          this.nombre = nombre;
     }

     public void setNumEmpleado(int numEmpleado) {
          this.numEmpleado = numEmpleado;
     }

     public void setRfc(String rfc) {
          this.rfc = rfc;
     }

}

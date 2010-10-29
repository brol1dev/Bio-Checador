package mx.gob.conocer.checador.model;

/**
 *
 * @author Ironbit
 */
public class Reporte {

     private int idRegistro;
     private String codigo;
     private String nombreEmpleado;
     private String fecha;
     private String entrada;
     private String salidaComer;
     private String entradaComer;
     private String salida;
     private String salidaExt;
     private String observacion;

     public int getIdRegistro() {
          return idRegistro;
     }

     public void setIdRegistro(int idRegistro) {
          this.idRegistro = idRegistro;
     }

     public String getCodigo() {
          return codigo;
     }

     public String getEntrada() {
          return entrada;
     }

     public String getEntradaComer() {
          return entradaComer;
     }

     public String getFecha() {
          return fecha;
     }

     public String getNombreEmpleado() {
          return nombreEmpleado;
     }

     public String getSalida() {
          return salida;
     }

     public String getSalidaComer() {
          return salidaComer;
     }

     public void setCodigo(String codigo) {
          this.codigo = codigo;
     }

     public void setEntrada(String entrada) {
          this.entrada = entrada;
     }

     public void setEntradaComer(String entradaComer) {
          this.entradaComer = entradaComer;
     }

     public void setFecha(String fecha) {
          this.fecha = fecha;
     }

     public void setNombreEmpleado(String nombreEmpleado) {
          this.nombreEmpleado = nombreEmpleado;
     }

     public void setSalida(String salida) {
          this.salida = salida;
     }

     public void setSalidaComer(String salidaComer) {
          this.salidaComer = salidaComer;
     }

     public String getObservacion() {
          return observacion;
     }

     public String getSalidaExt() {
          return salidaExt;
     }

     public void setObservacion(String observacion) {
          this.observacion = observacion;
     }

     public void setSalidaExt(String salidaExt) {
          this.salidaExt = salidaExt;
     }
}

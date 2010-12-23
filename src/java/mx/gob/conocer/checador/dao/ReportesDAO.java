package mx.gob.conocer.checador.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.conocer.checador.model.Reporte;
import mx.gob.conocer.checador.util.Conexion;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author Ironbit
 */
public class ReportesDAO {

     private final static String SP_REPORTE_INDIVIDUAL = "{ call SpObtenerReporteIndividual(?, ?, ?, ?, ?, ?) }";
     private final static String SP_REPORTE_DIARIO = "{ call PaObtenerReporteDiario(?, ?, ?, ?) }";
     private final static String SP_REPORTE_PERIODO = "{ call PaObtenerReportePeriodo(?, ?, ?, ?, ?) }";
     private Conexion conexion;
     private Connection conn;
     private CallableStatement proc;

     public Map obtenerReporteIndividual(int inicio, int fin,
             String codigo, Date fchInicio, Date fchFinal)
             throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_REPORTE_INDIVIDUAL);
          proc.setInt("inicio", inicio);
          proc.setInt("fin", fin);
          proc.setString("codigo", codigo);
          proc.setDate("fchInicial", new java.sql.Date(fchInicio.getTime()));
          proc.setDate("fchFinal", new java.sql.Date(fchFinal.getTime()));
          proc.registerOutParameter("totalRegistros", java.sql.Types.INTEGER);
          
          Map datos = new HashMap();
          List<Reporte> registros = new ArrayList<Reporte>();
          
          ResultSet rs = proc.executeQuery();
          while (rs.next()) {
               String[] aHoras = null;
               Reporte reporte = new Reporte();
               reporte.setIdRegistro(rs.getInt(1));
               reporte.setCodigo(rs.getString(2));
               reporte.setNombreEmpleado(WordUtils.capitalizeFully(rs.getString(3)));
               reporte.setFecha(invertirFecha(rs.getString(4)));
               
               if (rs.getString(5) != null) {
                    aHoras = rs.getString(5).split("\\.");
                    reporte.setEntrada(aHoras[0]);
               }

               if (rs.getString(6) != null) {
                    aHoras = rs.getString(6).split("\\.");
                    reporte.setSalidaComer(aHoras[0]);
               }

               if (rs.getString(7) != null) {
                    aHoras = rs.getString(7).split("\\.");
                    reporte.setEntradaComer(aHoras[0]);
               }

               if (rs.getString(8) != null) {
                    aHoras = rs.getString(8).split("\\.");
                    reporte.setSalida(aHoras[0]);
               }

               if (rs.getString(9) != null) {
                    aHoras = rs.getString(9).split("\\.");
                    reporte.setSalidaExt(aHoras[0]);
               }

               if (rs.getString(10) != null) {
                    reporte.setObservacion(rs.getString(10));
               }

               registros.add(reporte);
          }
          rs.close();
          
          Integer totalRegistros = proc.getInt("totalRegistros");
          conexion.cierraConexion(conn);

          datos.put("registros", registros);
          datos.put("totalRegistros", totalRegistros);

          return datos;
     }

     public Map obtenerReporteDiario(int inicio, int fin,
             Date fecha) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_REPORTE_DIARIO);
          proc.setInt("inicio", inicio);
          proc.setInt("fin", fin);
          proc.setDate("fecha", new java.sql.Date(fecha.getTime()));
          proc.registerOutParameter("totalRegistros", java.sql.Types.INTEGER);

          Map datos = new HashMap();
          List<Reporte> registros = new ArrayList<Reporte>();

          ResultSet rs = proc.executeQuery();
          while (rs.next()) {
               String[] aHoras = null;
               Reporte reporte = new Reporte();
               reporte.setIdRegistro(rs.getInt(1));
               reporte.setCodigo(rs.getString(2));
               reporte.setNombreEmpleado(WordUtils.capitalizeFully(rs.getString(3)));
               reporte.setFecha(invertirFecha(rs.getString(4)));

               if (rs.getString(5) != null) {
                    aHoras = rs.getString(5).split("\\.");
                    reporte.setEntrada(aHoras[0]);
               }

               if (rs.getString(6) != null) {
                    aHoras = rs.getString(6).split("\\.");
                    reporte.setSalidaComer(aHoras[0]);
               }

               if (rs.getString(7) != null) {
                    aHoras = rs.getString(7).split("\\.");
                    reporte.setEntradaComer(aHoras[0]);
               }

               if (rs.getString(8) != null) {
                    aHoras = rs.getString(8).split("\\.");
                    reporte.setSalida(aHoras[0]);
               }

               if (rs.getString(9) != null) {
                    aHoras = rs.getString(9).split("\\.");
                    reporte.setSalidaExt(aHoras[0]);
               }

               if (rs.getString(10) != null) {
                    reporte.setObservacion(rs.getString(10));
               }

               registros.add(reporte);
          }
          rs.close();

          Integer totalRegistros = proc.getInt("totalRegistros");
          conexion.cierraConexion(conn);

          datos.put("registros", registros);
          datos.put("totalRegistros", totalRegistros);

          return datos;
     }

     public Map obtenerReportePeriodo(int inicio, int fin,
             Date fchInicial, Date fchFinal) throws SQLException {

          conexion = new Conexion();
          conn = conexion.AbreConexion();
          proc = conn.prepareCall(SP_REPORTE_PERIODO);
          proc.setInt("inicio", inicio);
          proc.setInt("fin", fin);
          proc.setDate("fchInicial", new java.sql.Date(fchInicial.getTime()));
          proc.setDate("fchFinal", new java.sql.Date(fchFinal.getTime()));
          proc.registerOutParameter("totalRegistros", java.sql.Types.INTEGER);

          Map datos = new HashMap();
          List<Reporte> registros = new ArrayList<Reporte>();

          ResultSet rs = proc.executeQuery();
          while (rs.next()) {
               String[] aHoras = null;
               Reporte reporte = new Reporte();
               reporte.setIdRegistro(rs.getInt(1));
               reporte.setCodigo(rs.getString(2));
               reporte.setNombreEmpleado(WordUtils.capitalizeFully(rs.getString(3)));
               reporte.setFecha(invertirFecha(rs.getString(4)));

               if (rs.getString(5) != null) {
                    aHoras = rs.getString(5).split("\\.");
                    reporte.setEntrada(aHoras[0]);
               }

               if (rs.getString(6) != null) {
                    aHoras = rs.getString(6).split("\\.");
                    reporte.setSalidaComer(aHoras[0]);
               }

               if (rs.getString(7) != null) {
                    aHoras = rs.getString(7).split("\\.");
                    reporte.setEntradaComer(aHoras[0]);
               }

               if (rs.getString(8) != null) {
                    aHoras = rs.getString(8).split("\\.");
                    reporte.setSalida(aHoras[0]);
               }

               if (rs.getString(9) != null) {
                    aHoras = rs.getString(9).split("\\.");
                    reporte.setSalidaExt(aHoras[0]);
               }

               if (rs.getString(10) != null) {
                    reporte.setObservacion(rs.getString(10));
               }

               registros.add(reporte);
          }
          rs.close();

          Integer totalRegistros = proc.getInt("totalRegistros");
          conexion.cierraConexion(conn);

          datos.put("registros", registros);
          datos.put("totalRegistros", totalRegistros);

          return datos;
     }

     private String invertirFecha(String fecha) {
          String fechaInversa = "";

          String[] aInversa = fecha.split("-");

          for (int i = aInversa.length - 1; i >= 0; --i) {
               if (i != 0) {
                    fechaInversa += aInversa[i] + "/";
               } else {
                    fechaInversa += aInversa[i];
               }
          }

          return fechaInversa;
     }
}

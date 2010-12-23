/*
 * Clase usada en la página administrativa de reportes.
 * Muestra un panel principal conteniendo una tabla para mostrar los datos
 * del reporte, así como otro panel de pestañas que muestra
 * todos los tipos de reporte que se pueden generar.
 */
Ext.onReady(function() {
     Ext.QuickTips.init();

     var pageSize = 25;

     /* La variable datosReporte se usa para enviar los datos de un reporte
         * al servidor y que pueda ser exportado a Excel. */
     var datosReporte = new Array();


     /* Si los campos del panel de reporte individual no están vaciós,
      * habilita el botón para generar el reporte. */
     function validarPnlIndividual(codigo, fechaI, fechaF) {
          if (codigo == '' || fechaI == '' || fechaF == '') {
               Ext.getCmp('btnGenerarRI').disable();
          } else if (codigo != '' && fechaI != '' && fechaF != '') {
               Ext.getCmp('btnGenerarRI').enable();
          }
     }

     /* Si los campos del panel de reporte diario no están vaciós,
      * habilita el botón para generar el reporte. */
     function validarPnlDiario(fecha) {
          if (fecha == '') {
               Ext.getCmp('btnGenerarRD').disable();
          } else if (fecha != '') {
               Ext.getCmp('btnGenerarRD').enable();
          }
     }

     /* Si los campos del panel de reporte por periodo no están vaciós,
      * habilita el botón para generar el reporte. */
     function validarPnlPeriodo(fechaI, fechaF) {
          if (fechaI == '' || fechaF == '') {
               Ext.getCmp('btnGenerarRP').disable();
          } else if (fechaI != '' && fechaF != '') {
               Ext.getCmp('btnGenerarRP').enable();
          }
     }

     /* Manda los parametros necesarios al store del grdReporte, se procesa
      * la información en el servidor, en el metodo o función indicado en el parámetro,
      *  para generar el reporte y se muestra en la tabla.*/
     function generarReporte(codigo, fechaI, fechaF, metodo) {
          strReportes.setBaseParam('method', metodo);
          strReportes.setBaseParam('codigo', codigo);
          strReportes.setBaseParam('fchInicial', fechaI);
          strReportes.setBaseParam('fchFinal', fechaF);
          strReportes.load();
          llenarArrayParaExcel(codigo, fechaI, fechaF);
     }

     /* Llena el array datosReporte que se usa en el servidor para generar
         * el el excel del reporte que se visualiza en la tabla.
         *
         * datosReporte[0] = codigo del empleado
         * datosReporte[1] = fecha inicial para filtrado
         * datosReporte[2] = fecha final de filtrado
         *
         * Cuando todos las 3 posiciones tienen datos, se generará un reporte individual
         * Cuando el código es nulo y las fechas son válidas, se genera un reporte por periodo
         * Cuando sólo se envía la fecha inicial (datosReporte[1]) se generará un reporte diario con esa fecha */
     function llenarArrayParaExcel(codigo, fechaI, fechaF) {
          datosReporte[0] = codigo;
          datosReporte[1] = fechaI;
          datosReporte[2] = fechaF;
     }

     /*
      * Panel para generar un reporte individual. Contiene un textfield para
      * ingresar el código del empleado y otros dos para buscar entre fechas
      */
     var pnlIndividual = new Ext.FormPanel({
          title: 'Informe Individual',
          frame: true,
          height: 80,
          items: [{
               layout: 'column',
               defaults: {
                    columnWidth: 0.25,
                    layout: 'form',
                    xtype: 'panel'
               },
               labelAlign: 'right',
               items: [{
                    defaults: {anchor: '100%'},
                    items: [{
                         xtype: 'textfield',
                         id: 'txtCodigo',
                         maxLength: 8,
                         fieldLabel: 'Código',
                         labelAlign: 'right',
                         allowBlank: false,
                         autoCreate: {tag: 'input', type: 'text', maxLength: 8},
                         enableKeyEvents: true,
                         listeners: {
                              'keyup': function() {
                                   var codigo = this.getValue();
                                   var fechaI = Ext.getCmp('fchInicial').getValue();
                                   var fechaF = Ext.getCmp('fchFinal').getValue();
                                   validarPnlIndividual(codigo, fechaI, fechaF);
                              }
                         }
                    }]
               }, {
                    defaults: {anchor: '100%', format: 'd/m/Y', editable: false},
                    items: [{
                         xtype: 'datefield',
                         id: 'fchInicial',
                         fieldLabel: 'Fecha Inicial',
                         allowBlank: false,
                         listeners: {
                              'select': function() {
                                   var codigo = Ext.getCmp('txtCodigo').getValue();
                                   var fechaI = this.getValue();
                                   var fechaF = Ext.getCmp('fchFinal').getValue();
                                   validarPnlIndividual(codigo, fechaI, fechaF);
                              }
                         }
                    }]
               }, {
                    defaults: {anchor: '100%', format: 'd/m/Y', editable: false},
                    items: [{
                         xtype: 'datefield',
                         id: 'fchFinal',
                         fieldLabel: 'Fecha Final',
                         allowBlank: false,
                         listeners: {
                              'select': function() {
                                   var codigo = Ext.getCmp('txtCodigo').getValue();
                                   var fechaI = Ext.getCmp('fchInicial').getValue();
                                   var fechaF = this.getValue();
                                   validarPnlIndividual(codigo, fechaI, fechaF);
                              }
                         }
                    }]
               }, {
                    buttonAlign: 'right',
                    items: [{
                         xtype: 'button',
                         id: 'btnGenerarRI',
                         disabled: true,
                         text: 'Generar Reporte',
                         width: 80,
                         handler: function() {
                              var codigo = Ext.getCmp('txtCodigo').getValue();
                              var fechaI = Ext.getCmp('fchInicial').getRawValue();
                              var fechaF = Ext.getCmp('fchFinal').getRawValue();
                              if (codigo == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese un código de empleado.');
                              } else if (fechaI == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Inicial para poder generar el reporte.');
                              } else if (fechaF == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Final para poder generar el reporte.');
                              } else {
                                   var metodo = 'generarReporteIndividual';
                                   generarReporte(codigo, fechaI, fechaF, metodo);
                              }
                         }
                    }]
               }]
          }]
     });

     /*
      * Panel para generar un reporte diario. Contiene un textfield para
      * ingresar la fecha de la que se requiere el reporte.
      */
     var pnlDiario = new Ext.FormPanel({
          title: 'Informe Diario',
          frame: true,
          height: 80,
          items: [{
               layout: 'column',
               defaults: {
                    columnWidth: 0.50,
                    layout: 'form',
                    xtype: 'panel'
               },
               labelAlign: 'right',
               items: [{
                    defaults: {anchor: '100%', format: 'd/m/Y', editable: false},
                    items: [{
                         xtype: 'datefield',
                         id: 'fechaRD',
                         fieldLabel: 'Fecha',
                         allowBlank: false,
                         listeners: {
                              'select': function() {
                                   var fecha = this.getValue();
                                   validarPnlDiario(fecha);
                              }
                         }
                    }]
               }, {
                    buttonAlign: 'right',
                    items: [{
                         xtype: 'button',
                         id: 'btnGenerarRD',
                         disabled: true,
                         text: 'Generar Reporte',
                         width: 80,
                         handler: function() {
                              var fecha = Ext.getCmp('fechaRD').getRawValue();
                              if (fecha == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese un una fecha para el reporte.');
                              } else {
                                   var metodo = 'generarReporteDiario';
                                   generarReporte(undefined, fecha, undefined, metodo);
                              }
                         }
                    }]
               }]
          }]
     });

     /*
      * Panel para generar un reporte por periodo. Contiene dos textfield para
      * ingresar las fechas entre las que se requiere el reporte.
      */
     var pnlPeriodo = new Ext.FormPanel({
          title: 'Informe de Período',
          frame: true,
          height: 80,
          items: [{
               layout: 'column',
               defaults: {
                    columnWidth: 0.33,
                    layout: 'form',
                    xtype: 'panel'
               },
               labelAlign: 'right',
               items: [{
                    defaults: {anchor: '100%', format: 'd/m/Y', editable: false},
                    items: [{
                         xtype: 'datefield',
                         id: 'fchInicialRP',
                         fieldLabel: 'Fecha Inicial',
                         allowBlank: false,
                         listeners: {
                              'select': function() {
                                   var fechaI = this.getValue();
                                   var fechaF = Ext.getCmp('fchFinalRP').getValue();
                                   validarPnlPeriodo(fechaI, fechaF);
                              }
                         }
                    }]
               }, {
                    defaults: {anchor: '100%', format: 'd/m/Y', editable: false},
                    items: [{
                         xtype: 'datefield',
                         id: 'fchFinalRP',
                         fieldLabel: 'Fecha Final',
                         allowBlank: false,
                         listeners: {
                              'select': function() {
                                   var fechaI = Ext.getCmp('fchInicialRP').getValue();
                                   var fechaF = this.getValue();
                                   validarPnlPeriodo(fechaI, fechaF);
                              }
                         }
                    }]
               }, {
                    buttonAlign: 'right',
                    items: [{
                         xtype: 'button',
                         id: 'btnGenerarRP',
                         disabled: true,
                         text: 'Generar Reporte',
                         width: 80,
                         handler: function() {
                              var fechaI = Ext.getCmp('fchInicialRP').getRawValue();
                              var fechaF = Ext.getCmp('fchFinalRP').getRawValue();
                              if (fechaI == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Inicial para poder generar el reporte.');
                              } else if (fechaF == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Final para poder generar el reporte.');
                              } else {
                                   var metodo = 'generarReportePeriodo';
                                   generarReporte(undefined, fechaI, fechaF, metodo);
                              }
                         }
                    }]
               }]
          }]
     });

     /* Panel de pestañas que contiene los tres tipos de reportes. */
     var pnlBusquedas = new Ext.TabPanel({
          region: 'north',
          height: 80,
          activeTab: 0,
          items: [pnlIndividual, pnlDiario, pnlPeriodo]
     });

     /* Store usado para llenar la tabla de grdReporte */
     var strReportes = new Ext.data.JsonStore({
          url: 'obtenerReportes.do',
          baseParams: {
               method: '',
               codigo: '',
               fchInicial: '',
               fchFinal: '',
               start: 0,
               limit: pageSize
          },
          fields: ['codigo', 'nombreEmpleado', 'fecha', 'entrada', 'salidaComer', 'entradaComer', 'salida', 'salidaExt', 'observacion'],
          root: 'registros',
          method: 'POST',
          totalProperty: 'totalRegistros',
          listeners: {
               'load': function() {
                    if (this.getCount() > 0) {
                         Ext.getCmp('btnImprimir').enable();
                         btnExportar.enable();
                    } else if (this.getCount() == 0) {
                         Ext.getCmp('btnImprimir').disable();
                         btnExportar.disable();
                    }
               }
          }
     });

     /* Objeto para el paginado de la tabla grdReporte */
     var pagGrid = new Ext.PagingToolbar
     ({
          xtype : 'paging',
          pageSize: pageSize,
          store: strReportes,
          displayInfo: true,
          displayMsg: 'Registros {0} - {1} of {2}',
          emptyMsg: 'No Existen Registros',
          firstText: 'Primera Página',
          lastText: 'Última Página',
          nextText: 'Siguiente Página',
          prevText: 'Página Previa',
          refreshText: 'Refrescar tabla',
          beforePageText: 'Página',
          afterPageText: 'de {0}'
     });

     /* Exporta el reporte de la tabla grdReporte a Excel.
      * Manda al servidor los datos del array datosReporte*/
     var btnExportar = new Ext.Button({
          text: 'Exportar a Excel',
          disabled: true,
          handler: function() {
               var location = "/checador/admin/obtenerReportes.do?method=exportarReporteXls" +
                    "&codigo=" + datosReporte[0] +
                    "&fechaInicial=" + datosReporte[1] +
                    "&fechaFinal=" + datosReporte[2];
               //Ext.Msg.alert("location", location);
               //var strFeatures = "width=600,height=600,resizable=yes,toolbar=no, menubar=no, scrollbars=no, help=no";
               //var reporte = window.open("","_blank", strFeatures);
               //reporte.location.href= location;
               window.location = location;
               //reporte.document.close();
               //reporte.close();
          }
     });

     /* Tabla en donde se muestran los datos del reporte buscado. */
     var grdReporte = new Ext.grid.GridPanel({
          title: 'Reporte',
          region: 'center',
          height: 300,
          width: 800,
          store: strReportes,
          loadMask: {msg: 'Cargando Registros...'},
          stripeRows: true,
          tbar: [
               /* Hace uso del plugin GridPrinter
                * que ya tiene la funcionalidad para imprimir grids de ExtJS */
               {
               text: 'Imprimir',
               id: 'btnImprimir',
               disabled: true,
               handler: function() {
                    Ext.ux.GridPrinter.stylesheetPath = '/checador/css/grid-printer.css';
                    Ext.ux.GridPrinter.print(grdReporte);
               }
          }, btnExportar],
          bbar: pagGrid,
          columns: [{
               id: 'codigo',
               width: 70,
               header: 'Código',
               sortable: true,
               dataIndex: 'codigo'
          }, {
               id: 'nombreEmpleado',
               header: 'Nombre',
               sortable: true,
               dataIndex: 'nombreEmpleado'
          }, {
               id: 'fecha',
               width: 70,
               header: 'Fecha',
               sortable: true,
               dataIndex: 'fecha'
          }, {
               id: 'hrEntrada',
               header: 'Entrada',
               sortable: true,
               dataIndex: 'entrada'
          }, {
               id: 'hrSalidaComer',
               header: 'Salida a Comer',
               sortable: true,
               dataIndex: 'salidaComer'
          }, {
               id: 'hrEntradaComer',
               header: 'Entrada de Comer',
               sortable: true,
               dataIndex: 'entradaComer'
          }, {
               id: 'hrSalida',
               header: 'Salida',
               sortable: true,
               dataIndex: 'salida'
          }, {
               id: 'hrSalidaExt',
               header: 'Salida Especial',
               sortable: true,
               dataIndex: 'salidaExt'
          }, {
               id: 'observacion',
               header: 'Observación',
               sortable: true,
               dataIndex: 'observacion'
          }],
          autoExpandColumn: 'nombreEmpleado'
     });

     /*Panel que contiene todos los elementos. */
     var pnlReportes = new Ext.Panel({
          title: 'Reportes',
          layout: 'border',
          width: 800,
          height: 600,
          layoutConfig: {
               columns: 1
          },
          items: [pnlBusquedas, grdReporte]
     });
     pnlReportes.render('panelReportes');
});


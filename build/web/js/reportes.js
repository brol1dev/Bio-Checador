Ext.onReady(function() {
     Ext.QuickTips.init();

     var pageSize = 25;
     var datosRI = new Array();

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
                                   if (codigo == '' || fechaI == '' || fechaF == '') {
                                        Ext.getCmp('btnGenerarRI').disable();
                                   } else if (codigo != '' && fechaI != '' && fechaF != '') {
                                        Ext.getCmp('btnGenerarRI').enable();
                                   }
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
                                   if (codigo == '' || fechaI == '' || fechaF == '') {
                                        Ext.getCmp('btnGenerarRI').disable();
                                   } else if (codigo != '' && fechaI != '' && fechaF != '') {
                                        Ext.getCmp('btnGenerarRI').enable();
                                   }
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
                                   if (codigo == '' || fechaI == '' || fechaF == '') {
                                        Ext.getCmp('btnGenerarRI').disable();
                                   } else if (codigo != '' && fechaI != '' && fechaF != '') {
                                        Ext.getCmp('btnGenerarRI').enable();
                                   }
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
                              var fechaI = Ext.getCmp('fchInicial').getValue();
                              var fechaF = Ext.getCmp('fchFinal').getValue();
                              if (codigo == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese un código de empleado.');
                              } else if (fechaI == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Inicial para poder generar el reporte.');
                              } else if (fechaF == '') {
                                   Ext.Msg.alert('Error', 'Por favor ingrese una Fecha Final para poder generar el reporte.');
                              } else {
                                   strReportes.setBaseParam('method', 'generarReporteIndividual');
                                   strReportes.setBaseParam('codigo', codigo);
                                   strReportes.setBaseParam('fchInicial', fechaI);
                                   strReportes.setBaseParam('fchFinal', fechaF);
                                   strReportes.load();
                                   datosRI[0] = codigo;
                                   datosRI[1] = fechaI;
                                   datosRI[2] = fechaF;
                              }
                         }
                    }]
               }]
          }]
     });

     var pnlBusquedas = new Ext.TabPanel({
          region: 'north',
          height: 80,
          activeTab: 0,
          items: [pnlIndividual]
     });

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

     var btnExportar = new Ext.Button({
          text: 'Exportar a Excel',
          disabled: true,
          handler: function() {
               var strFeatures = "width=600,height=600,resizable=yes,toolbar=no, menubar=no, scrollbars=no, help=no";
               var reporte = window.open("","_blank", strFeatures);
               reporte.location.href="/bio-checador/admin/obtenerReportes.do?method=exportarRIndividualXls" + 
                    "&codigo=" + datosRI[0] +
                    "&fechaInicial=" + datosRI[1] +
                    "&fechaFinal=" + datosRI[2];
          }
     });

     var grdReporte = new Ext.grid.GridPanel({
          title: 'Reporte',
          region: 'center',
          height: 300,
          width: 800,
          store: strReportes,
          loadMask: {msg: 'Cargando Registros...'},
          stripeRows: true,
          tbar: [{
               text: 'Imprimir',
               id: 'btnImprimir',
               disabled: true,
               handler: function() {
                    Ext.ux.GridPrinter.stylesheetPath = '/bio-checador/css/grid-printer.css';
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


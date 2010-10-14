Ext.onReady(function() {
     Ext.QuickTips.init();

     var txtID = new Ext.form.TextField({
          id: 'id',
          fieldLabel: 'Código',
          name: 'id',
          allowBlank: true,
          style: 'margin-bottom: 10px'
     });

     var pnlRegistro = new Ext.FormPanel({
          title: 'Ingrese el código del empleado',
          url: 'empleados.do?method=obtenerEmpleado',
          defaultType: 'textfield',
          labelWidth: 45,
          bodyStyle: 'padding: 10px 5px',
          frame: true,
          defaults: {
               width: 315
          },
          items: [txtID, {
               xtype: 'fieldset',
               title: 'Razón de Registro',
               bodyStyle: 'margin-left: -15px',
               width: 365,
               defaults: {
                    anchor: '1'
               },
               plugins: new Ext.ux.Plugin.LiteRemoteComponent({
                    url: 'status.do?method=obtenerStatusParaRadio'
               })
               //items: [rbgRazones] // Se llenan los items desde el StatusAction.java
          }],
          buttonAlign: 'center',
          buttons: [{
               text: 'Buscar',
               style: 'margin-bottom:5px',
               handler: function() {
                    var status = Ext.getCmp('rbgRazones').getValue().getRawValue();
                    Ext.Ajax.request({
                    url: 'empleados.do?method=obtenerEmpleado',
                    method: 'POST',
                    params: {
                        codigo: txtID.getValue(),
                        razon: status
                    },
                    success: function(result, request) {
                         var jsonData = Ext.util.JSON.decode(result.responseText);
                         var empleado = jsonData.empleado;
                         if (empleado.error == null) {
                              mostrarYLlenarRegistro(empleado);
                         } else {
                              Ext.Msg.alert('Error', empleado.error);
                         }
                    },
                    failure: function(result, request) {
                         Ext.Msg.alert('Error', 'El servidor es inalcanzable. Ponganse en contacto con el administrador.');
                    }
                    });
               }
          }]
     });

     var winRegistro = new Ext.Window({
        layout:'fit',
        width:400,
        height:230,
        closable: false,
        resizable: false,
        draggable: false,
        plain: true,
        border: false,
        items: [pnlRegistro]
     });
     winRegistro.show();

     var txtNombre = new Ext.form.TextField({
          fieldLabel: 'Nombre',
          name: 'nombre',
          disabled: true,
          disabledClass: 'disabled-txt'
     });
     var txtNumEmpleado = new Ext.form.TextField({
          fieldLabel: 'Número de Empleado',
          name: 'numero',
          disabled: true,
          disabledClass: 'disabled-txt'
     });
     var txtRfc = new Ext.form.TextField({
          fieldLabel: 'RFC',
          name: 'rfc',
          disabled: true,
          disabledClass: 'disabled-txt'
     });
     var txtCodigo = new Ext.form.TextField({
          fieldLabel: 'Código',
          name: 'codigo',
          disabled: true,
          disabledClass: 'disabled-txt'
     });
     var txtArea = new Ext.form.TextField({
          fieldLabel: 'Área',
          name: 'area',
          disabled: true,
          disabledClass: 'disabled-txt',
          grow: true
     });
     var txtIdEmpleado = new Ext.form.TextField({
          fieldLabel: 'ID',
          name: 'id',
          disabled: true,
          disabledClass: 'disabled-txt'
     });
     var txtStatus = new Ext.form.TextField({
          name: 'status',
          disabled: true,
          hidden: true,
          disabledClass: 'disabled-txt'
     });

     /*var strStatus = new Ext.data.JsonStore({
          url: 'status.do?method=obtenerStatus',
          idProperty: 'id',
          root: 'registros',
          totalProperty: 'total',
          fields: ['id', 'status']
     });

     var cbxStatus = new Ext.form.ComboBox({
          store: strStatus,
          fieldLabel: 'Razón de Registro',
          displayField: 'status',
          valueField: 'id',
          triggerAction: 'all',
          mode: 'local',
          typeAhead: true,
          emptyText: 'Selecciona un Status...',
          selectOnFocus: true,
          listeners: {
               'select': function (combo, value, index) {
                    btnRegistrar.enable();
               },
               'blur': function(field) {
                    if (field.getValue() == '') {
                         btnRegistrar.disable();
                    } else {
                         btnRegistrar.enable();
                    }
               }
          }
     });*/

     var btnRegistrar = new Ext.Button({
          text: 'Registrar Hora',
          width: 100,
          style: 'margin-bottom: 5px',
          handler: function() {
               Ext.Ajax.request({
                    url: 'empleados.do?method=registrarHora',
                    method: 'POST',
                    params: {
                        idEmpleado: txtIdEmpleado.getValue(),
                        idStatus: txtStatus.getValue()
                    },
                    success: function(result, request) {
                         var jsonData = Ext.util.JSON.decode(result.responseText);
                         if (jsonData.error == null) {
                              var respuesta = jsonData.respuesta;
                              setTimeout(function() {
                                   msgExito.hide();
                                   mostrarPanelCodigo();
                              }, 5000);
                              var msgExito = Ext.Msg.alert('Registro Exitoso', respuesta, function(btn, text) {
                                   mostrarPanelCodigo();
                              });
                         } else {
                              Ext.Msg.alert('Registro Duplicado', jsonData.error, function(btn, text) {
                                   mostrarPanelCodigoSinReset();
                              });
                         }
                    },
                    failure: function(result, request) {
                         Ext.Msg.alert('Error', 'El servidor es inalcanzable. Ponganse en contacto con el administrador.');
                    }
               });
          }
     });

     var pnlInformacion = new Ext.Panel({
          title: 'Datos del Empleado',
          layout: 'form',
          //width: 700,
          hidden: true,  // inicia escondido pero aparece al buscar un empleado
          defaultType: 'textfield',
          labelWidth: 130,
          labelAlign: 'right',
          bodyStyle: 'padding: 10px 5px',
          frame: true,
          defaults: {
               width: 285
          },
          items: [txtNombre, txtNumEmpleado, txtRfc, txtCodigo, txtArea],
          buttonAlign: 'right',
          buttons: [btnRegistrar, {
               text: 'Cancelar',
               style: 'margin-bottom: 5px',
               width: 100,
               handler: function() {
                    mostrarPanelCodigo();
               }
          }]
     });
     pnlInformacion.render('panel');

     function mostrarYLlenarRegistro(empleado) {
          winRegistro.hide();
          pnlInformacion.show();
          txtNombre.setValue(empleado.nombre);
          txtNumEmpleado.setValue(empleado.numero);
          txtRfc.setValue(empleado.rfc);
          txtCodigo.setValue(empleado.codigo);
          txtArea.setValue(empleado.area);
          txtIdEmpleado.setValue(empleado.id);
          txtStatus.setValue(empleado.razon);
     }

     function mostrarPanelCodigo() {
          pnlInformacion.hide();
          winRegistro.show();
          txtID.reset();
     }

     function mostrarPanelCodigoSinReset() {
          pnlInformacion.hide();
          winRegistro.show();
     }
});


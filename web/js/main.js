/*
 * Clase usada en el index o página inicial del sistema.
 * Muestra un panel en el que se debe ingresar un código y
 * una razón de la asistencia del empleado. Le sigue otra pantalla para
 * confirmar los datos de esta persona y registrar la hora en la BD.
 */
Ext.onReady(function() {
     Ext.QuickTips.init();

     var txtID = new Ext.form.TextField({
          id: 'id',
          fieldLabel: 'Código',
          name: 'id',
          allowBlank: false,
          style: 'margin-bottom: 10px'
     });

     var pnlRegistro = new Ext.FormPanel({
          title: 'Ingrese el código del empleado',
          url: 'empleados.do?method=obtenerEmpleado',
          defaultType: 'textfield',
          width: 410,
          height: 230,
          labelWidth: 45,
          bodyStyle: 'padding: 10px 5px',
          frame: true,
          defaults: {
               width: 335
          },
         /*
               * El fieldset contiene los radio button con el status
               * que se necesita registrar. Estos botones se llenan desde
               * el servidor porque vienen de la base de datos.
               * El plugin LiteRemoteComponent hace este proceso.
               */
          items: [txtID, {
               xtype: 'fieldset',
               title: 'Razón de Registro',
               bodyStyle: 'margin-left: -15px',
               width: 385,
               defaults: {
                    anchor: '1'
               },
               plugins: new Ext.ux.Plugin.LiteRemoteComponent({
                    url: 'status.do?method=obtenerStatusParaRadio'
               })
          }],
          buttonAlign: 'center',
          buttons: [{
               text: 'Buscar',
               style: 'margin-bottom:5px',
               handler: function() {
                    var codigo = txtID.getValue();
                    if (codigo == '') {
                         Ext.Msg.alert('Error', 'Ingrese un código de empleado para hacer un registro.');
                    } else {
                         var status = Ext.getCmp('rbgRazones').getValue().getGroupValue();
                         Ext.Ajax.request({
                         url: 'empleados.do?method=obtenerEmpleado',
                         method: 'POST',
                         params: {
                             codigo: codigo,
                             razon: status
                         },
                         /* El resultado proviene en formato JSON.
                                        * El objeto JSON con la información del empleado
                                        * es usado para llenar los textfield del panel
                                        * de confirmación de registro del empleado, por
                                        * medio del metodo mostrarYLlenarRegistro
                                        */
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
               }
          }]
     });
     pnlRegistro.render('panelCodigo'); // Se muestra en el div con #id 'panelCodigo'

     /*
     * Los textfield a continuación guardan los datos del empleado a registrar
     */
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
     var txaComentario = new Ext.form.TextArea({
          fieldLabel: 'Observaciones',
          name: 'comentario',
          maxLength: 200,
          grow: true,
          autoCreate: {tag: 'textarea', maxlength: '200'}
     });

      /*
          * Este botón esta en el panel de confirmación de registro.
          * Manda al servidor el id del empleado, id de status y el comentario
          * para registrar la hora y regresa un mensaje mostrando la fecha
          * y hora del registro.
          */
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
                        idStatus: txtStatus.getValue(),
                        comentario: txaComentario.getValue()
                    },
                    success: function(result, request) {
                         var jsonData = Ext.util.JSON.decode(result.responseText);
                         if (jsonData.error == null) {
                            /*
                                             * Cuando el registro es exitoso muestra
                                             * un mensaje con la fecha y hora del registro.
                                             * Este desaparece automáticamente después de
                                             * 5 segundos y regresa al panel de registro de
                                             * código del empleado.
                                             */
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

     /*
      * Contiene todos los datos del empleado para registrar su hora
      */
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
          items: [txtNombre, txtNumEmpleado, txtRfc, txtCodigo, txtArea, txaComentario],
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

     /*
      * Esconde el panel de ingreso de código, muestra el panel de información
      * del empleado y llena los textfield del último panel con los datos
      * del empleado que se desea registrar.
      */
     function mostrarYLlenarRegistro(empleado) {
          pnlRegistro.hide();
          pnlInformacion.setTitle("Registro de " + empleado.status);
          txtNombre.setValue(empleado.nombre);
          txtNumEmpleado.setValue(empleado.numero);
          txtRfc.setValue(empleado.rfc);
          txtCodigo.setValue(empleado.codigo);
          txtArea.setValue(empleado.area);
          txtIdEmpleado.setValue(empleado.id);
          txtStatus.setValue(empleado.idStatus);
          pnlInformacion.show();
     }

     /*
      * Esconde el panel de información y muestra el panel de ingreso
      * de código reseteando el textfield de código.
      */
     function mostrarPanelCodigo() {
          pnlInformacion.hide();
          pnlRegistro.show();
          txtID.reset();
          txaComentario.reset();
     }

     /*
      * Esconde el panel de información y muestra el panel de ingreso
      * de código sin resetear el textfield de código.
      */
     function mostrarPanelCodigoSinReset() {
          pnlInformacion.hide();
          pnlRegistro.show();
          txaComentario.reset();
     }

     /*var winRegistro = new Ext.Window({
        layout:'fit',
        width:420,
        height:230,
        closable: false,
        resizable: false,
        draggable: false,
        plain: true,
        border: false,
        items: [pnlRegistro]
     });
     winRegistro.show();*/

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
});


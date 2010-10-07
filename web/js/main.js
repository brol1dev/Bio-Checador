Ext.onReady(function() {
     Ext.QuickTips.init();

     var txtID = new Ext.form.TextField({
          id: 'id',
          fieldLabel: 'Código',
          name: 'id',
          allowBlank: true
     });

     var pnlRegistro = new Ext.FormPanel({
          title: 'Ingrese el código del empleado',
          url: 'empleados.do?method=obtenerEmpleado',
          defaultType: 'textfield',
          labelWidth: 80,
          bodyStyle: 'padding: 5px',
          frame: true,
          defaults: {
               width: 250
          },
          items: [txtID],
          buttonAlign: 'center',
          buttons: [{
               text: 'Buscar',
               handler: function() {
                    Ext.Ajax.request({
                    url: 'empleados.do?method=obtenerEmpleado',
                    method: 'POST',
                    params: {
                        codigo: txtID.getValue()
                    },
                    success: function(result, request) {
                         var jsonData = Ext.util.JSON.decode(result.responseText);
                         var empleado = jsonData.empleado;
                         Ext.Msg.alert('Enviado', 'El id es: ' + empleado.id);
                    },
                    failure: function(result, request) {
                         var jsonData = Ext.util.JSON.decode(result.responseText);
                         var errors = jsonData.errors;
                         Ext.Msg.alert('Error', 'El servidor es inalcanzable. Ponganse en contacto con el administrador.' + errors.text);
                    }
               });
                    //pnlInformacion.show();
                    //winRegistro.hide();
               }
          }]
     });

     var winRegistro = new Ext.Window({
        layout:'fit',
        width:370,
        height:150,
        closable: false,
        resizable: false,
        plain: true,
        border: false,
        items: [pnlRegistro]
     });
     winRegistro.show();

     var pnlInformacion = new Ext.Panel({
          title: 'Datos de Registro',
          layout: 'form',
          hidden: true
     });
     pnlInformacion.render('panel');
});


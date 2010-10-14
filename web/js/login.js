Ext.onReady(function(){
    Ext.QuickTips.init();

    var login = new Ext.FormPanel({
        labelWidth:80,
        url:'j_spring_security_check',
        frame:true,
        title:'Ingreso al sistema',
        defaultType:'textfield',
        monitorValid:true,
	// El atributo 'name' define el nombre de variable que se enviará al servidor
        items:[{
                fieldLabel:'Usuario',
                name:'j_usuario',
                allowBlank:false
            },{
                fieldLabel:'Contraseña',
                name:'j_password',
                inputType:'password',
                allowBlank:false
            }],
        buttons:[{
                text:'Entrar',
                formBind: true,
                // Se lanza esta función cuando se da clic al botón
                handler:function(){
                     //window.location = 'login.do';
                    login.getForm().submit({

                         // Functions that fire (success or failure) when the server responds. The server would
                         // actually respond with valid JSON,
                         // something like: response.write "{ success: true}" or
                         // response.write "{ success: false, errors: { reason: 'Login failed. Try again.' }}"
                         // depending on the logic contained within your server script.
                         // If a success occurs, the user is notified with an alert messagebox,
                         // and when they click "OK", they are redirected to whatever page
                         // you define as redirect.
                        success:function() {
                             window.location = 'index.do';
                        },

			// Failure function, see comment above re: success and failure.
			// You can see here, if login fails, it throws a messagebox
			// at the user telling him / her as much.

                        failure:function(form, action){
                            if(action.failureType == 'server'){
                                obj = Ext.util.JSON.decode(action.response.responseText);
                                Ext.Msg.alert('Ingreso fallido!', obj.errors.reason);
                            }else{
                                Ext.Msg.alert('Alerta!', 'El servidor es inalcanzable. Comuniquese con el administrador.');
                            }
                            login.getForm().reset();
                        }
                    });
                }
            }]
    });

    var win = new Ext.Window({
        layout:'fit',
        width:300,
        height:150,
        closable: false,
        resizable: false,
        draggable: false,
        plain: true,
        border: false,
        items: [login]
    });
    win.show();
});
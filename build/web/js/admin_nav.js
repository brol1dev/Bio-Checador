/*
 * Barra de navegación mostrada a usuarios con permisos administrativos
 */
Ext.onReady(function() {
     Ext.QuickTips.init();

     var menu = new Ext.Toolbar({
          height: 26,
          items: [{
               text: 'Registro de Asistencia',
               handler: function() {
                    window.location = "/checador/index.do";
               }
          }, '-', {
               text: 'Reportes',
               handler: function() {
                    window.location = "/checador/admin/reportes.do";
               }
          }]
     });
     menu.render("admin_nav");
});


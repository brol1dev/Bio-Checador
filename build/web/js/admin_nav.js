Ext.onReady(function() {
     Ext.QuickTips.init();

     var menu = new Ext.Toolbar({
          height: 26,
          items: [{
               text: 'Registro de Asistencia',
               handler: function() {
                    window.location = "/bio-checador/index.do";
               }
          }, '-', {
               text: 'Reportes',
               handler: function() {
                    window.location = "/bio-checador/admin/reportes.do";
               }
          }]
     });
     menu.render("admin_nav");
});


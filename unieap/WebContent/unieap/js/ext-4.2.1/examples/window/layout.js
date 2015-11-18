Ext.require([
    'Ext.tab.*',
    'Ext.window.*',
    'Ext.tip.*',
    'Ext.layout.container.Border'
]);
Ext.onReady(function(){
    var win,
        button = Ext.get('show-btn');

    button.on('click', function(){

    	var dicdataForm = Ext.widget('form',{
    		defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
    		items:[
    		       		{xtype:'fieldset', title:'data', 
    		       			items:[
									{
									    xtype: 'textfield',
									    name: 'textfield1',
									    fieldLabel: '<font color=red>*</font>Text field',
									    value: 'Text field value',
									    listeners:{
									    	'blur':function(){
									    		//this.addClass('readonly_field');
									    		//formPanel.getForm().findField('textfield1').setFieldStyle({'background-color': 'red'});
									    		dicdataForm.getForm().findField('textfield1').setValue('aaaaa');
									    		dicdataForm.getForm().findField('textfield1').setReadOnly(true);
									    		//dicdataForm.getForm().findField('textfield1').inputEl.dom.className='readonly_field x-form-field x-form-text';
									    		
									    		dicdataForm.getForm().findField('textfield1').inputEl.addCls('readonly_field');
									    	}
									    }
									}
    		       			       
    		       			       
    		       			       ]}
    		       ]
    	});
    	
        if (!win) {
            win = Ext.create('widget.window', {
                title: 'Layout Window with title <em>after</em> tools',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                closeAction: 'hide',
                width: 600,
                minWidth: 350,
                height: 350,
                tools: [{type: 'pin'}],
                layout: {
                    type: 'border',
                    padding: 5
                },
                items: dicdataForm
            });
        }
        button.dom.disabled = true;
        if (win.isVisible()) {
            win.hide(this, function() {
                button.dom.disabled = false;
            });
        } else {
            win.show(this, function() {
                button.dom.disabled = false;
            });
        }
    });
});
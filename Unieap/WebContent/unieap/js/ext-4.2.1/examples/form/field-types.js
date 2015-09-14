Ext.require([
    'Ext.form.*'
]);

Ext.onReady(function() {

    var formPanel = Ext.create('Ext.form.Panel', {
        frame: true,
        title: 'Form Fields',
        width: 340,
        bodyPadding: 5,

        fieldDefaults: {
            labelAlign: 'left',
            labelWidth: 90,
            anchor: '100%'
        },
        items: [
                {xtype:'fieldset', title:'data', items:
                	[
                	 
							{
							    xtype: 'textfield',
							    id:'1223',
							    name: 'textfield1',
							    fieldLabel: '<font color=red>*</font>Text field',
							    value: 'Text field value',
							    listeners:{
							    	'blur':function(){
							    		//this.addClass('readonly_field');
							    		//formPanel.getForm().findField('textfield1').setFieldStyle({'background-color': 'red'});
							    		formPanel.getForm().findField('textfield1').setValue('aaaaa');
							    		formPanel.getForm().findField('textfield1').setReadOnly(true);
							    		formPanel.getForm().findField('textfield1').setReadOnly(false);
							    		formPanel.getForm().findField('textfield1').inputEl.removeCls('readonly_field');
							    		//formPanel.getForm().findField('textfield1').inputEl.addCls('readonly_field');
							    		//formPanel.getForm().findField('textfield1').setFieldStyle('readonly_field');
							    	}
							    }
							},
							{
							    xtype: 'textfield',
							    name: 'password1',
							    inputType: 'password',
							    fieldLabel: 'Password field',
							    listeners:{
							    	'blur':function(){
							    		//this.addClass('readonly_field');
							    		//alert('aaa');
							    		formPanel.getForm().findField('textfield1').inputEl.removeCls('readonly_field');
							    		//formPanel.getForm().findField('textfield1').setFieldStyle('');
							    	}
							    }
							}    
                	 
                	 
                	 
                	 ]},
            {
            xtype: 'textfield',
            name: 'textfield1',
            fieldLabel: '<font color=red>*</font>Text field',
            value: 'Text field value',
            listeners:{
            	'blur':function(){
            		//this.addClass('readonly_field');
            		//formPanel.getForm().findField('textfield1').setFieldStyle({'background-color': 'red'});
            		formPanel.getForm().findField('textfield1').setReadOnly(true);
            		formPanel.getForm().findField('textfield1').inputEl.addCls('readonly_field');
            	}
            }
        }, {
            xtype: 'hiddenfield',
            name: 'hidden1',
            value: 'Hidden field value'
        },{
            xtype: 'textfield',
            name: 'password1',
            inputType: 'password',
            fieldLabel: 'Password field',
            listeners:{
            	'blur':function(){
            		//this.addClass('readonly_field');
            		//alert('aaa');
            		formPanel.getForm().findField('textfield1').inputEl.removeCls('readonly_field');
            		//formPanel.getForm().findField('textfield1').setFieldStyle('');
            	}
            }
        }, {
            xtype: 'filefield',
            name: 'file1',
            fieldLabel: 'File upload'
        }, {
            xtype: 'textareafield',
            name: 'textarea1',
            fieldLabel: 'TextArea',
            value: 'Textarea value'
        }, {
            xtype: 'displayfield',
            name: 'displayfield1',
            fieldLabel: 'Display field',
            value: 'Display field <span style="color:green;">value</span>'
        }, {
            xtype: 'numberfield',
            name: 'numberfield1',
            fieldLabel: 'Number field',
            value: 5,
            minValue: 0,
            maxValue: 50
        }, {
            xtype: 'checkboxfield',
            name: 'checkbox1',
            fieldLabel: 'Checkbox',
            boxLabel: 'box label'
        }, {
            xtype: 'radiofield',
            name: 'radio1',
            value: 'radiovalue1',
            fieldLabel: 'Radio buttons',
            boxLabel: 'radio 1'
        }, {
            xtype: 'radiofield',
            name: 'radio1',
            value: 'radiovalue2',
            fieldLabel: '',
            labelSeparator: '',
            hideEmptyLabel: false,
            boxLabel: 'radio 2'
        }, {
            xtype: 'datefield',
            name: 'date1',
            fieldLabel: 'Date Field'
        }, {
            xtype: 'timefield',
            name: 'time1',
            fieldLabel: 'Time Field',
            minValue: '1:30 AM',
            maxValue: '9:15 PM'
        }]
    });

    formPanel.render('form-ct');

});

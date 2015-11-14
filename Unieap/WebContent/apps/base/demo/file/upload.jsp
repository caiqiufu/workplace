<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	
    	Ext.create('Ext.form.Panel', {
	        width: 400,
	        frame: true,
	        renderTo: 'fi-form',   
	        items: [{
	            xtype: 'filefield',
	            name: 'file',
	            msgTarget: 'side',
	            allowBlank: false,
	            anchor: '100%',
	            buttonText: 'Select a File...'
	        }],
	        buttons: [{
	            text: 'Upload',
	            handler: function() {
	                var form = this.up('form').getForm();
	                var defectId = '1222';
	                if(form.isValid()){
	                    form.submit({
	                        url: 'UploadController.do?method=upload',
	                        params:{'handlerId':'3','parameters':Ext.JSON.encode({'defectId':defectId})},
	                        waitMsg: 'Uploading your file...',
	                        success: function(form, action) {
                             	var result = Ext.JSON.decode(action.response.responseText);
			                    if(result.isSuccess == 'failed')
			                    {
			                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
                             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
			                    }else{
                                    	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
	                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
			                    }
                              },
                             failure: function(form, action) {
                            	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
                        			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                             }
	                    });
	                }
	            }
	        }]
	    });
    	
    });
    	
    </script>
</head>
<body>
    <div id="fi-form">file upload test</div> 
</body>
</html>

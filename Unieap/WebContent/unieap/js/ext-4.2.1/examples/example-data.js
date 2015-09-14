Ext.require(['Ext.data.*']);

Ext.onReady(function() {

    window.generateData = function(n, floor){
        var data = [],
            p = (Math.random() *  11) + 1,
            i;
            
        floor = (!floor && floor !== 0)? 20 : floor;
        
        for (i = 0; i < (n || 12); i++) {
            data.push({
                name: Ext.Date.monthNames[i % 12],
                data1: Math.floor(Math.max((Math.random() * 100), floor)),
                data2: Math.floor(Math.max((Math.random() * 100), floor)),
                data3: Math.floor(Math.max((Math.random() * 100), floor)),
                data4: Math.floor(Math.max((Math.random() * 100), floor)),
                data5: Math.floor(Math.max((Math.random() * 100), floor)),
                data6: Math.floor(Math.max((Math.random() * 100), floor)),
                data7: Math.floor(Math.max((Math.random() * 100), floor)),
                data8: Math.floor(Math.max((Math.random() * 100), floor)),
                data9: Math.floor(Math.max((Math.random() * 100), floor))
            });
        }
        return data;
    };
    
    window.generateDataNegative = function(n, floor){
        var data = [],
            p = (Math.random() *  11) + 1,
            i;
            
        floor = (!floor && floor !== 0)? 20 : floor;
            
        for (i = 0; i < (n || 12); i++) {
            data.push({
                name: Ext.Date.monthNames[i % 12],
                data1: Math.floor(((Math.random() - 0.5) * 100), floor),
                data2: Math.floor(((Math.random() - 0.5) * 100), floor),
                data3: Math.floor(((Math.random() - 0.5) * 100), floor),
                data4: Math.floor(((Math.random() - 0.5) * 100), floor),
                data5: Math.floor(((Math.random() - 0.5) * 100), floor),
                data6: Math.floor(((Math.random() - 0.5) * 100), floor),
                data7: Math.floor(((Math.random() - 0.5) * 100), floor),
                data8: Math.floor(((Math.random() - 0.5) * 100), floor),
                data9: Math.floor(((Math.random() - 0.5) * 100), floor)
            });
        }
        return data;
    };

    window.store1 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.storeNegatives = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateDataNegative()
    });
    window.store3 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.store4 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.store5 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });  
    window.store6 = Ext.create('Ext.data.JsonStore', {
        fields: ['datetime','newamount','assignamount','confirmedamount','pendingFixamount','rejectamount','pendingRetestamount','resolveamount','closeamount'],
        data: [{"new":"0","pendingFix":3,"pendingRetestamount":4,"resolve":6,"newamount":2,"confirmedamount":5,"confirmed":2,"datetime":"2014-06-20","pendingFixamount":6,"reject":4,"rejectamount":3,"resolveamount":3,"pendingRetest":5,"assignamount":4,"close":7,"assign":1,"closeamount":5},{"new":"0","pendingFix":3,"pendingRetestamount":3,"resolve":6,"newamount":1,"confirmedamount":2,"confirmed":2,"datetime":"2014-06-21","pendingFixamount":4,"reject":4,"rejectamount":5,"resolveamount":4,"pendingRetest":5,"assignamount":2,"close":7,"assign":1,"closeamount":5},{"new":"0","pendingFix":3,"pendingRetestamount":2,"resolve":6,"newamount":14,"confirmedamount":3,"confirmed":2,"datetime":"2014-06-22","pendingFixamount":1,"reject":4,"rejectamount":1,"resolveamount":1,"pendingRetest":5,"assignamount":2,"close":7,"assign":1,"closeamount":1},{"new":"0","pendingFix":3,"pendingRetestamount":5,"resolve":6,"newamount":20,"confirmedamount":6,"confirmed":2,"datetime":"2014-06-23","pendingFixamount":4,"reject":4,"rejectamount":4,"resolveamount":6,"pendingRetest":5,"assignamount":6,"close":7,"assign":1,"closeamount":4}]
    });
    
    window.loadTask = new Ext.util.DelayedTask();
});

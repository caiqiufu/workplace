Ext.define('DayData',{
	makeDate: function(d, h, m, s){
				var today = Ext.Date.clearTime(new Date());
				d = d * 86400;
               	h = (h || 0) * 3600;
               	m = (m || 0) * 60;
               	s = (s || 0);
               	return Ext.Date.add(today, Ext.Date.SECOND, d + h + m + s);
	},
	getToday: function(){
			return Ext.Date.clearTime(new Date());
 		},
 	getGridDate: function(datetime){
 		return datetime.getDate()+'/'+(datetime.getMonth()+1);
 	},
 	getDayNum:function(datetime){
 		
 		return datetime.getDate();
 	},
 	getMonthNum:function(datetime){
 		
 		return datetime.getMonth()+1;
 	},
 	getYearNum:function(datetime){
 		
 		return datetime.getYear();
 	},
 	getHourNum:function(datetime){
 		
 		return datetime.getHours();
 	},
 	getMinuteNum:function(datetime){
 		
 		return datetime.getMinutes();
 	},
 	getSecondNum:function(datetime){
 		
 		return datetime.getSeconds();
 	},
 	checkDateRange:function(columnText,startDate,endDate){
 		//var taskDate = Ext.Date.parseDate('2013-08-17 11:31:29','Y-m-d H:i:s'); 
 		var dds = columnText.split('/');
 		var ddsd = dds[0];
 		var ddsm = dds[1];
 		var sd = Ext.Date.parseDate(startDate,'Y-m-d H:i:s'); 
 		var startDated = sd.getDate();
 		var startDatem = sd.getMonth()+1;
 		var ed = Ext.Date.parseDate(endDate,'Y-m-d H:i:s'); 
 		var endDated = ed.getDate();
 		var endDatem = ed.getMonth()+1;
 		if(startDatem<=ddsm&&ddsm<=endDatem){
 			if(startDated<=ddsd&&ddsd<=endDated){
 				return true;
 			}else{
 				return false;
 			}
 		}else{
 			return false;
 		}
 	}
});
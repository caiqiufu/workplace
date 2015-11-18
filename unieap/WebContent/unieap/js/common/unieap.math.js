(function() {
	UNIEAP.namespace("math");

	/**
	 * 用于精度不高的除法运算.修正js计算浮点数的bug 计算结果保留有效位数大约16位,若需要更高精度的计算则调用divInServ函数在服务器端进行高精度的计算
	 * 
	 * @param {Number}
	 *            arg1 被除数
	 * @param {Number}
	 *            arg2 除数
	 * @param {Number}
	 *            scale 精度
	 * @return {Number} 返回值
	 * 2009-05-12 qindy 修改内容：修改精度问题。
	 */
	UNIEAP.math.divide = function(arg1, arg2, scale) {
		/*
		var t1 = 0, t2 = 0, r1, r2;
		try {
			t1 = arg1.toString().split(".")[1].length;
		} catch (e) {
		}
		try {
			t2 = arg2.toString().split(".")[1].length;
		} catch (e) {
		}
		with (Math) {
			r1 = Number(arg1.toString().replace(".", ""));
			r2 = Number(arg2.toString().replace(".", ""));
			var result = (r1 / r2) * pow(10, t2 - t1);
			if (!scale) {
				return result;
			} else {
				return parseFloat(result.toFixed(scale));
			}
		}
		*/
		var ret = "";
	    if (arg2== "0" ){
	        return 0;
	    }
	    if (scale > "0"){
	        ret = arg1/arg2
	        ret = ForDight(ret ,scale);
	    }else{
	       ret = arg1/arg2
	    }
	    return ret;
	};
	/*
	UNIEAP.math.divInServ = function(arg1, arg2, scale) {
		var res = executeRequest("BaseAction","divide","dividend="+arg1+"&divisor="+arg2+"&scale="+scale);
		var jso;
		try{
		    jso = string2json(res);
			if(jso.header == AJAX.RESPONSE_SUCCESS)
				return String(jso.body);
			else
				throw new Error(jso.body);
		}catch(e){
			MessageBoxAlert(e.message,"error","发生异常");
			throw e;
		}
	};
	*/  
	/**
	 * 用于精度不高的乘法运算. 保留有效位数大约16位,所以被乘数和乘数的有效位之和不能大于16否则会误差
	 * 若需要更高精度的计算则调用multInServ函数在服务器端进行高精度的计算
	 * 
	 * @param {Number}
	 *            arg1 乘数
	 * @param {Number}
	 *            arg2 被乘数
	 * @param {Number}
	 *            scale 精度
	 * @return {Number} 返回值
	 * 修改精度问题。modiy by qindy 2009-5-12.
	 */
	UNIEAP.math.multiply = function(arg1, arg2, scale) {
		/*
		var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
		try {
			m += s1.split(".")[1].length;
		} catch (e) {
		}
		try {
			m += s2.split(".")[1].length;
		} catch (e) {
		}
		var result = Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
				/ Math.pow(10, m);
		if (!scale) {
			return result;
		} else {
			return parseFloat(result.toFixed(scale));
		}
		*/
		var ret = "";
	    if (scale > "0"){
	        ret = arg1*arg2
	        ret = ForDight(ret ,scale);
	    }else{
	       ret = arg1*arg2
	    }
	    return ret;
	};
	/*
	UNIEAP.math.multInServ = function(arg1, arg2, scale) {
		var res = executeRequest("BaseAction","multiply","multiplier="+arg1+"&multiplicand="+arg2+"&scale="+scale);
		var jso;
		try{
		    jso = string2json(res);
			if(jso.header == AJAX.RESPONSE_SUCCESS)
				return String(jso.body);
			else
				throw new Error(jso.body);
		}catch(e){
			MessageBoxAlert(e.message,"error","发生异常");
			throw e;
		}
	};
	*/

	/**
	 * 用于精度不高的加法运算. 保留有效位数大约16位 可满足大多数应用.若需要更高精度的计算则调用addInServ函数在服务器端进行高精度的计算
	 * 
	 * @param {Number}
	 *            arg1 被加数
	 * @param {Number}
	 *            arg2 加数
	 * @param {Number}
	 *            scale 精度
	 * @return {Number} 返回值
	 * 
	 * 2009-05-12 qindy 修改精度问题。
	 */
	UNIEAP.math.add = function(arg1, arg2, scale) {
		/*
		var r1, r2, m;
		try {
			r1 = arg1.toString().split(".")[1].length;
		} catch (e) {
			r1 = 0
		}
		try {
			r2 = arg2.toString().split(".")[1].length;
		} catch (e) {
			r2 = 0
		}
		m = Math.pow(10, Math.max(r1, r2));
		var result = (arg1 * m + arg2 * m) / m;
		if (!scale) {
			return result;
		} else {
			return parseFloat(result.toFixed(scale));
		}*/
		var ret = "";
	    if (scale > "0"){
	        ret = parseFloat(arg1)+parseFloat(arg2);
	        ret = ForDight(ret,scale);
	    }else{
	       ret = parseFloat(arg1)+parseFloat(arg2);
	    }
	    return ret;
	};
	/*
	UNIEAP.math.addInServ = function(arg1, arg2, scale) {
		var res = executeRequest("BaseAction","add","augend="+arg1+"&augend="+arg2+"&scale="+scale);
		var jso;
		try{
		    jso = string2json(res);
			if(jso.header == AJAX.RESPONSE_SUCCESS)
				return String(jso.body);
			else
				throw new Error(jso.body);
		}catch(e){
			MessageBoxAlert(e.message,"error","发生异常");
			throw e;
		}
	};
	*/
	/**
	 * 用于精度不高的减法运算. 保留有效位数大约16位,所以小数点后位数太多的话,会有误差.
	 * 由于一般只用到小数点后最多8位,所以可满足大多数要求.若需要更高精度的计算则调用subInServ函数在服务器端进行高精度的计算
	 * 
	 * @param {Number}
	 *            arg1 被减数
	 * @param {Number}
	 *            arg2 减数
	 * @param {Number}
	 *            scale 精度
	 * @return {Number} 返回值
	 */
	UNIEAP.math.subtract = function(arg1, arg2, scale) {
		var r1, r2, m, n;
		try {
			r1 = arg1.toString().split(".")[1].length;
		} catch (e) {
			r1 = 0
		}
		try {
			r2 = arg2.toString().split(".")[1].length;
		} catch (e) {
			r2 = 0
		}
		m = Math.pow(10, Math.max(r1, r2));
		// 动态控制精度长度
		//n = (r1 >= r2) ? r1 : r2;
		//var result = ((arg1 * m - arg2 * m) / m).toFixed(n);
		var result = ((arg1 * m - arg2 * m) / m);
		if (!scale) {
			return result;
		} else {
			return parseFloat(result.toFixed(scale));
		}
	};
	/*
	UNIEAP.math.subInServ = function(arg1, arg2, scale) {
				
		var res = executeRequest("BaseAction","subtract","minuend="+arg1+"&subtrahend="+arg2+"&scale="+scale);
		var jso;
		try{
		    jso = string2json(res);
			if(jso.header == AJAX.RESPONSE_SUCCESS)
				return String(jso.body);
			else
				throw new Error(jso.body);
		}catch(e){
			MessageBoxAlert(e.message,"error","发生异常");
			throw e;
		}
	};
	*/
	/**
	 * 判断str是否是整数
	 * @param {String} str
	 * @return {Boolean} 返回值
	 */
	UNIEAP.math.isInt = function(str) {
		if (str == null || str == "") {
			return false;
		}
		if (/^(\-?)(\d+)$/.test(str)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断str是否是数字
	 * @param {String} str
	 * @return {Boolean} 返回值
	 */
	UNIEAP.math.isNumber = function(str) {
		if (str == null || str == "") {
			return false;
		}
		if (isNaN(parseFloat(String(str)))) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 精确到多少位，
	 * Dight：输入的数字
	 * How：精确的位数
	 */
	function ForDight(Dight,How)
	{ 
	    var Dight = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How); 
	    return Dight; 
	};
	 //格式化数据传入一个数，将其格式化为  #,###.###的格式,  number为要格式的数字,format为传入的number的格式,precision为精度
	UNIEAP.math.formatNum = function(number, format, precision)
	{
		number = UNIEAP.math.FormatNumber(number,precision);
		var tag = (number+"").indexOf("-");
		number = (number+"").replace("-","");
		var value = "";
		if(format == "float")
		{
			num = (number+"").split(".")
			if((number+"").indexOf(".") < 0)
			{
				num[1] = "0";
			}
			var len = parseInt(parseInt(num[0].length,10)/3,10);
			for(var i = 1;i < len + 1;i ++)
			{
				if(value != "")
				{
					value = num[0].substr(num[0].length - 3*i,3) + "," + value;
				}else
				{
					value = num[0].substr(num[0].length - 3*i,3)
				}
			}
			if(len > 0)
			{
				value = parseInt(num[0].length,10)%3 != 0? num[0].substr(0,num[0].length - len*3) + "," + value:value;
			}else
			{
				value = num[0];
			}
			if(num[1].length > 2)
			{
				if(precision != undefined || precision != null || precision != "")
				{
					value = value +".";
					for(var i = 0;i < parseInt(precision,10);i ++)
					{
						value = value + num[1].substr(i,1);
					}
				}else
				{
					value = value + "." + num[1];
				}
			}else
			{
				/*modify qindy 2009-04-29格式化错误*/
				if (num[1].length==2){
					value = value + "." + num[1];
				}else{
					value = value + "." + num[1]+"0";
				}
			}
			
		}
		if(tag > -1)
		{
			value = "-" + value;
		}
		return value;
	}
	//四舍五入，精确到N位的小数
	UNIEAP.math.FormatNumber = function(srcStr,nAfterDot)
	{
		var srcStr,nAfterDot;
		var resultStr,nTen;
		srcStr = ""+srcStr+"";
		strLen = srcStr.length;
		dotPos = srcStr.indexOf(".",0);
		if (dotPos == -1)
		{
			resultStr = srcStr+".";
			for (i=0;i<nAfterDot;i++)
			{
				resultStr = resultStr+"0";
			}
		}else
		{
			if ((strLen - dotPos - 1) >= nAfterDot)
			{
				nAfter = dotPos + nAfterDot + 1;
				nTen =1;
				for(j=0;j<nAfterDot;j++)
				{
					nTen = nTen*10;
				}
				resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
			}else
			{
				resultStr = srcStr;
				for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++)
				{
					resultStr = resultStr+"0";
				}
			}
		}
		return resultStr;
	}

})();
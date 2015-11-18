/**
 * @modify zhang_rui@neusoft.com
 */
(function() {
	UNIEAP.namespace("date");

	UNIEAP.date.MONTH_NAMES = new Array('January', 'February', 'March',
			'April', 'May', 'June', 'July', 'August', 'September', 'October',
			'November', 'December', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
			'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
	UNIEAP.date.DAY_NAMES = new Array('Sunday', 'Monday', 'Tuesday',
			'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sun', 'Mon', 'Tue',
			'Wed', 'Thu', 'Fri', 'Sat');
	UNIEAP.date.LZ = function(x) {
		return (x < 0 || x > 9 ? "" : "0") + x
	}
    /**
     * 将日期的毫秒值转换为对应的日期的字符形式
     * 如:1121021021201 转换为 '2009-03-10'
     */  
    UNIEAP.date.getDateStrFromMS = function(dateVal, format) {
    	if(dateVal == null || "" == dateVal)
    		return "";
		var d = new Date(dateVal);
		return UNIEAP.date.formatDate(d,format == null ? "yyyy-MM-dd":format);
	}
	/**
	 * 判断是否是指定格式的日期
	 * 
	 * @param {String}
	 *            val 日期字符串
	 * @param {String}
	 *            format 格式
	 * @return {Boolean} 返回值
	 */
	UNIEAP.date.isDate = function(val, format) {
		var date = UNIEAP.date.getDateFromFormat(val, format);
		if (date == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 返回类似 1998年9月12日 中文格式的代表日期的字符串 如果传入的非日期类型则返回当前日期
	 * 
	 * @param {String}
	 *            val 日期字符串
	 * @param {String}
	 *            format 格式
	 * @return {Boolean} 返回值
	 */
	UNIEAP.date.getChineseDate = function(date) {
		if (date instanceof Date) {
			return (date.getYear()) + "年" + (date.getMonth() + 1) + "月"
					+ date.getDate() + "日";
		}
		var today = new Date();
		return ((today.getYear()) + "年" + (today.getMonth() + 1) + "月"
				+ today.getDate() + "日");
	}
	/**
	 * 根据"yyyy-MM-dd"的模式解析日期并比较其大小 如果date1比date2大则返回1 如果date1比date2小则返回-1
	 * 如果date1比date2小则返回0 解析错误返回-2
	 * 
	 * @param {String}
	 *            date1 需要格式化的日期
	 * @param {String}
	 *            date2 需要格式化的日期
	 * @return {String} 返回值
	 */
	UNIEAP.date.compareTwoDates = function(date1, date2) {
		var d1 = UNIEAP.date.getDateFromFormat(date1, "yyyy-MM-dd");
		var d2 = UNIEAP.date.getDateFromFormat(date2, "yyyy-MM-dd");
		if (d1 == 0 || d2 == 0) {
			return -2;
		} else if (d1 > d2) {
			return 1;
		} else if (d1 < d2) {
			return -1
		}
		return 0;
	}
	/**
	 * 根据指定的模式解析日期并比较大小 如果date1比date2大则返回1 如果date1比date2小则返回-1
	 * 如果date1比date2小则返回0 解析错误返回-2
	 * 
	 * @param {String}
	 *            date1 需要格式化的日期
	 * @param {String}
	 *            dateformat1 需要格式化的日期
	 * @param {String}
	 *            date2 需要格式化的日期
	 * @param {String}
	 *            dateformat2 需要格式化的日期
	 * @return {String} 返回值
	 */
	UNIEAP.date.compareTwoDatesWithFormat = function(date1, dateformat1, date2,
			dateformat2) {
		var d1 = UNIEAP.date.getDateFromFormat(date1, dateformat1);
		var d2 = UNIEAP.date.getDateFromFormat(date2, dateformat2);
		if (d1 == 0 || d2 == 0) {
			return -2;
		} else if (d1 > d2) {
			return 1;
		} else if (d1 < d2) {
			return -1
		}
		return 0;
	}

	/**
	 * 根据指定的模式格式化日期
	 * 
	 * @param {Date}
	 *            date 需要格式化的日期
	 * @return {String} 返回值
	 */
	UNIEAP.date.formatDate = function(date, format) {
		format = format + "";
		var result = "";
		var i_format = 0;
		var c = "";
		var token = "";
		var y = date.getYear() + "";
		var M = date.getMonth() + 1;
		var d = date.getDate();
		var E = date.getDay();
		var H = date.getHours();
		var m = date.getMinutes();
		var s = date.getSeconds();
		var yyyy, yy, MMM, MM, dd, hh, h, mm, ss, ampm, HH, H, KK, K, kk, k;
		// Convert real date parts into formatted versions
		var value = new Object();
		if (y.length < 4) {
			y = "" + (y - 0 + 1900);
		}
		value["y"] = "" + y;
		value["yyyy"] = y;// 2008
		value["yy"] = y.substring(2, 4);// 08
		value["M"] = M;// 1-12
		value["MM"] = UNIEAP.date.LZ(M);// 01-12
		value["MMM"] = UNIEAP.date.MONTH_NAMES[M - 1];// english
		value["NNN"] = UNIEAP.date.MONTH_NAMES[M + 11];
		value["d"] = d;// 1-31
		value["dd"] = UNIEAP.date.LZ(d);// 01-31
		value["E"] = UNIEAP.date.DAY_NAMES[E + 7];
		value["EE"] = UNIEAP.date.DAY_NAMES[E];
		value["H"] = H;// 0-23
		value["HH"] = UNIEAP.date.LZ(H);// 00-23
		if (H == 0) {
			value["h"] = 12;
		} else if (H > 12) {
			value["h"] = H - 12;
		} else {
			value["h"] = H;
		}
		value["hh"] = UNIEAP.date.LZ(value["h"]);// 00-12
		if (H > 11) {
			value["K"] = H - 12;
		} else {
			value["K"] = H;
		}
		value["k"] = H + 1;
		value["KK"] = UNIEAP.date.LZ(value["K"]);
		value["kk"] = UNIEAP.date.LZ(value["k"]);
		if (H > 11) {
			value["a"] = "PM";
		} else {
			value["a"] = "AM";
		}
		value["m"] = m;
		value["mm"] = UNIEAP.date.LZ(m);
		value["s"] = s;
		value["ss"] = UNIEAP.date.LZ(s);
		// apply pattern
		while (i_format < format.length) {
			c = format.charAt(i_format);
			token = "";
			while ((format.charAt(i_format) == c) && (i_format < format.length)) {
				token += format.charAt(i_format++);
			}
			if (value[token] != null) {
				result = result + value[token];
			} else {
				result = result + token;
			}
		}
		return result;
	}

	/**
	 * 判断是否是整数
	 * 
	 * @param {Mixed}
	 *            arg1 乘数
	 * @return {Boolean} 返回值
	 */
	UNIEAP.date._isInteger = function(val) {
		var digits = "1234567890";
		for (var i = 0; i < val.length; i++) {
			if (digits.indexOf(val.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	UNIEAP.date._getInt = function(str, i, minlength, maxlength) {
		for (var x = maxlength; x >= minlength; x--) {
			var token = str.substring(i, i + x);
			if (token.length < minlength) {
				return null;
			}
			if (UNIEAP.date._isInteger(token)) {
				return token;
			}
		}
		return null;
	}

	/**
	 * 该函数从一个日期字符中根据给定的格式解析出日期对象,如果有返回日期值,如果没有匹配的值返回0
	 * 
	 * @param {String}
	 *            val 日期字符串
	 * @param {String}
	 *            format 格式
	 * @return {Boolean} 返回值
	 */
	UNIEAP.date.getDateFromFormat = function(val, format) {
		val = val + "";
		format = format + "";
		var i_val = 0;
		var i_format = 0;
		var c = "";
		var token = "";
		var token2 = "";
		var x, y;
		var now = new Date();
		var year = now.getYear();
		var month = now.getMonth() + 1;
		var date = 1;
		var hh = now.getHours();
		var mm = now.getMinutes();
		var ss = now.getSeconds();
		var ampm = "";

		while (i_format < format.length) {
			// Get next token from format string
			c = format.charAt(i_format);
			token = "";
			while ((format.charAt(i_format) == c) && (i_format < format.length)) {
				token += format.charAt(i_format++);
			}
			// Extract contents of value based on format token
			if (token == "yyyy" || token == "yy" || token == "y") {
				if (token == "yyyy") {
					x = 4;
					y = 4;
				}
				if (token == "yy") {
					x = 2;
					y = 2;
				}
				if (token == "y") {
					x = 2;
					y = 4;
				}
				year = UNIEAP.date._getInt(val, i_val, x, y);
				if (year == null) {
					return 0;
				}
				i_val += year.length;
				if (year.length == 2) {
					if (year > 70) {
						year = 1900 + (year - 0);
					} else {
						year = 2000 + (year - 0);
					}
				}
			} else if (token == "MMM" || token == "NNN") {
				month = 0;
				for (var i = 0; i < UNIEAP.date.MONTH_NAMES.length; i++) {
					var month_name = UNIEAP.date.MONTH_NAMES[i];
					if (val.substring(i_val, i_val + month_name.length)
							.toLowerCase() == month_name.toLowerCase()) {
						if (token == "MMM" || (token == "NNN" && i > 11)) {
							month = i + 1;
							if (month > 12) {
								month -= 12;
							}
							i_val += month_name.length;
							break;
						}
					}
				}
				if ((month < 1) || (month > 12)) {
					return 0;
				}
			} else if (token == "EE" || token == "E") {
				for (var i = 0; i < UNIEAP.date.DAY_NAMES.length; i++) {
					var day_name = UNIEAP.date.DAY_NAMES[i];
					if (val.substring(i_val, i_val + day_name.length)
							.toLowerCase() == day_name.toLowerCase()) {
						i_val += day_name.length;
						break;
					}
				}
			} else if (token == "MM" || token == "M") {
				month = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (month == null || (month < 1) || (month > 12)) {
					return 0;
				}
				i_val += month.length;
			} else if (token == "dd" || token == "d") {
				date = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (date == null || (date < 1) || (date > 31)) {
					return 0;
				}
				i_val += date.length;
			} else if (token == "hh" || token == "h") {
				hh = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (hh == null || (hh < 1) || (hh > 12)) {
					return 0;
				}
				i_val += hh.length;
			} else if (token == "HH" || token == "H") {
				hh = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (hh == null || (hh < 0) || (hh > 23)) {
					return 0;
				}
				i_val += hh.length;
			} else if (token == "KK" || token == "K") {
				hh = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (hh == null || (hh < 0) || (hh > 11)) {
					return 0;
				}
				i_val += hh.length;
			} else if (token == "kk" || token == "k") {
				hh = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (hh == null || (hh < 1) || (hh > 24)) {
					return 0;
				}
				i_val += hh.length;
				hh--;
			} else if (token == "mm" || token == "m") {
				mm = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (mm == null || (mm < 0) || (mm > 59)) {
					return 0;
				}
				i_val += mm.length;
			} else if (token == "ss" || token == "s") {
				ss = UNIEAP.date._getInt(val, i_val, token.length, 2);
				if (ss == null || (ss < 0) || (ss > 59)) {
					return 0;
				}
				i_val += ss.length;
			} else if (token == "a") {
				if (val.substring(i_val, i_val + 2).toLowerCase() == "am") {
					ampm = "AM";
				} else if (val.substring(i_val, i_val + 2).toLowerCase() == "pm") {
					ampm = "PM";
				} else {
					return 0;
				}
				i_val += 2;
			} else {
				if (val.substring(i_val, i_val + token.length) != token) {
					return 0;
				} else {
					i_val += token.length;
				}
			}
		}
		// If there are any trailing characters left in the value, it doesn't
		// match
		if (i_val != val.length) {
			return 0;
		}
		// Is date valid for month?
		if (month == 2) {
			// Check for leap year
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) { // leap
				// year
				if (date > 29) {
					return 0;
				}
			} else {
				if (date > 28) {
					return 0;
				}
			}
		}
		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			if (date > 30) {
				return 0;
			}
		}
		// Correct hours value
		if (hh < 12 && ampm == "PM") {
			hh = hh - 0 + 12;
		} else if (hh > 11 && ampm == "AM") {
			hh -= 12;
		}
		var newdate = new Date(year, month - 1, date, hh, mm, ss);
		return newdate.getTime();
	}

	/**
	 * 该函数从一个日期字符中根据给定的格式解析出日期对象,如果有返回日期值,解析失败返回null
	 * 
	 * @param {String}
	 *            val 日期字符串
	 * @param {String}
	 *            format 格式
	 * @return {Boolean} 返回值
	 */
	UNIEAP.date.parseDate = function(val, format) {
		if (!this.isDate(val)) {
			alert("UNIEAP.date.parseDate函数输入的字符:" + val + "是非法的日期字符!");
			return null;
		}
		var d = UNIEAP.date.getDateFromFormat(val, format == null
				? "yyyy-MM-dd"
				: format);
		if (d != 0) {
			return new Date(d);
		}
		return null;
	}
	/**
	 * 求两个时间的天数差 日期格式为 YYYY-MM-dd    
	 * @param {String} DateOne
	 * @param {String} DateTwo
	 */
	UNIEAP.date.daysBetween = function(DateOne, DateTwo) {
		var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
		var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf('-')
				+ 1);
		var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
		var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
		var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf('-')
				+ 1);
		var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
		var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
				.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
		return Math.abs(cha);
	}
})();
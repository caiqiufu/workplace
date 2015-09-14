/*
Created By: fuhao
E-mail: probablyCorey@gmail.com
Requires: Prototype Javascript library (http://prototype.conio.net/)
Use it all you want. Just remember to give me some credit :)
*/

// ------------
// Custom Event
// ------------
var CustomEvent = function() {
  	this.listeners = []
}

CustomEvent.prototype.addListener = function(method) {
	this.listeners.push(method)
}

CustomEvent.prototype.removeListener = function(method) {
	var foundIndexes = this._findListenerIndexes(method)

	for(var i = 0; i < foundIndexes.length; i++) {
		this.listeners.splice(foundIndexes[i], 1)
	}
}

CustomEvent.prototype.dispatch = function(handler) {
	for(var i = 0; i < this.listeners.length; i++) {
		try {
			this.listeners[i](handler)
		}
		catch (e) {
			alert("Could not run the listener " + this.listeners[i] + ". " + e);
		}
	}
}

// Private Methods
// ---------------
CustomEvent.prototype._findListenerIndexes = function(method) {
	var indexes = []
	for(var i = 0; i < this.listeners.length; i++) {			
		if (this.listeners[i] == method) {
			indexes.push(i)
		}
	}
	return indexes
}

// ------
// Cookie
// ------
var Cookie = {
	//有效日期(按天数)
	set : function(name, value, expirationInDays, path) {
		var cookie = escape(name) + "=" + escape(value)
		if (expirationInDays) {
			var date = new Date()
			date.setDate(date.getDate() + expirationInDays)
			cookie += "; expires=" + date.toGMTString()
		} 
		if (path) {
			cookie += ";path=" + path
		}
		document.cookie = cookie
		if (value && (expirationInDays == undefined || expirationInDays > 0) && !this.get(name)) {
			Logger.error("Cookie (" + name + ") was not set correctly... The value was " + value.toString().length + " charachters long (This may be over the cookie limit)");
		}
	},
	//取出value
	get : function(name) {
		var pattern = "(^|;)\\s*" + escape(name) + "=([^;]+)"
		var m = document.cookie.match(pattern)
		if (m && m[2]) {			
			return unescape(m[2])
		}
		else return null
	},

	getAll : function() {
		var cookies = document.cookie.split(';')
		var cookieArray = []				
		for (var i = 0; i < cookies.length; i++) {			
			try {
				var name = unescape(cookies[i].match(/^\s*([^=]+)/m)[1])
				var value = unescape(cookies[i].match(/=(.*$)/m)[1])
			}catch (e) {
				continue
			}
			cookieArray.push({name : name, value : value})
			if (cookieArray[name] != undefined) {
				Logger.waring("Trying to retrieve cookie named(" + name + "). There appears to be another property with this name though.");
			}			
			cookieArray[name] = value
		}
		return cookieArray
	},

	clear : function(name) {
		this.set(name, "", -1)
	},

	clearAll : function() {
		var cookies = this.getAll()
		for(var i = 0; i < cookies.length; i++) {
			this.clear(cookies[i].name)
		}
	}
}   

// ------
// Logger
// -----        
var LogLevel = {
	DEBUG : 4000,
	INFO  : 3000,
	WARN  : 2000,
	ERROR : 1000
}
var Logger = {
	logEntries : [],
	onupdate : new CustomEvent(),//
	onclear : new CustomEvent(),//清除事件
	level : LogLevel.INFO,
	// Logger output    
	log : function(message, tag) {
		var logEntry = new LogEntry(message, tag || "info")
		this.logEntries.push(logEntry)
		this.onupdate.dispatch(logEntry)
	},

	info : function(message) {
		if(this.level >= LogLevel.INFO)
			this.log(message, 'info')
	}, 

	debug : function(message) {
		if(this.level >= LogLevel.DEBUG)
			this.log(message, 'debug')
	},  

	warn : function(message) {
		if(this.level >= LogLevel.WARN)
	  		this.log(message, 'warning')
	},

	error : function(message, error) {
		if(this.level >= LogLevel.ERROR)
	  		this.log(message + ": \n" + error, 'error')
	},

	clear : function () {
		this.logEntries = []
		this.onclear.dispatch()
	},
	setLevel : function (l) {
		this.level = l;
	},
	getLevel : function () {
		return this.level;
	}
}  
var LogEntry = function(message, tag) {
      this.message = message
      this.tag = tag
}

var LogConsole = function() {
	//alert("LogConsole");
    this.outputCount = 0
    this.tagPattern = Cookie.get('tagPattern') || ".*"
  	// I hate writing javascript in HTML... but what's a better alternative
    this.logElement = document.createElement('div')
    document.body.appendChild(this.logElement)
    //Element.hide(this.logElement)  //rewrited by leixb
	this.logElement.style.display = "none";
	this.logElement.style.position = "absolute"
    this.logElement.style.left = '0px'
    this.logElement.style.width = '100%'

    this.logElement.style.textAlign = "left"
    this.logElement.style.fontFamily = "lucida console"
    this.logElement.style.fontSize = "100%"
    this.logElement.style.backgroundColor = 'darkgray'      
    this.logElement.style.opacity = 0.9 
    this.logElement.style.zIndex = 2000 

    // Add toolbarElement
    this.toolbarElement = document.createElement('div')
    this.logElement.appendChild(this.toolbarElement)     
    this.toolbarElement.style.padding = "0 0 0 2px"

    // Add buttons        
    this.buttonsContainerElement = document.createElement('span')
    this.toolbarElement.appendChild(this.buttonsContainerElement) 

    this.buttonsContainerElement.innerHTML += '<button onclick="logConsole.toggle()" style="float:right;color:black">close</button>'
    this.buttonsContainerElement.innerHTML += '<button onclick="Logger.clear()" style="float:right;color:black">clear</button>'        


		//Add Tag Filter
	this.tagFilterContainerElement = document.createElement('span')
    this.toolbarElement.appendChild(this.tagFilterContainerElement) 
    this.tagFilterContainerElement.style.cssFloat = 'left'
    this.tagFilterContainerElement.appendChild(document.createTextNode("Log Filter"))
    
    this.tagFilterElement = document.createElement('input')
    this.tagFilterContainerElement.appendChild(this.tagFilterElement)  
    this.tagFilterElement.style.width = '200px'                    
    this.tagFilterElement.value = this.tagPattern    
    this.tagFilterElement.setAttribute('autocomplete', 'off') // So Firefox doesn't flip out
    
    //Event.observe(this.tagFilterElement, 'keyup', this.updateTags.bind(this))
    //Event.observe(this.tagFilterElement, 'click', function() {this.tagFilterElement.select()}.bind(this))    
    var __method = this;
    this.tagFilterElement.onkeyup = function(e){__method.updateTags();};
    //this.tagFilterElement.onclick = function() {this.tagFilterElement.select()}.bind(this);
    // Add outputElement
    this.outputElement = document.createElement('div')
    this.logElement.appendChild(this.outputElement)  
    this.outputElement.style.overflow = "auto"              
    this.outputElement.style.clear = "both"
    this.outputElement.style.height = "200px"
    this.outputElement.style.backgroundColor = 'black' 
          
    this.inputContainerElement = document.createElement('div')
    this.inputContainerElement.style.width = "100%"
    this.logElement.appendChild(this.inputContainerElement)      
    
    this.inputElement = document.createElement('input')
    this.inputContainerElement.appendChild(this.inputElement)  
    this.inputElement.style.width = '100%'                    
    this.inputElement.style.borderWidth = '0px' // Inputs with 100% width always seem to be too large (I HATE THEM) they only work if the border, margin and padding are 0
    this.inputElement.style.margin = '0px'
    this.inputElement.style.padding = '0px'
    this.inputElement.value = 'Type command here' 
    this.inputElement.setAttribute('autocomplete', 'off') // So Firefox doesn't flip out
    //Event.observe(this.inputElement, 'keyup', this.handleInput.bind(this))
    //Event.observe(this.inputElement, 'click', function() {this.inputElement.select()}.bind(this))    
    this.inputElement.onkeyup = function(e){	__method.handleInput()};
    //this.inputElement.onclick = function() {this.inputElement.select()}.bind(this);
	
	window.setInterval(function(){
    	__method.repositionWindow();
	}, 500)
	this.repositionWindow()
    Logger.onupdate.addListener(function(oneLogEntry){	__method.logUpdate(oneLogEntry);})
    Logger.onclear.addListener(function(oneLogEntry){	__method.clear(oneLogEntry);})		

    // Preload log element with the log entries that have been entered
	for (var i = 0; i < Logger.logEntries.length; i++) {
  		this.logUpdate(Logger.logEntries[i])
  	}   
  	
  	// Feed all errors into the logger (For some unknown reason I can only get this to work
  	// with an inline event declaration)
  	//Event.observe(window, 'error', function(msg, url, lineNumber) {Logger.error("Error in (" + (url || location) + ") on line "+lineNumber+"", msg)})

    // Allow acess key link          
    var accessElement = document.createElement('span')
    accessElement.innerHTML = '<button style="position:absolute;top:-100px" onclick="javascript:logConsole.toggle()" accesskey="w"></button>'
  	document.body.appendChild(accessElement)

	if (Cookie.get('ConsoleVisible') == 'true') {
	  this.toggle()
	}
}
LogConsole.prototype = {  

	// Properties
	// ----------
	commandHistory : [],
	commandIndex : 0,
	
	// Methods
	// -------
	toggle : function() {
	    if(this.logElement.style.display == 'none') {
		  	this.show();
		}else {
			this.hide();
		}
	}, 
	
	show : function() {
	  	this.logElement.style.display = "";
	  	//滚动至顶层
	  	this.outputElement.scrollTop = this.outputElement.scrollHeight // Scroll to bottom when toggled
	  	Cookie.set('ConsoleVisible', 'true')
 	  	//this.inputElement.select()
	}, 
	
	hide : function() {
	  	this.logElement.style.display = "none";
	  	Cookie.set('ConsoleVisible', 'false')
	},  
	output : function(message, style) {
		// If we are at the bottom of the window, then keep scrolling with the output			
		var shouldScroll = (this.outputElement.scrollTop + (2 * this.outputElement.clientHeight)) >= this.outputElement.scrollHeight
		this.outputCount++
	  	style = (style ? style += ';' : '')	  	
	  	style += 'padding:1px;margin:0 0 5px 0'	     
		if (this.outputCount % 2 == 0) style += ";background-color:#101010"
	  	message = message || "undefined"
	  	//转义html
	  	message = message.toString().replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
	  	this.outputElement.innerHTML += "<pre style='" + style + "'>" + message + "</pre>"
	  	if (shouldScroll) {				
			this.outputElement.scrollTop = this.outputElement.scrollHeight
		}
	},
	
	updateTags : function() {
		var pattern = this.tagFilterElement.value
		if (this.tagPattern == pattern) return
		try {
			new RegExp(pattern)
		}catch (e) {
			return
		}
		this.tagPattern = pattern
		Cookie.set('tagPattern', this.tagPattern)
		this.outputElement.innerHTML = ""
		// Go through each log entry again
		this.outputCount = 0;
		for (var i = 0; i < Logger.logEntries.length; i++) {
  		this.logUpdate(Logger.logEntries[i])
  	}  
	},
	//获取元素
	getDimensions: function(element) {
	    var display = element.style.display;
	    if(display != 'none' && display != null) // Safari bug
	      	return {width: element.offsetWidth, height: element.offsetHeight};
	    // All *Width and *Height properties give 0 on elements with display none,
	    // so enable the element temporarily
	    var els = element.style;
	    var originalVisibility = els.visibility;
	    var originalPosition = els.position;
	    var originalDisplay = els.display;
	    els.visibility = 'hidden';
	    els.position = 'absolute';
	    els.display = 'block';
	    var originalWidth = element.clientWidth;
	    var originalHeight = element.clientHeight;
	    els.display = originalDisplay;
	    els.position = originalPosition;
	    els.visibility = originalVisibility;
	    return {width: originalWidth, height: originalHeight};
    },
    //修改日志窗口的位置使其为与窗口下端
	repositionWindow : function() {
		var offset = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
		var pageHeight = self.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
		this.logElement.style.top = (offset + pageHeight - this.getDimensions(this.logElement).height) + "px"
	},

	// Event Handlers
	// --------------

	logUpdate : function(logEntry) {
		if (logEntry.tag.search(new RegExp(this.tagPattern, 'igm')) == -1) 
			return
		var style = ''
	  	if (logEntry.tag.search(/error/) != -1) style += 'color:red'
	  	else if (logEntry.tag.search(/warning/) != -1) style += 'color:orange'
	  	else if (logEntry.tag.search(/debug/) != -1) style += 'color:springgreen'
 	  	else if (logEntry.tag.search(/info/) != -1) style += 'color:white'
	  	else style += 'color:yellow'
		this.output(logEntry.message, style)
	},

	clear : function(e) {
		this.outputElement.innerHTML = ""
	},

	handleInput : function() {
		var e = event;
		if (e.keyCode == 13 ) {//KEY_RETURN    
	  		var command = this.inputElement.value
		  	switch(command) {
		    	case "clear":
		      		Logger.clear()  
		      		break
			    	
		    	default:        
		      	var consoleOutput = "" 
		      	try {
		        	consoleOutput = eval(this.inputElement.value)	        	
		      	}catch (e) {  
		        	Logger.error("Problem parsing input <" + command + ">", e)	        	
		        	break
				}
				Logger.log(consoleOutput)	      	
		      	break
			}        
		
		  	if (this.inputElement.value != "" && this.inputElement.value != this.commandHistory[0]) {
		    	this.commandHistory.unshift(this.inputElement.value)
		  	}
		  	this.commandIndex = 0 
		  	this.inputElement.value = ""                                                        
		}else if (e.keyCode == 38 && this.commandHistory.length > 0) {//Event.KEY_UP
	    	this.inputElement.value = this.commandHistory[this.commandIndex]
			if (this.commandIndex < this.commandHistory.length - 1) {
	      		this.commandIndex += 1
	      	}
   	    }else if (e.keyCode == 40 && this.commandHistory.length > 0) {//Event.KEY_DOWN
	    	if (this.commandIndex > 0) {                                      
	      		this.commandIndex -= 1
		    }                       
			this.inputElement.value = this.commandHistory[this.commandIndex]
	    }else {
    		this.commandIndex = 0
    	}
	}
}         

// Load the Console when the window loads
//Event.observe(window, "load", function() {logConsole = new LogConsole()}) 
if(window.addEventListener) {//// firefox , w3c
        window.addEventListener("load", function() { logConsole = new LogConsole();}, false);
}else {//ie
        window.attachEvent("on"+"load",  function() { logConsole = new LogConsole();});
}
// -------------------------
// Helper Functions And Junk
// -------------------------
function inspect(element, hideProperties, hideMethods) {
	var properties = []
	var methods = [] 
	//element = $(element)
	for(var internal in element) {
		try {
			if(element[internal] instanceof Function) {
				if(!hideMethods) 
					methods.push(internal + ":\t" + element[internal] )
			}else {
				if(!hideProperties) 
					properties.push(internal + ":\t" + element[internal] )
			}
		}catch (e) {
			Logger.error("Excetion thrown while inspecting object.", e)
		}
	}
	properties.sort()
	methods.sort()
	var internals = properties.concat(methods)
	var output = ""
	for (var i = 0; i < internals.length; i++) {
		output += (internals[i] + "\n")
	}
	return output
}   
/*
Array.prototype.contains = function(object) {
	for(var i = 0; i < this.length; i++) {
		if (object == this[i]) return true
	}

	return false
}  
*/
// Helper Alias for simple logging
var puts = function() {return Logger.log(arguments[0], arguments[1])}      
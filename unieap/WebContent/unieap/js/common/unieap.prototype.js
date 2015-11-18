/**
 * @modify zhang_rui@neusoft.com
 */
Object.extend = function(destination, source){
    for (var property in source) 
        destination[property] = source[property];
    return destination;
};

var Hash = function(object){
  if (object instanceof Hash) this.merge(object);
  else Object.extend(this, object || {});
};

function $H(object){
  if (object instanceof Hash) return object;
  return new Hash(object);
};

var $A = function(iterable){
    if (!iterable) 
        return [];
    if (iterable.toArray) {
        return iterable.toArray();
    }
    else {
        var results = [];
        for (var i = 0, length = iterable.length; i < length; i++) 
            results.push(iterable[i]);
        return results;
    }
};

(function(){

    var Prototype = {
 		Version: '1.5.1.2',
        ScriptFragment: '<script[^>]*>([\\S\\s]*?)<\/script>',
        JSONFilter: /^\/\*-secure-([\s\S]*)\*\/\s*$/,
        
        emptyFunction: function(){
        },
        K: function(x){
            return x
        }
    };
    
    Object.extend(Object, {
        inspect: function(object){
            try {
                if (object === undefined) 
                    return 'undefined';
                if (object === null) 
                    return 'null';
                return object.inspect ? object.inspect() : object.toString();
            } 
            catch (e) {
                if (e instanceof RangeError) 
                    return '...';
                throw e;
            }
        },
        
        toJSON: function(object){
            var type = typeof object;
            switch (type) {
                case 'undefined':
                case 'function':
                case 'unknown':
                    return;case 'boolean':
                    return object.toString();
            }
            if (object === null) 
                return 'null';
            if (object.toJSON) 
                return object.toJSON();
            if (object.ownerDocument === document) 
                return;
            var results = [];
            for (var property in object) {
                var value = Object.toJSON(object[property]);
                if (value !== undefined) 
                    results.push(property.toJSON() + ': ' + value);
            }
            return '{' + results.join(', ') + '}';
        },
        
        keys: function(object){
            var keys = [];
            for (var property in object) 
                keys.push(property);
            return keys;
        },
        
        values: function(object){
            var values = [];
            for (var property in object) 
                values.push(object[property]);
            return values;
        },
        
        clone: function(object){
            return Object.extend({}, object);
        },
		
		isElement: function(object) {
    		return object && object.nodeType == 1;
		},
		
		isArray: function(object) {
			return object && object.constructor === Array;
		},
		
		isHash: function(object) {
			return object instanceof Hash;
		},
		
		isFunction: function(object) {
		    return typeof object == "function";
		},
		
		isString: function(object) {
		    return typeof object == "string";
		},
		
		isNumber: function(object) {
		    return typeof object == "number";
		},
		
		isUndefined: function(object) {
		    return typeof object == "undefined";
		}
		
    });
    
    Object.extend(Number.prototype, {
        toColorPart: function(){
            return this.toPaddedString(2, 16);
        },
        
        succ: function(){
            return this + 1;
        },
        
        times: function(iterator){
            $R(0, this, true).each(iterator);
            return this;
        },
        
        toPaddedString: function(length, radix){
            var string = this.toString(radix || 10);
            return '0'.times(length - string.length) + string;
        },
        
        toJSON: function(){
            return isFinite(this) ? this.toString() : 'null';
        }
    });
    
    Date.prototype.toJSON = function(){
        return '"' + this.getFullYear() + '-' +
        (this.getMonth() + 1).toPaddedString(2) +
        '-' +
        this.getDate().toPaddedString(2) +
        'T' +
        this.getHours().toPaddedString(2) +
        ':' +
        this.getMinutes().toPaddedString(2) +
        ':' +
        this.getSeconds().toPaddedString(2) +
        '"';
    };
    
    Object.extend(String.prototype, {
        gsub: function(pattern, replacement){
            var result = '', source = this, match;
            replacement = arguments.callee.prepareReplacement(replacement);
            
            while (source.length > 0) {
                if (match = source.match(pattern)) {
                    result += source.slice(0, match.index);
                    result += String.interpret(replacement(match));
                    source = source.slice(match.index + match[0].length);
                }
                else {
                    result += source, source = '';
                }
            }
            return result;
        },
        
        sub: function(pattern, replacement, count){
            replacement = this.gsub.prepareReplacement(replacement);
            count = count === undefined ? 1 : count;
            
            return this.gsub(pattern, function(match){
                if (--count < 0) 
                    return match[0];
                return replacement(match);
            });
        },
        
        scan: function(pattern, iterator){
            this.gsub(pattern, iterator);
            return this;
        },
        
        truncate: function(length, truncation){
            length = length || 30;
            truncation = truncation === undefined ? '...' : truncation;
            return this.length > length ? this.slice(0, length - truncation.length) + truncation : this;
        },
        
        strip: function(){
            return this.replace(/^\s+/, '').replace(/\s+$/, '');
        },
        
        stripTags: function(){
            return this.replace(/<\/?[^>]+>/gi, '');
        },
        
        stripScripts: function(){
            return this.replace(new RegExp(Prototype.ScriptFragment, 'img'), '');
        },
        
        extractScripts: function(){
            var matchAll = new RegExp(Prototype.ScriptFragment, 'img');
            var matchOne = new RegExp(Prototype.ScriptFragment, 'im');
            return (this.match(matchAll) || []).map(function(scriptTag){
                return (scriptTag.match(matchOne) || ['', ''])[1];
            });
        },
        
        evalScripts: function(){
            return this.extractScripts().map(function(script){
                return eval(script)
            });
        },
        
        escapeHTML: function(){
            var self = arguments.callee;
            self.text.data = this;
            return self.div.innerHTML;
        },
        
        unescapeHTML: function(){
            var div = document.createElement('div');
            div.innerHTML = this.stripTags();
            return div.childNodes[0] ? (div.childNodes.length > 1 ? $A(div.childNodes).inject('', function(memo, node){
                return memo + node.nodeValue
            }) : div.childNodes[0].nodeValue) : '';
        },
        
        toQueryParams: function(separator){
            var match = this.strip().match(/([^?#]*)(#.*)?$/);
            if (!match) 
                return {};
            
            return match[1].split(separator || '&').inject({}, function(hash, pair){
                if ((pair = pair.split('='))[0]) {
                    var key = decodeURIComponent(pair.shift());
                    var value = pair.length > 1 ? pair.join('=') : pair[0];
                    if (value != undefined) 
                        value = decodeURIComponent(value);
                    
                    if (key in hash) {
                        if (hash[key].constructor != Array) 
                            hash[key] = [hash[key]];
                        hash[key].push(value);
                    }
                    else 
                        hash[key] = value;
                }
                return hash;
            });
        },
        
        toArray: function(){
            return this.split('');
        },
        
        succ: function(){
            return this.slice(0, this.length - 1) +
            String.fromCharCode(this.charCodeAt(this.length - 1) + 1);
        },
        
        times: function(count){
            var result = '';
            for (var i = 0; i < count; i++) 
                result += this;
            return result;
        },
        
        camelize: function(){
            var parts = this.split('-'), len = parts.length;
            if (len == 1) 
                return parts[0];
            
            var camelized = this.charAt(0) == '-' ? parts[0].charAt(0).toUpperCase() + parts[0].substring(1) : parts[0];
            
            for (var i = 1; i < len; i++) 
                camelized += parts[i].charAt(0).toUpperCase() + parts[i].substring(1);
            
            return camelized;
        },
        
        capitalize: function(){
            return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
        },
        
        underscore: function(){
            return this.gsub(/::/, '/').gsub(/([A-Z]+)([A-Z][a-z])/, '#{1}_#{2}').gsub(/([a-z\d])([A-Z])/, '#{1}_#{2}').gsub(/-/, '_').toLowerCase();
        },
        
        dasherize: function(){
            return this.gsub(/_/, '-');
        },
        
        inspect: function(useDoubleQuotes){
            var escapedString = this.gsub(/[\x00-\x1f\\]/, function(match){
                var character = String.specialChar[match[0]];
                return character ? character : '\\u00' + match[0].charCodeAt().toPaddedString(2, 16);
            });
            if (useDoubleQuotes) 
                return '"' + escapedString.replace(/"/g, '\\"') + '"';
            return "'" + escapedString.replace(/'/g, '\\\'') + "'";
        },
        
        toJSON: function(){
            return this.inspect(true);
        },
        
        unfilterJSON: function(filter){
            return this.sub(filter || Prototype.JSONFilter, '#{1}');
        },
        
        isJSON: function(){
            var str = this.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, '');
            return (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(str);
        },
        
        evalJSON: function(sanitize){
            var json = this.unfilterJSON();
            try {
                if (!sanitize || json.isJSON()) 
                    return eval('(' + json + ')');
            } 
            catch (e) {
            }
            throw new SyntaxError('Badly formed JSON string: ' + this.inspect());
        },
        
        include: function(pattern){
            return this.indexOf(pattern) > -1;
        },
        
        startsWith: function(pattern){
            return this.indexOf(pattern) === 0;
        },
        
        endsWith: function(pattern){
            var d = this.length - pattern.length;
            return d >= 0 && this.lastIndexOf(pattern) === d;
        },
        
        empty: function(){
            return this == '';
        },
        
        blank: function(){
            return /^\s*$/.test(this);
        }
    });
    
    var $break = {}, $continue = new Error('"throw $continue" is deprecated, use "return" instead');
    
    var Enumerable = {
        each: function(iterator){
            var index = 0;
            try {
                this._each(function(value){
                    iterator(value, index++);
                });
            } 
            catch (e) {
                if (e != $break) 
                    throw e;
            }
            return this;
        },
        
        eachSlice: function(number, iterator){
            var index = -number, slices = [], array = this.toArray();
            while ((index += number) < array.length) 
                slices.push(array.slice(index, index + number));
            return slices.map(iterator);
        },
        
        all: function(iterator){
            var result = true;
            this.each(function(value, index){
                result = result && !!(iterator || Prototype.K)(value, index);
                if (!result) 
                    throw $break;
            });
            return result;
        },
        
        any: function(iterator){
            var result = false;
            this.each(function(value, index){
                if (result = !!(iterator || Prototype.K)(value, index)) 
                    throw $break;
            });
            return result;
        },
        
        collect: function(iterator){
            var results = [];
            this.each(function(value, index){
                results.push((iterator || Prototype.K)(value, index));
            });
            return results;
        },
        
        detect: function(iterator){
            var result;
            this.each(function(value, index){
                if (iterator(value, index)) {
                    result = value;
                    throw $break;
                }
            });
            return result;
        },
        
        findAll: function(iterator){
            var results = [];
            this.each(function(value, index){
                if (iterator(value, index)) 
                    results.push(value);
            });
            return results;
        },
        
        grep: function(pattern, iterator){
            var results = [];
            this.each(function(value, index){
                var stringValue = value.toString();
                if (stringValue.match(pattern)) 
                    results.push((iterator || Prototype.K)(value, index));
            })
            return results;
        },
        
        include: function(object){
            var found = false;
            this.each(function(value){
                if (value == object) {
                    found = true;
                    throw $break;
                }
            });
            return found;
        },
        
        inGroupsOf: function(number, fillWith){
            fillWith = fillWith === undefined ? null : fillWith;
            return this.eachSlice(number, function(slice){
                while (slice.length < number) 
                    slice.push(fillWith);
                return slice;
            });
        },
        
        inject: function(memo, iterator){
            this.each(function(value, index){
                memo = iterator(memo, value, index);
            });
            return memo;
        },
        
        invoke: function(method){
            var args = $A(arguments).slice(1);
            return this.map(function(value){
                return value[method].apply(value, args);
            });
        },
        
        max: function(iterator){
            var result;
            this.each(function(value, index){
                value = (iterator || Prototype.K)(value, index);
                if (result == undefined || value >= result) 
                    result = value;
            });
            return result;
        },
        
        min: function(iterator){
            var result;
            this.each(function(value, index){
                value = (iterator || Prototype.K)(value, index);
                if (result == undefined || value < result) 
                    result = value;
            });
            return result;
        },
        
        partition: function(iterator){
            var trues = [], falses = [];
            this.each(function(value, index){
                ((iterator || Prototype.K)(value, index) ? trues : falses).push(value);
            });
            return [trues, falses];
        },
        
        pluck: function(property){
            var results = [];
            this.each(function(value, index){
                results.push(value[property]);
            });
            return results;
        },
        
        reject: function(iterator){
            var results = [];
            this.each(function(value, index){
                if (!iterator(value, index)) 
                    results.push(value);
            });
            return results;
        },
        
        sortBy: function(iterator){
            return this.map(function(value, index){
                return {
                    value: value,
                    criteria: iterator(value, index)
                };
            }).sort(function(left, right){
                var a = left.criteria, b = right.criteria;
                return a < b ? -1 : a > b ? 1 : 0;
            }).pluck('value');
        },
        
        toArray: function(){
            return this.map();
        },
        
        zip: function(){
            var iterator = Prototype.K, args = $A(arguments);
            if (typeof args.last() == 'function') 
                iterator = args.pop();
            
            var collections = [this].concat(args).map($A);
            return this.map(function(value, index){
                return iterator(collections.pluck(index));
            });
        },
        
        size: function(){
            return this.toArray().length;
        },
        
        inspect: function(){
            return '#<Enumerable:' + this.toArray().inspect() + '>';
        }
    }
    
    Object.extend(Enumerable, {
        map: Enumerable.collect,
        find: Enumerable.detect,
        select: Enumerable.findAll,
        member: Enumerable.include,
        entries: Enumerable.toArray
    });
    
    Object.extend(Array.prototype, Enumerable);
    
    if (!Array.prototype._reverse) 
        Array.prototype._reverse = Array.prototype.reverse;
    
    Object.extend(Array.prototype, {
        _each: function(iterator){
            for (var i = 0, length = this.length; i < length; i++) 
                iterator(this[i]);
        },
        
        clear: function(){
            this.length = 0;
            return this;
        },
        
        first: function(){
            return this[0];
        },
        
        last: function(){
            return this[this.length - 1];
        },
        
        compact: function(){
            return this.select(function(value){
                return value != null;
            });
        },
        
        flatten: function(){
            return this.inject([], function(array, value){
                return array.concat(value && value.constructor == Array ? value.flatten() : [value]);
            });
        },
        
        without: function(){
            var values = $A(arguments);
            return this.select(function(value){
                return !values.include(value);
            });
        },
        
        indexOf: function(object){
            for (var i = 0, length = this.length; i < length; i++) 
                if (this[i] == object) 
                    return i;
            return -1;
        },
        
        reverse: function(inline){
            return (inline !== false ? this : this.toArray())._reverse();
        },
        
        reduce: function(){
            return this.length > 1 ? this : this[0];
        },
        
        uniq: function(sorted){
            return this.inject([], function(array, value, index){
                if (0 == index || (sorted ? array.last() != value : !array.include(value))) 
                    array.push(value);
                return array;
            });
        },
        
        clone: function(){
            return [].concat(this);
        },
        
        size: function(){
            return this.length;
        },
        
        inspect: function(){
            return '[' + this.map(Object.inspect).join(', ') + ']';
        },
        
        toJSON: function(){
            var results = [];
            this.each(function(object){
                var value = Object.toJSON(object);
                if (value !== undefined) 
                    results.push(value);
            });
            return '[' + results.join(', ') + ']';
        }
    });
    
    Array.prototype.toArray = Array.prototype.clone;
    
    Object.extend(Hash, {
        toQueryString: function(obj){
            var parts = [];
            parts.add = arguments.callee.addPair;
            
            this.prototype._each.call(obj, function(pair){
                if (!pair.key) 
                    return;
                var value = pair.value;
                
                if (value && typeof value == 'object') {
                    if (value.constructor == Array) 
                        value.each(function(value){
                            parts.add(pair.key, value);
                        });
                    return;
                }
                parts.add(pair.key, value);
            });
            
            return parts.join('&');
        },
        
        toJSON: function(object){
            var results = [];
            this.prototype._each.call(object, function(pair){
                var value = Object.toJSON(pair.value);
                if (value !== undefined) 
                    results.push(pair.key.toJSON() + ': ' + value);
            });
            return '{' + results.join(', ') + '}';
        }
    });
    
    Hash.toQueryString.addPair = function(key, value, prefix){
        key = encodeURIComponent(key);
        if (value === undefined) 
            this.push(key);
        else 
            this.push(key + '=' + (value == null ? '' : encodeURIComponent(value)));
    }
    
    Object.extend(Hash.prototype, Enumerable);
    Object.extend(Hash.prototype, {
        _each: function(iterator){
            for (var key in this) {
                var value = this[key];
                if (value && value == Hash.prototype[key]) 
                    continue;
                
                var pair = [key, value];
                pair.key = key;
                pair.value = value;
                iterator(pair);
            }
        },
		
        set: function(key, value) {
			
	      	return this[key] = value;
	    },
	
	    get: function(key) {
	      	return this[key];
	    },
	
	    unset: function(key) {
	      	var value = this[key];
	      	delete this[key];
	      	return value;
	    },

    	toObject: function() {
      		return Object.clone(this._object);
    	},
		
        keys: function(){
            return this.pluck('key');
        },
        
        values: function(){
            return this.pluck('value');
        },
        
        merge: function(hash){
            return $H(hash).inject(this, function(mergedHash, pair){
                mergedHash[pair.key] = pair.value;
                return mergedHash;
            });
        },
        
        remove: function(){
            var result;
            for (var i = 0, length = arguments.length; i < length; i++) {
                var value = this[arguments[i]];
                if (value !== undefined) {
                    if (result === undefined) 
                        result = value;
                    else {
                        if (result.constructor != Array) 
                            result = [result];
                        result.push(value)
                    }
                }
                delete this[arguments[i]];
            }
            return result;
        },
        
        toQueryString: function(){
            return Hash.toQueryString(this);
        },
        
        inspect: function(){
            return '#<Hash:{' +
            this.map(function(pair){
                return pair.map(Object.inspect).join(': ');
            }).join(', ') +
            '}>';
        },
        toJSON: function(){
            return Hash.toJSON(this);
        }
    });
    
})();

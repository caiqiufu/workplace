Ext.define('AClassDefine', //定义类名
		{
		name: 'a Property', //一个属性
		title: 'other Property', //另外一个属性
		fnShowMsg: function () {
		    var me = this;    //定义一个变量存本身的this，以防其他地方调用作用域改变，照成this的混乱
		    //alert(me.name + me.title);
		}
});
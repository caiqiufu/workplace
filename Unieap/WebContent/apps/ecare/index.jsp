<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" type="image/x-icon"  href="http://img0.shop.10086.cn/favicon.ico__4.ico" />
<meta name="WT.location" content="200" />
<meta name="keywords" content="中国移动网上商城,网上营业厅,话费查询,充话费,查流量 移动4G手机,移动合约机,移动4G套餐,移动业务办理,积分兑换" />
<meta name="description" content="中国移动网上商城为移动客户提供充值交费,账单查询,流量、4G套餐、宽带等业务介绍及办理,提供苹果、三星、小米等多款正品低价手机在线购买,让您足不出户,享受便捷移动服务！" />
<title>中国移动网上商城_广东广州_话费查询与充值,手机流量查询,4G套餐办理,移动宽带,手机正品低价</title>
<link href="http://img0.shop.10086.cn/combo/__139/script/interactive/shop/interactive.css__www/core/css/global.css__www/core/css/structure/structure.css__www/core/css/btn/btn.css__www/core/css/header_footer/header_footer.css__www/core/css/pop/pop.css__www/index/css/index.css__www/mod/conner/css/conner.css" rel="stylesheet" type="text/css" />
</head>
<body>




<script type="text/html" id="login_box">
<div class="minloginbox loadingBlock clearfix" style="width:350px;">
	<iframe src="https://login.10086.cn/SSOCheck.action?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fsso%2Fminilogincallback.php%3Faction%3Dforcelogin" width="350" height="400" allowTransparency="true" frameborder="0" scrolling="no"></iframe>
</div>
</script>

	
	<div class="topNav">
		<div class="clearfix globalMain">
			<p class="leftMenu clearfix">
				<a href="http://www.10086.cn/gd/" class="current">个人客户</a>
				<a href="http://www.10086.cn/power/">政企客户</a>
			</p>
			<p class="floatleft">
				欢迎来到中国移动！
			</p>
			<div class="rightMenu clearfix">
				<span id="userinfo" class="floatleft">
					<a href="https://login.10086.cn/SSOCheck.action?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1" class="bold red marginRight10">登录商城</a>
					<a href="https://login.10086.cn/html/register/register.html">[注册]</a>
				</span><em>|</em>
																		<a href="http://www.10086.cn/cmccclient/">手机营业厅</a>
													<em>|</em>					<a href="http://www.10086.cn/iphone/">iphone专区</a>
													<em>|</em>					<a href="http://www.10086.cn/support/focus/onlineservice/index.htm">在线客服</a>
													<em>|</em>					<a href="http://www.10086.cn/aboutus/gd/">关于中国移动</a>
													<em>|</em>					<a href="http://www.chinamobileltd.com/en/global/home.php">ENGLISH</a>
							</div>
		</div>
	</div>
	
	
	
	<iframe src="about:blank" id="ssocheckframe" style="display:none"></iframe>
	<script type="text/javascript">

	function logout(){
		if (window.$){
			$.getJSON('/ajax/user/logout.json',function(r){
				if(r.code) alert(r.msg);
				else location.href="/";
			});
		}
	}
	window.allcity ={ "200": [{"city_id":200,"city_name":"\u5e7f\u5dde","weight":440100,"province_id":200,"extend":"guangzhou"},{"city_id":751,"city_name":"\u97f6\u5173","weight":440200,"province_id":200,"extend":"shaoguan"},{"city_id":755,"city_name":"\u6df1\u5733","weight":440300,"province_id":200,"extend":"shenzhen"},{"city_id":756,"city_name":"\u73e0\u6d77","weight":440400,"province_id":200,"extend":"zhuhai"},{"city_id":754,"city_name":"\u6c55\u5934","weight":440500,"province_id":200,"extend":"shantou"},{"city_id":757,"city_name":"\u4f5b\u5c71","weight":440600,"province_id":200,"extend":"foshan"},{"city_id":750,"city_name":"\u6c5f\u95e8","weight":440700,"province_id":200,"extend":"jiangmen"},{"city_id":759,"city_name":"\u6e5b\u6c5f","weight":440800,"province_id":200,"extend":"zhanjiang"},{"city_id":668,"city_name":"\u8302\u540d","weight":440900,"province_id":200,"extend":"maoming"},{"city_id":758,"city_name":"\u8087\u5e86","weight":441200,"province_id":200,"extend":"zhaoqing"},{"city_id":752,"city_name":"\u60e0\u5dde","weight":441300,"province_id":200,"extend":"huizhou"},{"city_id":753,"city_name":"\u6885\u5dde","weight":441400,"province_id":200,"extend":"meizhou"},{"city_id":660,"city_name":"\u6c55\u5c3e","weight":441500,"province_id":200,"extend":"shanwei"},{"city_id":762,"city_name":"\u6cb3\u6e90","weight":441600,"province_id":200,"extend":"heyuan"},{"city_id":662,"city_name":"\u9633\u6c5f","weight":441700,"province_id":200,"extend":"yangjiang"},{"city_id":763,"city_name":"\u6e05\u8fdc","weight":441800,"province_id":200,"extend":"qingyuan"},{"city_id":769,"city_name":"\u4e1c\u839e","weight":441900,"province_id":200,"extend":"dongguan"},{"city_id":760,"city_name":"\u4e2d\u5c71","weight":442000,"province_id":200,"extend":"zhongshan"},{"city_id":768,"city_name":"\u6f6e\u5dde","weight":445100,"province_id":200,"extend":"chaozhou"},{"city_id":663,"city_name":"\u63ed\u9633","weight":445200,"province_id":200,"extend":"jieyang"},{"city_id":766,"city_name":"\u4e91\u6d6e","weight":445300,"province_id":200,"extend":"yunfu"}]};
	</script>
	<div class="globalMain clearfix" id="header">
		
		<div class="search_mlogo clearfix">
			<span class="logo_10086"><img src="http://img0.shop.10086.cn/www/core/css/header_footer/logo_10086.png__36.png" /></span>
			<!--[if IE]>
				<style>
				#header .search .txt{padding-top:6px;}
				</style>
			<![endif]-->
			<div class="search">
				<form class="clearfix" action="/search/index.php" method="get">
					<input type="text" name="key" class="txt" iprompt="请输入搜索内容" />
					<input type="submit" class="b" value="" />
					<input type="hidden" class="txt" name="province_id" value="200" />
					<input type="hidden" class="txt" name="city_id" value="200" />
				</form>
			</div>
		</div>
		
		<div class="clearfix">
			<div class="logoWhere clearfix">
				<a href="http://www.10086.cn/gd/" class="logo"><img src="http://img0.shop.10086.cn/www/core/css/header_footer/logo.gif__99.gif" /></a>
				<div class="floatleft clearfix" style="padding-top:1px;">
					<div class="area province">
						<span class="iconSharp sheng ac_show_areabar" province_id="200">广东</span>
						<ul class="provinceSort">
												<li>
							<dl class="clearfix">
								<dt>A-G</dt>
																<dd>
                                    <a province_id="100" class="ac_show_city" iscity="1"  href="javascript:void(0)">北京</a>
                                </dd>
																<dd>
                                    <a province_id="551" class="ac_show_city" iscity=""  href="javascript:void(0)">安徽</a>
                                </dd>
																<dd>
                                    <a province_id="230" class="ac_show_city" iscity="1"  href="javascript:void(0)">重庆</a>
                                </dd>
																<dd>
                                    <a province_id="591" class="ac_show_city" iscity=""  href="javascript:void(0)">福建</a>
                                </dd>
																<dd>
                                    <a province_id="200" class="ac_show_city" iscity=""  href="javascript:void(0)">广东</a>
                                </dd>
																<dd>
                                    <a province_id="771" class="ac_show_city" iscity=""  href="javascript:void(0)">广西</a>
                                </dd>
																<dd>
                                    <a province_id="931" class="ac_show_city" iscity=""  href="javascript:void(0)">甘肃</a>
                                </dd>
																<dd>
                                    <a province_id="851" class="ac_show_city" iscity=""  href="javascript:void(0)">贵州</a>
                                </dd>
															</dl>
						</li>
												<li>
							<dl class="clearfix">
								<dt>H-J</dt>
																<dd>
                                    <a province_id="311" class="ac_show_city" iscity=""  href="javascript:void(0)">河北</a>
                                </dd>
																<dd>
                                    <a province_id="371" class="ac_show_city" iscity=""  href="javascript:void(0)">河南</a>
                                </dd>
																<dd>
                                    <a province_id="898" class="ac_show_city" iscity=""  href="javascript:void(0)">海南</a>
                                </dd>
																<dd>
                                    <a province_id="270" class="ac_show_city" iscity=""  href="javascript:void(0)">湖北</a>
                                </dd>
																<dd>
                                    <a province_id="731" class="ac_show_city" iscity=""  href="javascript:void(0)">湖南</a>
                                </dd>
																<dd>
                                    <a province_id="451" class="ac_show_city" iscity=""  href="javascript:void(0)">黑龙江</a>
                                </dd>
																<dd>
                                    <a province_id="431" class="ac_show_city" iscity=""  href="javascript:void(0)">吉林</a>
                                </dd>
																<dd>
                                    <a province_id="250" class="ac_show_city" iscity=""  href="javascript:void(0)">江苏</a>
                                </dd>
																<dd>
                                    <a province_id="791" class="ac_show_city" iscity=""  href="javascript:void(0)">江西</a>
                                </dd>
															</dl>
						</li>
												<li>
							<dl class="clearfix">
								<dt>L-S</dt>
																<dd>
                                    <a province_id="240" class="ac_show_city" iscity=""  href="javascript:void(0)">辽宁</a>
                                </dd>
																<dd>
                                    <a province_id="471" class="ac_show_city" iscity=""  href="javascript:void(0)">内蒙古</a>
                                </dd>
																<dd>
                                    <a province_id="951" class="ac_show_city" iscity=""  href="javascript:void(0)">宁夏</a>
                                </dd>
																<dd>
                                    <a province_id="971" class="ac_show_city" iscity=""  href="javascript:void(0)">青海</a>
                                </dd>
																<dd>
                                    <a province_id="210" class="ac_show_city" iscity="1"  href="javascript:void(0)">上海</a>
                                </dd>
																<dd>
                                    <a province_id="280" class="ac_show_city" iscity=""  href="javascript:void(0)">四川</a>
                                </dd>
																<dd>
                                    <a province_id="531" class="ac_show_city" iscity=""  href="javascript:void(0)">山东</a>
                                </dd>
																<dd>
                                    <a province_id="351" class="ac_show_city" iscity=""  href="javascript:void(0)">山西</a>
                                </dd>
																<dd>
                                    <a province_id="290" class="ac_show_city" iscity=""  href="javascript:void(0)">陕西</a>
                                </dd>
															</dl>
						</li>
												<li>
							<dl class="clearfix">
								<dt>T-Z</dt>
																<dd>
                                    <a province_id="220" class="ac_show_city" iscity="1"  href="javascript:void(0)">天津</a>
                                </dd>
																<dd>
                                    <a province_id="991" class="ac_show_city" iscity=""  href="javascript:void(0)">新疆</a>
                                </dd>
																<dd>
                                    <a province_id="891" class="ac_show_city" iscity=""  href="javascript:void(0)">西藏</a>
                                </dd>
																<dd>
                                    <a province_id="871" class="ac_show_city" iscity=""  href="javascript:void(0)">云南</a>
                                </dd>
																<dd>
                                    <a province_id="571" class="ac_show_city" iscity=""  href="javascript:void(0)">浙江</a>
                                </dd>
															</dl>
						</li>
												</ul>
					</div>
										<div class="area city">
						<span class="iconSharp shi ac_show_areabar">广州</span>
						<ul>
															<li style="height:24px;overflow:hidden"><a title="广州" href="javascript:void(0)" class="ac_city_choose" location="200_200" >广州</a></li>
															<li style="height:24px;overflow:hidden"><a title="韶关" href="javascript:void(0)" class="ac_city_choose" location="200_751" >韶关</a></li>
															<li style="height:24px;overflow:hidden"><a title="深圳" href="javascript:void(0)" class="ac_city_choose" location="200_755" >深圳</a></li>
															<li style="height:24px;overflow:hidden"><a title="珠海" href="javascript:void(0)" class="ac_city_choose" location="200_756" >珠海</a></li>
															<li style="height:24px;overflow:hidden"><a title="汕头" href="javascript:void(0)" class="ac_city_choose" location="200_754" >汕头</a></li>
															<li style="height:24px;overflow:hidden"><a title="佛山" href="javascript:void(0)" class="ac_city_choose" location="200_757" >佛山</a></li>
															<li style="height:24px;overflow:hidden"><a title="江门" href="javascript:void(0)" class="ac_city_choose" location="200_750" >江门</a></li>
															<li style="height:24px;overflow:hidden"><a title="湛江" href="javascript:void(0)" class="ac_city_choose" location="200_759" >湛江</a></li>
															<li style="height:24px;overflow:hidden"><a title="茂名" href="javascript:void(0)" class="ac_city_choose" location="200_668" >茂名</a></li>
															<li style="height:24px;overflow:hidden"><a title="肇庆" href="javascript:void(0)" class="ac_city_choose" location="200_758" >肇庆</a></li>
															<li style="height:24px;overflow:hidden"><a title="惠州" href="javascript:void(0)" class="ac_city_choose" location="200_752" >惠州</a></li>
															<li style="height:24px;overflow:hidden"><a title="梅州" href="javascript:void(0)" class="ac_city_choose" location="200_753" >梅州</a></li>
															<li style="height:24px;overflow:hidden"><a title="汕尾" href="javascript:void(0)" class="ac_city_choose" location="200_660" >汕尾</a></li>
															<li style="height:24px;overflow:hidden"><a title="河源" href="javascript:void(0)" class="ac_city_choose" location="200_762" >河源</a></li>
															<li style="height:24px;overflow:hidden"><a title="阳江" href="javascript:void(0)" class="ac_city_choose" location="200_662" >阳江</a></li>
															<li style="height:24px;overflow:hidden"><a title="清远" href="javascript:void(0)" class="ac_city_choose" location="200_763" >清远</a></li>
															<li style="height:24px;overflow:hidden"><a title="东莞" href="javascript:void(0)" class="ac_city_choose" location="200_769" >东莞</a></li>
															<li style="height:24px;overflow:hidden"><a title="中山" href="javascript:void(0)" class="ac_city_choose" location="200_760" >中山</a></li>
															<li style="height:24px;overflow:hidden"><a title="潮州" href="javascript:void(0)" class="ac_city_choose" location="200_768" >潮州</a></li>
															<li style="height:24px;overflow:hidden"><a title="揭阳" href="javascript:void(0)" class="ac_city_choose" location="200_663" >揭阳</a></li>
															<li style="height:24px;overflow:hidden"><a title="云浮" href="javascript:void(0)" class="ac_city_choose" location="200_766" >云浮</a></li>
							
						</ul>
					</div>
									</div>
			</div>
			<ul class="clearfix withhover" id="nav">
													<li  class="first">
					<a  href="http://www.10086.cn/gd/">首页</a>
				</li>
																<li class="current">
					<a  href="/mall_200_200.html">移动商城</a>
					<span class="gongce"></span>
					<div class="shortcut">
						<ul>
							<li>
								<a href="http://shop.10086.cn/list/101_200_200_1_0_0.html" target="blank">买手机</a>
							</li>
							<li>
								<a href="http://shop.10086.cn/list/140_200_200_1_0_0.html" target="blank">办套餐</a>
							</li>
							<li>
								<a href="http://shop.10086.cn/list/146_200_200_1_0_0.html" target="blank">办业务</a>
							</li>
														<li>
								<a href="javascript:void(0)" style="color:#999">选号码</a>
							</li>
														<li>
								<a href="http://shop.10086.cn/list/128_200_200_1_0_0.html" target="blank">挑配件</a>
							</li>
							<li>
								<a href="http://shop.10086.cn/i/?f=rechargecredit" target="blank">立即充值</a>
							</li>
							<li class="last">
								<a href="http://shop.10086.cn/i/?f=home" target="blank">个人中心</a>
							</li>
						</ul>
					</div>
				</li>
																<li >
					<a  href="http://www.gd.10086.cn/my/">我的移动</a>
				</li>
																<li >
					<a  href="http://service.gd.10086.cn/index.jsp#home">网上营业厅</a>
				</li>
																<li >
					<a  href="http://www.10086.cn/support/gd/">服务与支持</a>
				</li>
									</ul>
		</div>
	</div>
	
	
	
	
	
		
	<div class="header_area_template" style="display:none;"> <!-- <li style="height:24px;overflow:hidden;"><a href="javascript:void(0)" class="ac_city_choose" location="{province_id}_{city_id}" title="{city_name}">{city_name}</a></li> --> </div>
	




<style text="text/css">
.goodsNavBanner .leftBox .sd_hat{_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader (enabled=bEnabled ,sizingMethod=crop, src="http://img0.shop.10086.cn/www/index/css/sd_hat.png__98.png");}
</style>
<div class="content clearfix">
    	<div class="goodsNavBanner clearfix">
		<div class="leftBox">
			<ul class="goodsNav clearfix withhover">
	<h1 class="h1 fontYaHei">商品服务分类</h1>
	<li>
		<a target="_blank" dcs_id="101" href="/list/101_200_200_0_0_0_0.html" class="sname iconNavPhone ac_dcstracker_homefirstscreen">买手机<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">
						<dl class="noBorderTop">
				<dt>品牌</dt>
				<dd class="clearfix">
					<a href="/list/101_200_200_1_0_0_1_0_0_0_0.html">华为</a>
					<a href="/list/101_200_200_1_0_0_2_0_0_0_0.html">三星</a>
					<a href="/list/101_200_200_1_0_0_3_0_0_0_0.html">苹果</a>
					<a href="/list/101_200_200_1_0_0_4_0_0_0_0.html">中兴</a>
					<a href="/list/101_200_200_1_0_0_5_0_0_0_0.html">魅族</a>
					<a href="/list/101_200_200_1_0_0_6_0_0_0_0.html">小米</a>
					<a href="/list/101_200_200_1_0_0_7_0_0_0_0.html">联想</a>
					<a href="/list/101_200_200_1_0_0_8_0_0_0_0.html">CMCC</a>
					<a href="/list/101_200_200_1_0_0_9_0_0_0_0.html">酷派</a>
					<a href="/list/101_200_200_1_0_0_10_0_0_0_0.html">HTC</a>
					<a href="/list/101_200_200_1_0_0_11_0_0_0_0.html">荣耀</a>
					<a href="/list/101_200_200_1_0_0_12_0_0_0_0.html">TCL</a>
					<a href="/list/101_200_200_1_0_0_13_0_0_0_0.html">大神</a>
					<a href="/list/101_200_200_1_0_0_14_0_0_0_0.html">VIVO</a>
									</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>价格</dt>
				<dd class="clearfix">
					<a href="/list/101_200_200_1_0_0_0_1_0_0_0.html">0-299</a>
					<a href="/list/101_200_200_1_0_0_0_2_0_0_0.html">300-699</a>
					<a href="/list/101_200_200_1_0_0_0_3_0_0_0.html">700-999</a>
					<a href="/list/101_200_200_1_0_0_0_4_0_0_0.html">1000-1999</a>
					<a href="/list/101_200_200_1_0_0_0_5_0_0_0.html">2000-2999</a>
					<a href="/list/101_200_200_1_0_0_0_6_0_0_0.html">3000以上</a>
									</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>尺寸</dt>
				<dd class="clearfix">
					<a href="/list/101_200_200_1_0_0_0_0_1_0_0.html">3.0英寸以下</a>
					<a href="/list/101_200_200_1_0_0_0_0_2_0_0.html">3.0-4.0英寸</a>
					<a href="/list/101_200_200_1_0_0_0_0_3_0_0.html">4.1-4.9英寸</a>
					<a href="/list/101_200_200_1_0_0_0_0_4_0_0.html">5.0-5.6英寸</a>
					<a href="/list/101_200_200_1_0_0_0_0_5_0_0.html">5.6英寸以上</a>
									</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>类型</dt>
				<dd class="clearfix">
					<a href="/list/101_200_200_1_0_0_0_0_0_1_0.html">裸机</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_2_0.html">合约机</a>
									</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>特色</dt>
				<dd class="clearfix">
					<a href="/list/101_200_200_1_0_0_0_0_0_0_1.html">旗舰机皇</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_2.html">热销爆款</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_3.html">超高性价比</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_4.html">清仓甩卖</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_5.html">拍照神器</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_6.html">超薄手机</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_7.html">双卡双待</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_8.html">超长待机</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_9.html">商务精英</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_10.html">时尚丽人</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_11.html">学生机</a>
					<a href="/list/101_200_200_1_0_0_0_0_0_0_12.html">老人机</a>
									</dd>
			</dl>
					</div>
	</li>
    
	<li>
		<a target="_blank" dcs_id="jiaohuafei" href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_.html" class="sname iconNavHuafei ac_dcstracker_homefirstscreen">交话费<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">
			<dl class="noBorderTop">
				<dt>话费充值</dt>
								<dd class="clearfix">
                                        <span>网银充值</span>
                                    </dd>
					<dd class="clearfix">
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_30.html" target="_blank">30元</a>
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_50.html" target="_blank">50元</a>
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_100.html" target="_blank">100元</a>
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_300.html" target="_blank">300元</a>
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_500.html" target="_blank">500元</a>
											<a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_000.html" target="_blank">任意金额</a>
										</dd>
								<dd class="clearfix">
                                        <a href="http://shop.10086.cn/i/?f=rechargecard" target="_blank" class="noBorder">充值卡充值</a>
                                    </dd>
					<dd class="clearfix">
										</dd>
							</dl>
		</div>
	</li>
    
	<li>
		<a target="_blank" dcs_id="140" href="/list/140_200_200_0_0_0_0.html" class="sname iconNavTaocan ac_dcstracker_homefirstscreen">办套餐<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">
			
						<dl class="noBorderTop">
				<dt>适用品牌</dt>
				<dd class="clearfix">
																									<a href="/list/140_200_200_1_0_0_1_0.html">全球通</a>
																				<a href="/list/140_200_200_1_0_0_2_0.html">神州行</a>
																				<a href="/list/140_200_200_1_0_0_3_0.html">动感地带</a>
														</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>套餐类型</dt>
				<dd class="clearfix">
																									<a href="/list/140_200_200_1_0_0_0_1.html">资费套餐</a>
																				<a href="/list/140_200_200_1_0_0_0_2.html">4G套餐</a>
														</dd>
			</dl>
					</div>
	</li>
	<li>
		<a target="_blank" dcs_id="146" href="/list/146_200_200_0_0_0_0.html" class="sname iconNavYewu ac_dcstracker_homefirstscreen">办业务<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">
						<dl class="noBorderTop">
				<dt>适用品牌</dt>
				<dd class="clearfix">
																									<a href="/list/146_200_200_1_0_0_1_0_0.html">全球通</a>
																				<a href="/list/146_200_200_1_0_0_2_0_0.html">神州行</a>
																				<a href="/list/146_200_200_1_0_0_3_0_0.html">动感地带</a>
														</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>使用习惯</dt>
				<dd class="clearfix">
																									<a href="/list/146_200_200_1_0_0_0_1_0.html">基础功能</a>
																				<a href="/list/146_200_200_1_0_0_0_2_0.html">新闻资讯</a>
																				<a href="/list/146_200_200_1_0_0_0_3_0.html">短彩信</a>
																				<a href="/list/146_200_200_1_0_0_0_4_0.html">手机报</a>
																				<a href="/list/146_200_200_1_0_0_0_5_0.html">生活娱乐</a>
																				<a href="/list/146_200_200_1_0_0_0_6_0.html">业务体验</a>
																				<a href="/list/146_200_200_1_0_0_0_7_0.html">上网必备</a>
																				<a href="/list/146_200_200_1_0_0_0_8_0.html">长途漫游</a>
														</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>业务类型</dt>
				<dd class="clearfix">
																									<a href="/list/146_200_200_1_0_0_0_0_1.html">流量套餐</a>
																				<a href="/list/146_200_200_1_0_0_0_0_2.html">数据业务</a>
																				<a href="/list/146_200_200_1_0_0_0_0_3.html">优惠业务包</a>
																				<a href="/list/146_200_200_1_0_0_0_0_4.html">积分兑换商品</a>
														</dd>
			</dl>
					</div>
	</li>
	<li>
		
					<a target="_blank" dcs_id="134" class="sname iconNavNumberGray">选号码<i class="bg"></i><i class="border_t"></i></a>
			</li>
	<li>
		<a target="_blank" dcs_id="128" href="/list/128_200_200_0_0_0_0.html" class="sname iconNavPeijian ac_dcstracker_homefirstscreen">挑配件<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">
						<dl class="noBorderTop">
				<dt>适配</dt>
				<dd class="clearfix">
																									<a href="/list/128_200_200_1_0_0_1_0_0.html">小米</a>
																				<a href="/list/128_200_200_1_0_0_2_0_0.html">酷派</a>
																				<a href="/list/128_200_200_1_0_0_3_0_0.html">海信</a>
																				<a href="/list/128_200_200_1_0_0_4_0_0.html">苹果</a>
																				<a href="/list/128_200_200_1_0_0_5_0_0.html">其他</a>
																				<a href="/list/128_200_200_1_0_0_6_0_0.html">联想</a>
																				<a href="/list/128_200_200_1_0_0_7_0_0.html">中兴</a>
																				<a href="/list/128_200_200_1_0_0_8_0_0.html">摩托罗拉</a>
																				<a href="/list/128_200_200_1_0_0_9_0_0.html">HTC</a>
																				<a href="/list/128_200_200_1_0_0_10_0_0.html">LG</a>
																				<a href="/list/128_200_200_1_0_0_11_0_0.html">Nokia</a>
																				<a href="/list/128_200_200_1_0_0_12_0_0.html">华为</a>
																				<a href="/list/128_200_200_1_0_0_13_0_0.html">三星</a>
														</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>特色</dt>
				<dd class="clearfix">
																									<a href="/list/128_200_200_1_0_0_0_1_0.html">清新</a>
																				<a href="/list/128_200_200_1_0_0_0_2_0.html">艺术</a>
																				<a href="/list/128_200_200_1_0_0_0_3_0.html">欧美风</a>
																				<a href="/list/128_200_200_1_0_0_0_4_0.html">中国风</a>
																				<a href="/list/128_200_200_1_0_0_0_5_0.html">简约</a>
																				<a href="/list/128_200_200_1_0_0_0_6_0.html">商务</a>
																				<a href="/list/128_200_200_1_0_0_0_7_0.html">奢华</a>
																				<a href="/list/128_200_200_1_0_0_0_8_0.html">时尚</a>
																				<a href="/list/128_200_200_1_0_0_0_9_0.html">高端</a>
																				<a href="/list/128_200_200_1_0_0_0_10_0.html">卡通</a>
																				<a href="/list/128_200_200_1_0_0_0_11_0.html">复古</a>
														</dd>
			</dl>
						<dl class="noBorderTop">
				<dt>分类属性</dt>
				<dd class="clearfix">
																									<a href="/list/128_200_200_1_0_0_0_0_1.html">智能穿戴</a>
																				<a href="/list/128_200_200_1_0_0_0_0_2.html">关爱定位</a>
																				<a href="/list/128_200_200_1_0_0_0_0_3.html">智能手环</a>
																				<a href="/list/128_200_200_1_0_0_0_0_4.html">智能家居</a>
																				<a href="/list/128_200_200_1_0_0_0_0_5.html">运动装备</a>
																				<a href="/list/128_200_200_1_0_0_0_0_6.html">其他</a>
																				<a href="/list/128_200_200_1_0_0_0_0_7.html">智能车载</a>
																				<a href="/list/128_200_200_1_0_0_0_0_8.html">健康监测</a>
																				<a href="/list/128_200_200_1_0_0_0_0_9.html">Mifi</a>
																				<a href="/list/128_200_200_1_0_0_0_0_10.html">蓝牙音箱</a>
																				<a href="/list/128_200_200_1_0_0_0_0_11.html">内存卡</a>
																				<a href="/list/128_200_200_1_0_0_0_0_12.html">蓝牙耳机</a>
																				<a href="/list/128_200_200_1_0_0_0_0_13.html">车载充电器</a>
																				<a href="/list/128_200_200_1_0_0_0_0_14.html">电池</a>
																				<a href="/list/128_200_200_1_0_0_0_0_15.html">耳机</a>
																				<a href="/list/128_200_200_1_0_0_0_0_16.html">手机壳</a>
																				<a href="/list/128_200_200_1_0_0_0_0_17.html">4G USIM卡</a>
																				<a href="/list/128_200_200_1_0_0_0_0_18.html">专业版旅行充电器</a>
																				<a href="/list/128_200_200_1_0_0_0_0_19.html">移动电源</a>
														</dd>
			</dl>
					</div>
	</li>
	<li id="personalCenter">
		<a target="_blank" dcs_id="home_firstscreen_grzx" href="http://shop.10086.cn/i/?f=home"  class="sname  iconNavUser ac_dcstracker_homefirstscreen">个人中心<i class="bg"></i><i class="border_t"></i></a>
		<div class="smore">         
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>我的订单</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://shop.10086.cn/i/?f=myorders">商品订单</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=mypreorders">预购订单</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=rechargerecord">充值订单</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=myfav">我的收藏</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=recaddrmag">收货地址</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=invoicemag">发票信息</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>充值交费</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://shop.10086.cn/i/?f=rechargecredit">银行卡充值</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=rechargecard">充值卡充值</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=rechargejf">积分充值</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>售后服务</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://shop.10086.cn/i/?f=returnorderqry">退货申请/查询</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>我的账户</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://shop.10086.cn/i/?f=billqry">账单查询</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=packremainqry">套餐余量查询</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=recharhisqry">交费记录查询</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=pointqry">积分明细查询</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=custinfoqry">用户信息查询</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=busiqrydeal">业务查询退订</a>
					                                          
                    <a target="_blank" href="http://shop.10086.cn/i/?f=belongqry">归属地查询</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>我的邮箱</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://shop.10086.cn/i/?f=remail">139邮箱</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                  
            <dl class="noBorderTop">          
                                                                       
                <dt>安全设置</dt>                                                                       
                <dd class="clearfix">                                                                             
                                                              
                    <a target="_blank" href="http://login.10086.cn/home/safe.htm">安全助手</a>
					                                          
                    <a target="_blank" href="http://login.10086.cn/account/bind.htm">账号绑定</a>
					                                          
                    <a target="_blank" href="http://login.10086.cn/security/questionDetail.htm">密保设置</a>
					                                          
                    <a target="_blank" href="http://login.10086.cn/protect/protect_web.htm">登录安全</a>
					                                          
                    <a target="_blank" href="http://login.10086.cn/account/modify_pwd.htm">密码管理</a>
					                                                                                  
                </dd>                                                                                             
            </dl>                                                                                                 
                                                                                                      
        </div>       
	</li>
</ul>


			<div class="sd_hat"></div>
		</div>
		<div class="mainBox">
			<div id="focus" class="banner">
	<ul class="clearfix">
		        <li style="display:block;"><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_0" href="http://shop.10086.cn/hd/bxltc/index.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tmvtnuqr8erf7rwmt.png" alt="" /></a></li>
		        <li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_1" href="http://shop.10086.cn/hd/events/132125.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tmv7u8dwhkzb9uytue.png" alt="" /></a></li>
		        <li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_2" href="http://shop.10086.cn/goods/200_200_1025829_1017876.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tm93s9p7dnnv9yd9b.png" alt="" /></a></li>
		        <li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_3" href="http://shop.10086.cn/goods/200_200_1026969_1018460.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tm934zskej8b7ubxj.png" alt="" /></a></li>
		        <li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_4" href="http://shop.10086.cn/goods/200_200_1011821_1007503.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2tbws6cg9qtebvga.png" alt="" /></a></li>
			</ul>
</div>
<div class="slide clearfix" id="bannerSlide">
	<ul class="clearfix piclist mainlist" style="width:2000px;">
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_0" href="http://www.10086.cn/events/note2/" target="_blank" title=" "><img src="http://img1.shop.10086.cn/file/tmdcpyfwwn4khxaz.png" alt=" " /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_1" href="http://shop.10086.cn/goods/200_200_1014331_1009720.html" target="_blank" title="TCL I310"><img src="http://img1.shop.10086.cn/file/t2pnqqxmq68f7rhqh.png" alt="TCL I310" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_2" href="http://shop.10086.cn/goods/230_230_1022530_1015912.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tmezxqaxkymv9ydn6.png" alt="" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_3" href="http://shop.10086.cn/goods/200_200_1022546_1015919.html" target="_blank" title="iPhone 6 4.7英寸"><img src="http://img1.shop.10086.cn/file/t23npgfhuakv5eb9z.png" alt="iPhone 6 4.7英寸" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_4" href="http://shop.10086.cn/goods/200_200_1025415_1017595.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tm93pbvq3p3thva52.png" alt="" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_5" href="http://shop.10086.cn/goods/200_200_1027021_1018514.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/tm93pt6hmyvkhzxh.png" alt="" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_6" href="http://shop.10086.cn/goods/200_200_1011842_1007514.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2vb534zpu5f7rhxc.png" alt="" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_7" href="http://shop.10086.cn/goods/200_200_1011862_1007553.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2vb55t4x3qv5ebq9.png" alt="" /></a></li>
				<li><a class="ac_dcstracker_homefirstscreen" dcs_id="home_firstscreen_ad_banner_slide_8" href="http://shop.10086.cn/goods/200_200_1011821_1007503.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2vb5kr9f9pv5ebw2.png" alt="" /></a></li>
			</ul>
	<span class="turn_left"></span>
	<span class="turn_right"></span>
</div>


		</div>
		<div class="rightBox">
			<div class="userAbout">
				<div class="btnBox" id="indexAbout"><a class="btnLogin" href="https://login.10086.cn/SSOCheck.action?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1"><i></i><b></b><span>登录商城</span></a></div>

				<ul class="clearfix">
					
					<li class="floatleft"><a dcs_id="Home_FirstScreen_WDSC" class="wdsc ac_dcstracker_homefirstscreen" target="_blank" href="http://shop.10086.cn/i/?f=myfav">我的收藏</a></li>
					<li class="floatright"><a dcs_id="Home_FirstScreen_WDDD" class="wddd ac_dcstracker_homefirstscreen" target="_blank" href="http://shop.10086.cn/i/?f=myorders">商品订单</a></li>
				</ul>
			</div>
			<div class="favMsg">
				<p class="title clearfix"><span class="name fontYaHei tt_sckb cur">商城快报</span><span class="name fontYaHei  tt_scgg"><a href="http://shop.10086.cn/mall_200_200.html" target="_blank">商城公告</a></span></p>
				<ul class="hoverBlue con_sckb" style="display:block">
											<li><a href="http://shop.10086.cn/list/101_200_200_1_0_0_0_7.html" target="_blank">超高性价比手机</a></li>
											<li><a href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_.html" target="_blank">乐享充值99折</a></li>
											<li><a href="http://shop.10086.cn/hd/events/132125.html" target="_blank">暑假手机大促</a></li>
									</ul>
				<ul class="hoverBlue con_scgg" style="display:none">
                                    				</ul>
			</div>
			<div class="recharge">
				<div class="form clearfix charge_area">
					<p class="title clearfix"><span class="name fontYaHei marginRight15">充值交费</span><a target="_blank" href="http://shop.10086.cn/i/?f=rechargecredit" class="floatright green" title="更多优惠">更多优惠<em class="fontSun">&gt;&gt;</em></a></p>
					<dl class="clearfix">
						<dt>手机号：</dt>
						<dd class="number"><input type="text" class="txt" name="phonenum" iprompt="请输入手机号码" class="floatleft" /></dd>
					</dl>
					<dl class="clearfix">
						<dt>金　额：</dt>
						<dd class="yuan">
							<span class="yuan_show">100 元</span>
							<input type="hidden" name="amount" value="100" />
							<a href="javascript:void(0);" class="toggle"></a>
							<div class="select_yuan">
							<a dcs_id="home_firstscreen_50" class="ac_dcstracker_homefirstscreen" href="javascript:void(0);">50 元</a>
							<a dcs_id="home_firstscreen_300" class="ac_dcstracker_homefirstscreen rightborder" href="javascript:void(0);">300 元</a>
							<a dcs_id="home_firstscreen_100" class="ac_dcstracker_homefirstscreen" href="javascript:void(0);">100 元</a>
							<a dcs_id="home_firstscreen_500" class="ac_dcstracker_homefirstscreen rightborder" href="javascript:void(0);">500 元</a>
							<div class="clear"></div>
							</div>
						</dd>
					</dl>
					<dl class="clearfix"><dd><a dcs_id="home_firstscreen_ljcz" class="btn btnBlue rechargeSubmit ac_dcstracker_homefirstscreen" href="http://shop.10086.cn/i/?f=rechargecredit" target="_blank"><i></i><b></b><em>立即充值</em></a></dd></dl>
				</div>
			</div>
		</div>
	</div>
	
                    <div class="barTitle clearfix">
	<em class="icon"></em>
	<a dcs_id="PHONE" class="ac_dcstracker_common" act="FLOOR_TITLE" target="_blank" href="/list/101_200_200_0_0_0_0.html"><span class="name">1F<em class="fontTahoma">/</em>买手机</span></a>
	<p class="menu clearfix">
		
						<a dcs_id="PHONE_MENU_0" class="ac_dcstracker_phone" target="_blank" href="/list/101_200_200_1_0_0_0_0_0_0_0.html">全部</a><i>|</i>				<a dcs_id="PHONE_MENU_1" class="ac_dcstracker_phone" target="_blank" href="/list/101_200_200_1_0_0_0_0_0_0_2.html">热销爆款</a><i>|</i>				<a dcs_id="PHONE_MENU_2" class="ac_dcstracker_phone" target="_blank" href="/list/101_200_200_1_0_0_0_0_0_0_3.html">超高性价比</a><i>|</i>				<a dcs_id="PHONE_MENU_3" class="ac_dcstracker_phone" target="_blank" href="/list/101_200_200_1_0_0_0_0_0_0_7.html">双卡双待</a>			</p>
</div>
<div class="clearfix">
	<ul class="modCm clearfix">
	    <li><a dcs_id="PHONE_AD_0" class="ac_dcstracker_PHONE_AD" href="http://shop.10086.cn/list/101_200_200_0_0_0_0.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t24bmzmc695f7ra8k.png" class="" alt="" /></a></li>
	</ul>


	<div class="modGoods">
		<ul class="clearfix phoneList withhover">
									<li>
				<div class="body">
					<p class="position pic">
					

                    <a dcs_id="PHONE_GOODS_PICURL_0" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_200_1027304_1018682.html" title="中国移动 N1 max M823"><img src="http://img1.shop.10086.cn/file/tmdaybnx4vskhxv5.png" alt="中国移动 N1 max M823" /></a></p>
					<p class="price red size20"><em class="fontYaHei size14">￥</em>1499.00</p>
					<p class="name" title="中国移动 N1 max M823移动自有品牌新机 精雕细琢设计 拍照神器"><a dcs_id="PHONE_GOODS_TITLEURL_0" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_200_1027304_1018682.html">中国移动 N1 max M823<span class="red">移动自有品牌新机 精雕细琢设计 拍照神器</span></a></p>
                 
				</div>
			</li>
												<li>
				<div class="body">
					<p class="position pic">
					

                    <a dcs_id="PHONE_GOODS_PICURL_1" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_200_1025829_1017876.html" title="魅蓝Note2"><img src="http://img1.shop.10086.cn/file/tmvsm5c2m2nf7rhj3.png" alt="魅蓝Note2" /></a></p>
					<p class="price red size20"><em class="fontYaHei size14">￥</em>899.00</p>
					<p class="name" title="魅蓝Note2赠话费50元（除广州）"><a dcs_id="PHONE_GOODS_TITLEURL_1" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_200_1025829_1017876.html">魅蓝Note2<span class="red">赠话费50元（除广州）</span></a></p>
                 
				</div>
			</li>
												<li>
				<div class="body">
					<p class="position pic">
					

                    <a dcs_id="PHONE_GOODS_PICURL_2" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn//goods/200_200_1020730_1015188.html" title="酷派 Y75"><img src="http://img1.shop.10086.cn/file/tmvsmjy2chpfnvv3e.png" alt="酷派 Y75" /></a></p>
					<p class="price red size20"><em class="fontYaHei size14">￥</em>799.00</p>
					<p class="name" title="酷派 Y755.5英寸炫彩大屏，四核畅快极速"><a dcs_id="PHONE_GOODS_TITLEURL_2" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn//goods/200_200_1020730_1015188.html">酷派 Y75<span class="red">5.5英寸炫彩大屏，四核畅快极速</span></a></p>
                 
				</div>
			</li>
												<li>
				<div class="body">
					<p class="position pic">
					

                    <a dcs_id="PHONE_GOODS_PICURL_3" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_754_1026395_1018133.html" title="德赛 魔镜X5"><img src="http://img1.shop.10086.cn/file/tmev5cy64wckh4qt.png" alt="德赛 魔镜X5" /></a></p>
					<p class="price red size20"><em class="fontYaHei size14">￥</em>899.00</p>
					<p class="name" title="德赛 魔镜X5赠专属保护套+德赛M399功能机"><a dcs_id="PHONE_GOODS_TITLEURL_3" class="ac_dcstracker_phone"  target="_blank" href="http://shop.10086.cn/goods/200_754_1026395_1018133.html">德赛 魔镜X5<span class="red">赠专属保护套+德赛M399功能机</span></a></p>
                 
				</div>
			</li>
						
		</ul>
		<div class="brands clearfix">
			<span>品牌：</span>
						            <a dcs_id="PHONE_BRAND_0" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_26_0_0_0_0.html" title="德赛"><img src="http://img1.shop.10086.cn/file/tqhm59ptufp4f7r6zq.png" style="width:75px;" alt="德赛" /></a>
									            <a dcs_id="PHONE_BRAND_1" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_12_0_0_0_0.html" title="TCL"><img src="http://img1.shop.10086.cn/file/tqhtjj7dke8ffv3yy.png" style="width:75px;" alt="TCL" /></a>
									            <a dcs_id="PHONE_BRAND_2" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_1_0_0_0_0.html" title="华为"><img src="http://img1.shop.10086.cn/file/tqhm5ge946f7r7pz.png" style="width:75px;" alt="华为" /></a>
									            <a dcs_id="PHONE_BRAND_3" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_9_0_0_0_0.html" title="酷派"><img src="http://img1.shop.10086.cn/file/tqhm5gytts8zfnvvem.png" style="width:75px;" alt="酷派" /></a>
									            <a dcs_id="PHONE_BRAND_4" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_4_0_0_0_0.html" title="中兴"><img src="http://img1.shop.10086.cn/file/tqhm3v5xg4bfnvvvk.png" style="width:75px;" alt="中兴" /></a>
									            <a dcs_id="PHONE_BRAND_5" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_10_0_0_0_0.html" title="HTC"><img src="http://img1.shop.10086.cn/file/tqz7vdungbzf7rw9z.png" style="width:75px;" alt="HTC" /></a>
									            <a dcs_id="PHONE_BRAND_6" class="ac_dcstracker_phone"  target="_blank" href="/list/101_200_200_1_0_0_3_0_0_0_0.html" title="苹果"><img src="http://img1.shop.10086.cn/file/t2usc33yjc7edrdm.png" style="width:75px;" alt="苹果" /></a>
								</div>
	</div>
</div>


        	
    
                    <div class="barTitle clearfix">
	<em class="icon yewu"></em>
	<a dcs_id="HUAFEI" class="ac_dcstracker_common" act="FLOOR_TITLE" target="_blank" href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_.html"><span class="name">2F<em class="fontTahoma">/</em>交话费</span></a>
	<p class="menu">
		        		<a  dcs_id="HUAFEI_MENU_0" class="ac_dcstracker_huafeimenu" target="_blank" href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_.html">全部</a>			</p>
</div>
<div class="clearfix">
	<ul class="modCm clearfix">
	    <li><a dcs_id="HUAFEI_AD_0" class="ac_dcstracker_HUAFEI_AD" href="http://shop.10086.cn/i/service/recharge/item/itemcollect_200_200_.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2cw38t25ceb9uykv.png" class="" alt="" /></a></li>
	</ul>


	<div class="modGoods">
		<div class="huafeiList clearfix">
			<ul class="clearfix withhover">
								<li class="clearfix">
                    <a title="任意金额" dcs_id="HUAFEI_GOODS_PICURL_0" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_575.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbm6aqjmpf7raca.png" alt="任意金额" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_575.html" dcs_id="HUAFEI_GOODS_TITLE_0" class="title ac_dcstracker_huafei" target="_blank"><span>任意金额</span></a>
				</li>
								<li class="clearfix">
                    <a title="话费充值300元" dcs_id="HUAFEI_GOODS_PICURL_1" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_573.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbm6nrh4vf7r6js.png" alt="话费充值300元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_573.html" dcs_id="HUAFEI_GOODS_TITLE_1" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值300元</span></a>
				</li>
								<li class="clearfix">
                    <a title="话费充值500元" dcs_id="HUAFEI_GOODS_PICURL_2" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_574.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbm6ev98qedr42.png" alt="话费充值500元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_574.html" dcs_id="HUAFEI_GOODS_TITLE_2" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值500元</span></a>
				</li>
								<li class="clearfix" style="margin-right:0;">
                    <a title="话费充值100元" dcs_id="HUAFEI_GOODS_PICURL_3" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_571.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbmw4k9kpdf3js.png" alt="话费充值100元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_571.html" dcs_id="HUAFEI_GOODS_TITLE_3" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值100元</span></a>
				</li>
								<li class="clearfix">
                    <a title="话费充值50元" dcs_id="HUAFEI_GOODS_PICURL_4" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_570.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbmwmp77cf7rwe5.png" alt="话费充值50元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_570.html" dcs_id="HUAFEI_GOODS_TITLE_4" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值50元</span></a>
				</li>
								<li class="clearfix">
                    <a title="话费充值200元" dcs_id="HUAFEI_GOODS_PICURL_5" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_572.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/t2cp954pwb4f7rws5.png" alt="话费充值200元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_572.html" dcs_id="HUAFEI_GOODS_TITLE_5" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值200元</span></a>
				</li>
								<li class="clearfix">
                    <a title="话费充值30元" dcs_id="HUAFEI_GOODS_PICURL_6" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_569.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbmmpr3utffv35m.png" alt="话费充值30元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_569.html" dcs_id="HUAFEI_GOODS_TITLE_6" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值30元</span></a>
				</li>
								<li class="clearfix" style="margin-right:0;">
                    <a title="话费充值10元" dcs_id="HUAFEI_GOODS_PICURL_7" class="ac_dcstracker_huafei pic" href="http://shop.10086.cn/i/service/recharge/item/item_568.html" target="_blank"><img src="http://img1.shop.10086.cn/cm/tmbbm2ayvmufnvvwu.png" alt="话费充值10元" /></a>
					<a href="http://shop.10086.cn/i/service/recharge/item/item_568.html" dcs_id="HUAFEI_GOODS_TITLE_7" class="title ac_dcstracker_huafei" target="_blank"><span>话费充值10元</span></a>
				</li>
							</ul>
		</div>
	</div>
</div>



            
	
                    <div class="barTitle clearfix">
	<em class="icon taocan"></em>
	<a dcs_id="EXPENSE" class="ac_dcstracker_common" act="FLOOR_TITLE" target="_blank" href="/list/140_200_200_0_0_0_0.html"><span class="name">3F<em class="fontTahoma">/</em>办套餐</span></a>
	<p class="menu">
				<a dcs_id="EXPENSE_MENU_0" class="ac_dcstracker_expensemenu"   target="_blank" href="/list/140_200_200_1_0_0_0_0.html">全部</a><i>|</i>				<a dcs_id="EXPENSE_MENU_1" class="ac_dcstracker_expensemenu"   target="_blank" href="/list/140_200_200_1_0_0_0_1.html">资费套餐</a>			</p>
</div>
<div class="clearfix">
	<ul class="modCm clearfix">
	    <li><a dcs_id="EXPENSE_AD_0" class="ac_dcstracker_EXPENSE_AD" href="http://shop.10086.cn/list/140_200_200_1_0_0_0.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t24bwtgr49zdf35s.png" class="" alt="" /></a></li>
	</ul>


	<div class="modGoods">
		<ul class="taocanList clearfix">
						<li class="ft">
				<dl class="clearfix">
					<dt class="position">
						
						

                        <a dcs_id="EXPENSE_GOODS_PICURL_0" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018537_1013526.html" target="_blank" title="4G随心王(预付费)"><img  src="http://img1.shop.10086.cn/goods/t2c5xrfrxfhb9uyeg_100x100.png" alt="4G随心王(预付费)" /></a>
					</dt>					<dd class="name hoverBlue"><a dcs_id="EXPENSE_GOODS_TITLE_0" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018537_1013526.html" target="_blank" title="4G随心王(预付费)">4G随心王(预付费)</a></dd>
					<dd title="4G随心王，套餐内容无束缚，语音、流量、短信随心换">4G随心王，套餐内容无束缚，语音、流量、短信随心换　<a  dcs_id="EXPENSE_GOODS_SHOWMORE_0" class="ac_dcstracker_expenseexpense" target="_blank" href="http://shop.10086.cn/goods/200_200_1018537_1013526.html" class="more">查看详情<em class="fontSun">&gt;&gt;</em></a></dd>
					<dd><span class="red size16"><em class="fontTahoma">19</em> <em class="size12">元/月起</em></span></dd>
				</dl>
			</li>
									<li class="ft">
				<dl class="clearfix">
					<dt class="position">
						
						

                        <a dcs_id="EXPENSE_GOODS_PICURL_1" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018546_1013532.html" target="_blank" title="4G随心王（全品牌）"><img  src="http://img1.shop.10086.cn/goods/t2c5xgd56wtv5eb8j_100x100.png" alt="4G随心王（全品牌）" /></a>
					</dt>					<dd class="name hoverBlue"><a dcs_id="EXPENSE_GOODS_TITLE_1" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018546_1013532.html" target="_blank" title="4G随心王（全品牌）">4G随心王（全品牌）</a></dd>
					<dd title="4G随心王，套餐内容无束缚，语音、流量、短信随心换。">4G随心王，套餐内容无束缚，语音、流量、短信随心换。　<a  dcs_id="EXPENSE_GOODS_SHOWMORE_1" class="ac_dcstracker_expenseexpense" target="_blank" href="http://shop.10086.cn/goods/200_200_1018546_1013532.html" class="more">查看详情<em class="fontSun">&gt;&gt;</em></a></dd>
					<dd><span class="red size16"><em class="fontTahoma">69</em> <em class="size12">元/月起</em></span></dd>
				</dl>
			</li>
									<li>
				<dl class="clearfix">
					<dt class="position">
						
						

                        <a dcs_id="EXPENSE_GOODS_PICURL_2" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018832_1013819.html" target="_blank" title="动感地带18元上网套餐"><img  src="http://img1.shop.10086.cn/goods/t2c5h73zzq5tebv5w_100x100.png" alt="动感地带18元上网套餐" /></a>
					</dt>					<dd class="name hoverBlue"><a dcs_id="EXPENSE_GOODS_TITLE_2" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1018832_1013819.html" target="_blank" title="动感地带18元上网套餐">动感地带18元上网套餐</a></dd>
					<dd title="数据流量50MB,本地主叫30分钟，短信100条">数据流量50MB,本地主叫30分钟，短信100条　<a  dcs_id="EXPENSE_GOODS_SHOWMORE_2" class="ac_dcstracker_expenseexpense" target="_blank" href="http://shop.10086.cn/goods/200_200_1018832_1013819.html" class="more">查看详情<em class="fontSun">&gt;&gt;</em></a></dd>
					<dd><span class="red size16"><em class="fontTahoma">18</em> <em class="size12">元起</em></span></dd>
				</dl>
			</li>
									<li>
				<dl class="clearfix">
					<dt class="position">
						
						

                        <a dcs_id="EXPENSE_GOODS_PICURL_3" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1025883_1017922.html" target="_blank" title="话音短信不限量套餐"><img  src="http://img1.shop.10086.cn/goods/tmkcrfbjc64f7rwwh_100x100.png" alt="话音短信不限量套餐" /></a>
					</dt>					<dd class="name hoverBlue"><a dcs_id="EXPENSE_GOODS_TITLE_3" class="ac_dcstracker_expenseexpense" href="http://shop.10086.cn/goods/200_200_1025883_1017922.html" target="_blank" title="话音短信不限量套餐">话音短信不限量套餐</a></dd>
					<dd title="请阅读温馨提示中协议内容，订购成功视为接受该协议">请阅读温馨提示中协议内容，订购成功视为接受该协议　<a  dcs_id="EXPENSE_GOODS_SHOWMORE_3" class="ac_dcstracker_expenseexpense" target="_blank" href="http://shop.10086.cn/goods/200_200_1025883_1017922.html" class="more">查看详情<em class="fontSun">&gt;&gt;</em></a></dd>
					<dd><span class="red size16"><em class="fontTahoma">338</em> <em class="size12">元/月起</em></span></dd>
				</dl>
			</li>
								</ul>
	</div>
</div>


        	
	
                    <div class="barTitle clearfix">
	<em class="icon yewu"></em>
	<a dcs_id="VAS" class="ac_dcstracker_common" act="FLOOR_TITLE" target="_blank" href="/list/146_200_200_0_0_0_0.html"><span class="name">4F<em class="fontTahoma">/</em>办业务</span></a>
	<p class="menu">
		        		<a  dcs_id="VAS_MENU_0" class="ac_dcstracker_vasmenu" target="_blank" href="/list/146_200_200_1_0_0_0_0_0.html">全部</a><i>|</i>				<a  dcs_id="VAS_MENU_1" class="ac_dcstracker_vasmenu" target="_blank" href="/list/146_200_200_1_0_0_0_0_1.html">流量套餐</a><i>|</i>				<a  dcs_id="VAS_MENU_2" class="ac_dcstracker_vasmenu" target="_blank" href="/list/146_200_200_1_0_0_0_0_2.html">数据业务</a><i>|</i>				<a  dcs_id="VAS_MENU_3" class="ac_dcstracker_vasmenu" target="_blank" href="/list/146_200_200_1_0_0_0_0_3.html">优惠业务包</a>			</p>
</div>
<div class="clearfix">
	<ul class="modCm clearfix">
	    <li><a dcs_id="VAS_AD_0" class="ac_dcstracker_VAS_AD" href="http://shop.10086.cn/list/146_200_200_0_0_0_0.html" target="_blank" title=""><img src="http://img1.shop.10086.cn/file/t2tbwmftf5wqf7r6pe.png" class="" alt="" /></a></li>
	</ul>


	<div class="modGoods">
		<div class="yewuList clearfix">
			<ul class="clearfix withhover">
				                				<li class="position clearfix">
                                    
					

                    <a title="流量套餐" dcs_id="VAS_GOODS_PICURL_0" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1011842_1007514.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/t2c3tvarxt8edrqd_100x100.png" alt="流量套餐" /></a>
				</li>
                				                				<li class="position clearfix">
                                    
					

                    <a title="4G手机流量叠加包" dcs_id="VAS_GOODS_PICURL_1" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1015878_1013654.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/tmdtfkgfcpzb7ubh3_100x100.png" alt="4G手机流量叠加包" /></a>
				</li>
                				                				<li class="position clearfix">
                                    
					

                    <a title="通信助手" dcs_id="VAS_GOODS_PICURL_2" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1015638_1010825.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/tmdtfn98rjstpmarm_100x100.png" alt="通信助手" /></a>
				</li>
                				                				<li class="position clearfix" style="margin-right:0;">
                                    
					

                    <a title="流量保障服务" dcs_id="VAS_GOODS_PICURL_3" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1019165_1014006.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/tmdtf9thyattpman6_100x100.png" alt="流量保障服务" /></a>
				</li>
                				                				<li class="position clearfix">
                                    
					

                    <a title="全球通20元短信套餐" dcs_id="VAS_GOODS_PICURL_4" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1020306_1015036.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/tmdtfyfezn9kh4jp_100x100.png" alt="全球通20元短信套餐" /></a>
				</li>
                				                				<li class="position clearfix">
                                    
					

                    <a title="流量保障服务" dcs_id="VAS_GOODS_PICURL_5" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1019165_1014006.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/tmdtf9thyattpman6_100x100.png" alt="流量保障服务" /></a>
				</li>
                				                				<li class="position clearfix">
                                    
					

                    <a title="神州行短信套餐" dcs_id="VAS_GOODS_PICURL_6" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1020299_1015021.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/t2c5pzy4dcsffv3rs_100x100.png" alt="神州行短信套餐" /></a>
				</li>
                				                				<li class="position clearfix" style="margin-right:0;">
                                    
					

                    <a title="139邮箱" dcs_id="VAS_GOODS_PICURL_7" class="ac_dcstracker_vasphone" href="http://shop.10086.cn/goods/200_200_1011821_1007503.html" target="_blank"><img src="http://img1.shop.10086.cn/goods/t2c3t99q38mf7r6xk_100x100.png" alt="139邮箱" /></a>
				</li>
                				                							</ul>
		</div>
	</div>
</div>


        	

	
		

	
                    <div class="barTitle clearfix">
	<em class="icon peijian"></em>
	<a dcs_id="ACCESSORY" class="ac_dcstracker_common" act="FLOOR_TITLE" target="_blank" href="/list/128_200_200_0_0_0_0.html"><span class="name">5F<em class="fontTahoma">/</em>挑配件</span></a>
	<p class="menu">
				<a  dcs_id="ACCESSORY_MENU_0" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_0.html">全部</a><i>|</i>				<a  dcs_id="ACCESSORY_MENU_1" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_19.html">移动电源</a><i>|</i>				<a  dcs_id="ACCESSORY_MENU_2" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_12.html">蓝牙耳机</a><i>|</i>				<a  dcs_id="ACCESSORY_MENU_3" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_16.html">手机壳</a><i>|</i>				<a  dcs_id="ACCESSORY_MENU_4" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_4.html">智能家居</a><i>|</i>				<a  dcs_id="ACCESSORY_MENU_5" class="ac_dcstracker_accessorymenu" target="_blank" href="/list/128_200_200_1_0_0_0_0_8.html">健康监测</a>			</p>
</div>
<div class="clearfix">
	<ul class="modCm clearfix">
	    <li><a dcs_id="ACCESSORY_AD_0" class="ac_dcstracker_ACCESSORY_AD" href="http://shop.10086.cn/goods/200_200_1017702_1013048.html" target="_blank" title="RSR DS-406"><img src="http://img1.shop.10086.cn/file/t24xat2qcspf7rw3k.png" class="" alt="RSR DS-406" /></a></li>
	</ul>


	<div class="modGoods">
		<div class="pjList clearfix">
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_0" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/100_100_1024582_1017340.html" target="_blank" title="Broadlink智能插座SP mini"><img src="http://img1.shop.10086.cn/file/tm9ysmrakaxkhxdw.png" alt="Broadlink智能插座SP mini" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_0" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/100_100_1024582_1017340.html" target="_blank">Broadlink智能插座SP mini 定时省电，手机控制，家电保护专家！</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>79.00</dd>
			</dl>
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_1" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1022527_1015905.html" target="_blank" title="找TA智能定位防丢电筒"><img src="http://img1.shop.10086.cn/file/tmbn9f6f6cdf7ra2p.png" alt="找TA智能定位防丢电筒" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_1" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1022527_1015905.html" target="_blank">找TA智能定位防丢电筒 关爱老人、紧急求助</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>239.00</dd>
			</dl>
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_2" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1022895_1016119.html" target="_blank" title="和目720P高清WIFI云摄像头"><img src="http://img1.shop.10086.cn/file/tmbn9ec7fqxf7rhn7.png" alt="和目720P高清WIFI云摄像头" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_2" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1022895_1016119.html" target="_blank">和目720P高清WIFI云摄像头 双向对讲、异常监测</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>499.00</dd>
			</dl>
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_3" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017703_1013049.html" target="_blank" title="库克斯 T3-Pro"><img src="http://img1.shop.10086.cn/file/t22v3tdttefxffv37g.png" alt="库克斯 T3-Pro" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_3" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017703_1013049.html" target="_blank">库克斯 T3-Pro 纯净音质 随你点播 轻巧机身 打造全能好声音！</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>129.00</dd>
			</dl>
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_4" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017705_1013051.html" target="_blank" title="蜗牛 BTEC018"><img src="http://img1.shop.10086.cn/file/t22v5zxcanrf7r736.png" alt="蜗牛 BTEC018" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_4" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017705_1013051.html" target="_blank">蜗牛 BTEC018 耳边的精致艺术品 口袋里的语音精灵！</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>149.00</dd>
			</dl>
						<dl class="clearfix">
				<dt class="position">
					
					

                    <a dcs_id="ACCESSORY_GOODS_PICURL_5" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017702_1013048.html" target="_blank" title="RSR DS-406"><img src="http://img1.shop.10086.cn/file/t22v5cwkaddf3sz.png" alt="RSR DS-406" /></a>
				</dt>
				<dd class="name hoverBlue"><a dcs_id="ACCESSORY_GOODS_TITLE_5" class="ac_dcstracker_accessoryphone"  href="http://shop.10086.cn/goods/200_200_1017702_1013048.html" target="_blank">RSR DS-406 向乔帮主致敬！为您开启音乐魔幻之旅！</a></dd>
				<dd class="price red size20"><span class="fontYaHei size14">￥</span>599.00</dd>
			</dl>
					</div>
	</div>
</div>


        </div>



<div class="globalMain margin10">
	<div class="footerService">
		<ul class="clearfix">
			<li>
				<dl class="clearfix gwzn">
					<dt>购物指南</dt>
					<dd><a target="_blank"  href="/help/200_200/zcsm.html">注册说明</a></dd>
					<dd><a  target="_blank" href="/help/200_200/gwsm.html">购物说明</a></dd>
					<dd><a  target="_blank" href="/help/200_200/mjbd.html">买家必读</a></dd>
				</dl>
			</li>
			<li>
				<dl class="clearfix zffs">
					<dt>付款方式</dt>
					<dd><a  target="_blank" href="/help/200_200/zxzf.html">在线支付</a></dd>
					<dd><a  target="_blank" href="/help/200_200/hdfk.html">货到付款</a></dd>
					<dd><a  target="_blank" href="/help/200_200/bzzx.html">分期付款</a></dd>
				</dl>
			</li>
			<li>
				<dl class="clearfix wlps">
					<dt>物流配送</dt>
					<dd><a  target="_blank" href="/help/200_200/kdps.html">快递配送</a></dd>
					<dd><a  target="_blank" href="/help/200_200/qsts.html">签收提示</a></dd>
				</dl>
			</li>
			<li>
				<dl class="clearfix shfw">
					<dt>售后服务</dt>
					<dd><a  target="_blank" href="/help/200_200/thyz.html">退换货原则</a></dd>
					<dd><a  target="_blank" href="/help/200_200/thsm.html">退款说明</a></dd>
				</dl>
			</li>
			<li>
				<dl class="clearfix grzx">
					<dt>个人中心</dt>
					<dd><a  target="_blank" href="/help/200_200/ddzx.html">订单中心</a></dd>
					<dd><a  target="_blank" href="/help/200_200/czjf_new.html">充值交费</a></dd>
					<dd><a  target="_blank" href="/help/200_200/zhzx.html">账户中心</a></dd>
				</dl>
			</li>
		</ul>
	</div>
</div>
<div id="footer">
	<p>
													<a href="http://www.10086.cn/aboutus/news/" target="_blank">新闻中心</a>
													 | 					法律声明
													 | 					<a href="http://job.10086.cn/" target="_blank">诚聘英才</a>
													 | 					<a href="http://b2b.10086.cn/" target="_blank">采购信息</a>
													 | 					<a href="http://www.10086.cn/aboutus/hezuo/" target="_blank">企业合作</a>
													 | 					<a href="http://www.10086.cn/web_notice/contact/" target="_blank">联系我们</a>
													 | 					<a href="http://www.10086.cn/web_notice/navigation/" target="_blank">站点导航</a>
													 | 					<a href="http://labs.chinamobile.com/" target="_blank">中国移动研究院</a>
													 | 					<a href="http://www.cmdi.chinamobile.com/" target="_blank">中国移动设计院</a>
													 | 					<a href="http://www.gd.10086.cn/sitemap" target="_blank">网站地图</a>
																			 | 					<a href="http://www.10086.cn/web_notice/links/" target="_blank">友情链接</a>
								</p>
	<p>掌上营业厅：<a href="http://wap.10086.cn" target="_blank">wap.10086.cn</a> 语音自助服务：10086 短信营业厅：10086 <a href="http://www.10086.cn/support/channel/self_service/" target="_blank">自助终端</a> <a href="http://www.10086.cn/support/channel/Entity1/" target="_blank">营业厅</a> <a href="http://www.10086.cn/cmccclient/index.htm" target="_blank">手机营业厅下载</a></p>
	<p style="width:300px; margin:0 auto;" class="clearfix">
		<a target="_blank" href="https://ss.knet.cn/verifyseal.dll?sn=e130905110100423008ilb000000&ct=df&a=1&pa=0.3082310492237422" class="floatleft"><img src="http://img0.shop.10086.cn/www/core/css/header_footer/cnnic.png__4.png" align="absmiddle"  oncontextmenu="return false;" /></a>
		<span class="floatleft alignleft" style="padding-left:20px;">
			<a href="http://www.miibeian.gov.cn/" target="_blank"><img src="http://img0.shop.10086.cn/www/core/css/header_footer/gov.gif__1.gif" align="absmiddle" />京ICP备05002571号</a><br />　中国移动通信版权所有
		</span>
	</p>
</div>
<div class="mask" id="mask"></div>

<ul class="rightfloatnav" id="returntop" style="display:none;">
	<li><a href="http://shop.10086.cn/list/101_200_200_1_0_0.html" class="rfn_1">购买手机</a></li>
	<li><a href="http://shop.10086.cn/list/140_200_200_1_0_0.html" class="rfn_8">办理套餐</a></li>
	<li><a href="http://shop.10086.cn/list/146_200_200_1_0_0.html" class="rfn_2">办理业务</a></li>
		<li><a href="http://shop.10086.cn/list/128_200_200_1_0_0.html" class="rfn_4">挑选配件</a></li>
	<li><a href="http://shop.10086.cn/i/?f=home" class="rfn_5">个人中心</a></li>
		<li><a href="#" class="rfn_7">返回顶部</a></li>
</ul>
<script type="text/javascript" src="http://img0.shop.10086.cn/script/loader.js__76.js"></script>
<script src="http://img0.shop.10086.cn/www/core/js/sdc_mall.js__97.js"></script>
<script type="text/javascript">
window.ENVOBJ = {"loc":{"province_id":200,"city_id":200},"loginurl":"https:\/\/login.10086.cn\/SSOCheck.action?channelID=12002&backUrl=http%3A%2F%2Fshop.10086.cn%2Fmall_200_200.html%3Fforcelogin%3D1"};
LOADJS('http://img0.shop.10086.cn/combo/__116/script/jquery-1.10.2.js__script/iprompt/jquery.iprompt.js__script/formater.js__script/pubsub/jquery.pubsub.js__script/interactive/jquery.interactive.js__www/core/js/global.js__www/core/js/dcs_tracker.js__www/index/js/index.js', function() {
//	$.domains = window.ENVOBJ.domains;


$
.sub('click_login_mini',__mini_login)
.sub('login_mini',__mini_login)
.sub('login_mini_msg',__mini_login_msg)
;
var _options = null
function __mini_login(event,options){
	_options = options;
	var miniframe = $('#login_box').html();
	if(miniframe){
		var $box = $.alert(miniframe,{
			onLoad:function($box){
				$box.find('.interactive-content').css({
					'background-color':'#ECECEC'
				});
			},
			onClose:function(){
				$.pub('login_mini_cancel');
				_options = null;
			},
			liveTime:null
		});
		return false;
	}else{
		return true;
	}
}	
function __mini_login_msg(event , r){
	if(window.initUser){
		window.initUser(r);
	}
	$.UI.hide();
	if(r.code == 0){
		if(_options)$.ajax(_options);
		_options = null;
	}
}


	
	window.initUser = function(r){
		if(r.code == 0){
			window.loginUser = r.data;
			var nickname = r.data.nick_name || r.data.user_name
			var tmpnick = nickname.split('@')[0];
			if(tmpnick.length>=15) tmpnick = tmpnick.substr(0,14) +"...";
			var tmpstr =  '<span class="floatleft marginRight10" title="' + nickname + '">' + tmpnick + '</span>' +'<a class="red bold" href="javascript:void(logout())" title="退出">[退 出]</a>';
			document.getElementById('userinfo').innerHTML = tmpstr;
			if ($.pub){
				$.pub('login_success' ,r.data);
			}
		}else if (r.code == 3 ){
			$('#ssocheckframe').attr('src' ,r.data);
		}
	}
		LOADJS('/ajax/user/userinfo.json?update=1&province_id=200&city_id=200&callback=initUser');
		
	
	
	;
	$(function(){
		$
		.sub('click_show_areabar',__show_areabar)
		.sub('click_show_city',__show_city)
		.sub('click_city_choose',__gotocity)
		;
		//清除浮层
		function __clear_areabar(){
			$('#header').find('.area ul').hide();
		}
		//展开浮层
		function __show_areabar(){
			__clear_areabar();
			var $this = $(this);
			$this.next().show();
			return false;
		}
		function __gotocity(loc){
			if(typeof loc != "string"){
				loc = $(this).attr('location');
			}
			var locarray = loc.split('_');
			var pid = locarray[0],cid = locarray[1];
			var url = location.href ;
			if(url.indexOf('/list/') > 0){
				url = url.replace(/_\d{3}_\d{3}/i,"_"+loc);
				setTimeout(function(){
					location.href=url;
				});
			}else{
				setTimeout(function(){
					location.href="/mall_{loc}.html".replace("{loc}",loc);
				});
			}
		}
		function getCityByProvince(province_id){
			if ( !window.allcity[province_id] ){
				$.ajax({
					async : false,
					url : "/ajax/region/singleregion.json",
					data:{province_id:province_id},
					success:function(r){
						if(r.code){
							$.alert(r.code)
						}else{
							window.allcity[province_id] = r.data || [];
						}
					},
					dataType:'json'
				});
			}
			return window.allcity[province_id];
		}
		var $province = $('#header .province'),
			$city = $('#header .city'),
			current_province_id = $province.find('span').attr('province_id'),
			city_id = '200',
			reg = new RegExp('<!--|-->','g') ,
			template = new Formater($('.header_area_template').html().replace(reg,'')||'<li><a href="#" target="_blank">{city_name}</a></li>'),
			city_name = '广州'
			;
		//点击省下拉项展开市浮层
		function __show_city(){
			var $this = $(this),
				province_id = $this.attr('province_id'),
				tmp=''
			;
			if( $this.attr('iscity')) {
				__gotocity(province_id+"_"+province_id);
				return false;
			}
			var data=getCityByProvince(province_id);
			$city.find('span').text('请选择');
			//var cityAreaWidth = (data.length < 4) ? 40*(data.length + 1) + 5 : 245;//根据市的个数，判断外框宽度
			$.each(data,function(inx,item){
				tmp+=template.exec(item);
			});
			__clear_areabar();
			$this.closest('ul').prev().attr('province_id',$this.attr('province_id')).text($this.text());
			$city.find('ul').html(tmp).show();
			return false;	
		}

		//点击其他dom收起浮层
		$(document).bind('click.area',function(e){
			if($(e.target).closest('.area').length == 0){
				__clear_areabar();
			}

		});

		//搜索默认文字
		$('#header .search_mlogo .search .txt').iPrompt({
			refer:'iprompt'
		});
	})
	;
	
	

$(function(){
	$('.shopSearch .mainmenu').hover(function(){
		$(this).addClass('hover');
	},function(){
		$(this).removeClass('hover');
	});
	var dcs_map= {
		'101':'Home_FirstScreen_PHONE',
		'140':'Home_FirstScreen_EXPENSE',
		'146':'Home_FirstScreen_VAS',
		'134':'Home_FirstScreen_NUMBER',
		'128':'Home_FirstScreen_ACCESORY',
		'jiaohuafei':'Home_FirstScreen_HUAFEI',
		'home_firstscreen_grzx':'Home_FirstScreen_PERSONALCENTER',
		'home_firstscreen_ad_1':'Home_FirstScreen_AD_1',
		'home_firstscreen_ad_2':'Home_FirstScreen_AD_2',
		'home_firstscreen_ad_3':'Home_FirstScreen_AD_3',
		'home_firstscreen_ad_4':'Home_FirstScreen_AD_4',
		'home_firstscreen_ljcz':'Home_FirstScreen_RECHARGE',
		'home_firstscreen_30':'Home_FirstScreen_30',
		'home_firstscreen_50':'Home_FirstScreen_50',
		'home_firstscreen_100':'Home_FirstScreen_100',
		'home_firstscreen_200':'Home_FirstScreen_200',
		'home_firstscreen_500':'Home_FirstScreen_500'
	};
	$.sub('click_dcstracker_homefirstscreen',__dcstracker_homefirstscreen);
	function __dcstracker_homefirstscreen(){
		var $t = $(this);
		$.dcstracker({
			category:'Home',
			action:'FirstScreen',
			event:dcs_map[$t.attr('dcs_id')]||$t.attr('dcs_id')
		});
	}
});



$(function() {
	//banner部分
	var $banner = $('#focus'),
		$bannerLi = $banner.find('li'),
		$liWidth = $bannerLi.eq(0).width(),
		$btnsHtml = '<p class="btns">',
		bannerCurrent = 0,
		timeBanner = 0;
	//计算ul宽度
	//$banner.find('ul').width($liWidth*$bannerLi.length);
	$bannerLi.each(function(i,item){
		if(i == 0){
			$btnsHtml += '<span class="current">1</span>';
		}else{
			$btnsHtml += '<span>' + (i+1) + '</span>';
		}
	});
	$btnsHtml += '</p>';
	$banner.append($btnsHtml);
	$banner.find('.btns span').click(function(){
		var $this = $(this);
		clickIndex = $banner.find('.btns span').index($this);
		if(clickIndex != bannerCurrent){
			$bannerLi.eq(bannerCurrent).fadeOut(800);
			$bannerLi.eq(clickIndex).fadeIn(800);
			bannerCurrent = clickIndex;
			$this.addClass('current').siblings('span').removeClass('current');
		}
	});
	
	function bannerNext(){
		clearTimeout(timeBanner);
		if(bannerCurrent >= $bannerLi.length-1){
			$banner.find('.btns span').eq(0).triggerHandler('click');
		}else{
			$banner.find('.btns span').eq(bannerCurrent +1).triggerHandler('click');
		}
		timeBanner = setTimeout(bannerNext,3000);
	}
	timeBanner = setTimeout(bannerNext,6000);
	$banner.hover(function(){
		clearTimeout(timeBanner);
	},function(){
		timeBanner = setTimeout(bannerNext,3000);
	});

	//banner下方图片
	var $slideBox = $('#bannerSlide'),
		$slideLi = $slideBox.find('li'),
		liWidth = 193,
		timeSlide = 0;
	$slideBox.find('ul').width($slideLi.length*liWidth);
	if($slideLi.length >3){
		$slideBox.find('.turn_left, .turn_right').show();
	}
	$slideBox.find('.turn_left, .turn_right').click(function(){
		if(!$slideBox.find('ul').is(':animated') && $slideLi.length >3){
			var $this = $(this);
			var clickIndex = ($this.hasClass('turn_left'))?-1:1;
			if(clickIndex == -1){
				$slideBox.find('ul').css('left',-liWidth).find('li:last').prependTo($slideBox.find('ul')).end().end().animate({
					'left':0
				});
			}else if(clickIndex == 1){
				$slideBox.find('ul').animate({
					'left':-liWidth
				},function(){
					$slideBox.find('li:first').appendTo($slideBox.find('ul'));
					$slideBox.find('ul').css('left',0);
				});
			}
		}
	});
	function autoSlide(){
		clearTimeout(timeSlide);
		$slideBox.find('.turn_right').triggerHandler('click');
		timeSlide = setTimeout(autoSlide,3000);
	}
	timeSlide = setTimeout(autoSlide,4000);
	$slideBox.hover(function(){
		$slideBox.find('.turn_left').stop().animate({
			left:0
		});
		$slideBox.find('.turn_right').stop().animate({
			right:0
		});
		clearTimeout(timeSlide);
	},function(){
		$slideBox.find('.turn_left').stop().animate({
			left:'-18px'
		});
		$slideBox.find('.turn_right').stop().animate({
			right:'-18px'
		});
		timeSlide = setTimeout(autoSlide,3000);
	});
});




(function(){
$.sub('login_success',__draw_userarea);
function __draw_userarea(event,data,action){
	var str = '<dl class="logined clearfix"><dt></dt><dd class="hy">欢迎您</dd><dd class="green fontTahoma" title="' + (data.nick_name || data.user_name) + '">' + (data.nick_name || data.user_name) + '</dd></dl>';
	$('#indexAbout').html(str);
}
})();



;$(function(){
	$
	.sub('click_dcstracker_PHONE_AD',__dcstracker_phonead)
	.sub('click_dcstracker_phone',__dcstracker_phone)
	;
	function __dcstracker_phonead(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'PHONE',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_phone(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'PHONE',
			event:$t.attr('dcs_id')
		});
	}
});

                        

;$(function(){
	$
	.sub('click_dcstracker_HUAFEI_AD',__dcstracker_huafei)
	.sub('click_dcstracker_huafei',__dcstracker_huafei)
        .sub('click_dcstracker_huafeimenu',__dcstracker_huafei)
	;

	function __dcstracker_huafei(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'HUAFEI',
			event:$t.attr('dcs_id')
		});
	}
});



;$(function(){
	$
	.sub('click_dcstracker_EXPENSE_AD',__dcstracker_expensead)
	.sub('click_dcstracker_expensemenu',__dcstracker_expensemenu)
	.sub('click_dcstracker_expenseexpense',__dcstracker_expenseexpense)
	;
	function __dcstracker_expensead(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'EXPENSE',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_expensemenu(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'EXPENSE',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_expenseexpense(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'EXPENSE',
			event:$t.attr('dcs_id')
		});
	}
});



;$(function(){
	$
	.sub('click_dcstracker_VAS_AD',__dcstracker_vasad)
	.sub('click_dcstracker_vasmenu',__dcstracker_vasmenu)
	.sub('click_dcstracker_vasphone',__dcstracker_vasphone)
	;
	function __dcstracker_vasad(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'VAS',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_vasmenu(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'VAS',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_vasphone(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'VAS',
			event:$t.attr('dcs_id')
		});
	}
});



;$(function(){
	$
	.sub('click_dcstracker_ACCESSORY_AD',__dcstracker_accessoryad)
	.sub('click_dcstracker_accessorymenu',__dcstracker_accessorymenu)
	.sub('click_dcstracker_accessoryphone',__dcstracker_accessoryphone)
	;
	function __dcstracker_accessoryad(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'ACCESSORY',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_accessorymenu(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'ACCESSORY',
			event:$t.attr('dcs_id')
		});
	}
	function __dcstracker_accessoryphone(){
		var $t= $(this);
		$.dcstracker({
			category:'Home',
			action:'ACCESSORY',
			event:$t.attr('dcs_id')
		});
	}
});



$(function(){
	$
	.sub('click_dcstracker_common',__dcstracker_common)
	;
	function __dcstracker_common(){
		var $t = $(this);
		$.dcstracker({
			category:'Home',
			action:$t.attr('act'),
			event:$t.attr('dcs_id')
		});
	}
	var $rechargeBtn = $('.rechargeSubmit');
	var $rechargeForm = $rechargeBtn.closest('.charge_area');
	$('.goodsNavBanner .recharge .yuan .toggle').click(function(){
		$(this).closest('.yuan').find('.select_yuan').toggle();
	});
	var url ="http://shop.10086.cn/i/?f=rechargecredit";
	$rechargeForm.find('input[name="phonenum"]').keyup(function(){
		initUrl();
	});
	$('.goodsNavBanner .recharge .yuan .select_yuan a').click(function(){
		var $this = $(this);
		$this.closest('.yuan').find('.yuan_show').text($this.text());
		$this.parent().siblings('input[name=amount]').val(parseInt($this.text()));
		$this.closest('.select_yuan').hide();
		initUrl();
	});

	function initUrl(){
		var data = {
			mobileNo : $rechargeForm.find('input[name="phonenum"]').val(),
			amount: $rechargeForm.find('input[name="amount"]').val()
		}
		if(isNaN(data.mobileNo)){
			delete data.mobileNo;
		}
		$rechargeBtn.attr("href",url+"&"+ $.param(data))
	}
	//手机号默认文字
	$('.recharge .number input[name="phonenum"]').iPrompt({
		refer:'iprompt'
	});
	initUrl();
});

$(document).ready(function(){
  $(".favMsg p span").hover(function(){
	  $(".favMsg p span").removeClass("cur");
	  $(this).addClass("cur");
  });
  $(".tt_sckb").hover(function(){
	  $(".con_scgg").hide();
	  $(".con_sckb").show();
  });
  $(".tt_scgg").hover(function(){
	  $(".con_sckb").hide();
	  $(".con_scgg").show();
  });
});


},'utf-8');
</script>
</body>
</html>
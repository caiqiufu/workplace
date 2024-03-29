<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>超酷的CSS列表排行榜</title>
    <meta http-equiv="content-type" content="text/html;charset=gb2312">
    <!--把下面代码加到<head>与</head>之间-->
    <style type="text/css">
        *
        {
            font-family: simsun;
            margin: 0px;
            padding: 0px;
        }
        body
        {
            font-size: 12px;
            text-align: center;
        }
        ul, li
        {
            list-style: none;
        }
        a
        {
            text-decoration: none;
            color: #3381BF;
        }
        a:hover
        {
            text-decoration: underline;
        }
        #movie_rank
        {
            height: 260px;
        }
        .box2
        {
            border: 1px solid #ADDFF2;
            text-align: left;
            overflow: hidden;
            color: #9C9C9C;
            text-align: left;
        }
        .box2
        {
            margin-bottom: 7px;
        }
        .box2 h2
        {
            background: #EEF7FE;
            height: 21px;
            line-height: 21px;
            overflow-y: hidden;
            border-bottom: 1px solid #ADDFF2;
            color: #1974C8;
            font-size: 12px;
            padding: 0px 8px;
        }
        .box2 h2 a.more
        {
            float: right;
            text-decoration: underline;
            background: url() no-repeat 100% -123px;
            padding-right: 8px;
            font-weight: normal;
        }
        .box2 h2 span
        {
            margin-left: 5px;
            font-weight: normal;
            color: #B9B7B8;
        }
        .box2 .inner
        {
            padding: 8px;
            line-height: 18px;
            overflow: hidden;
            color: #3083C7;
        }
        .box2 a
        {
            color: #3083C7;
            white-space: nowrap;
        }
        .rank_list
        {
            line-height: 14px;
            margin: auto;
            padding-top: 5px;
        }
        .rank_list li
        {
            height: 14px;
            margin-bottom: 8px;
            width: 290px;
            padding-left: 20px;
            white-space: nowrap;
            overflow: hidden;
            position: relative;
        }
        .rank_list li.top3 em
        {
            background: #FFE4B7;
            border: 1px solid #FFBB8B;
            color: #FF6800;
        }
        .rank_list em
        {
            position: absolute;
            left: 0;
            top: 0;
            width: 12px;
            height: 12px;
            border: 1px solid #B1E0F4;
            color: #6298CC;
            font-style: normal;
            font-size: 10px;
            font-family: Arial;
            background: #E6F0FD;
            text-align: center;
            line-height: 12px;
            overflow: hidden;
        }
        .rank_list span
        {
            position: absolute;
            width: 60px;
            color: #B7B7B7;
            text-align: right;
            height: 14px;
            background: #fff;
            left: 110px;
        }
        #movie_rank .rank_list span
        {
            position: absolute;
            width: 40px;
            color: #B7B7B7;
            text-align: right;
            height: 14px;
            background: #fff;
            left: 260px;
        }
    </style>
</head>
<body>
    <!--把下面代码加到<body>与</body>之间-->
    <div class="box2" id="movie_rank">
        <h2>
            <a href="#" class="more">更多</a>源码排行</h2>
        <div class="inner">
            <ul class="rank_list">
                <li class="top3"><em>1</em><a href="#">艾恩An-Upload无组件上传类</a><span>621</span></li>
                <li class="top3"><em>2</em><a href="#">EasySlide jQuery图片轮显</a><span>528</span></li>
                <li class="top3"><em>3</em><a href="#">通用Ajax无刷新表彰验证类</a><span>432</span></li>
                <li><em>4</em><a href="#">支持中文的鼠标取词VB源码</a><span>374</span></li>
                <li><em>5</em><a href="#">通用Ajax无刷新表彰验证类</a><span>256</span></li>
                <li><em>6</em><a href="#">EasySlide jQuery图片轮显</a><span>185</span></li>
                <li><em>7</em><a href="#">支持中文的鼠标取词VB源码</a><span>95</span></li>
                <li><em>8</em><a href="#">多样式链接提示框组件</a><span>85</span></li>
                <li><em>9</em><a href="#">类似lightbox的无刷新图片显示插件</a><span>51</span></li>
                <li><em>10</em><a href="#">通用Ajax无刷新表彰验证类</a><span>32</span></li>
            </ul>
        </div>
    </div>
</body>
</html>
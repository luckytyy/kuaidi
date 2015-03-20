<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8"></meta>
<title>中国邮政-给据邮件跟踪查询系统</title>
<meta http-equiv="keywords" content="keyword1,keyword2,by huxj"></meta>
<meta http-equiv="description" content="This is my page"></meta>
<style>
@page {
	size: 297mm 210mm;
}
/***datacell固定表头的css.*/
.DATACELL_FIXHEAD tr {
	position: relative;
	/* expression is for WinIE 5.x only. Remove to validate and for pure CSS solution */
	top: expression(this.offsetParent.scrollTop);
	background-color: #eeeeee;
	Z-INDEX: 5;
}
/***datacell固定列的css.*/
.DATACELL_FIXCOL {
	border: none;
	z-index: 5;
	POSITION: relative;
	LEFT: expression(this.parentElement.offsetParent.parentElement.scrollLeft);
}
/***datacell固定表头的列的css.*/
.DATACELL_FIXHEADCOL {
	border: none;
	Z-INDEX: 10;
	POSITION: relative;
	LEFT: expression(this.parentElement.offsetParent.scrollLeft);
}

.dialog_title_mid {
	background-image: url('../images/title_mid.gif');
	height: 29px;
	overflow: hidden;
	background-repeat: repeat-x;
	background-color: #004bfb;
	color: #ffffff;
	font-weight: bold;
	border: 0;
}

.dialog_container {
	width: 100%;
	height: 100%;
}

.dialog_body {
	border-left: 3px solid #0D2FE0;
	border-right: 3px solid #0D2FE0;
	border-bottom: 3px solid #0D2FE0
}

.dialog_title_left {
	background-image: url('../images/title_left.gif');
	height: 29px;
	width: 7px;
	background-repeat: no-repeat;
	background-position: right;
	overflow: hidden;
}

.dialog_title_right {
	background-image: url('../images/title_right.gif');
	height: 29px;
	width: 7px;
	background-repeat: no-repeat;
	background-position: left;
	overflow: hidden;
}

* {
	font: 9pt "SimSun";
	color: #222;
}

body {
	scrollbar-highlight-color: white;
	scrollbar-3dlight-color: #F7FCFD;
	scrollbar-face-color: #E7E7E7;
	scrollbar-arrow-color: #222222;
	scrollbar-shadow-color: #C0D2DB;
	scrollbar-darkshadow-color: #716F64;
	scrollbar-track-color: #F8F7F9;
	margin: 0 0 0 0;
}

a:link {
	text-decoration: underline;
	color: #00414E;
}

a:hover {
	text-decoration: underline;
	color: FE6C00;
}

a:visited {
	color: #588993;
	text-decoration: underline;
}

.button {
	height: 20px;
	background: #DCE1E7;
	border-top: 1px #fff solid;
	border-right: 1px #7F9DB9 solid;
	border-bottom: 1px #7F9DB9 solid;
	border-left: 1px #fff solid;
	padding: 1px 4px;
	/*
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #fbfdfc),
		color-stop(1, #cfe0f0) );
	filter: progid : DXImageTransform.Microsoft.gradient ( enabled =
		'enabled', startColorstr = #fbfdfc, endColorstr = #cfe0f0 );
	background: -moz-linear-gradient(top, #fbfdfc, #cfe0f0);
	background: -ms-linear-gradient(#fbfdfc 0%, #cfe0f0 100%);
	*/
	background: #ccc;
}

.button[disabled],.button[disabled]:hover,.button[disabled]:active {
	border-color: #eaeaea;
	background: #fafafa;
	cursor: default;
	position: static;
	color: #999;
	box-shadow: none !important;
	text-shadow: none !important;
}

.textbox {
	border: 1px solid #016F87;
	background: #fff;
}

hr /* compart line's style */ {
	color: #01B7DE;
	background: #fff;
	height: 2px;
	border-bottom: 1px solid #fff;
	width: 98%;
}
/* work area's style*/
.workarea {
	background: #F3F9FA;
	border: 1px solid #75B5C3;
}

.workarea .workarea_title {
	background: #E3F0F2;
	height: 27px;
	border-bottom: 1px solid #75B5C3;
	padding-left: 18px;
}

h3 /*the title's font style of current page*/ {
	display: inline;
	font-weight: bold;
	color: #FE6C00;
}

.EOS_panel_head /* the block title */ {
	background: #BFC9CD;
	height: 22px;
	font-weight: bold;
	border: none;
	color: #000;
	padding-left: 20px;
}

/*the different table's style*/
.EOS_panel_body
	/* Query table and Result table share this table style */ {
	background: #F5F5F5;
}

.EOS_table {
	background: #F7FDFD;
	border-collapse: collapse;
	border: 1px solid #AEC2CD;
}

.EOS_table tr td,.EOS_table tr th
	/*  the common style of Result table's td and th */ {
	border: 1px solid #AEC2CD;
	height: 20px;
	padding: 0px 3px;
}

.EOS_table tr th {
	height: 25px;
	vertical-align: middle;
	padding-top: 3px;
	background: #F4F5F9;
	filter: progid : DXImageTransform.Microsoft.gradient ( enabled =
		'enabled', startColorstr = #D3DAE0, endColorstr = #F4F5F9 );
	text-align: center;
}

.EOS_table_row
	/* the different background color between rows in result table */ {
	background: #EBF3F5;
}

.EOS_table_selectRow
	/* the selected row's background color in result table */ {
	background: #CDDEE6;
}

.command_sort_area /* the bottom of result table */ {
	padding: 2px 0 5px 8px;
	background: #F5F5F5;
}

.command_sort_area .h3 {
	float: left;
}

.command_sort_area .h4 {
	float: right;
}

.command_sort_area h4 /*the pagination's style*/ {
	float: right;
	padding-right: 8px;
	margin-top: -18px;
}

.form_table {
	border-collapse: collapse;
	border: 1px solid #AEC2CD;
	background: #F7FDFD;
	padding-left: 5px;
}

.form_table td {
	height: 25px;
	border: 1px solid #AEC2CD;
}

.form_bottom /* the bottom of form table */ {
	padding: 10px 0 5px 8px;
	background: #F5F5F5;
	text-align: center;
}

.form_label {
	background: #EBF3F5;
}

.eos-popwin .eos-popwin-head-max-button a {
	cursor: pointer;
	height: 15px;
	width: 15px;
	background-repeat: no-repeat;
	background-position: 0px 0px;
	text-decoration: none;
	-moz-opacity: 0.5;
	opacity: .50;
	filter: alpha(opacity =   50);
}

.eos-popwin .eos-popwin-head-max-button a:hover {
	-moz-opacity: 1;
	opacity: 1;
	filter: alpha(opacity =   100);
}

* {
	margin: 0;
	padding: 0;
	font-size: 12px;
}

html,body {
	height: 100%;
	width: 100%;
}

html,body {
	height: 100%;
	width: 100%;
	margin: 0;
	padding: 0;
}

body {
	background-color: #fff;
	font-family: "SimSun", "宋体", "Helvetica Neue", Helvetica, Arial,
		sans-serif;
	font-size: 12px;
	color: #333;
}

#content {
	background: #f8f8f8;
	padding-left: 2px;
}

#treeDiv {
	border: 1px solid #369;
	width: 245px;
    height: 563px;
	background: #e2ecf5;
	z-index: 100;
	position: absolute;
	display: block;
}

#pgheader {
	height: 64px;
}

.text-center {
	text-align: center;
}

#statebox {
	height: 36px;
}

.contentCss {
	height: 79% !important;
	height: 77.9%;
	overflow: hidden;
}

#footer {
	height: 28px;
	position: absolute;
	width: 100%;
	background:red;
	bottom: 57px;
}

.pgheader {
	background: url(yz-header-bg.jpg) left top repeat-x;
	height: 64px;
}

.pgheader .cont {
	background: url(yz-head-logo1.jpg) right top no-repeat;
	margin: 0 15px;
}

.pgheader .cont h1.logo {
	background: url(yz-logo.jpg) left top no-repeat;
	height: 62px;
}

.treeContent {
	clear: both;
	margin-top: 0px;
	border-top: none;
	height: 181px;
	padding-top: 8px;
	height: 100%;
}

.treeContent ul {
	list-style: none;
	margin: 7px;
	padding: 0;
}

.treeContent ul li {
	line-height: 24px;
	border-bottom: 1px dotted #ccc;
	cursor: hand;
	color: #298266;
}
.content-box {
	height: 100%;
	background: url(yz-cont-bg.jpg) left top
		repeat-x;
	position: relative;
	overflow: hidden;
}

.content-box div#cont {
	height: 100%;
	margin-left: 46px;
}

.footer-box {
	height: 27px;
	border-top: 1px solid #b8c1bd;
	background-color: #ededed;
}

.copyright {
	color: #3e6f68;
	line-height: 27px;
}

.tyy_td {
	height: 29px;
}


.tyy_num {
	width: 6%;
}

.tyy_ems {
	width: 16%;
}

.tyy_address {
	width: 15%;
}

.tyy_status {
	width: 47%;
}

.tyy_time {
	width: 16%;
}

.tyy_special1 {
	width: 6%;
}
.tyy_special2 {
	width: 16%;
}
.tyy_special3 {
	width: 78%;
}


.div_table{
	width: 68%; 
	display: inline-block; 
	height: 20px;
	position: absolute; 
	margin-left:268px; 
	margin-top:-1.5px; 
	padding: 0 auto;
}

.tyy_table,.tyy2_table {
	background: #F7FDFD;
	border-collapse: collapse;
	border: 1px solid #AEC2CD;
	border-spacing: 2px; 
	height: 100%;
	table-layout: fixed;
}
.tyy_table{
 width:99.5%;
}

#detail_table
{
width:97%;
}

.tyy_table tr,.tyy_table td,.tyy2_table tr,.tyy2_table td{
border-collapse:collapse;border:1px solid #e5e5e5;
height:20px;text-align:center;
font: normal 11px "SimSun";
vertical-align: center;
overflow: hidden;
white-space: nowrap;
word-break: keep-all;
word-wrap: normal;
text-overflow: ellipsis;
background-color:#e3eefb;
text-align:left;
/* vertical-align: top;*/
}

#detail_table .tr_odd td{
background-color:#f1f5f6;
}

</style>

</head>
<body style="overflow: hidden;">
	<table width="100%" height="100%">
		<tr height="68">
			<td colspan="2">
				<div class="pgheader">
					<div class="cont">
						<h1 title="中国邮政-给据邮件跟踪查询系统" class="logo"></h1>
					</div>
				</div></td>
		</tr>
		<tr >
			<td width="250px" valign="top">
				<div id="content">
					<div id="treeDiv" class="treeContent">
						<ul>
							<li >·<a href="#">邮件跟踪查询</a>
							</li>
							<li>·<a href="#">协议客户查询</a>
							</li>
						</ul>
					</div>
				</div></td>
			<td valign="top" >
				<div id="queryHeaderId" style="border: 2px inset;">
					<form id="queryFormID" name="queryForm" action="" target="_self"
						method="post">
						<table border="0" width="100%" class="form_table" cellpadding="0"
							cellspacing="0" >
							<tr>
								<td class="form_label">邮件号码</td>
								<td colspan="3"><textarea id="vYjhm" name="vYjhm" style="display: block; width: 320px; height: 79px;
								 border: 1px solid rgb(169, 169, 169);"> ${object.orderNum?default('')}</textarea>
									</td>
							</tr>
							<tr>
								<td class="form_label">验证码</td>
								<td colspan="3" nowrap="nowrap" style="vertical-align:middle;">
								
									<input type="text" id="validcode" name="validcode" style="width: 128px; height: 18px; border: 2px inset; vertical-align:middle;" /><font style="color:red;">*</font> 
								
								<img id="validcodeimg" src="http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_drawValidCode.action"
									style="margin-left: 100px;" />


<button class="button" id="subBtnId" type="button"
	style="line-height: 25px; text-align: center; width: 40px; height: 25px; cursor: pointer; margin-left: 50px; font-size: 12px;">查
	询</button>
<button class="button" type="reset"
	style="line-height: 25px; text-align: center; width: 40px; height: 25px; cursor: pointer; margin-left: 30px; font-size: 12px;">重
	置</button>
  </td>
							</tr>
							<tr id="errorMsgTrId" style="display: none; margin-top: 0">
								<td colspan="4">
									<div id="errorMsgId"
										style="color: red; background-color: #FEF9BA;"></div></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="cell1_container" style="height: 453px;overflow:hidden;">

					<div style="display: block; margin-left: 3px;">
						<img src="table_header.jpg" style="margin-left: -2.8px;width:100%;" />
					</div>

					<div>
						<div style="margin-top: -2.8px;width:2%;height: 65%; display: inline-block;">
							<img src="table_left.jpg" style="width:100%;height:65%;"/>
						</div>

						<div class="div_table">
							<table class="tyy_table">
								
									<tr class="tyy_td">
										<td class="tyy_num">1</td>
										<td class="tyy_ems">XA24435342142</td>
										<td class="tyy_address">湖北宜昌市</td>
										<td class="tyy_status">【宜昌市本口投递支局】已妥投 收发室收</td>
										<td class="tyy_time">
										2015-02-14 16:00:00
										</td>
									</tr>
							
							</table>
							
							<table id="detail_table" class="tyy2_table" >
							
								<#list object.detail.records as record> 
									<#if record_index%2 = 0>
									 <tr>
									<#else>
									<tr class="tr_odd">
									</#if>
									<td class="tyy_special1"></td>
										<td class="tyy_special2"><#if record.datetime?? >${record.datetime?string("yyyy-MM-dd HH:mm:ss")}</#if>
										</td>
										<td class="tyy_special3">${record.address?default('')}</td></tr>
 									</#list>							
							
							</table>	
						</div>

						<div
							style="display: block; margin-top: -4px; margin-left: -1.3px; width: 100%; height: 80px;">
							<img src="table_bottom.jpg" style="" />
						</div>
					</div>
				</div>
			</td>
		</tr>
		<tr height="27px">
			<td colspan="2">
				<div id="footer">
					<div class="footer-box">
						<div class="copyright text-center">中国邮政集团公司版权所有</div>
					</div>
				</div></td>
		</tr>
	</table>

</body>
</html>
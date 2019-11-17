<%@ include file="/ecps/console/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<title>添加品牌_品牌管理_商品管理</title>
<meta name="heading" content="品牌管理"/>
<meta name="tag" content="tagName"/>
<script type="text/javascript" src="${path}/ecps/console/res/js/jquery.form.js"></script>
<script type="text/javascript">var path = "${path}";</script>
<script type="text/javascript" src="${path}/ecps/console/res/item/js/editbrand.js"/>

</head>
<body id="main">
<div class="frameL"><div class="menu icon">
    <jsp:include page="/${system}/common/itemmenu.jsp"/>
</div></div>

<div class="frameR"><div class="content">
	<div class="loc icon"><samp class="t12"></samp>当前位置：商品管理&nbsp;&raquo;&nbsp;<a href="<c:url value="/${system }/item/brand/listBrand.do"/>" title="品牌管理">品牌管理</a>&nbsp;&raquo;&nbsp;<span class="gray">编辑品牌</span>
    <a href="<c:url value="${path}/item/listBrand.do"/>" title="返回品牌管理" class="inb btn80x20">返回品牌管理</a>
    </div>
	<form id="form111" name="form111" action="${path }/item/updateBrand.do" method="post" enctype="multipart/form-data">
		<input type="hidden" id="lastRealPath" name="lastRealPath">
		<div class="edit set">
			<input type="hidden" id="brandId" name="brandId" value="${brand.brandId }">
			<p><label><samp>*</samp>品牌名称：</label>
				<input type="text" value="${brand.brandName }" id="brandName" name="brandName" class="text state" reg2="^[a-zA-Z0-9\u4e00-\u9fa5]{1,20}$" tip="必须是中英文或数字字符，长度1-20"/>
				<span></span>
			</p>
            <p><label class="alg_t"><samp>*</samp>品牌LOGO：</label>
				<img id='imgsImgSrc' src="${request_file_path}${brand.imgs}" height="100" width="100" />
            </p>
             <p><label></label>
				 <input type='file' size='27' id='imgsFile' name='imgsFile' class="file" onchange='submitUpload()' />
				 <span id="submitImgTip" class="pos">请上传图片宽为120px，高为50px，大小不超过100K。</span>
                <input type='hidden' id='imgs' name='imgs' value="${brand.imgs }" reg2="^.+$" tip="亲！您忘记上传图片了。" />
			</p> 
				
			<p><label>品牌网址：</label><input type="text" value="${brand.website}" name="website" class="text state" maxlength="100"  tip="请以http://开头" reg1="http:///*"/>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label class="alg_t">品牌描述：</label><textarea rows="4" cols="45"  name="brandDesc" class="are" reg1="^(.|\n){0,300}$" tip="任意字符，长度0-300">${brand.brandDesc }</textarea>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label>排序：</label><input type="text" value="${brand.brandSort }" id="brandSort" reg1="^[1-9][0-9]{0,2}$" tip="排序只能输入1-3位数的正整数" name="brandSort" class="text small"/>
				<span class="pos">&nbsp;</span>
			</p>
			<p><label>&nbsp;</label><input type="submit" name="button1" d class="hand btn83x23" value="完成" />
				<input type="button" class="hand btn83x23b" id="reset1" value='取消'/>
			</p>
		</div>
	</form>
    <div class="loc">&nbsp;</div>

</div></div>
</body>


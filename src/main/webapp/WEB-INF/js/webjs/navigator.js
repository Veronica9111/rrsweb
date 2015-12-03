$(document).ready(function(){
	$.post("/user/getRoles",{},function(data){
		console.log(data)
		var roles = data.data;
		console.log(roles.indexOf("发票识别员"));
		if(roles.indexOf("发票识别员") != -1){
			$("#navigator").append('<li><a class="link" href="/views/webviews/invoice/recognize.html">发票识别</a></li>');
		}
		if(roles.indexOf("发票审核员") !=-1){
			$("#navigator").append('<li><a class="link" href="/views/webviews/invoice/check.html">发票审核</a></li>');
		}
		if(roles.indexOf("管理") != -1){
			$("#navigator").append('<li><a class="link" href="/views/webviews/user/manage.html">用户管理</a></li>');
			$("#navigator").append('<li><a class="link" href="/views/webviews/role/manage.html">权限管理</a></li>');
		}
		if(roles.indexOf("发票管理员") != -1){
			$("#navigator").append('<li><a class="link" href="/views/webviews/invoice/manage.html">发票管理</a></li>');
		}
		$("#navigator").append('<li><a class="link" href="/views/webviews/user/setting.html">个人设置</a></li>');
		$("#navigator").append('<li><a class="link" href="/logout">退出登录</a></li>');
		
		$(".link").each(function(){
			if($(this).attr("href") == window.location.pathname){
				$(this).addClass("active");
			}
			
		});
	});
	
});
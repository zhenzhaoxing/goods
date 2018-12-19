 $(function() {
	/*
	 * 1. 得到所有的错误信息，循环遍历之。调用一个方法来确定是否显示错误信息！
	 */
	$(".errorClass").each(function() {
		showError($(this));//遍历每个元素，使用每个元素来调用showError方法
	});
	
	/*
	 * 2. 切换注册按钮的图片
	 */
	$("#submitBtn").hover(
		function() {
			$("#submitBtn").attr("src", "/goods/images/regist2.jpg");
		},
		function() {
			$("#submitBtn").attr("src", "/goods/images/regist1.jpg");
		}
	);
	
	/*
	 * 3. 输入框得到焦点隐藏错误信息 当鼠标移到这里面 就把内容清空
	 */
	$(".inputClass").focus(function() {
		var labelId = $(this).attr("id") + "Error";//通过输入框找到对应的label的id
		$("#" + labelId).text("");//把label的内容清空！
		showError($("#" + labelId));//隐藏没有信息的label
	});
	
	/*
	 * 4. 输入框失去焦点进行校验
	 */
	$(".inputClass").blur(function() {
		var id = $(this).attr("id");//获取当前输入框的id
		var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()";//得到对应的校验函数名
		eval(funName);//执行函数调用
	});
	
	/*
	 * 5. 表单提交时进行校验  最后必须进行表单验证     
	 */
	
	$("#registForm").submit(function() {
		var bool = true;//表示校验通过
		if(!validateLoginname()) {
			bool = false;//未嫁的表示未通过
		}
		if(!validateLoginpass()) {
			bool = false;
		}
		if(!validateReloginpass()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
			bool = false;
		}
		
		return bool;
	});
	
	
	
	
	
});

/*
 * 登录名校验方法
 */
function validateLoginname() {
	var id = "loginname";
	//var value = $("#" + id).val();//获取输入框内容
	var value = $("#loginname").val();//获取输入框内容
	/*
	 * 1. 非空校验
	 */
	if(!value) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("用户名不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2. 长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("用户名长度必须在3 ~ 20之间！");
		showError($("#" + id + "Error"));
		false;
	}
	/*
	 * 3. 是否注册校验
	 */
	
	
	
	
	
	//7个参数ajaxValidateLoginname
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateLoginname", loginname:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		//传到一个值如果为true 就说明有数据           如果为 false 就执行 if（！result）里面的
		success:function(result) {//返回为 true 下面就不执行
			//alert(result);
			if(!result) {//如果校验失败 返回为 false
				$("#" + id + "Error").text("用户名已被注册！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	
	
	
	
	
	
	
	
	
	
	return true;
}

/*
 * 登录密码校验方法
 */
function validateLoginpass() {
	var id = "loginpass";
	var value = $("#" + id).val();//获取输入框内容
	/*
	 * 1. 非空校验
	 */
	if(!value) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("密码不能为空！");
		showError($("#" + id + "Error"));  //这个文本内容 传到 showError方法里 然后 
		return false;
	}
	/*
	 * 2. 长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("密码长度必须在3 ~ 20之间！为了安全 必须是字母加数字形式 ");
		showError($("#" + id + "Error"));
		false;
	}
	/*
	 * 3. 是否注册校验
	 */
	
	return true;
	
	
	
}

/*
 * 确认密码校验方法
 */
function validateReloginpass() {
	var id ="reloginpass";
	var value = $("#"+id).value();
	
	if(!value){
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("确认密码不能为空");
		showError($("#"+id+"Error")) ;
  return false;		
		
	}
	
	if(value != $("#loginpass").val()){
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("两次输入不一致！");
		showError($("#" + id + "Error"));
		false;
	}
	return true;	
	
	
//	//先获取 密码文本框中的值 
//	$("#loginpass").val();
	
	
	
	
	
}

/*
 * Email校验方法
 */
function validateEmail() {
	var id = "email";
	var value = $("#" + id).val();//获取输入框内容
	
	/*
	 * 1. 非空校验
	 */
	if(!value) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("Email不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2. Email格式校验  用到 正则表达式
	 */
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("错误的Email格式！");
		showError($("#" + id + "Error"));
		false;
	}
	/*
	 * 3. 是否注册校验
	 * 
	 */
	
	
	
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateEmail", loginname:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		success:function(result) {//返回为 true 下面就 不执行
			//alert(result);
			if(!result) {//如果校验失败                返回为 false就执行
				$("#" + id + "Error").text("邮箱已被注册！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	
	
	
	return true;		
}

/*
 * 验证码校验方法
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val();//获取输入框内容
	/*
	 * 1. 非空校验
	 */
	if(!value) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("验证码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 2. 长度校验
	 */
	if(value.length != 4) {
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("错误的验证码！");
		showError($("#" + id + "Error"));//传到 showError里面
		false;
	}
	/*
	 * 3. 是否正确
	 */
	
	
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateCodefi", verifyCode:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		success:function(result) { true //不执行 不显示 为 false 就执行下面的
			if(!result) {//如果校验失败
				$("#" + id + "Error").text("验证码错误！");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	
	
	return true;		
}

/*
 * 判断当前元素是否存在内容，如果存在显示，不页面不显示！
 */
function showError(ele) {
	var ctext = ele.text();//获取元素的内容//把错误信息 传到这里 有内容l 就显示 
	if(!ctext) {//如果没有内容
		ele.css("display", "none");//隐藏元素
	} 
	else {//如果有内容
		ele.css("display", "");//显示错误的元素
	}
}

/*
 * 换一张验证码
 */
function  xh(){
//	  var img = document.getElementById("imgVerifyCode");
//	             验证码直接是封装好的 直接点用 在xml 里面调用
//	  img.src= "<c:url value='/goods/VerifyCodeServleta='  + new Date().getTime() />";
	  /*
		 * 1. 获取<img>元素
		 * 2. 重新设置它的src
		 * 3. 使用毫秒来添加参数
		 */
		$("#imgVerifyCode").attr("src", "/goods/VerifyCodeServlet?a=" + new Date().getTime());
	  }

$(function() {
	/*
	 * 1. 让登录按钮在得到和失去焦点时切换图片
	 */
	$("#submit").hover(
		function() {
			$("#submit").attr("src", "/goods/images/login2.jpg");
		},
		function() {
			$("#submit").attr("src", "/goods/images/login1.jpg");
		}
	);
	
	/*
	 * 2. 给注册按钮添加submit()事件，完成表单校验
	 */
	$("#submit").submit(function(){
		$("#msg").text("");
		var bool = true;
		$(".input").each(function() {
			var inputName = $(this).attr("name");
			if(!invokeValidateFunction(inputName)) {
				bool = false;
			}
		});
		return bool;
	});
	
	/*
	 * 3. 输入框得到焦点时隐藏错误信息
	 */
	$(".input").focus(function() {
		var inputName = $(this).attr("name");
		$("#" + inputName + "Error").css("display", "none");
	});
	
	/*
	 * 4. 输入框推动焦点时进行校验
	 */
	$(".input").blur(function() {
		var inputName = $(this).attr("name");
		invokeValidateFunction(inputName);
	})
});

/*
 * 输入input名称，调用对应的validate方法。
 * 例如input名称为：loginname，那么调用validateLoginname()方法。
 */
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

/*
 * 校验登录名  这就不用 后台校验了
 */
function validateLoginname() {
	var bool = true;
	$("#loginnameError").css("display", "none");
	var value = $("#loginname").val();
	if(!value) {// 非空校验
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名长度必须在3 ~ 20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验密码
 */
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {// 非空校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码长度必须在3 ~ 20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验验证码
 */
function validateVerifyCode() {
	
	$("#verifyCodeError").css("display", "none");
	var id = "verifyCode";
	var value = $("#" + id).val();//获取输入框内容
	if(!value) {//非空校验
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("验证码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	} else if(value.length != 4) {//长度不为4就是错误的
		/*
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#" + id + "Error").text("错误的验证码！");
		showError($("#" + id + "Error"));//传到 showError里面
		false;
	} 
	
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





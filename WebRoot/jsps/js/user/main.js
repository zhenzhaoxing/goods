
//倒计时
	var secondskill = 4763000;

	function djs(){
	//计算时间差能换算成多少小时
	var hours = parseInt(secondskill/(60*60*1000));
	//获得计算完小时后还剩余的毫秒数
	difftime = secondskill%(60*60*1000);
	//计算分钟
	var minutes = parseInt(difftime/(60*1000));
	//获得计算完分钟后,还剩余的毫秒数
	difftime = difftime%(60*1000);
	//计算秒数
	var seconds = parseInt(difftime/1000);

	$("#boxda #shan .bentime div").eq(0).html(hours);
	$("#boxda #shan .bentime div").eq(1).html(minutes);
	$("#boxda #shan .bentime div").eq(2).html(seconds);
	}


	djs();


	setInterval(function(){
	secondskill = secondskill-1000;
	djs();
	},1000)

















$(function() {
	$("#from_shan #boxda .you >#mi").mouseenter(function(){
		$(this).animate({'top':'-20px'},500);
		$(this).find('.bot').animate({'top':'260px','opacity':1},500);
	})
	
	$("#from_shan #boxda .you >#mi").mouseleave(function(){
		$(this).animate({'top':0},300);
		$(this).find('.bot').animate({'top':'310px','opacity':0},300);
	});
	











	




});
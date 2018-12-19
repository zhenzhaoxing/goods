$(function() {

	var c = 1;
	function run() {
		c++;
		if (c == 6) {
			c = 0;
		}

		$("#sess img").eq(c).fadeIn(300).siblings('img').fadeOut(300);
		$("#sess ul li").eq(c).css({
			'background' : '#A10000'
		}).siblings('li').css({
			'background' : 'black'
		});
	}
	//设置定时器自动轮播
	var timer = setInterval(run, 3000);

	//	给li加鼠标移入事件
	$("#sess ul li").mouseenter(function() {
		var jqthis = $(this);
		//		停止定时器
		clearInterval(timer);
		//		如果$(this)写在定时器里,那么$(this)指向的就是定时器
		tt = setTimeout(function() {
			//		获得移入的li的序号
			c = jqthis.index();
			//		让c号图片显示,兄弟图片隐藏

			$("#sess img").eq(c).stop().fadeIn(300).siblings('img').stop().fadeOut(300);
			//		让c号li变红
			$("#sess ul li").eq(c).css({
				'background' : '#A10000'
			}).siblings('li').css({
				'background' : 'black'
			});
		}, 300)

	})

	//	鼠标移出事件
	$("#sess ul li").mouseleave(function() {
		//		清理定时炸弹
		clearTimeout(tt);
		//		恢复定时器
		timer = setInterval(run, 3000);
	})

	$("#sess .icon-xiangzuo").click(function() {
		c--;
		c = c == -1 ? 3 : c;
		//	让c号图片显示,兄弟图片隐藏
		$("#sess img").eq(c).fadeIn(300).siblings('img').fadeOut(300);
		//	让c号li变红,其他li变灰
		$("#sess ul li").eq(c).css('background', '#a10000')
			.siblings('li').css('background', '#ddd');
	})

	$("#sess  .icon-you").click(function() {
		c++;
		c = c == 4 ? 0 : c;
		//	让c号图片显示,兄弟图片隐藏
		$("#sess  img").eq(c).fadeIn(300).siblings('img').fadeOut(300);
		//	让c号li变红,其他li变灰
		$("#sess  ul li").eq(c).css('background', '#a10000')
			.siblings('li').css('background', '#ddd');
	})






})
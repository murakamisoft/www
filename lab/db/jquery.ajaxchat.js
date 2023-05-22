$(function() {

	// データ抽出用PHPの呼び出し
	$.post("./read.php", {
		default_val : 1
	}, function(data) {
		if (data.length > 0) {
			$("#results").html(data);
		}
	})

	// コメント送信時のアクションを呼び出す
	$("#button").click(function(e) {
		$("#results").prepend("<div class='loading'><img src='img/loading.gif'></div>");
		var t = setTimeout(ajax_send, 500);
	});

});

// コメント送信時のアクション
function ajax_send() {

	// 送信されたデータを変数に格納
	var comment_val = $("#message").val();

	// データ投稿用PHPの呼び出し
	$.post("./action.php", {
		message : comment_val
	}, function(data) {

		if (data.length > 0) {
			$(".loading").remove();
			$("#results").prepend(data);
			$("#results div:first-child").animate({
				'opacity' : '0'
			}, 0);
			$("#results div:first-child").animate({
				'opacity' : '1'
			}, 1000);
		} else {
			$(".loading").remove();
		}
	})
}
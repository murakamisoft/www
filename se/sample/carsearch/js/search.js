/**
 * 検索処理
 */
$(function() {
	jQuery.support.cors = true; // force cross-site scripting (as of jQuery 1.5)
	$("#btnSearch").click(function() {
		searchAjax();
	});

	$("#btnSearch").focus();
});
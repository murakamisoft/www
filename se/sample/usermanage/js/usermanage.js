/**
 * ユーザー管理
 */
var userMng = {
	list : [ {
		name : "村上 太郎",
		address : "東京都品川区品川６−７−８",
		post : "第一開発部",
	}, {
		name : "青木 一郎",
		address : "東京都千代田区神田橋３−４−５",
		post : "第二開発部",
	}, {
		name : "山田 正一",
		address : "東京都目黒区目黒５−５−５",
		post : "第三開発部",
	}, {
		name : "太田 宏介",
		address : "東京都港区芝浦１−１−１",
		post : "営業部",
	}, ],
	add : function(u) {
		this.list.push(u);
	},
};

$(function() {
	jQuery.support.cors = true; // force cross-site scripting (as of jQuery 1.5)
	$("#btnAddUser").click(function() {

		var user = {
			name : $("#txtName").val(),
			address : $("#txtAddress").val(),
			post : $("#txtPost").val(),
		};

		userMng.add(user);
		// $.each(userMng.list, function(key, value) {
		// alert(key + " , " + value.name + " , " + value.address);
		// });

		drawUserListTable();
	});

	drawUserListTable();
	$(".txtName").focus();
});

/**
 * ユーザーリストテーブルを描画する。
 */
function drawUserListTable() {
	var html = "";

	html += "<table class='table table-striped'>";
	html += "<tr>";
	html += "<th>名前</th>";
	html += "<th>住所</th>";
	html += "<th>部署名</th>";
	html += "</tr>";
	$.each(userMng.list, function(idx, value) {
		html += "<tr>";
		html += "<td>" + value.name + "</td>";
		html += "<td>" + value.address + "</td>";
		html += "<td>" + value.post + "</td>";
		html += "</tr>";
	});

	html += "</table>";

	$(".userListArea").html(html);
}
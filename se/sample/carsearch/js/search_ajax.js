/**
 * Ajax通信を実行する。
 */
function searchAjax() {

	openProcessing();

	var urlData = makeApiUrl();

	$.ajax({
		type : "GET",
		url : urlData,
		dataType : "json",
		success : function(json) {
			// 通信成功時の処理

			closeProcessing();

			makeImageHtml(json);

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 通信失敗時の処理
			msg = "--- Error Status ---";
			msg = msg + "<BR>" + "status:" + XMLHttpRequest.status;
			msg = msg + "<BR>" + "statusText:" + XMLHttpRequest.statusText;
			msg = msg + "<BR>" + "textStatus:" + textStatus;
			msg = msg + "<BR>" + "errorThrown:" + errorThrown;
			for ( var i in errorThrown) {
				msg = msg + "<BR>" + "error " + i + ":" + errorThrown[i];
			}
			document.open();
			document.write(msg);
			document.close();
		}
	});
}

/**
 * 処理中ウインドウを開く
 */
function openProcessing() {
	$(".resultMessage").text("処理中です...");
}

/**
 * 処理中ウインドウを閉じる
 */
function closeProcessing() {
	$(".resultMessage").text("");
}

/**
 * 画像のHTMLソースを作成する。
 * 
 * @param json
 */
function makeImageHtml(json) {

	var value = json.results.usedcar.length;

	var html = "";
	for (var i = 0; i < value; i++) {
		html += '<dl>';
		html += '<dt>' + json.results.usedcar[i].model + '</dt>';
		html += '<dd><a href="' + json.results.usedcar[i].urls.pc + '" target="_blank">';
		html += makeImageHtmlSource(json, i, $(".img_size").val());
		html += '</a></dd>';
		html += '<dd class="comment">コメント</dd>';
		html += '<dd class="price">' + makePriceHtml(json.results.usedcar[i].price) + '</dd>';
		html += '</dl>';
	}

	$(".gallery").html(html);
}

/**
 * 価格を作成する
 * 
 * @param price
 *            価格の文字列
 * @returns 価格
 */
function makePriceHtml(price) {
	try {
		var n = String(price);
		var len = n.length;
		if (len < 5) {
			return price;
		}

		var ret = "";
		if (len == 7) {
			ret = n.substring(0, 3);
		} else if (len == 8) {
			ret = n.substring(0, 4);
		} else if (len == 6) {
			ret = n.substring(0, 2);
		} else {
			ret = n.substring(0, 1);
		}
		ret += "万円";
		return ret;
	} catch (e) {
		alert(e);
	}

}

/**
 * 出力する画像のHTMLソースを作成する。
 * 
 * @param json
 *            jsonデータ
 * @param i
 *            jsonデータの要素番号
 * @param size
 *            画像のサイズ
 * @returns {String} HTMLソース
 */
function makeImageHtmlSource(json, i, size) {

	var ret = "";

	try {

		if (size == "large") {
			ret = '<img src="' + json.results.usedcar[i].photo.main.l + '">';
		} else {
			ret = '<img src="' + json.results.usedcar[i].photo.main.s + '">';
		}

	} catch (e) {
		alert(e);
	}

	return ret;

}

/**
 * 中古車の減価償却として最適な年数を作成する。
 * 
 * @returns {String} 年数
 */
function makeCarYear() {
	// var date = String(new Date(jQuery.now()).toLocaleString());
	// var n = date.substring(0, 5);
	// alert(n);

	var ret = "2011";
	return ret;
}

function makeCarCountry(country) {
	var ret = "";

	if (country == "JPN") {
		ret = "&country=JPN";
	}

	return ret;
}

/**
 * 色のリクエストパラメータを作成する。
 * 
 * @param color
 *            色
 * @returns 色のリクエストパラメータ
 */
function makeCarColor(color) {

	var ret = "";

	if (color == "WT") {
		ret = "&color=WT";
	} else if (color == "BK") {
		ret = "&color=BK";
	} else if (color == "GY") {
		ret = "&color=GY";
	} else {
		ret = "";
	}

	return ret;
}

/**
 * 開始件数を作成する。
 * 
 * @returns {String}
 */
function makeStartCount() {
	var ret = "&start=1";
	return ret;
}

/**
 * ボディタイプを作成する
 * 
 * @param body
 *            ボディタイプ
 * @returns {String} ボディタイプのリクエストパラメータ
 */
function makeCarBody(body) {
	var ret = "";

	if (body == "S") {
		ret = "&body=S";
	} else if (body == "X") {
		ret = "&body=X";
	}

	return ret;
}

/**
 * APIのURLを作成する。
 * 
 * @returns {String}
 */
function makeApiUrl() {
	var url = "http://webservice.recruit.co.jp/carsensor/usedcar/v1/";

	// キー
	url += "?key=08bd97b138b115a3";

	// ボディタイプコード
	url += makeCarBody($("#body").val());

	// 国産
	url += makeCarCountry($("#home:checked").val());

	// 色
	url += makeCarColor($("#color").val());

	// 年式
	var year = makeCarYear();
	url += "&year_old=" + year;
	url += "&year_new=" + year;

	// 最高価格
	url += "&price_min=500000";
	url += "&price_max=" + $("#price_max").val();

	// 並び順
	url += "&order=2";

	// 開始件数
	url += makeStartCount();

	// 出力件数
	url += "&count=100";

	// フォーマット
	url += "&format=json";

	return url;
}
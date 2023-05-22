/** 青色申告特別控除額 */
var BLUE_RETURN_VALUE = 650000;

/** 国民年金保険料額 */
var NATIONAL_PENSION = 30000;

/**
 * 処理
 */
$(function() {
	jQuery.support.cors = true; // force cross-site scripting (as of jQuery 1.5)

	// 計算ボタン押下
	$("#calc").click(function() {
		// 税金を計算
		var tax = calcTax();
		// 月の税金
		var taxMonth = Math.round(tax / 12);

		// カンマ付与
		tax = String(tax).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
		taxMonth = String(taxMonth).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');

		$("h1").html("￥" + tax + " / 年<br>￥" + taxMonth + " / 月");
	});

	// クリアボタン押下
	$("#clear").click(function() {
		$("#SalesMoney").val("");
		$("#Cost").val("");
		$("#SocialInsurancePremium").val("");
		$("#LifeInsurancePremium").val("");
		$("#SpouseDeduction").val("");
		$("#TaxExemption").val("");
		$("#BasicDeduction").val("");
		$("h1").text("");
	});
});

/**
 * 税金の計算を行う。
 *
 * @returns {Number} 税金の金額
 */
function calcTax() {
	// 税金
	var tax = 0;

	// 画面の入力値
	var SalesMoney = Number($("#SalesMoney").val());
	var Cost = Number($("#Cost").val());
	var SocialInsurancePremium = Number($("#SocialInsurancePremium").val());
	var LifeInsurancePremium = Number($("#LifeInsurancePremium").val());
	var SpouseDeduction = Number($("#SpouseDeduction").val());
	var TaxExemption = Number($("#TaxExemption").val());
	var BasicDeduction = Number($("#BasicDeduction").val());

	// 所得税算出
	var income = calcIncomeTax(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction);

	// 住民税を算出
	var ResidentTax = calcResidentTax(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction);

	// 国民健康保険を算出
	var NationalHealth = calcNationalHealth(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction);

	// 国民年金を算出
	var NationalPension = calcNationalPension(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction);

	tax = income + ResidentTax + NationalHealth + NationalPension;

	return tax;
}

/**
 * 所得税の計算をする。
 *
 * @param SalesMoney
 *            売上金額
 * @param Cost
 *            経費
 * @param SocialInsurancePremium
 *            社会保険料控除額
 * @param LifeInsurancePremium
 *            生命保険料控除額
 * @param SpouseDeduction
 *            配偶者控除額
 * @param TaxExemption
 *            扶養控除額
 * @param BasicDeduction
 *            基礎控除額
 *
 * @returns {Number} 税金の金額
 */
function calcIncomeTax(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction) {
	var tax = 0;

	var n = SalesMoney - Cost - BLUE_RETURN_VALUE;
	var m = n - SocialInsurancePremium - LifeInsurancePremium - SpouseDeduction - TaxExemption - BasicDeduction;

	// 課税される所得金額がマイナスの場合、所得税なし？
	if (m < 0) {
		return tax;
	}

	if (m < 1950000) {
		tax = Math.round(((m * 0.05) - 0));
	} else if (m >= 1950000 && m < 3300000) {
		tax = Math.round(((m * 0.10) - 97500));
	} else if (m >= 3300000 && m < 6950000) {
		tax = Math.round(((m * 0.20) - 427500));
	} else if (m >= 6950000 && m < 9000000) {
		tax = Math.round(((m * 0.23) - 636000));
	} else if (m >= 9000000 && m < 18000000) {
		tax = Math.round(((m * 0.23) - 636000));
	} else {
		tax = Math.round(((m * 0.40) - 2796000));
	}

	return tax;
}

/**
 * 国民健康保険の計算をする。
 *
 * @param SalesMoney
 *            売上金額
 * @param Cost
 *            経費
 * @param SocialInsurancePremium
 *            社会保険料控除額
 * @param LifeInsurancePremium
 *            生命保険料控除額
 * @param SpouseDeduction
 *            配偶者控除額
 * @param TaxExemption
 *            扶養控除額
 * @param BasicDeduction
 *            基礎控除額
 *
 * @returns {Number} 税金の金額
 */
function calcNationalHealth(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction) {
	var tax = 0;

	var m = SalesMoney - Cost - BLUE_RETURN_VALUE;
	var n = m - 330000;

	tax = Math.round((n * 0.159));

	return tax;
}

/**
 * 国民年金の計算をする。
 *
 * @param SalesMoney
 *            売上金額
 * @param Cost
 *            経費
 * @param SocialInsurancePremium
 *            社会保険料控除額
 * @param LifeInsurancePremium
 *            生命保険料控除額
 * @param SpouseDeduction
 *            配偶者控除額
 * @param TaxExemption
 *            扶養控除額
 * @param BasicDeduction
 *            基礎控除額
 *
 * @returns {Number} 税金の金額
 */
function calcNationalPension(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction) {
	var tax = 0;

	tax = NATIONAL_PENSION * 12;

	return tax;
}

/**
 * 住民税の計算をする。
 *
 * @param SalesMoney
 *            売上金額
 * @param Cost
 *            経費
 * @param SocialInsurancePremium
 *            社会保険料控除額
 * @param LifeInsurancePremium
 *            生命保険料控除額
 * @param SpouseDeduction
 *            配偶者控除額
 * @param TaxExemption
 *            扶養控除額
 * @param BasicDeduction
 *            基礎控除額
 *
 * @returns {Number} 税金の金額
 */
function calcResidentTax(SalesMoney, Cost, SocialInsurancePremium, LifeInsurancePremium, SpouseDeduction, TaxExemption, BasicDeduction) {
	var tax = 0;

	var m = SalesMoney - Cost - BLUE_RETURN_VALUE;
	var n = m - SocialInsurancePremium - LifeInsurancePremium - (SpouseDeduction - 50000) - (TaxExemption - 50000) - (BasicDeduction - 50000);

	// 住民税がマイナスの場合、均等割額？
	if (n < 0) {
		return 0;
	}

	tax = Math.round((n * 0.10));

	return tax;
}

<html>
<head>
<link rel="stylesheet" href="style.css" type="text/css">
<title>書籍管理システム</title>
</head>
<body>
<h1>書籍管理システム</h1>
<h2>書籍一覧画面</h2>
<h3>現在登録されている書籍</h3>
<p>現在登録されている書籍の一覧です。</p>
<p>DBが無いため取得できません。</p>
<h3>新規登録</h3>
<p>書籍を新規登録します。</p>
<form method="post" action="registed.php">
  <table border=1>
    <tr>
      <td>書籍名</td>
      <td><input type="text" name="name" /></td>
    </tr>
    <tr>
      <td>著者</td>
      <td><input type="text" name="writer" /></td>
    </tr>
  </table>
  <br>
  <input type="submit" value="実行"">
</form>
</body>
</head>
</html>
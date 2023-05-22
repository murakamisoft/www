<?php

mb_internal_encoding("UTF-8");

//DB接続
$dbname = "chat.db";
$conn = sqlite_open($dbname) or die ("データベースに接続できませんぜよ");

$comment = '';

//データの抽出
if($_POST['default_val'] == 1){

    $sql = "SELECT * FROM comment ORDER BY id DESC;";
    $res = sqlite_unbuffered_query($sql,$conn) or die ("データベースを接続できませんぜ");

    while ($row = sqlite_fetch_array($res, SQLITE_ASSOC)){
        $message .= "<div>" . nl2br(htmlentities($row["message"], ENT_QUOTES, "UTF-8")) . "</div>";
    }
}
else{
    $message = "Hey You! What are you doing?";
}

//データを書き出し
echo $message;
?>
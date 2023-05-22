<?php

mb_internal_encoding("UTF-8");

//DB接続
$dbname = "sql/bbs";
$conn = sqlite_open($dbname) or die ("データベースに接続できませんぜよ");


//データの投稿
if(($_POST['message'] != "") and (!mb_ereg_match("^(\s|　)+$", $_POST['message']))){

    //エスケープ
    $message = htmlentities($_POST['message'], ENT_QUOTES, "UTF-8");
    $message = sqlite_escape_string($message);

    //データベースに追加
    $sql = "INSERT INTO comment (message) VALUES ('$message') ";
    $res = sqlite_unbuffered_query($sql,$conn) or die ('$error');

    $message = "<div>" . nl2br($message) . "</div>";
    echo $message;
}

?>
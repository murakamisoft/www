#!/usr/local/bin/perl5

#
# CGIのヘッダを書き出します。通常は text/html を指定します。
#
print "Content-type: text/html\n\n";


print "<HTML>\n";
print "<BODY>\n";
print "<h1>CGI Labo</h1>";
print "</BODY>\n";	
print "</HTML>\n";

use DBI;
my $database = 'nori.db';
my $data_source = "dbi:SQLite:dbname=$database";
my $dbh = DBI->connect($data_source);

# テーブルの作成


# insert文の実行
$dbh->do("insert into books (title, author) values ('お金持ちdeはない', 'のり侍');");

# select文の実行
my $sth = $dbh->prepare("select * from books;");
$sth->execute;




while (my @row = $sth->fetchrow_array) {
    # 各行を出力
    print "@row , \n";
    print "\n";
}

# データベースの切断
$dbh->disconnect;

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
use CGI;

my $database = 'nori.db';
my $data_source = "dbi:SQLite:dbname=$database";
my $dbh = DBI->connect($data_source);

# テーブルの作成
my $create_table = "create table books (" .
                       "title," .
                       "author" .
                   ");";

$dbh->do($create_table);

my $q = new CGI;

my $title = $q->param('title');
my $author = $q->param('author');

# insert文の実行
$sth = $dbh->prepare("insert into books (title, author) values (?, ?);");

$sth->execute($title,$author);


# select文の実行
my $sth = $dbh->prepare("select * from books;");
$sth->execute;



while ( my $arr_ref = $sth->fetchrow_arrayref ){
	my ($t, $a) = @$arr_ref;
	print ("$t , $a\n");
}




# データベースの切断
$dbh->disconnect;

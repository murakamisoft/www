#!/usr/local/bin/perl5

#
# CGI�̃w�b�_�������o���܂��B�ʏ�� text/html ���w�肵�܂��B
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

# �e�[�u���̍쐬


# insert���̎��s
$dbh->do("insert into books (title, author) values ('��������de�͂Ȃ�', '�̂莘');");

# select���̎��s
my $sth = $dbh->prepare("select * from books;");
$sth->execute;




while (my @row = $sth->fetchrow_array) {
    # �e�s���o��
    print "@row , \n";
    print "\n";
}

# �f�[�^�x�[�X�̐ؒf
$dbh->disconnect;

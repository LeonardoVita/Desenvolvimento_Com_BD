# Desenvolvimento_Com_BD

Desenvolver uma aplicação PDV (Ponto de Venda), utilizando a programação orientação a objetos em JAVA Swing e o banco de dados MySQL, para efetuar a INCLUSÃO e a EXCLUSÃO na venda de produto.

Mais de um programa deve ser inicializado, fazendo alterações simultaneas a mesma tabela do banco de dados.
o programa deve gerencias as concorrecias de acesso ao banco e evitar o Deadlock.

# Programa Finalizado v1.0

TUTORIAL

1:Rode o Script MySQL em um Servidor Wamp do MySQL na porta: localhost:3306/

2:Abra o Projeto Projeto_Mercado no NetBeans

3:Em Serviços clique com o botão direito em (banco de dados) e selecione "registras servidor MySQL"

4:nome do host servidor: localhost // porta: 3306

5:entre com o seu login e senha do servidor (geralmente login:"root" senha:"" ou  "root")

6: antes de dar OK, clique em "propriedades de admin" na aba a cima
  execute as seguintes modificações
  
  caminho do comando iniciar: C:\wamp64\bin\mysql\mysql5.7.23\bin\mysqld.exe
  Argumentos: --console
  caminho do comando interromper: C:\wamp64\bin\mysql\mysql5.7.23\bin\mysqladmin.exe
  Argumentos: -u root shutdown
  
  *importante encontrar os arquivos mysqld.exe e mysqladmin.exe, caso nao esteja ultilizando WampServer
  os caminhos serão diferentes*
 
 7:selecione o banco de dados aps_mercado e depois em conectar
 
 8:clique em limpar e contruir projeto para criar o arquivo .jar
 
 9:execute o arquivo aps_mercado.jar no caminho do seu projeto netbeans dist\aps_mercado.jar
 
 10:execute mais de uma vez para acessar o banco como usuarios diferentes





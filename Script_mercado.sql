
--
-- Iniciando o Script para o banco de dados
--

-- --------------------------------------------------------

--
-- deletando e criando um novo banco
--


drop database if exists aps_mercado;
create database  aps_mercado;
use aps_mercado;

--
-- Criando as tabelas e seus relacionamentos
--

-- --------------------------------------------------------



Create table cliente (
CodCli integer auto_increment primary key,
nome varchar(35) not null,
bonus integer not null,
perfil enum('P','M','G'),
`status` enum ('A','I')
)ENGINE = InnoDB;

create table localidade(
CodLocal integer auto_increment primary key,
nome varchar(35) not null,
endereco varchar(80) not null ,
telefone varchar(14)
)ENGINE = InnoDB;

create table produto(
CodProd integer auto_increment primary key,
CodLocal integer,
descricao varchar(35),
qtd_estoque integer,
preco_unitario float)ENGINE = InnoDB;

alter table produto add constraint localidade_produto_fk
foreign key (CodLocal) references localidade(CodLocal);

create table venda(
CodCli integer,
CodProd integer,
CodLocal integer,
qtd_venda integer,
valor_total float,
data_venda date,

primary key (CodCli,CodProd,CodLocal)
 )ENGINE = InnoDB;
 
 alter table venda add constraint cliente_venda_fk
 foreign key (CodCli) references cliente(CodCli);
 
 alter table venda add constraint cliente_produto_fk
 foreign key (CodProd) references produto(CodProd);
 
  alter table venda add constraint cliente_localidade_fk
 foreign key (CodLocal) references localidade(CodLocal);
 
 create table desconto(
 id_desconto integer auto_increment primary key,
 CodProd integer,
 percentual integer(100) not null,
 qtd_min integer not null,
 qtd_max integer not null
 )ENGINE = InnoDB;
 
alter table desconto add constraint produto_desconto_fk
 foreign key (CodProd) references produto(CodProd);
 
--
-- Inserindo dados as tabelas
--

-- --------------------------------------------------------


insert into cliente values 
(default,'Leonardo',900,'G','A'),
(default,'Thiago',150,'M','A'),
(default,'Larissa',100,'P','A'),
(default,'Pedro',100,'P','I'),
(default,'Ronaldo',100,'M','A'),
(default,'Marcia',200,'M','A'),
(default,'Maria',300,'P','A'),
(default,'Agatha',100,'P','I'),
(default,'Leandro',100,'G','A');

insert into localidade values 
(default,'Rio de Janeiro','Dom helder camara, 950','2566-8947'),
(default,'São Paulo','Rua xptoSP,220','2598-8966'),
(default,'Rio Grande do Sul','Rua xptoRGS,86','2587-6556'),
(default,'Minas Gerais','Rua xptoMG,77','92568-7849'),
(default,'Bahia','Rua xptoB,30','92698-5777');

insert into produto values 
(default,1,'Short',60,30.99),
(default,3,'Calça Jeans',35,59.99),
(default,2,'Mouse',100,14.99),
(default,1,'Boné',80,15.00),
(default,4,'Cadeira',60,49.99),
(default,2,'Teclado',50,19.90),
(default,5,'Toalha',200,22.80),
(default,3,'Cinto',100,12.90);
-- (default,1,'Chinelo',90,14.99),
-- ((default,2,'Head-Set',120,80),
-- ((default,5,'Lençol',110,25),
-- ((default,4,'Mesa',20,119.99),
-- ((default,3,'Sapato social',50,79.90),
-- ((default,2,'Microfone',100,25.50),
-- ((default,1,'Camiseta',120,15.90),
-- ((default,1,'Picolé',1000,0.70),
-- ((default,5,'Travesseiro',70,14.99),
-- ((default,3,'Chapéu',30,20.00),
-- ((default,2,'Mouse Pad',150,19.90),
-- ((default,1,'Celular',35,550.00),
-- ((default,3,'Oculos Escuros',80,29.90),
-- ((default,4,'Estante',25,150.00),
-- ((default,5,'Colchão',30,200.00)

insert into desconto values 
(default,1,10,10,19),
(default,1,15,20,39),
(default,1,20,40,9999),
(default,2,10,6,9),
(default,2,15,10,15),
(default,2,20,16,9999),
(default,3,5,5,10),
(default,3,10,11,20),
(default,3,15,21,9999),
(default,4,5,10,20),
(default,4,10,21,30),
(default,4,15,31,9999),
(default,5,5,5,10),
(default,5,10,11,16),
(default,5,20,17,9999),
(default,6,5,3,9),
(default,6,10,10,20),
(default,6,20,21,9999),
(default,7,5,20,30),
(default,7,10,31,50),
(default,7,20,51,9999),
(default,8,5,15,30),
(default,8,10,31,50),
(default,8,15,51,9999);



















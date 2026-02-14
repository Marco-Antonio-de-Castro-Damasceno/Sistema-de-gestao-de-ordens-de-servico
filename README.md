# Sistema de gerenciamento de ordens de servico

# Sobre o projeto

O projeto desenvolvido em Java e em SQL apresenta um Sistema que gerencia ordens de servico em uma empresa. A primeira tela apresentada 
é a tela de login, onde o usuário entra com seu login e senha, se este usuário possuir a credencial de administrador, ele terá 
acesso pleno a todas as funcionalidades do sistema, dentre essas funcionalidades,temos:

- sistema CRUD para usuários e clientes no banco de dados, com pesquisa dinâmica por nome
- sistema CRUD para Ordens de Servico
- sistema de geração de relatórios de clientes
- sistema de filtro para relatórios de ordens de servico, podendo segmenta-los por tipo, situação, cliente e técnico.

# Tecnologias utilizadas
- Java
- Banco de dados SQL
- Jasper Report
  
# Como executar o projeto

- clonar repositório
- configurar br.com.infox.dal -> moduloConexao (Usando as configurações do seu banco de dados)
- Comandos no MySQL:
  
CREATE DATABASE dbinfox;  
create table tbusuarios(  
iduser int primary key,  
usuario varchar (50) not null,  
fone varchar(20),  
login varchar(15) not null unique,  
senha varchar(15) not null  
);  
CREATE TABLE tbclientes (  
idcli INT AUTO_INCREMENT PRIMARY KEY,  
nomecli VARCHAR(40) not null,  
endcli VARCHAR(150),  
fonecli VARCHAR(20),  
emailcli VARCHAR(30),  
);  
create table tbos(  
os int primary key auto_increment,  
data_os timestamp default current_timestamp,  
situacao varchar(40) not null,  
tipo varchar(30) not null,  
equipamento varchar(150) not null,  
defeito varchar(200) not null,  
servico varchar(150),  
tecnico varchar(30),  
valor decimal(10,2),  
idcli int not null,  
foreign key(idcli) references tbclientes(idcli)  
);  
insert into tbusuarios(iduser, usuario, fone, login, senha)  
values(1, 'adm', '999999-9999', 'adm', '');  
- Através do Eclipse rode a "TelaLogin", preencha login como "adm" e senha vazia.  

# Autor

Marco Antônio

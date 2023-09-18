<h1> Challenge ORACLE ONE / ALURA : Forum - API REST (JAVA) </h1>

<ul>
    <li> <h3> Desafio </h3> </li>
    Criar uma API REST de um FORUM, com as funções:
    <ul>
    <li> Criação de um novo tópico.</li>
    <li> Listagem dos tópicos com paginação.</li>
    <li> Exibir um tópico. </li>
    <li> Alteração de um tópico. </li>
    <li> Deletar um tópico. </li>
    </ul>

</ul>

<ul> 
    <li> <h3> Tecnologias Usadas </h3> </li>
    <ul>
        <li>JAVA</li>
        <li>MySQL</li>
        <li>Java Spring Boot</li>
        <li>IntelliJ</li>
    </ul>

</ul>

<ul>
    <li> <h3> Criação do banco de dados MySQL</h3></li>

    <code> create database forum; 

    use forum; 

    create table topico (
	    id int auto_increment primary key,
        titulo varchar(250) not null,
        msg TEXT CHARACTER SET latin1 COLLATE latin1_general_cs,
        data_criacao varchar(10) not null,
        estado int not null,
        autor varchar(50) not null,
        curso varchar(50) not null ); </code>

</ul>


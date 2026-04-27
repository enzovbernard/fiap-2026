--aula 22/04/26

/*
exceptions
condição para burlar erros que acontecem na programação, vinculada a regras
de negócio ou de algum processamento

mais usadas: no_date_found - não acha por dado(s) vinculados a uma psquisa
             too_many_rows - retorno maior do que suportado, geralmente mais
                             de uma linha, nõ utlização de cursores
             dup_val_on_index - duplicdade de dado(conteúdo) em uma coluna 
             others - trata tudo mas não consta o erro

sempre usadas no final de um bloco de programação
*/

--tratamento de erros - pré definida

create table aluno (ra number(1), nome varchar2(20));

insert into aluno values (1, 'Marcel');
insert into aluno values (2, 'Adriana');
insert into aluno values (3, 'Samuel');
commit;

set serveroutput on;
set verify off;
declare 
    v_ra aluno.ra%type := &ra;
    v_nome aluno.nome%type;
begin
    select nome into v_nome from aluno where ra = v_ra;
    dbms_output.put_line(v_ra || ' - ' || v_nome);
    exception
        when no_data_found then
            dbms_output.put_line ('Não há nenhum aluno com este RA');
        when too_many_rows then
            dbms_output.put_line ('Há mais de um aluno com este RA');
        when others then
            dbms_output.put_line ('Chame o técnico');
end;

--exception personalizada
declare
    v_conta number(2);
    turma_cheia exception;
begin
    select count(ra) into v_conta from aluno;
    if v_conta = 5 then
        raise turma_cheia;
    else
        insert into aluno values (4, 'Rafaela');
    end if;
    exception
    when turma_cheia then
        dbms_output.put_line('Não foi possivel incluir: turma cheia');
end;

--exercicio

create table produto_cp (
codigo number(4) primary key,
categoria char(1),
valor number(6,2));
commit;

insert into produto_cp values (1, 'A', 10);
insert into produto_cp values (2, 'B', 10);
insert into produto_cp values (3, 'C', 10);

declare
    cursor c_aumenta is select * from produto_cp;
begin
    for v_aumenta in c_aumenta loop
        if (v_aumenta.categoria) = 'A' then
            update produto_cp set valor = v_aumenta.valor * 1.05
            where codigo = v_aumenta.codigo;
        elsif
            (v_aumenta.categoria) = 'B' then
            update produto_cp set valor = v_aumenta.valor * 1.10
            where codigo = v_aumenta.codigo;
        else
            update produto_cp set valor = v_aumenta.valor * 1.15
            where codigo = v_aumenta.codigo;
        end if;
    end loop;
end;    
select * from produto_cp;

create table produto_cp (
codigo number(4) primary key,
categoria char(1),
valor number(6,2));
commit;

begin
    insert into produto_cp values (&codigo, '&nome_produto', &preco);
    insert into produto_cp values (&codigo, '&nome_produto', &preco);
    insert into produto_cp values (&codigo, '&nome_produto', &preco);
    exception 
        when dup_val_on_index then 
            dbms_output.put_line('Atenção, código já cadastrado...');
end;

--exercicio 2
alter table produto_cp add qtde number(8,2) default 10;







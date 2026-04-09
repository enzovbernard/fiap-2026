--exercicios cursores 08/04

--Enzo Vieira Bernardini
-- RM563000

-- exercicio 1
--Criar um bloco PL/SQL (usando cursor) para atualizar a tabela abaixo, conforme segue:
--Produtos categoria A deverão ser reajustados em 5%
--Produtos categoria B deverão ser reajustados em 10%
--Produtos categoria C deverão ser reajustados em 15%

CREATE TABLE produto_cp (
codigo NUMBER(4),
categoria CHAR(1),
valor NUMBER(4,2));
INSERT INTO produto_cp values (1001,'A',7.55);
INSERT INTO produto_cp values(1002,'B',5.95);
INSERT INTO produto_cp values (1003,'C',3.45);

set serverout on
declare
    cursor c_produto is select * from produto_cp;
begin
    for v_produto in c_produto loop
        if (v_produto.categoria) = 'A' then
            update produto_cp set valor = v_produto.valor * 1.05
            where codigo = v_produto.codigo;
        elsif (v_produto.categoria) = 'B' then 
            update produto_cp set valor = v_produto.valor * 1.10
            where codigo = v_produto.codigo;
        elsif (v_produto.categoria) = 'C' then
            update produto_cp set valor = v_produto.valor * 1.15
            where codigo = v_produto.codigo;
        else 
            dbms_output.put_line('Produto não sofre alteração de preço');
        end if;
         dbms_output.put_line('Valor reajustado: ' || v_produto.valor  );
end loop;
end;

-- exercicio 2
--Criar um bloco que receberá um RA, um NOME e quatro notas conforme a sequência: (RA,NOME,A1,A2,A3,A4) - FEITO
-- mínimo de dados: 2 linhas uma para aprovado e uma para reprovado. 
--A partir criar um bloco usando cursores para processar o cálculo da média somando o maior valor entre A1 e A2 às notas A3 e A4 
--e dividindo o valor obtido por três (achando a média). Se a média for menor que 6 (seis) o aluno estará REPROVADO e se a média
--for igual ou superior a 6 (seis) o aluno estará APROVADO. O bloco deverá inserir os valores acima numa tabela denominada ALUNO com
--as seguintes colunas RA,NOME,A1,A2,A3,A4,MEDIA,RESULTADO.

create table aluno_cp (
ra number(2) primary key,
nome varchar2(20),
A1 number(4,2),
A2 number(4,2),
A3 number(4,2),
A4 number(4,2),
media number(4,2),
resultado varchar2(15));

insert into aluno_cp values (01, 'Pedro', 8, 7.5, 9, 7, 0, '');
insert into aluno_cp values (02, 'Ana', 9, 6.5, 8, 10, 0, '');
insert into aluno_cp values (03, 'Marcos', 5, 7, 6, 6.5, 0, '');
insert into aluno_cp values (04, 'Paulo', 5, 4, 2, 5, 0, '');

set serverout on
declare
    cursor c_aluno is select * from aluno_cp;
begin
    for v_aluno in c_aluno loop
        if (v_aluno.A1) >= (v_aluno.A2) then
            update aluno_cp set media = (v_aluno.A1 + v_aluno.A3 + v_aluno.A4) / 3
            where ra = v_aluno.ra;
        else
            update aluno_cp set media = (v_aluno.A2 + v_aluno.A3 + v_aluno.A4) / 3
            where ra = v_aluno.ra;
        end if;
        if (v_aluno.media) >= 6 then
            update aluno_cp set resultado = 'APROVADO'
            where ra = v_aluno.ra;
            dbms_output.put_line('O aluno ' || v_aluno.nome || ' foi ' || v_aluno.resultado || '. Média: ' || v_aluno.media);
        else
            update aluno_cp set resultado = 'REPROVADO'
            where ra = v_aluno.ra;
            dbms_output.put_line('O aluno ' || v_aluno.nome || ' foi ' || v_aluno.resultado || '. Média: ' || v_aluno.media);
        end if;
end loop;
end;



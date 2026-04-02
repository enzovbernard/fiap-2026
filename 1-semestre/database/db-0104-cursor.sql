-- aula 01/04 - matrizes e cursores

-- cursor é um vetor em banco de dados
-- existem cursores explicitos e implicitos

-- cursor explicito
-- comandos

-- open - abre o cursor
-- fetch - carga de dados - ctrl c ctrl v
-- close - fecha o cursor

-- %rowtype

-- %found, %notfound, %rowcount, %isopen

create table db_funcionario(
cd_fun number(2) primary key, 
nm_fun varchar2(20), 
salario number(10,2), 
dt_adm date);

begin
insert into db_funcionario values (1, 'Marcel', 10000, '17/04/2000');
insert into db_funcionario values (2, 'Claudia', 16000, '02/10/1998');
insert into db_funcionario values (3, 'Joaquim', 5500, '10/07/2010');
insert into db_funcionario values (4, 'Valéria', 7300, '08/06/2015');
commit;
end;



-- codigo longo com loop
set serveroutput on
declare
    cursor c_exibe is select nm_fun, salario from db_funcionario;
    v_exibe c_exibe%rowtype;
begin
    open c_exibe;
    loop
        fetch c_exibe into v_exibe;
    exit when c_exibe%notfound;
    dbms_output.put_line('Nome: ' ||v_exibe.nm_fun || ' - Salário: ' || v_exibe.salario);
    end loop;
    close c_exibe;
end;

-- codigo reduzido com for
set serveroutput on
declare 
    cursor c_exibe is select nm_fun, salario from db_funcionario;
begin
    for v_exibe in c_exibe loop
        dbms_output.put_line('Nome: ' || v_exibe.nm_fun || ' - Salário: ' || v_exibe.salario);
end loop;
end;

-- exercicio 1
alter table db_funcionario add tempo number(10);

set serveroutput on
declare 
    cursor c_data is select nm_fun, dt_adm, tempo from db_funcionario;
begin
    for v_data in c_data loop
        v_data.tempo := sysdate - v_data.dt_adm;
        dbms_output.put_line('Nome: ' || v_data.nm_fun ||' - Dias na empresa: ' || v_data.tempo);
end loop;
end;

-- gabarito
set serveroutput on
declare
    cursor c_exibe is select * from db_funcionario;
begin
    for v_exibe in c_exibe loop
        update db_funcionario set tempo = sysdate -v_exibe.dt_adm
        where cd_fun = v_exibe.cd_fun;
    end loop;
end;

-- ex 2
set serverout on
declare
    cursor c_exibe is select * from db_funcionario;
begin
    for v_exibe in c_exibe loop
        if v_exibe.tempo >= 4565 then
            update db_funcionario set salario = v_exibe.salario * 1.1
            where cd_fun = v_exibe.cd_fun;
        else 
            update db_funcionario set salario = v_exibe.salario * 1.05
            where cd_fun = v_exibe.cd_fun;
        end if;
         dbms_output.put_line('Nome: ' || v_exibe.nm_fun || ' - Salário: ' || v_exibe.salario);
end loop;
end;

-- gabarito

set serverout on
declare
    cursor c_exibe is select * from db_funcionario;
begin
    for v_exibe in c_exibe loop
        if (v_exibe.tempo / 30) >= 150 then
            update db_funcionario set salario = salario * 1.1
            where cd_fun = v_exibe.cd_fun;
        else 
            update db_funcionario set salario = salario * 1.05
            where cd_fun = v_exibe.cd_fun;
        end if;
         dbms_output.put_line('Nome: ' || v_exibe.nm_fun || ' - Salário: ' || v_exibe.salario);
end loop;
end;


        



/*
insert into assignment(id, description, correct_query)
values (1, 'Получить полные сведения обо всех изделиях.', 'select * from j');

insert into assignment(id, description, correct_query)
values (2, 'Получить полные  сведения обо всех изделиях в Томске.', 'select * from j where St =\'Томск\'');*/

insert into assignment(id, description, correct_query)
values (3, 'Получить номер детали, для которой нет другой детали, имеющей меньшее значение веса.', 'select Pnum from P where We = select min(We) from P');
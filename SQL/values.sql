insert into authors values ('Wellbert Awdry');
insert into authors values ('George Ohwell');
insert into authors values ('Professor Oak');
insert into authors values ('Will Shookpears');
insert into authors values ('Rob Robbert');
insert into authors values ('William Silvering');
insert into authors values ('E.Scott Fitzgerald');
insert into authors values ('D.J. Salinger');
insert into authors values ('Jane Often');
insert into authors values ('Sharpener Lee');
insert into authors values ('Rob Boss');
insert into authors values ('Ernest Highway');
insert into authors values ('J.K Bowling');
insert into authors values ('');

insert into publishers values ('p111111111', 'Puffin publishing', '21 Street, New York', 'puffin@gmail.com', '613-111-1111', '1111222233334444');
insert into publishers values ('p111111112', 'Geese publishing', '22 Street, New York', 'geese@hotmail.com', '613-222-2222', '2222333344445555');
insert into publishers values ('p111111113', 'Tapir publishing', '23 Street, New York', 'tapir@yahoooo.com', '613-333-4444', '3333444455556666');

insert into books values ('b111111111', 'Tom the truck engine', 'Wellbert Awdry', '', 'Mystery', 120, 'p111111113', 21, 30);
insert into books values ('b111111112', 'Animal Barn', 'George Ohwell', '', 'Horror', 120, 'p111111111', 21, 30);
insert into books values ('b111111113', 'How to feed Pokemon', 'Professor Oak', '', 'Non-fiction', 120, 'p111111113', 21, 30);
insert into books values ('b111111114', 'Mcbath', 'Will Shookpears', '', 'Comedy', 120, 'p111111111', 21, 30);
insert into books values ('b111111115', 'Rob the robber', 'Rob Robbert', '', 'Non-fiction', 120, 'p111111112', 21, 30);
insert into books values ('b111111116', 'Twelveth day', 'Will Shookpears', '', 'Action and Adventure', 120, 'p111111111', 21, 30);
insert into books values ('b111111117', 'Lord of the bugs', 'William Silvering', '', 'Romance', 120, 'p111111113', 21, 30);
insert into books values ('b111111118', 'The decent Gatsby', 'E.Scott Fitzgerald', '', 'Folktale', 120, 'p111111113', 21, 30);
insert into books values ('b111111119', 'Helmet', 'Will Shookpears', '', 'Science Fiction', 120, 'p111111111', 21, 30);
insert into books values ('b111111120', 'The Catcher in the corn', 'D.J. Salinger', '', 'Horror', 120, 'p111111112', 21, 30);
insert into books values ('b111111121', 'Pride and Postjustice', 'Jane Often', '', 'Western', 120, 'p111111112', 21, 30);
insert into books values ('b111111122', 'To kill a complimenting bird', 'Sharpener Lee', '', 'Fantasy', 120, 'p111111113', 21, 30);
insert into books values ('b111111123', 'How to color, for beginners', 'Rob Boss', '', 'Mystery', 120, 'p111111113', 21, 30);
insert into books values ('b111111124', 'The old guy and the pond', 'Ernest Highway', '', 'Non-fiction', 120, 'p111111112', 21, 30);
insert into books values ('b111111125', 'Harry Mopper', 'J.K Bowling', '', 'Western', 120, 'p111111111', 21, 30);
insert into books values ('b111111126', 'The bad weather', 'Will Shookpears', '', 'Science Fiction', 120, 'p111111111', 21, 30);
insert into books values ('b111111127', 'Molecular Biology of plant cells', 'Professor Oak', '', 'Romance', 120, 'p111111113', 21, 30);

insert into customers values ('c111111111', 'Shoplifter Rosie', '1 Road, Kentucky');
insert into customers values ('c111111112', 'Imposter Oscar', '1 Road, Kentucky');

insert into publishes values ('p111111111', 'b111111112');
insert into publishes values ('p111111111', 'b111111114');
insert into publishes values ('p111111111', 'b111111116');
insert into publishes values ('p111111111', 'b111111119');
insert into publishes values ('p111111111', 'b111111125');
insert into publishes values ('p111111111', 'b111111126');
insert into publishes values ('p111111112', 'b111111115');
insert into publishes values ('p111111112', 'b111111120');
insert into publishes values ('p111111112', 'b111111121');
insert into publishes values ('p111111112', 'b111111124');
insert into publishes values ('p111111113', 'b111111111');
insert into publishes values ('p111111113', 'b111111113');
insert into publishes values ('p111111113', 'b111111117');
insert into publishes values ('p111111113', 'b111111118');
insert into publishes values ('p111111113', 'b111111122');
insert into publishes values ('p111111113', 'b111111123');
insert into publishes values ('p111111113', 'b111111127');

insert into writes values ('b111111111','Wellbert Awdry','p111111113');
insert into writes values ('b111111112','George Ohwell','p111111111');
insert into writes values ('b111111113','Professor Oak','p111111113');
insert into writes values ('b111111114','Will Shookpears','p111111111');
insert into writes values ('b111111115','Rob Robbert','p111111112');
insert into writes values ('b111111116','Will Shookpears','p111111111');
insert into writes values ('b111111117','William Silvering','p111111113');
insert into writes values ('b111111118','E.Scott Fitzgerald','p111111113');
insert into writes values ('b111111119','Will Shookpears','p111111111');
insert into writes values ('b111111120','D.J. Salinger','p111111112');
insert into writes values ('b111111121','Jane Often','p111111112');
insert into writes values ('b111111122','Sharpener Lee','p111111113');
insert into writes values ('b111111123','Rob Boss','p111111113');
insert into writes values ('b111111124','Ernest Highway','p111111112');
insert into writes values ('b111111125','J.K Bowling','p111111111');
insert into writes values ('b111111126','Will Shookpears','p111111111');
insert into writes values ('b111111127','Professor Oak','p111111113');


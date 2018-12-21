create table pete_epil(
	pete_epil_no number,
	pete_epil_id varchar2(20) --글쓴이
	REFERENCES pete_entrust ( pete_id ) ON DELETE CASCADE,
	join_epil_id varchar2(20), --로그인한사람
	pete_epil_subject varchar2(100), --����
	pete_epil_content varchar2(500), --����
	pete_epil_star varchar2(30) not null, --����
	foreign key (pete_epil_id) references pete_entrust(pete_id),
	foreign key (join_epil_id) references join_member(join_id)
)
insert into pete_epil
   		(pete_epil_id, join_epil_id, pete_epil_subject,
   		pete_epil_content, pete_epil_no)
   		values ('popf', 'admin', 'zz','dd', pete_epil_no_seq.nextval)
   		
alter table pete_epil add(pete_epil_no number)
alter table pete_epil modify(pete_epil_star default '20')
create sequence pete_epil_no_seq
increment by 1 start with 1
delete from pete_epil
drop table pete_epil
select * from pete_epil
select * from pete_epil where pete_epil_id = 'admin'
delete pete_epil where pete_epil_subject = 'ㅋ'
select count(*) from pete_epil where pete_epil_id = 'popf'
delete from pete_epil
    	where pete_epil_no = 30
    	and pete_epil_id = 'popf1'
    	or join_epil_id = 'admin'
select count(*) from pete_epil where pete_epil_id = 'popf1'
select * from
	   		(select rownum rnum, pete_epil_id, join_epil_id, pete_epil_subject,
	   		pete_epil_content, pete_epil_star, pete_epil_no
   		from 
		   	(select * from pete_epil where pete_epil_id = 'popf1' order by pete_epil_no desc)
		)
select sum(pete_epil_star) from pete_epil where pete_epil_id = 'popf1'
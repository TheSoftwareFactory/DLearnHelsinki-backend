-- "Themes" Finnish fields

UPDATE "Themes"
SET title_fi = 'Ongelmanratkaisu', description_fi= 'Kysymykset ovat oppilaan kekseliäisyyttä koskevia'
WHERE title = 'Ideas and problem solving';

UPDATE "Themes"
SET title_fi = 'Mielipiteet', description_fi= 'Kysymykset ovat oppilaan mielipiteitä koskevia'
WHERE title = 'Opinions and arguments';

UPDATE "Themes"
SET title_fi = 'Sinnikyys', description_fi= 'Kysymykset ovat oppilaan sinnikkyyttä koskevia'
WHERE title = 'Persistence';

UPDATE "Themes"
SET title_fi = 'Vastuullisuus', description_fi= 'Kysymykset ovat oppilaan vastullisuutta koskevia'
WHERE title = 'Responsibility';

UPDATE "Themes"
SET title_fi = 'Kestävä kehitys', description_fi= 'Kysymykset ovat oppilaan kehitystä koskevia'
WHERE title = 'Sustainable work - respect';

-- "Questions" Finnish fields

UPDATE "Questions"
SET question_fi = 'Kaikki osallistuivat ryhmän toimintaan tasapuolisesti'
WHERE question = 'Everyone participated equally in the groupwork';

UPDATE "Questions"
SET question_fi = 'Huomioin muiden ehdotukset työskentelyssä'
WHERE question = 'I took others ideas into account in the groupwork';

UPDATE "Questions"
SET question_fi = 'Kannustin ryhmäämme yhteisen tehtävän tekemisessä'
WHERE question = 'I encouraged our group in doing the collaborative task';

UPDATE "Questions"
SET question_fi = 'Työskentelin toisten kanssa jotka osasivat auttaa'
WHERE question = 'I worked with others who could help';

UPDATE "Questions"
SET question_fi = 'Kuuntelin muiden mielipiteitä'
WHERE question = 'I listened to other''s opinions';

UPDATE "Questions"
SET question_fi = 'Esitin omia mielipiteitäni ryhmässä'
WHERE question = 'I presented my own viewpoints in the group';

UPDATE "Questions"
SET question_fi = 'Yritin ymmärtää muiden ideoita'
WHERE question = 'I tried to understand others ideas';

UPDATE "Questions"
SET question_fi = 'Perustelin oman kantani ryhmän keskusteluissa'
WHERE question = 'I argued my own perspective in group discussions';

UPDATE "Questions"
SET question_fi = 'Osallistuin aktiivisesti ryhmän työskentelyyn'
WHERE question = 'I participated actively to the groupework';

UPDATE "Questions"
SET question_fi = 'Annoin muille palautetta työn kehittämisestä'
WHERE question = 'I gave feedback of the developments for others';

UPDATE "Questions"
SET question_fi = 'Autoin muita ryhmätyön haasteissa'
WHERE question = 'I helped others in the challenges of the groupwork';

UPDATE "Questions"
SET question_fi = 'Otin riittävästi vastuuta ryhmän työskentelystä'
WHERE question = 'I took enough responsibility of the groupwork';

UPDATE "Questions"
SET question_fi = 'Keskityin ryhmätyössä koko ajan tehtävän tekemiseen'
WHERE question = 'I focused completely on doing the groupwork';

UPDATE "Questions"
SET question_fi = 'Sain ryhmässä hyödyllistä palautetta työn jatkamiseen'
WHERE question = 'I received useful feedback in the group to continue the work';

INSERT INTO "Questions" (max_answer, min_answer, question, question_fi, theme_id)
VALUES ('5', '1', 'I developed others'' ideas further', 'Kehittelin toisten ideoita eteenpäin', '1');

-- UPDATE "Questions"
-- SET question_fi = 'Kehittelin toisten ideoita eteenpäin.'
-- WHERE _id = 15;

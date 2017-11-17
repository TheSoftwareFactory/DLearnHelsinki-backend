ALTER TABLE "Classes"
ADD name_fi character varying(80);

ALTER TABLE "Questions"
ADD question_fi character varying(600);

ALTER TABLE "Surveys"
ADD title_fi character varying(60);

ALTER TABLE "Surveys"
ADD description_fi character varying(100);

ALTER TABLE "Themes"
ADD title_fi character varying(50);

ALTER TABLE "Themes"
ADD description_fi character varying(256);

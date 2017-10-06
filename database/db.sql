--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.5
-- Dumped by pg_dump version 9.6.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Answers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Answers" (
    question_id integer NOT NULL,
    student_id integer NOT NULL,
    answer integer DEFAULT 0,
    survey_id integer NOT NULL
);


--
-- Name: Classes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Classes" (
    _id integer NOT NULL,
    name character varying(80),
    teacher_id integer
);


--
-- Name: Classes__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Classes__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Classes__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Classes__id_seq" OWNED BY "Classes"._id;


--
-- Name: Groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Groups" (
    name character varying(80),
    _id integer NOT NULL,
    class_id integer
);


--
-- Name: Groups__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Groups__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Groups__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Groups__id_seq" OWNED BY "Groups"._id;


--
-- Name: Questions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Questions" (
    question character varying(600),
    min_answer integer,
    max_answer integer,
    _id integer NOT NULL
);


--
-- Name: Questions__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Questions__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Questions__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Questions__id_seq" OWNED BY "Questions"._id;


--
-- Name: Student_Classes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Student_Classes" (
    _id integer NOT NULL,
    student_id integer,
    class_id integer,
    group_id integer
);


--
-- Name: Student_Classes__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Student_Classes__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Student_Classes__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Student_Classes__id_seq" OWNED BY "Student_Classes"._id;


--
-- Name: Students; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Students" (
    _id integer NOT NULL,
    username character varying(35),
    pwd character varying(100),
    gender character varying(15),
    age integer
);


--
-- Name: Students__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Students__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Students__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Students__id_seq" OWNED BY "Students"._id;


--
-- Name: Survey_questions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Survey_questions" (
    survey_id integer NOT NULL,
    question_id integer NOT NULL
);


--
-- Name: Surveys; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Surveys" (
    title character varying(60),
    start_date date,
    end_date date,
    teacher_id integer,
    _id integer NOT NULL,
    open boolean,
    class_id integer,
    description character varying(100)
);


--
-- Name: Surveys__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Surveys__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Surveys__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Surveys__id_seq" OWNED BY "Surveys"._id;


--
-- Name: Teachers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Teachers" (
    lastname character varying(40),
    firstname character varying(40),
    username character varying(40),
    pwd character varying(100),
    _id integer NOT NULL
);


--
-- Name: Teachers__id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Teachers__id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Teachers__id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Teachers__id_seq" OWNED BY "Teachers"._id;


--
-- Name: Classes _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Classes" ALTER COLUMN _id SET DEFAULT nextval('"Classes__id_seq"'::regclass);


--
-- Name: Groups _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Groups" ALTER COLUMN _id SET DEFAULT nextval('"Groups__id_seq"'::regclass);


--
-- Name: Questions _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Questions" ALTER COLUMN _id SET DEFAULT nextval('"Questions__id_seq"'::regclass);


--
-- Name: Student_Classes _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Student_Classes" ALTER COLUMN _id SET DEFAULT nextval('"Student_Classes__id_seq"'::regclass);


--
-- Name: Students _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Students" ALTER COLUMN _id SET DEFAULT nextval('"Students__id_seq"'::regclass);


--
-- Name: Surveys _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Surveys" ALTER COLUMN _id SET DEFAULT nextval('"Surveys__id_seq"'::regclass);


--
-- Name: Teachers _id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Teachers" ALTER COLUMN _id SET DEFAULT nextval('"Teachers__id_seq"'::regclass);


--
-- Name: Answers Answers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Answers"
    ADD CONSTRAINT "Answers_pkey" PRIMARY KEY (question_id, student_id, survey_id);


--
-- Name: Classes Classes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Classes"
    ADD CONSTRAINT "Classes_pkey" PRIMARY KEY (_id);


--
-- Name: Student_Classes Student_Classes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Student_Classes"
    ADD CONSTRAINT "Student_Classes_pkey" PRIMARY KEY (_id);


--
-- Name: Students Students_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Students"
    ADD CONSTRAINT "Students_pkey" PRIMARY KEY (_id);


--
-- Name: Surveys Surveys_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Surveys"
    ADD CONSTRAINT "Surveys_pkey" PRIMARY KEY (_id);


--
-- Name: Groups pk_group; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Groups"
    ADD CONSTRAINT pk_group PRIMARY KEY (_id);


--
-- Name: Questions pk_id; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Questions"
    ADD CONSTRAINT pk_id PRIMARY KEY (_id);


--
-- Name: Survey_questions pk_sur_ques; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Survey_questions"
    ADD CONSTRAINT pk_sur_ques PRIMARY KEY (survey_id, question_id);


--
-- Name: Teachers pk_teach_id; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Teachers"
    ADD CONSTRAINT pk_teach_id PRIMARY KEY (_id);


--
-- Name: Teachers un_teach_id; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Teachers"
    ADD CONSTRAINT un_teach_id UNIQUE (_id);


--
-- Name: Surveys unique_id; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Surveys"
    ADD CONSTRAINT unique_id UNIQUE (_id);


--
-- Name: Questions unique_id_question; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Questions"
    ADD CONSTRAINT unique_id_question UNIQUE (_id);


--
-- Name: fki_fk_student_answer; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_fk_student_answer ON "Answers" USING btree (student_id);


--
-- Name: fki_fk_teacher; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_fk_teacher ON "Surveys" USING btree (teacher_id);


--
-- Name: fki_sur_ques; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_sur_ques ON "Survey_questions" USING btree (question_id);


--
-- Name: fki_survey_question; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_survey_question ON "Survey_questions" USING btree (survey_id);


--
-- Name: Survey_questions sur_ques; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Survey_questions"
    ADD CONSTRAINT sur_ques FOREIGN KEY (question_id) REFERENCES "Questions"(_id);


--
-- Name: Survey_questions survey_question; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Survey_questions"
    ADD CONSTRAINT survey_question FOREIGN KEY (survey_id) REFERENCES "Surveys"(_id);


--
-- PostgreSQL database dump complete
--


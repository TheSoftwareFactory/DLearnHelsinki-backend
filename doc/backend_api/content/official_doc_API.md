# Dlearn

This is our high-quality API. You can use this API to request
and remove different students and spidergraphs.


## Researcher role

Endpoints available for researchers

### Get researcher information

Get researcher information

#### Example request

```endpoint
GET researcher
```

#### Example request

```curl
$ curl localhost:8080/webapi/researcher
```

#### Example response

```json
{
  "id" : 1,
  "username" : "dr.jekyl"
}
```

### Create new teacher

Creates new teacher account

```endpoint
POST researcher/create_teacher
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/researcher/create_teacher
  -d @data.json
```

#### Example request body

```json
{
  "password" : "kekkonen",
  "teacher" : {
    "username" : "MarttiM",
  }
}
```

#### Example response

```json
{
  "_id" : 1,
  "username" : "marttim",
}
```

### Get all Surveys

Gets all surveys

```endpoint
GET researcher/surveys
```

#### Example request

```curl
$ curl localhost:8080/webapi/researcher/surveys
  -d @data.json
```

#### Example response

```json
[
  {
    "_id" : 1,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Math survey",
    "description" : "survey for the exercie 3 page 40",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : true
  },
  {
    "_id" : 2,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Physic survey",
    "description" : "survey for the exercie 5 page 13",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : false
  },
]
```

## Teacher role

End points available for teacher.

### Create new student

Creates new student account

```endpoint
POST teachers/{teacher_id}/create_student
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/teachers/1/create_student
  -d @data.json
```

#### Example request body

```json
{
  "groud_id" : 1,
  "class_id" : 1,
  "password" : "hunter2",
  "student" : {
    "username" : "LegoLass",
    "age" : 13,
    "gender" : "dragon"
  }
}
```

#### Example response

```json
{
  "_id" : 1,
  "username" : "LegoLass",
  "age" : 13,
  "gender" : "dragon"
}
```

### List students in one class (TODO) check student api branch

Lists all students inside one specified class.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/students/
```

#### Example response

```json
[
  {
    "_id" : 1,
    "lastname" : "Meikäläinen",
    "firstname" : "Matti",
    "username" : "iloinen tanssiva aurinko"
  },
  {
    "_id" : 2,
    "lastname" : "Meikäläinen",
    "firstname" : "Maija",
    "username" : "kirjava hyppivä puu"
  }
]
```

### List of all groups in one specified class (TESTING)

This request allows you to retrieve a detailed list of all students in their respective groups, in one class

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/groups/
```

#### Example response

```json
[
  {
    "_id" : 1,
    "name" : "Grp1",
    "students" : [
      {
        "_id" : 1,
        "username" : "Meikäläinen",
        "age" : 10,
        "gender" : "male"
      },
      {
        "_id" : 1,
        "username" : "Meikäläinen",
        "age" : 10,
        "gender" : "male"
      }
    ]
  },
  {
    "_id" : 2,
    "name" : "Grp2",
    "students" : [
      {
        "_id" : 1,
        "username" : "Meikäläinen",
        "age" : 10,
        "gender" : "male"
      },
      {
        "_id" : 1,
        "username" : "Meikäläinen",
        "age" : 10,
        "gender" : "male"
      }
    ]
  }
]
```

### List of students in a group (TESTING)

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/{group_id}/students
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/groups/1/students
```

#### Example response

```json
[
  {
    "_id" : 3,
    "lastname" : "Thomas",
    "firstname" : "Mimi",
    "username" : "iloinen tanssiva aurinko"
  },
  {
    "_id" : 3,
    "lastname" : "Jean",
    "firstname" : "Dujardin",
    "username" : "iloinen tanssiva aurinko"
  }
]
```


### List individual students (TODO)

This is how you can get a specific student by their id.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/students/1
```

#### Example response

```json
{
  "_id" : 1,
  "lastname" : "Meikäläinen",
  "firstname" : "Matti",
  "username" : "iloinen tanssiva aurinko"
}
```

### Retrieve a student's result (TODO)

Returns a single result for a specified survey.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/students/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### Retrieve student's progression

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}/progression/{amount}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/students/1/progression/2
```

#### Example response

```json
[
  [
    {
      "answer" : 3.0,
      "description" : "These questions are about respect and the sustainability of the work.",
      "start_date" : "2017-10-13 10:13:09.972716+00",
      "student_id" : 1,
      "survey_id" : 53,
      "theme_id" : 5,
      "theme_title" : "Sustainable work - respect"
    },
    {
      "answer" : 3.1,
      "description" : "These questions are about the persistence of the excercise.",
      "start_date" : "2017-10-13 10:13:09.972716+00",
      "student_id" : 1,
      "survey_id" : 53,
      "theme_id" : 3,
      "theme_title" : "Persistence"
    },
  ],
  [
    {
      "answer" : 3.2,
      "description" : "These questions are about respect and the sustainability of the work.",
      "start_date" : "2017-11-09 10:12:03.972716+00",
      "student_id" : 1,
      "survey_id" : 85,
      "theme_id" : 5,
      "theme_title" : "Sustainable work - respect"
    },
    {
      "answer" : 2.9,
      "description" : "These questions are about the persistence of the excercise.",
      "start_date" : "2017-11-09 10:12:03.972716+00",
      "student_id" : 1,
      "survey_id" : 85,
      "theme_id" : 3,
      "theme_title" : "Persistence"
    },
  ]
]
```

### Retrieve a group's results (TODO)

Returns a single result for a specified survey for a specified group.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/{group_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/groups/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json

  {
      "survey_id": 1,
      "question_id": 1,
      "group_id": 1,
      "answer": 4
  }

```

### Retrieve a class's results (DONE)

Returns a single result for a specified survey for a specified class.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
  {
      "survey_id": 1,
      "question_id": 1,
      "class_id": 1,
      "answer": 6
  }
```


### List surveys (TODO)

Retrieves surveys for a teacher, for one class, on one survey

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/1
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### Add/Open new survey (DONE)

Adds new survey

```endpoint
POST teachers/{teacher_id}/classes/{class_id}/surveys
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/teachers/1/classes/1/surveys
  -d @data.json
```

#### Example request body

```json
{
  "title" : "math survey",
  "description" : "Fist math survey of the year"
}
```

#### Example response

<!--TODO: Actual response-->

```json
{}
```
<!-- TODO: Updating survey? -->

#### Note

This will create a survey even if another survey is currently open.


### Get list of All surveys for one class (TODO)

Adds new survey

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys
  -d @data.json
```

#### Example response

<!--TODO: Actual response-->
```json
[
  {
    "_id" : 1,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Math survey",
    "description" : "survey for the exercie 3 page 40",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : true
  },
  {
    "_id" : 2,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Physic survey",
    "description" : "survey for the exercie 5 page 13",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : false
  },
]
```



-----------------------------------------------
-----------------------------------------------
-----------------------------------------------



## Student role

Endpoints available for student.

### List information (TESTING, on branch student_id_api)

This is how student can get their own information.

```endpoint
GET /students/{student_id}
```

#### Example request

```curl
$ curl localhost:8080/webapi/students/{student_id}
```

#### Example response

```json
{
  "_id" : 1,
  "lastname" : "Meikäläinen",
  "firstname" : "Matti",
  "username" : "iloinen tanssiva aurinko"
}
```

### Retrieve a student's result for specific survey (DONE)

Returns a single spidergraph.

```endpoint
GET /students/{student_id}/classes/{class_id}/surveys/{survey_id}/answers
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/students/1/classes/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
[
    {
        "survey_id": 27,
        "question_id": 1,
        "student_id": 1,
        "answer": 4
    },
    {
        "survey_id": 27,
        "question_id": 2,
        "student_id": 1,
        "answer": 4
    },
    {
        "survey_id": 27,
        "question_id": 3,
        "student_id": 1,
        "answer": 4
    }
]
```

### Retrieve all the surveys (TODO)

Returns all the survey for one class. Can be used to find the open survey

```endpoint
GET /students/{student_id}/classes/{class_id}/surveys
```

#### Example request

```curl
$ curl localhost:8080/webapi/students/1/classes/1/surveys
```

#### Example response

```json
[
  {
    "_id" : 1,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Math survey",
    "description" : "survey for the exercie 3 page 40",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : true
  },
  {
    "_id" : 2,
    "teacher_id" : 1,
    "class_id" : 1,
    "title" : "Physic survey",
    "description" : "survey for the exercie 5 page 13",
    "start_date" : "2007-04-05T13:30Z",
    "end_date" : "2007-04-05T13:30Z",
    "open" : false
  },
]
```

### Retrieve questions for a survey (DONE)

Returns the array of questions for a survey.

```endpoint
GET /students/{student_id}/classes/{class_id}/surveys/{survey_id}/questions
```
#### Example request

```curl
$ curl localhost:8080/webapi/students/1/classes/1/surveys/1/questions
```

#### Example response

```json
[
  {
    "_id" : 1,
    "question" : "I presented my own viewpoints in the group",
    "min_answer" : 1,
    "max_answer" : 5
  },
  {
    "_id" : 2,
    "question" : "I took enough responsibility of the group work.",
    "min_answer" : 1,
    "max_answer" : 7
  },
]
```

### Send answers to the questions (DONE)

Sends one and only one answer to the specified question.

```endpoint
PUT /students/{student_id}/classes/{class_id}/surveys/{survey_id}/answers/{question_id}
```

#### Example of the message body

```json
{
  "answer" : 2
}
```

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

### Create new student (DONE)

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
### Create new class (DONE)

```endpoint
POST teachers/{teacher_id}/classes
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/teachers/1/classes
  -d @data.json
```

#### Example request body

```json
{
  "name" : "Physics 101"
}
```
#### Example response

No return response

### Change student password

```endpoint
POST teachers/{teacher_id}/change_student_password
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/teachers/1/change_student_password
  -d @data.json
```

#### Example request body

```json
{
  "student_id" : 1,
  "password" : "DontUseThisPassword",
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

### List students in one class (TESTING) rework by Denis

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
### List of all classes and groups(TESTING)

This request allows you to retrieve a list of all classes and their respective groups for a particular teacher.

```endpoint
GET teachers/{teacher_id}/classes/
```
#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/
```
#### Example response

```json
[
  {
    "_id":1,
    "groups":
            [
              {
                "_id":1,
                "name":"{Alpha}",
                "class_id":1
              },
              {
                "_id":2,
                "name":"{Beta}",
                "class_id":1
              }
            ],
    "name":"{First Class}",
    "teacher_id":1
  },
  {
    "_id":2,
    "groups":[],
    "name":"{Second Class}",
    "teacher_id":1
  }
]
```

### List of all groups in one specified class (DONE)

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
      "_id":1,
      "name":"first group",
      "students":[  
         {  
            "_id":2,
            "username":"pzaragoza",
            "gender":"male",
            "age":10
         },
         {  
            "_id":1,
            "username":"nhlad",
            "gender":"male",
            "age":12
         },
         {  
            "_id":3,
            "username":"kbroflovski",
            "gender":"male",
            "age":10
         }
      ]
   },
   {  
      "_id":2,
      "name":"second group",
      "students":[  
         {  
            "_id":4,
            "username":"smarsh",
            "gender":"male",
            "age":9
         },
         {  
            "_id":6,
            "username":"ecartman",
            "gender":"male",
            "age":10
         },
         {  
            "_id":5,
            "username":"kmccormick",
            "gender":"male",
            "age":9
         }
      ]
   }
]
```

### List theme averages in a class (DONE)

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/27/answers
```

#### Example response

```json
[  
   {  
      "answer":3.8888888,
      "class_id":1,
      "description":"These questions ask about the ideas brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":1,
      "theme_title":"Ideas and problem solving"
   },
   {  
      "answer":3.5,
      "class_id":1,
      "description":"These questions ask about the opinions brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":2,
      "theme_title":"Opinions and arguments"
   },
   {  
      "answer":2.851852,
      "class_id":1,
      "description":"These questions are about the persistence of the excercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":3,
      "theme_title":"Persistence"
   },
   ...
]
```


### List of students in a group (TODO)

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

### Move student to group

```endpoint
POST teachers/{teacher_id}/classes/{class_id}/students/{student_id}/move_to_group/{group_id}
```

#### Example request

```curl
$ curl --request POST localhost:8080/webapi/teachers/1/classes/1/students/1/move_to_group/2
```

#### Example response

```json
{
  "_id" : 2,
  "name" : "second group",
  "class_id" : 0
}
```

### List the information of individual student (TESTING)

This is how you can get a specific student information by their id.

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

### Retrieve a student's result (DONE)

Returns a single result for a specified survey.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/{student_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/students/1/surveys/27/answers
```

#### Example response

```json
[  
   {  
      "answer":4.6666665,
      "description":"These questions ask about the responsibilities of the student during the exercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":4,
      "theme_title":"Responsibility"
   },
   {  
      "answer":3.6666667,
      "description":"These questions are about the persistence of the excercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":3,
      "theme_title":"Persistence"
   },
   {  
      "answer":3.0,
      "description":"These questions ask about the opinions brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":2,
      "theme_title":"Opinions and arguments"
   },
]
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
  {
    "themes" : [
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
  },
  {
    "themes" : [
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
  }
]
```

### Retrieve a group's results (DONE)

Returns a single result for a specified survey for a specified group.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/{group_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/groups/1/surveys/27/answers
```

#### Example response

```json

[  
   {  
      "answer":4.3333335,
      "description":"These questions ask about the ideas brought by the student during the exercise.",
      "group_id":1,
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":1,
      "theme_title":"Ideas and problem solving"
   },
   {  
      "answer":3.0,
      "description":"These questions ask about the opinions brought by the student during the exercise.",
      "group_id":1,
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":2,
      "theme_title":"Opinions and arguments"
   },
   {  
      "answer":2.3333333,
      "description":"These questions are about the persistence of the excercise.",
      "group_id":1,
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":3,
      "theme_title":"Persistence"
   },
   ...
]

```

### Retrieve group's progression

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/{group_id}/progression/{amount}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/groups/1/progression/2
```

#### Example response

```json
[
  {
    "themes" : [
      {
        "answer" : 2.3,
        "description" : "These questions are about respect and the sustainability of the work.",
        "start_date" : "2017-10-13 10:13:09.972716+00",
        "group_id" : 1,
        "survey_id" : 53,
        "theme_id" : 5,
        "theme_title" : "Sustainable work - respect"
      },
      {
        "answer" : 3.9,
        "description" : "These questions are about the persistence of the excercise.",
        "start_date" : "2017-10-13 10:13:09.972716+00",
        "group_id" : 1,
        "survey_id" : 53,
        "theme_id" : 3,
        "theme_title" : "Persistence"
      },
    ],
  },
  {
    "themes" : [
      {
        "answer" : 2.1,
        "description" : "These questions are about respect and the sustainability of the work.",
        "start_date" : "2017-11-09 10:12:03.972716+00",
        "group_id" : 1,
        "survey_id" : 85,
        "theme_id" : 5,
        "theme_title" : "Sustainable work - respect"
      },
      {
        "answer" : 3.5,
        "description" : "These questions are about the persistence of the excercise.",
        "start_date" : "2017-11-09 10:12:03.972716+00",
        "group_id" : 1,
        "survey_id" : 85,
        "theme_id" : 3,
        "theme_title" : "Persistence"
      },
    ]
  }
]
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
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/27/answers
```

#### Example response

<!--TODO: Actual response-->

```json
[  
   {  
      "answer":3.7407408,
      "class_id":1,
      "description":"These questions ask about the ideas brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":1,
      "theme_title":"Ideas and problem solving"
   },
   {  
      "answer":3.2777777,
      "class_id":1,
      "description":"These questions ask about the opinions brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":2,
      "theme_title":"Opinions and arguments"
   },
   {  
      "answer":2.7037036,
      "class_id":1,
      "description":"These questions are about the persistence of the excercise.",
      "start_date":"1970-01-01",
      "survey_id":27,
      "theme_id":3,
      "theme_title":"Persistence"
   },
]
```

### Retrieve classes progression

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/progression/{amount}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/progression/2
```

#### Example response

```json
[
  {
    "themes" : [
      {
        "answer" : 4.2,
        "description" : "These questions are about respect and the sustainability of the work.",
        "start_date" : "2017-10-13 10:13:09.972716+00",
        "class_id" : 1,
        "survey_id" : 53,
        "theme_id" : 5,
        "theme_title" : "Sustainable work - respect"
      },
      {
        "answer" : 1.1,
        "description" : "These questions are about the persistence of the excercise.",
        "start_date" : "2017-10-13 10:13:09.972716+00",
        "class_id" : 1,
        "survey_id" : 53,
        "theme_id" : 3,
        "theme_title" : "Persistence"
      },
    ],
  },
  {
    "themes" : [
      {
        "answer" : 4.3,
        "description" : "These questions are about respect and the sustainability of the work.",
        "start_date" : "2017-11-09 10:12:03.972716+00",
        "class_id" : 1,
        "survey_id" : 85,
        "theme_id" : 5,
        "theme_title" : "Sustainable work - respect"
      },
      {
        "answer" : 1.4,
        "description" : "These questions are about the persistence of the excercise.",
        "start_date" : "2017-11-09 10:12:03.972716+00",
        "class_id" : 1,
        "survey_id" : 85,
        "theme_id" : 3,
        "theme_title" : "Persistence"
      },
    ]
  }
]
```

### List surveys for one class (DONE)

Retrieves surveys for a teacher, for one class, on one survey

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/
```

#### Example response

```json
[  
   {  
      "_id":27,
      "title":"MR F of survey",
      "description":"arrested development",
      "start_date":"1970-01-01",
      "end_date":"2017-10-11",
      "teacher_id":1,
      "class_id":1,
      "open":false
   },
]
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


### Close specific survey of a class (DONE)

Adds new survey

```endpoint
POST teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}
```

#### Example request

```curl
$ curl localhost:8080/webapi/teachers/1/classes/1/surveys/28
  -d @data.json
```

#### Example response

```json

```

#### Note

No response except a standard code 200 HTTP response.



-----------------------------------------------
-----------------------------------------------
-----------------------------------------------



## Student role

Endpoints available for student.

### List information (TESTING)

This is how student can get their own information.

```endpoint
GET /students/{student_id}
```

#### Example request

```curl
$ curl localhost:8080/webapi/students/1
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
### Retrieve all the classes (TESTING)

The student can get a list of all classes he is in.

```endpoint
GET /students/{student_id}/classes
```
#### Example request

```curl
$ curl localhost:8080/webapi/students/1/classes
```
#### Example response

```json
  [
    {
      "_id":1,
      "name":"{First Class}",
      "teacher_id":1
    },
    {
      "_id":2,
      "name":"{Second Class}",
      "teacher_id":1
    }
  ]
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

```json
[  
   {  
      "answer":3.6666667,
      "description":"These questions ask about the responsibilities of the student during the exercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":4,
      "theme_title":"Responsibility"
   },
   {  
      "answer":5.0,
      "description":"These questions are about the persistence of the excercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":3,
      "theme_title":"Persistence"
   },
   {  
      "answer":5.0,
      "description":"These questions ask about the opinions brought by the student during the exercise.",
      "start_date":"1970-01-01",
      "student_id":1,
      "survey_id":27,
      "theme_id":2,
      "theme_title":"Opinions and arguments"
   }
]
```

### Retrieve student's progression

```endpoint
GET students/{student_id}/progression/{amount}
```

#### Example request

```curl
$ curl localhost:8080/webapi/students/1/progression/2
```

#### Example response

```json
[
  {
    "themes" : [
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
  },
  {
    "themes" : [
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
      "_id":27,
      "title":"MR F of survey",
      "description":"arrested development",
      "start_date":"1970-01-01",
      "end_date":"2017-10-11",
      "teacher_id":1,
      "class_id":1,
      "open":false
   },
   {  
      "_id":28,
      "title":"test_2",
      "description":"arrested_development",
      "start_date":"1970-01-01",
      "end_date":"",
      "teacher_id":1,
      "class_id":1,
      "open":true
   }
]
```

### Retrieve student's progression in a class

```endpoint
GET students/{student_id}/classes/{class_id}/progression/{amount}
```

#### Example request

```curl
$ curl localhost:8080/webapi/students/1/classes/1/progression/2
```

#### Example response

```json
[
  {
    "themes" : [
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
  }
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
  }
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

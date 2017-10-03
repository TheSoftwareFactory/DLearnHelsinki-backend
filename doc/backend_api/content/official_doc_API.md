# Dlearn

This is our high-quality API. You can use this API to request
and remove different students and spidergraphs.


## Teacher role

End points available for teacher.

### List students in one class (TODO)

Lists all students inside one specified class.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/students/
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/students/
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

### List of all groups in one specified class (TODO)

This request allows you to retrieve a detailed list of all students in their respective groups, in one class

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/groups/
```

#### Example response

```json
[
  {
    "group_id" : 1,
    "grpName" : "Grp1",
    "students" : [
      {
        "_id" : 1,
        "lastname" : "Meikäläinen",
        "firstname" : "Matti",
        "username" : "iloinen tanssiva aurinko"
      },
      {
        "_id" : 2,
        "lastname" : "Jo",
        "firstname" : "Doe",
        "username" : "iloinen tanssiva aurinko"
      }
    ]
  },
  {
    "group_id" : 2,
    "grpName" : "Grp2",
    "students" : [
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
  }
]
```

### List of students in a group (TODO)

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/groups/{group_id}/students
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/groups/1/students
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
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/students/1
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
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/students/{student_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/surveys/1/students/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### Retrieve a group's results (TODO)

Returns a single result for a specified survey for a specified group.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/groups/{group_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/surveys/1/groups/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### Retrieve a class's results (TODO)

Returns a single result for a specified survey for a specified class.

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/answers
GET /
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```


### List surveys (TODO)

Retrieves surveys for a teacher, for one class, on one survey

```endpoint
GET teachers/{teacher_id}/classes/{class_id}/surveys/{survey_id}/answers
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/teachers/1/classes/1/surveys/1/answers
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
$ curl --request POST localhost:8080/skeleton/webapi/teachers/1/classes/1/surveys
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
{

}
```

<!-- TODO: Updating survey? -->

#### Note

This will create a survey even if another survey is currently open.

-----------------------------------------------

## Student role

Endpoints available for student.

### List information (TODO)

This is how student can get their own information.

```endpoint
GET /student/{student_id}
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/student/{student_id}
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

### Retrieve a student's result for specific survey (TODO)

Returns a single spidergraph.

```endpoint
GET /student/{student_id}/classes/{class_id}/surveys/{survey_id}/answers
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/student/1/classes/1/surveys/1/answers
```

#### Example response

<!--TODO: Actual response-->

```json
{
  "question_id" : 1,
  "student_id" : 1,
  "answer" : 3
}
```

### Retrieve all the surveys (TODO)

Returns all the survey for one class. Can be used to find the open survey

```endpoint
GET /students/{student_id}/classes/{class_id}/surveys
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/students/1/classes/1/surveys
```

#### Example response

```json
[
  {
    "_id" : 1,
    "teacher_id" : 1,
    "class_id" : 1,
    "name" : "Math survey",
    "description" : "survey for the exercie 3 page 40",
    "date" : "2007-04-05T13:30Z",
    "open" : true
  },
  {
    "_id" : 2,
    "teacher_id" : 1,
    "class_id" : 1,
    "name" : "Math survey",
    "description" : "survey for the exercie 5 page 13",
    "date" : "2007-04-05T13:30Z",
    "open" : false
  },
]
```

### Retrieve questions for a survey (TODO)

Returns the array of questions for a survey.

```endpoint
GET /students/{student_id}/classes/{class_id}/surveys/{survey_id}/questions
```
#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/student/groups/1/surveys/1/questions
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

### Send answers to the questions (TODO)

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

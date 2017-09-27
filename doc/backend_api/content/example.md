# Dlearn

This is our high-quality API. You can use this API to request
and remove different students and spidergraphs.

## Teacher role

End points available for teacher.

### List students

Lists all students available.

```endpoint
GET /students/
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/students/
```

#### Example response

```json
[
  {
    "_id": 1,
    "lastname": "Meikäläinen",
    "firstname": "Matti",
    "username": "iloinen tanssiva aurinko"
  },
  {
    "_id": 2,
    "lastname": "Meikäläinen",
    "firstname": "Maija",
    "username": "kirjava hyppivä puu"
  }
]
```

### List individual students

This is how you can get a specific student by their id.

```endpoint
GET /students/{student_id}
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/students/1
```

#### Example response

```json
{
  "_id": 1,
  "lastname": "Meikäläinen",
  "firstname": "Matti",
  "username": "iloinen tanssiva aurinko"
}
```

### Retrieve a student's spidergraph

Returns a single spidergraph.

```endpoint
GET /students/{student_id}/spidergraphs/{id}
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
curl localhost:8080/skeleton/webapi/students/1/spidergraphs/1
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### List surveys

Retrieves surveys for a teacher

```endpoint
GET teachers/{teacher_id}/surveys/
```

#### Example request

```curl
curl localhost:8080/skeleton/webapi/teachers/1/surveys/
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

### Add new survey

Adds new survey

```endpoint
POST teachers/{teacher_id}/surveys/
```

#### Example request

```curl
curl --request POST localhost:8080/skeleton/webapi/teachers/{teacher_id}/surveys \
  -d @data.json
```

#### Example request body

```json
{
  "_id": 1,
  "name": "Reilu peli leikkikentällä",
  "group_id": 1,
  "start_data": "2007-04-05T12:30Z",
  "end_date": "2007-04-05T13:30Z",
  "teacher_id": 1,
}
```

#### Example response

<!--TODO: Actual response-->

```json
{

}
```

<!-- TODO: Updating survey? -->

## Student role

Endpoints available for student.

### List information

This is how student can get their own information.

```endpoint
GET /student/
```

#### Example request

```curl
$ curl localhost:8080/skeleton/webapi/student/
```

#### Example response

```json
{
  "_id": 1,
  "lastname": "Meikäläinen",
  "firstname": "Matti",
  "username": "iloinen tanssiva aurinko"
}
```

### Retrieve a student's spidergraph

Returns a single spidergraph.

```endpoint
GET /student/spidergraphs/1
```

Retrieve information about an existing spidegraph.

#### Example request

```curl
curl localhost:8080/skeleton/webapi/student/spidergraphs/1
```

#### Example response

<!--TODO: Actual response-->

```json
{
  
}
```

### Retrieve the active survey ID

Returns the survey ID that the specified group of students can answer right now. If no survey is active, returns nothing.

```endpoint
GET /student/groups/{group_id}/surveys
```

#### Example request

```curl
curl localhost:8080/skeleton/webapi/student/groups/1/surveys
```

#### Example response

```json
{
  "_id": 1
}
```

### Retrieve questions for a survey

Returns the array of questions for a survey.

```endpoint
GET /student/groups/{group_id}/surveys/{survey_id}/questions
```
#### Example request

```curl
curl localhost:8080/skeleton/webapi/student/groups/1/surveys/1/questions
```

#### Example response

```json
[
	{
  		"_id" : 1,
		"question" : "Are you happy?",
		"min_answer" : 1,
		"max_answer" : 5
	},
	{
  		"_id" : 2,
		"question" : "Rate your teacher.",
		"min_answer" : 1,
		"max_answer" : 5
	}
]
```

### Send answers to the questions

Sends the answer to the specified question.

```endpoint
PUT /student/groups/{group_id}/surveys/{survey_id}/questions/{question_id}/answer
```

#### Example of the message body

```json
{
	"answer" : 2
}
```
### Update a wobble

Updates the properties of a particular wobble.

```endpoint
PATCH /wobbles/v1/{username}/{wobble_id}
```

#### Example request

```curl
curl --request PATCH https://wobble.biz/wobbles/v1/{username}/{wobble_id} \
  -d @data.json
```

```python
resp = wobbles.update_wobble(
  wobble_id,
  name='updated example',
  description='An updated example wobble'
  ).json()
```

```bash
$ wbl wobble update-wobble wobble-id
```

```javascript
var options = { name: 'foo' };
client.updateWobble('wobble-id', options, function(err, wobble) {
  console.log(wobble);
});
```

#### Example request body

```json
{
  "name": "foo",
  "description": "bar"
}
```

Property | Description
---|---
`name` | (optional) the name of the wobble
`description` | (optional) a description of the wobble

#### Example response

```json
{
  "owner": "{username}",
  "id": "{wobble_id}",
  "name": "foo",
  "description": "bar",
  "created": "{timestamp}",
  "modified": "{timestamp}"
}
```

### Delete a wobble

Deletes a wobble, including all wibbles it contains.

```endpoint
DELETE /wobbles/v1/{username}/{wobble_id}
```

#### Example request

```curl
curl -X DELETE https://wobble.biz/wobbles/v1/{username}/{wobble_id}
```

```bash
$ wbl wobble delete-wobble wobble-id
```

```python
resp = wobbles.delete_wobble(wobble_id)
```

```javascript
client.deleteWobble('wobble-id', function(err) {
  if (!err) console.log('deleted!');
});
```

#### Example response

> HTTP 204

### List wibbles

List all the wibbles in a wobble. The response body will be a
WobbleCollection.

```endpoint
GET /wobbles/v1/{username}/{wobble_id}/wibbles
```

#### Example request

```curl
curl https://wobble.biz/wobbles/v1/{username}/{wobble_id}/wibbles
```

```bash
$ wbl wobble list-wibbles wobble-id
```

```python
collection = wobbles.list_wibbles(wobble_id).json()
```

```javascript
client.listWobbles('wobble-id', {}, function(err, collection) {
  console.log(collection);
});
```

#### Example response

```json
{
  "type": "Wobble",
  "wibbles": [
    {
      "id": "{wibble_id}",
      "type": "Wobble",
      "properties": {
        "prop0": "value0"
      }
    },
    {
      "id": "{wibble_id}",
      "type": "Wobble",
      "properties": {
        "prop0": "value0"
      }
    }
  ]
}
```

### Insert or update a wibble

Inserts or updates a wibble in a wobble. If there's already a wibble
with the given ID in the wobble, it will be replaced. If there isn't
a wibble with that ID, a new wibble is created.

```endpoint
PUT /wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id}
```

#### Example request

```curl
curl https://wobble.biz/wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id} \
  -X PUT \
  -d @file.geojson
```

```bash
$ wbl wobble put-wibble wobble-id wibble-id 'geojson-wibble'
```

```javascript
var wibble = {
  "type": "Wobble",
  "properties": { "name": "Null Island" }
};
client.insertWobble(wibble, 'wobble-id', function(err, wibble) {
  console.log(wibble);
});
```

#### Example request body

```json
{
  "id": "{wibble_id}",
  "type": "Wobble",
  "properties": {
    "prop0": "value0"
  }
}
```

Property | Description
--- | ---
`id` | the id of an existing wibble in the wobble

#### Example response

```json
{
  "id": "{wibble_id}",
  "type": "Wobble",
  "properties": {
    "prop0": "value0"
  }
}
```

### Retrieve a wibble

Retrieves a wibble in a wobble.

```endpoint
GET /wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id}
```

#### Example request

```curl
curl https://wobble.biz/wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id}
```

```bash
$ wbl wobble read-wibble wobble-id wibble-id
```

```javascript
client.readWobble('wibble-id', 'wobble-id',
  function(err, wibble) {
    console.log(wibble);
  });
```

```python
wibble = wobbles.read_wibble(wobble_id, '2').json()
```

#### Example response

```json
{
  "id": "{wibble_id}",
  "type": "Wobble",
  "properties": {
    "prop0": "value0"
  }
}
```

### Delete a wibble

Removes a wibble from a wobble.

```endpoint
DELETE /wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id}
```

#### Example request

```javascript
client.deleteWobble('wibble-id', 'wobble-id', function(err, wibble) {
  if (!err) console.log('deleted!');
});
```

```curl
curl -X DELETE https://wobble.biz/wobbles/v1/{username}/{wobble_id}/wibbles/{wibble_id}
```

```python
resp = wobbles.delete_wibble(wobble_id, wibble_id)
```

```bash
$ wbl wobble delete-wibble wobble-id wibble-id
```

#### Example response

> HTTP 204

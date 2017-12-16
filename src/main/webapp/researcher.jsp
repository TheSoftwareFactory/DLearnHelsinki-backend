<%-- 
    Document   : researcher
    Created on : Dec 15, 2017, 3:25:57 PM
    Author     : Eero
--%>

<%@ page import = "java.util.Map" %>
<%@ page import = "org.dlearn.helsinki.skeleton.database.ResearcherHelper" %>
<%@ page import="org.dlearn.helsinki.skeleton.model.Teacher"%>
<%@ page import="org.dlearn.helsinki.skeleton.model.Question"%>
<%@ page import="org.dlearn.helsinki.skeleton.model.Theme"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Researchers Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </head>
    <body>
    <div class="container">
    
    <h1 class="text-center">Researchers Control Page</h1>
 
  
    <%
        String msg = "";
        Teacher teacher;
        Question question;
        Theme theme;
        
        if ((teacher = ResearcherHelper.addTeacher(request)) != null) {
            msg = "Added teacher: "+teacher.username;
        } 
        else if ((question = ResearcherHelper.addQuestion(request)) != null) {
            msg = "Added question: "+question.question;
        }
        else if ((theme = ResearcherHelper.addTheme(request)) != null) {
            msg = "Added Theme "+theme.getTitle();
        }
    %>
    
    <div class="accordion-group" id="adders">
      
    <div class="form-group">
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#teacher_div">Add Teacher</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#theme_div">Add Theme</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#question_div">Add Question</button>
    </div>
    
    <div id="teacher_div" class="collapse indent">
    <div style="max-width: 50%">
    <form name="add_teacher" action="" method="post">
        <h3>Add teacher:</h3>
        <div class="form-group">
            <label>First Name</label>
            <input type="text" class="form-control input-md" name="add_teacher_first"/>
        </div>
        <div class="form-group">
            <label>Last Name</label>
            <input type="text" class="form-control input-md" name="add_teacher_last"/>
        </div>
        <div class="form-group">
            <label>Username</label>
            <input type="text" class="form-control input-md" name="add_teacher_user"/>
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="text" class="form-control input-md" name="add_teacher_pwd"/>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
        
    <table class="table table-striped">
    <thead>
      <tr>
        <th>Id</th>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Username</th>
      </tr>
    </thead>
    <tbody>
        <% 
        for (Teacher t : ResearcherHelper.listTeachers()) {
        %>
        <tr>
            <td> <%= t.get_id() %> </td>
            <td> <%= t.getName() %> </td>
            <td> <%= t.getLastname()%> </td>
            <td> <%= t.getUsername()%> </td>            
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    
    <div id="theme_div" class="collapse indent">
    <div style="max-width: 50%;">
    <form name="add_theme" action="" method="post">
        <h3>Add theme:</h3>
        <div class="form-group">
            <label>Title</label>
            <input type="text" class="form-control input-md" name="add_theme_title"/>
        </div>
        <div class="form-group">
            <label>Title in Finnish</label>
            <input type="text" class="form-control input-md" name="add_theme_title_fi"/>
        </div>
        <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control input-md" name="add_theme_description"/>
        </div>
        <div class="form-group">
            <label>Description in Finnish</label>
            <input type="text" class="form-control input-md" name="add_theme_description_fi"/>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
    <table class="table table-striped">
    <thead>
      <tr>
        <th>Id</th>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Username</th>
      </tr>
    </thead>
    <tbody>
        <% 
        for (Teacher t : ResearcherHelper.listTeachers()) {
        %>
        <tr>
            <td> <%= t.get_id() %> </td>
            <td> <%= t.getName() %> </td>
            <td> <%= t.getLastname()%> </td>
            <td> <%= t.getUsername()%> </td>            
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    
    <div id="question_div" class="collapse">
    <div style="max-width: 50%;">
    <form name="add_question" action="" method="post">
        <h3>Add Question</h3>
        <div class="form-group">
            <label>Question</label>
            <input type="text" class="form-control input-md" name="add_question_question"/>
        </div>
        <div class="form-group">
            <label>Question in Finnish</label>
            <input type="text" class="form-control input-md" name="add_question_question_fi"/>
        </div>
        <div class="form-group">
            <label>Min Value of answer</label>
            <input type="text" class="form-control input-md" name="add_question_min_answer"/>
        </div>
        <div class="form-group">
            <label>Max Value of answer</label>
            <input type="text" class="form-control input-md" name="add_question_max_answer"/>
        </div>
        <div class="form-group">
            <label>Theme</label>
            <select name="add_question_theme">
            <% for (Theme t : ResearcherHelper.listThemes()) {
                out.print("<option value=\""+t.getId()+"\">"+t.getTitle()+"</option>");
            } %>
            </select>         
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
    <table class="table table-striped">
    <thead>
      <tr>
        <th>Id</th>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Username</th>
      </tr>
    </thead>
    <tbody>
        <% 
        for (Teacher t : ResearcherHelper.listTeachers()) {
        %>
        <tr>
            <td> <%= t.get_id() %> </td>
            <td> <%= t.getName() %> </td>
            <td> <%= t.getLastname()%> </td>
            <td> <%= t.getUsername()%> </td>            
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    </div>
    </body>
    
    <% 
        if (!msg.isEmpty()) {
    %> 
    <script>
        document.getElementsByTagName("body")[0].onload = function() {myFunction()};

        function myFunction() {
            alert("<%=msg%>")
        }
    </script>
    <% } %>
    <script>
        var $myGroup = $('#adders');
        $myGroup.on('show.bs.collapse','.collapse', function() {
            console.log("HERE");
            $myGroup.find('.collapse.in').collapse('hide');
        }); 
    </script>
</html>

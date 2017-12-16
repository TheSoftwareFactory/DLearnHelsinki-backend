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
        <style>
            .table-wrapper { height: 300px; overflow: auto; }   
        </style>
    </head>
    <body>
    <div class="container">
    
    <h1 class="text-center">Researchers Control Page</h1>
 
    <%--Success Popups--%>
    
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
    
    <%--Menu buttons--%>
    
    <div class="accordion-group" id="adders">
      
    <div class="form-group">
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#teacher_div">Add Teacher</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#theme_div">Add Theme</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#question_div">Add Question</button>
    </div>
    
    <%--Teacher Page--%>
        
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
    
    <h2> Teachers </h2>
    <table style="margin-bottom: 0px;" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Username</th>
        </tr>
        </thead>
    </table>
    <div class="table-wrapper">
    <table class="table table-striped">
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
    
    <%--Theme Page--%>
    
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
        <th style="text-align: center;">ID</th>
        <th>title</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
        <% 
        for (Theme t : ResearcherHelper.listThemes()) {
        %>
        <tr>
            <td rowspan="2" style="vertical-align: middle; text-align: center;"> <%= t.getId() %> </td>
            <td> <%= t.getTitle() %> </td>
            <td> <%= t.getDescription() %> </td>            
        </tr>
        <tr>
            <td> <%= t.getTitle_fi() %> </td>
            <td> <%= t.getDescription_fi() %> </td>            
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    
    <%--Questions Page--%>
    
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
    <table class="table table-striped" style="display: table; border-collapse: separate;">
    <thead style="display: table-header-group; width: 100%;">
      <tr>
        <th style="vertical-align: middle; text-align: center;" rowspan="2">ID</th>
        <th style="vertical-align: middle;" rowspan="2">Question</th>
        <th style="vertical-align: middle;" colspan="2">Answer range</th>
        <th style="vertical-align: middle;" rowspan="2">Theme</th>
      </tr>
       <tr>
        <th>Min</th>
        <th>Max</th>
      </tr>
    </thead>
    <tbody style="display: block; height: 400px; overflow: auto; width: 100%;">
        <% 
        for (Question t : ResearcherHelper.listQuestions()) {
        %>
        <tr>
            <td rowspan="2" style="vertical-align: middle; text-align: center;"> <%= t.get_id() %> </td>
            <td> <%= t.getQuestion() %> </td>
            <td rowspan="2" style="vertical-align: middle;"> <%= t.getMin_answer() %> </td>
            <td rowspan="2" style="vertical-align: middle;"> <%= t.getMax_answer() %> </td>
            <td rowspan="2" style="vertical-align: middle;"> <%= t.get_theme_title() %> </td>             
        </tr>
        <tr>
            <td> <%= t.getQuestion_fi() %> </td>            
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
        document.getElementsByTagName("body")[0].onload = function() {myFunction();};

        function myFunction() {
            if(confirm(<%=msg%>)){
                window.location.reload();  
            }
            
        }
    </script>
    <% } %>
    <script>
        $( "table" ).wrap( "<div class='table-overflow'></div>" );
        var $myGroup = $('#adders');
        $myGroup.on('show.bs.collapse','.collapse', function() {
            console.log("HERE");
            $myGroup.find('.collapse.in').collapse('hide');
        }); 
    </script>
</html>

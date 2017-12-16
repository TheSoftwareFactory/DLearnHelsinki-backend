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
            body{ padding-bottom:70px; }
            .table-wrapper { height: 300px; overflow: auto; }
            .td_id {width: 10%; text-align: left;}
            .td_2row {vertical-align: middle !important;}
            .td_title {width: 20%; text-align: left;}
            .td_description {width: 70%; text-align: left;}
            .td_name {width: 30%; text-align: left;}
        </style>
        <script>
            var $myGroup = $('#adders');
            $myGroup.on('show.bs.collapse','.collapse', function() {
                $myGroup.find('.collapse.in').collapse('hide');
            }); 
        </script>
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
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#teacher_div">Teachers</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#theme_div">Themes</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#question_div">Questions</button>
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
    
    <h3> Teachers </h3>
    <table style="margin-bottom: 0px;" class="table table-striped">
        <thead>
        <tr>
            <th class="td_id">ID</th>
            <th class="td_name">Firstname</th>
            <th class="td_name">Lastname</th>
            <th class="td_name">Username</th>
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
            <td class="td_id"> <%= t.get_id() %> </td>
            <td class="td_name"> <%= t.getName() %> </td>
            <td class="td_name"> <%= t.getLastname()%> </td>
            <td class="td_name"> <%= t.getUsername()%> </td>            
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
        
    <h3> Themes </h3>
    <table style="margin-bottom: 0px;" class="table">
        <thead>
          <tr>
            <th class="td_id">ID</th>
            <th class="td_title">Title</th>
            <th class="td_description">Description</th>
          </tr>
        </thead>
    </table>
    <div class="table-wrapper">
    <table class="table">
    <tbody>
        <% 
        for (Theme t : ResearcherHelper.listThemes()) {
        %>
        <tr>
            <td rowspan="2" class="td_id td_2row"> <%= t.getId() %> </td>
            <td class="td_title"> <%= t.getTitle() %> </td>
            <td class="td_description"> <%= t.getDescription() %> </td>            
        </tr>
        <tr>
            <td class="td_title"> <%= t.getTitle_fi() %> </td>
            <td class="td_description"> <%= t.getDescription_fi() %> </td>            
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
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
   
    <h3> Questions </h3>
    <table style="margin-bottom: 0px;" class="table">
        <thead>
          <tr>
            <th class="td_name" rowspan="2">Theme</th>
            <th class="td_id" rowspan="2">ID</th>
            <th class="td_name" rowspan="2">Question</th>
            <th class="td_title" colspan="2">Answer range</th>
          </tr>
             <tr>
                <th class="td_id">Min</th>
                <th class="td_id">Max</th>
            </tr>
        </thead>
    </table>
    <div class="table-wrapper">
    <table class="table">
    <tbody>
        <% 
        for (Question q : ResearcherHelper.listQuestions()) {
        %>
        <tr>
            <td class="td_name td_2row"> <%= q.get_theme_title() %> </td>             
            <td class="td_id td_2row"> <%= q.get_id() %> </td>
            <td class="td_name td_2row"> <%= q.getQuestion() %> <br> <%= q.getQuestion_fi() %> </td>
            <td class="td_id td_2row"> <%= q.getMin_answer() %> </td>
            <td class="td_id td_2row"> <%= q.getMax_answer() %> </td>
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    
    
    </div>
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
</html>

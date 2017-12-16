<%-- 
    Document   : researcher
    Created on : Dec 15, 2017, 3:25:57 PM
    Author     : Eero
--%>

<%@ page import="org.dlearn.helsinki.skeleton.database.ResearcherHelper" %>
<%@ page import="org.dlearn.helsinki.skeleton.model.Teacher"%>
<%@ page import="org.dlearn.helsinki.skeleton.model.Question"%>
<%@ page import="org.dlearn.helsinki.skeleton.model.Theme"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Researchers Page</title>
        <script src="scripts/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/bootstrap.min.css">
        <script src="scripts/bootstrap.min.js"></script>

        <style>
            body{ padding-bottom:70px; }
            .table-wrapper { height: 300px; overflow: auto; }
            .td_id {width: 10%; text-align: left;}
            .td_2row {vertical-align: middle !important;}
            .td_title {width: 20%; text-align: left;}
            .td_description {width: 70%; text-align: left;}
            .td_name {width: 30%; text-align: left;}
            .td_question {width: 50%; text-align: left;}
            .td_center {text-align: center;}
            
        </style>
    </head>
    <body>
    <div class="container">
    
    <h1 class="text-center">Researchers Control Page</h1>
 
    <%--Success Popups--%>
    
    <%
        String method = request.getParameter("method");
        boolean success = false;
        String msg = "";

        if (method != null && !method.isEmpty()) {
            switch (method) {
            case "teacher":
                Teacher teacher = ResearcherHelper.addTeacher(request);
                if (teacher != null) {
                    msg = "Added teacher: "+teacher.username;
                    success = true;
                }
                break;
            case "question":
                Question question = ResearcherHelper.addQuestion(request);
                if (question != null) {
                    msg = "Added question: "+question.question;
                    success = true;
                }
                break;
            case "theme":
                Theme theme = ResearcherHelper.addTheme(request);
                if (theme != null) {
                    msg = "Added theme: "+theme.getTitle();
                    success = true;
                }
                break;
            default:  
                success = true;
                break;
            }
            if (!success) {
                msg = "Failed to create "+method;
            }
        }
    %>
            
    <%--Menu buttons--%>
    
    <div class="accordion-group" id="adders">
      
    <div class="form-group">
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#teacher_div">Teachers</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#theme_div">Themes</button>
        <button type="button" class="btn btn-info" data-toggle="collapse" data-parent="#adders" data-target="#question_div">Questions</button>
    </div>
        
    <%-- Alerts --%>   
    
    <% if (!msg.isEmpty()) { %>
    <% if (success) { %>
    <div class="alert alert-success alert-dismissable" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <%= msg %>
    </div>
    <% } else { %>
    <div class="alert alert-danger alert-dismissable" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
         <%= msg %>
    </div>
    <% } } %>
    
    <%--Teacher Page--%>
        
    <div id="teacher_div" class="collapse indent">
    <div style="max-width: 50%">
    <form name="add_teacher" action="" method="post">
        <h3>Add teacher:</h3>
        <div class="form-group">
            <label>First Name</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_teacher_first" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter First name Here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Last Name</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_teacher_last" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter Last name Here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Username</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_teacher_user" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter Username Here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Password</label>
            <input pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" type="text" class="form-control input-md" name="add_teacher_pwd" placeholder="Must contain upper and lower case letters, and numbers." required
                oninvalid="this.setCustomValidity('Enter password Here, requires one lower case and upper case letter, and a number')"
                oninput="setCustomValidity('')"  />
            
        </div>
        <input type="hidden" name="method" value="teacher">
        <button type="submit" name="submit" class="btn btn-primary">Submit</button>
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
    </div>  <%--/div teacher_div --%>
    
    <%--Theme Page--%>
    
    <div id="theme_div" class="collapse indent">
    <div style="max-width: 50%;">
    <form name="add_theme" action="" method="post">
        <h3>Add theme:</h3>
        <div class="form-group">
            <label>Title</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_theme_title" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter theme title here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Title in Finnish</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_theme_title_fi" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter theme title in finnish')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Description</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_theme_description" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter theme description here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Description in Finnish</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_theme_description_fi" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter theme description in finnish here')"
                oninput="setCustomValidity('')"  />
        </div>
        <input type="hidden" name="method" value="theme">
        <button type="submit" name="submit" class="btn btn-primary">Submit</button>
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
    </div> <%--/div theme_div --%>
    
    <%--Questions Page--%>
    
    <div id="question_div" class="collapse">
    <div style="max-width: 50%;">
    <form name="add_question" action="" method="post">
        <h3>Add Question</h3>
        <div class="form-group">
            <label>Question</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_question_question" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter question here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Question in Finnish</label>
            <input pattern=".{5,}" type="text" class="form-control input-md" name="add_question_question_fi" placeholder="Must be atleast 5 characters" required
                oninvalid="this.setCustomValidity('Enter question in finnish here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Min Value of answer</label>
            <input type="text" class="form-control input-md" name="add_question_min_answer" required
                oninvalid="this.setCustomValidity('Enter minimum answer here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Max Value of answer</label>
            <input type="text" class="form-control input-md" name="add_question_max_answer" required
                ninvalid="this.setCustomValidity('Enter maximum answer here here')"
                oninput="setCustomValidity('')"  />
        </div>
        <div class="form-group">
            <label>Theme</label>
            <select name="add_question_theme" class="form-control">
                <option value="" disabled selected>Select theme</option>
            <% for (Theme t : ResearcherHelper.listThemes()) {
                out.print("<option value=\""+t.getId()+"\">"+t.getTitle()+"</option>");
            } %>
            </select>         
        </div>           
        <input type="hidden" name="method" value="question">
        <button type="submit" name="submit" class="btn btn-primary">Submit</button>
    </form>
    </div>
   
    <h3> Questions </h3>
    <table style="margin-bottom: 0px;" class="table">
        <thead>
          <tr>
            <th class="td_title" rowspan="2">Theme</th>
            <th class="td_id td_center" rowspan="2">ID</th>
            <th class="td_question" rowspan="2">Question</th>
            <th class="td_title td_center" colspan="2">Answer range</th>
          </tr>
             <tr>
                <th class="td_id td_center">Min</th>
                <th class="td_id td_center">Max</th>
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
            <td class="td_title td_2row"> <%= q.get_theme_title() %> </td>             
            <td class="td_id td_center td_2row"> <%= q.get_id() %> </td>
            <td class="td_question td_2row"> <%= q.getQuestion() %> <br> <%= q.getQuestion_fi() %> </td>
            <td class="td_id td_center td_2row"> <%= q.getMin_answer() %> </td>
            <td class="td_id td_center td_2row"> <%= q.getMax_answer() %> </td>
        </tr>
        <%}%>
    </tbody>
    </table>
    </div>
    </div>   <%--/div question_div --%>
    
    </div>   <%--/div accordion--%>
    </div>   <%--/div container--%>
    
    <script>
        var $myGroup = $('#adders');
        $myGroup.on('show.bs.collapse','.collapse', function() {
            $myGroup.find('.collapse.in').collapse('hide');
        });
        
        <% if (method != null) { %>
            $myGroup.find('#<%=method%>_div').collapse('show');
        <% } else { %>
            $myGroup.find('#teacher_div').collapse('show');
        <% } %>
    </script>
    
    </body>
</html>


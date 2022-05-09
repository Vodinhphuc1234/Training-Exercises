<%-- 
    Document   : Profile
    Created on : May 9, 2022, 9:54:36 AM
    Author     : vodinhphuc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" 
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    
    </head>
    <body class = "bg-dark">
        
        <div class = "container col-6 bg-gradient">
            <div class="card mt-5 bg-light">
                <div class = "card-header">
                    <h5>Your profile</h5>
                </div>
                <div class="card-body">
                    <h5 class="card-title" id = "name">Name: </h5>
                    <p class="card-text" id = "username">Username: </p>
                    <a href="#" class="btn btn-primary" id="btnUpdate">Update</a>
                    <a href="#" class="btn btn-primary ms-3" id="btnChangePass">Change Password</a>
                </div>
            </div>
        </div>
        
        
        <script>
            window.onload = (e)=>{
               
                document.querySelector("#name").innerText="Name: "+"${user.getName()}";
                document.querySelector("#username").innerText="Username: "+"${user.getUsername()}";
                
            };
            
            $("#btnUpdate").click(function(){
                window.location.href = "/profile?action=update";
            });
            
            $("#btnChangePass").click(function(){
                window.location.href = "/profile?action=changepassword";
            });
        </script>
    </body>
    
    
</html>

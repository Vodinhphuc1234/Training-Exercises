<%-- 
    Document   : ProfileChangingPassword
    Created on : May 9, 2022, 1:46:31 PM
    Author     : vodinhphuc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity=
              "sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" 
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    </head>
    <body class = "bg-dark">
        <div class="container col-6 mt-5">
            <form class = "border p-5 rounded bg-light">
                <div class="mb-3">
                    <label for="oldPassword" class="form-label">Enter your old password</label>
                    <label for="oldPassword" id="alertWrongPassword" class="form-label ms-5" style="color: red; display: none"> Your old password was wrong.</label>
                    <input type="password" class="form-control" id="oldPassword" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label ">Enter your new password</label>
                    <input type="password" class="form-control" id="newPassword" required>
                </div>
                <div class="mb-3">
                    <label for="againPassword" class="form-label">Enter new password again</label>
                    <label for="oldPassword" id="alertMatching" class="form-label ms-5" style="color: red; display: none"> Your new password was not matching.</label>
                    <input type="password" class="form-control" id="againPassword" required>
                </div>
                
                <button id="btnUpdate" type="submit" class="btn btn-primary">Update</button>
            </form>
        </div>
        
        <script>
            $('#oldPassword').change(function(e){
                var oldPassword = $('#oldPassword').val();
                
                $.ajax({
                    url: "api/profile?action=checkpassword",
                    method: "POST",
                    contentType:"text",
                    data: oldPassword,
                    success: function(e){
                        $('#alertWrongPassword').css('display','none');
                        document.querySelector('#btnUpdate').disabled=false;
                    },
                    error: function(e){
                        $('#alertWrongPassword').css('display','block');
                        document.querySelector('#btnUpdate').disabled=true;
                    }
                    
                });
            });
            $('#newPassword').change(function(e){
                var newPassword = $('#newPassword').val();
                var againPassword = $('#againPassword').val();
                
                console.log(newPassword);                
                console.log(againPassword);

                
                if (newPassword != againPassword){
                    $('#alertMatching').css('display','block');
                    document.querySelector('#btnUpdate').disabled=true;
                }
                else
                {
                    $('#alertMatching').css('display','none');
                    document.querySelector('#btnUpdate').disabled=false;
                }
            });
            $('#againPassword').change(function(e){
                var newPassword = $('#newPassword').val();
                var againPassword = $('#againPassword').val();
                
                console.log(newPassword);                
                console.log(againPassword);
                
                 if (newPassword != againPassword){
                    $('#alertMatching').css('display','block');
                    document.querySelector('#btnUpdate').disabled=true;
                }
                else
                {
                    $('#alertMatching').css('display','none');
                    document.querySelector('#btnUpdate').disabled=false;
                }
            });
            
            $('#btnUpdate').click(function(e){
                e.preventDefault();
                
                var user = {}
                
                user.id = ${user.getId()}
                user.name = "${use.getName()}";
                user.username = "${user.getUsername()}";
                user.password = $('#newPassword').val();
                
                $.ajax({
                    url: "api/profile?action=changepassword",
                    method: "PUT",
                    contentType: "application/json",
                    data: JSON.stringify(user),
                    success: function(){
                        window.location.href="/profile?action=view";
                    }
                });
                
            })
        </script>
        
    </body>
</html>

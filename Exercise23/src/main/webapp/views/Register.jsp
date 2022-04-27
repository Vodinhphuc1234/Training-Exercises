<%-- 
    Document   : Login
    Created on : Apr 13, 2022, 10:56:32 AM
    Author     : vodinhphuc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="<c:url value='/template/login/styles.css'/>" rel="stylesheet"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <h5 class="card-title text-center mb-5 fw-light fs-5">Sign Up</h5>
                            <form action="login" method="POST" id = "submitForm">
                                <div id="registerAlert" class="alert alert-danger" role="alert" style = "display: none">
                                    Username is existed !!!
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" name="name" id="floatingInput" placeholder="name" required>
                                    <label for="floatingInput">Name</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" name="username" id="floatingInput" placeholder="Username" required>
                                    <label for="floatingInput">Username</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password" required>
                                    <label for="floatingPassword">Password</label>
                                </div>

                                
                                <div class="d-grid">
                                    <button id="btnSubmit" class="btn btn-primary btn-login text-uppercase fw-bold">Sign
                                        up</button>
                                </div>
                                <hr class="my-4">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <script>
                $('#btnSubmit').click(function(e){
                    console.log("hello");
                    e.preventDefault();
                    var formData = $('#submitForm').serializeArray();
                    var data={};
                    $.each(formData, function (i, v) {
                        data[v.name]=v.value;
                    });
                    console.log(JSON.stringify(data))
                
                $.ajax({
                    url: 'profile',
                    method: "POST",
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function(r){
                        window.location.href="/";
                    },
                    error: function(e){
                        $('#registerAlert').css('display','block');
                    }
                });
            });
        </script>
    </body>
    
</html>

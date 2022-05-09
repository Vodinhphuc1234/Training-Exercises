<%-- 
    Document   : ProfileUpdating
    Created on : May 9, 2022, 10:55:46 AM
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
        <div class = "container col-6 mt-5">
            <form class = "border p-5 rounded bg-light" id = "submitForm">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input name="username" class="form-control" id="username" value="${user.getUsername()}" required>
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input name="name" class="form-control" id="name" value="${user.getName()}" required>
                </div>

                <button class="btn btn-primary" id = "btnUpdate">Update</button>
            </form>
        </div>

        <script>

            $('#btnUpdate').click(function (e) {
                e.preventDefault();
                var formData = $('#submitForm').serializeArray();
                let data = {};
                $.each(formData, function (i, v) {
                    data[v.name] = v.value;
                });
                data.id = ${user.getId()};
                data.password = "${user.getPassword()}";

                console.log(data);
                
                $.ajax({
                    url: 'api/profile',
                    method: "PUT",
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function(r){
                        window.location.href="/profile?action=view";
                    }
                });
            });

        </script>
    </body>



</html>

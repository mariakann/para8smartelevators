<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
	String email = request.getParameter("email");
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String confirm = request.getParameter("confirm");
	String terms = request.getParameter("terms");

    List<String> errors = new ArrayList<String>();

    if (name == null || name.trim().length() < 3) {
		errors.add("Name must be at least 3 characters long.");
	}

	if (surname == null || surname.trim().length() < 3) {
		errors.add("Surname must be at least 3 characters long.");
	}

	if (username == null || username.trim().length() < 5) {
		errors.add("Username must be at least 5 characters long.");
	}

	if (password == null || password.trim().length() < 6) {
		errors.add("Password must be at least 6 characters long.");
	}

	if (confirm == null || !confirm.equals(password)) {
		errors.add("Password and Confirm Password must match.");
	}

	if (terms == null) {
		errors.add("You must agree to the terms and conditions.");
	}
%>

<!DOCTYPE html>
<html lang="en"
<head>
<meta charset="UTF-8">
<title>Registration Result</title>

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
</head>

<body>
    <div class="container">

        <div class="jumbotron">
            <h1>1η Ατομική Άσκηση</h1>
        </div>

        <% if (!errors.isEmpty()) { %>
            <h3>Registration form has errors</h3>
            <hr>

            <div class="alert alert-danger">
                <ol>
                    <% for (String error : errors) { %>
                        <li><%=error%></li>
                   <% } %>
                </ol>
            </div>

            <a href="register_ex1_8240148.jsp"
            class="btn btn-info">
            &lt; Back to the form
            </a>

     <%   } else { %>
        <h3>Registration almost done!</h3>
        <hr>

        <div class="alert alert-success">
            Note: A verification link has been sent to the email: <%= email %>
        </div>

        <p>Name: <%= name %></p>
        <p>Surname: <%= surname %></p>
        <p>Email: <%= email %></p>
        <p>Username: <%= username %></p>

    <% } %>
    </div>
</body>
</html>

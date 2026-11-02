<!DOCTYPE html>
<html lang="en">
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta name="description" content="Lab exercise template 2019-2020">
	<meta name="author" content="sofos@aueb.gr">
	<link rel="icon" href="../images/favicon.ico">

	<title>1η Εργαστηριακη Άσκηση</title>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="../css/bootstrap.min.css">	
	<!-- Bootstrap Optional theme -->
	<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
	<!-- Custom styles for this template -->
	<link href="../css/theme_8240148.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	</head>
	
	<body>

		<!-- Fixed navbar -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span> <span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">8XXXXXX</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li><a href="../index_8240148.html">Home</a></li>
						<li class="active"><a href="../register_8240148.html">Register</a></li>					
										
						<li><a href="../about_8240148.html">About</a></li>					
					</ul>		
		
				</div>
				
				<!--/.nav-collapse -->
			</div>
		</nav>


        <div class="container">
            <h1>1η Ατομική Άσκηση</h1>
        </div>
    

        <h3>Registration Form</h3>
        <hr>

        <div class="alert alert-warning text-center">
            Please fill in the following form to create an account.
        </div>

        <form class="form-horizontal"
        method="post"
        action="../registerController_ex1_8240148.jsp">

        <div class="form-group">
            <label class="control-label col-sm-2">Name</label>
            <div class="col-sm-6">
                <input type="text" name="name" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Surname</label>
            <div class="col-sm-6">
                <input type="text" name="surname" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Email</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Username</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Password</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Confirm</label>
            <div class="col-sm-6">
                <input type="password" name="confirm" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-6">
                <label>
                    <input type="checkbox" name="terms" value="yes">
                    I agree to the terms and conditions.
                </label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-6">
                <button type="submit" class="btn btn-success">
                Submit
            </button>

            <button type="reset" class="btn btn-danger">
                Cancel
            </button>

            </div>
        </div>
    </form>
</div>


		

		<div class="container theme-showcase" role="main">

			
		</div>
	</body>

</html>
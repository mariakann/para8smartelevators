<!DOCTYPE html>
<html lang="en">  
    <head>
        <meta charset="UTF-8">
        <title>Registration Form</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
    </head>
    <body>
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
        action="registerrController_ex1_8240148.jsp">

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
</body>
</html>


        

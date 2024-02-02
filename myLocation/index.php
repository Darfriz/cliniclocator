<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Login</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        max-width: 400px;
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    }

    h2 {
        margin-top: 0;
        text-align: center;
    }

    form {
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 5px;
    }

    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    button {
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
    }

    button:hover {
        background-color: #45a049;
    }

    .error {
        color: red;
        margin-bottom: 10px;
        text-align: center;
    }

    .register-link {
        text-align: center;
        margin-top: 10px;
    }

    .register-link a {
        color: #007bff;
        text-decoration: none;
    }

    .register-link a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>User Login</h2>
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            // Database connection parameters
            $hostname = "localhost"; // Change this to your database hostname
            $username = "root"; // Change this to your database username
            $password = ""; // Change this to your database password
            $database = "location"; // Change this to your database name

            // Connect to the database
            $conn = new mysqli($hostname, $username, $password, $database);

            // Check connection
            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            // Get user credentials from the form
            $username = $_POST['username'];
            $password = $_POST['password'];

            // SQL query to retrieve user from the 'different_table' table
            $sql = "SELECT * FROM users WHERE username='$username' AND password='$password'";
            $result = $conn->query($sql);

            if ($result->num_rows > 0) {
                // User authenticated successfully
                // Redirect to position_report.php
                header("Location: position_report.php");
                exit; // Stop further execution
            } else {
                // Authentication failed
                $error_message = "Invalid username or password. Please try again.";
            }

            $conn->close(); // Close database connection
        }
        ?>
        <form method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" required>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
            <button type="submit">Login</button>
            <div class="error"><?php if(isset($error_message)) { echo $error_message; } ?></div>
        </form>
        <div class="register-link">
            <a href="register.php">Don't have an account? Click here</a>
        </div>
    </div>
</body>
</html>

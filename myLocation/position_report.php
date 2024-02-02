<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Position Reports</title>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    .toolbox {
        margin-top: 20px;
        text-align: center;
    }

    .logout-btn {
        padding: 10px 20px;
        background-color: #ff0000;
        color: #fff;
        border: none;
        cursor: pointer;
        border-radius: 5px;
    }
</style>
</head>
<body>
    <h2>Position Reports</h2>
    <table>
        <tr>
            <th>No</th>
            <th>Name</th>
            <th>Time</th>
            <th>User Agent</th>
            <th>GPS Coordinate</th>
        </tr>
        <?php
        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "location";

        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);

        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        // SQL query to retrieve data from the 'data' table
        $sql = "SELECT `No`, `username`, `Time`, `User_Agent`, `lat`, `lng` FROM data";
        $result = $conn->query($sql);

        // Check for SQL errors
        if ($result === false) {
            die("Error executing query: " . $conn->error);
        }

        if ($result->num_rows > 0) {
            // Output data in a table
            while($row = $result->fetch_assoc()) {
                echo "<tr>";
                echo "<td>" . $row["No"] . "</td>";
                echo "<td>" . $row["username"] . "</td>";
                echo "<td>" . $row["Time"] . "</td>";
                echo "<td>" . $row["User_Agent"] . "</td>";
                echo "<td>" . $row["lat"] . "</td>";
                echo "<td>" . $row["lng"] . "</td>";
                echo "</tr>";
            }
        } else {
            echo "<tr><td colspan='5'>No data found.</td></tr>";
        }

        $conn->close();
        ?>
    </table>

    <div class="toolbox">
        <button class="logout-btn" onclick="logout()">Logout</button>
    </div>

    <script>
        function logout() {
            var confirmLogout = confirm("Are you sure you want to log out?");
            if (confirmLogout) {
                window.location.href = "index.php";
            }
        }
    </script>
</body>
</html>

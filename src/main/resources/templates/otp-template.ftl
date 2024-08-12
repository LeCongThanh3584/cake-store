<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>OTP Verification - SWEET_CAKE</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #dddddd;
            border-radius: 5px;
        }
        .header {
            text-align: center;
            padding: 20px 0;
        }
        .header img {
            max-width: 150px;
        }
        .content {
            text-align: center;
        }
        .otp {
            font-size: 24px;
            font-weight: bold;
            color: #ff6f61;
            margin: 20px 0;
        }
        .message {
            font-size: 16px;
            color: #333333;
        }
        .footer {
            text-align: center;
            padding: 20px 0;
            font-size: 12px;
            color: #999999;
        }
        .footer a {
            color: #ff6f61;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <img src="/../webapp/img/footer-logo.png">
    </div>
    <div class="content">
        <h2>Hello, ${name}!</h2>
        <p class="message">Thank you for shopping with SWEET_CAKE. To complete your request, please use the following OTP:</p>
        <div class="otp">${otp}</div>
        <p class="message">This OTP is valid for the next 1 minutes. Please do not share this OTP with anyone.</p>
    </div>
    <div class="footer">
        <p>If you did not request this OTP, please ignore this email or contact our support team.</p>
        <p>© 2024 SWEET_CAKE. All rights reserved.</p>
        <p><a href="https://example.com">Visit our website</a></p>
    </div>
</div>
</body>
</html>

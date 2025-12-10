<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>@yield('title', 'Task Manager') - Admin Dashboard</title>

    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root {
            --primary: #ec4899;
            --primary-dark: #db2777;
            --primary-light: #f472b6;
            --secondary: #f97316;
            --success: #10b981;
            --danger: #ef4444;
            --warning: #f59e0b;
            --dark: #0a0a0a;
            --light: #1a1a1a;
            --white: #ffffff;
            --gray: #9ca3af;
            --gray-light: #374151;
            --shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
            --shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.3);
            --shadow-lg: 0 20px 60px rgba(0, 0, 0, 0.6);
            --border-radius: 16px;
            --border-radius-sm: 12px;
            --transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #000000 0%, #1a1a1a 100%);
            min-height: 100vh;
            color: #ffffff;
            line-height: 1.6;
        }

        .bg-pattern {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background:
                radial-gradient(circle at 20% 50%, rgba(236, 72, 153, 0.15), transparent 50%),
                radial-gradient(circle at 80% 80%, rgba(249, 115, 22, 0.1), transparent 50%),
                radial-gradient(circle at 40% 20%, rgba(236, 72, 153, 0.1), transparent 50%);
            z-index: 0;
            animation: bgFloat 20s ease-in-out infinite;
        }

        @keyframes bgFloat {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.7; }
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes slideIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .fade-in {
            animation: fadeIn 0.5s ease-out;
        }
    </style>

    @yield('styles')
</head>
<body>
    <div class="bg-pattern"></div>

    @yield('content')

    @yield('scripts')
</body>
</html>

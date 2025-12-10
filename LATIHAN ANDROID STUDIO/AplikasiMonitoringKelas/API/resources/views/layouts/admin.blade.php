<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>@yield('title', 'Dashboard') - SMKN 2 Buduran</title>

    <!-- Preconnect untuk CDN -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="preconnect" href="https://cdnjs.cloudflare.com">

    <!-- Fonts dengan display=swap untuk performa -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome dengan defer -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" media="print" onload="this.media='all'">

    <style>
        :root {
            --primary: #ec4899;
            --primary-dark: #db2777;
            --primary-light: #f472b6;
            --secondary: #f97316;
            --success: #10b981;
            --danger: #ef4444;
            --warning: #f59e0b;
            --info: #3b82f6;
            --dark: #0a0a0a;
            --light: #1a1a1a;
            --white: #ffffff;
            --gray: #9ca3af;
            --gray-light: #374151;
            --gray-dark: #1f2937;
            --sidebar-width: 280px;
            --topbar-height: 70px;
            --shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            --shadow-lg: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
            --transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background: #000000;
            color: #ffffff;
            line-height: 1.6;
        }

        /* Sidebar */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: var(--sidebar-width);
            height: 100vh;
            background: linear-gradient(180deg, #0a0a0a 0%, #000000 100%);
            color: white;
            z-index: 1000;
            overflow-y: auto;
            box-shadow: var(--shadow-lg);
            border-right: 1px solid rgba(236, 72, 153, 0.3);
        }

        .sidebar::-webkit-scrollbar {
            width: 6px;
        }

        .sidebar::-webkit-scrollbar-track {
            background: rgba(255, 255, 255, 0.1);
        }

        .sidebar::-webkit-scrollbar-thumb {
            background: rgba(255, 255, 255, 0.2);
            border-radius: 3px;
        }

        .sidebar-brand {
            padding: 25px 20px;
            border-bottom: 1px solid rgba(236, 72, 153, 0.3);
        }

        .brand-content {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .brand-icon {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
        }

        .brand-text h2 {
            font-size: 18px;
            font-weight: 800;
            margin-bottom: 2px;
        }

        .brand-text p {
            font-size: 11px;
            opacity: 0.7;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .sidebar-menu {
            padding: 20px 0;
        }

        .menu-section {
            margin-bottom: 25px;
        }

        .menu-label {
            padding: 0 20px 10px;
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            opacity: 0.5;
            font-weight: 700;
        }

        .menu-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 20px;
            color: rgba(255, 255, 255, 0.8);
            text-decoration: none;
            transition: var(--transition);
            position: relative;
        }

        .menu-item:hover {
            background: rgba(255, 255, 255, 0.05);
            color: white;
        }

        .menu-item.active {
            background: linear-gradient(90deg, rgba(99, 102, 241, 0.2), transparent);
            color: white;
            border-left: 3px solid var(--primary);
        }

        .menu-item.active::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 3px;
            background: var(--primary);
        }

        .menu-item i {
            font-size: 18px;
            width: 24px;
            text-align: center;
        }

        .menu-item span {
            font-size: 14px;
            font-weight: 500;
        }

        /* Main Content */
        .main-content {
            margin-left: var(--sidebar-width);
            min-height: 100vh;
        }

        /* Topbar */
        .topbar {
            height: var(--topbar-height);
            background: #0a0a0a;
            border-bottom: 1px solid rgba(236, 72, 153, 0.3);
            padding: 0 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: sticky;
            top: 0;
            z-index: 999;
            box-shadow: var(--shadow);
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .breadcrumb {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            color: var(--gray);
        }

        .breadcrumb i {
            font-size: 12px;
        }

        .topbar-right {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .topbar-user {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 16px;
        }

        .user-info {
            text-align: right;
        }

        .user-info strong {
            display: block;
            font-size: 14px;
            color: #ffffff;
        }

        .user-info small {
            font-size: 12px;
            color: var(--gray);
        }

        .btn-logout {
            padding: 8px 16px;
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger);
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: var(--transition);
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .btn-logout:hover {
            background: var(--danger);
            color: white;
        }

        /* Page Content */
        .page-content {
            padding: 30px;
        }

        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .page-title {
            font-size: 28px;
            font-weight: 800;
            color: #ffffff;
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 5px;
        }

        .page-title i {
            color: var(--primary);
        }

        .page-subtitle {
            color: var(--gray);
            font-size: 14px;
        }

        /* Stats Grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: #0a0a0a;
            border-radius: 16px;
            padding: 25px;
            display: flex;
            align-items: center;
            gap: 20px;
            box-shadow: var(--shadow);
            transition: var(--transition);
            border-left: 4px solid;
            border: 1px solid rgba(236, 72, 153, 0.2);
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-lg);
        }

        .stat-card.blue { border-left-color: #ec4899; }
        .stat-card.green { border-left-color: #10b981; }
        .stat-card.orange { border-left-color: #f59e0b; }
        .stat-card.purple { border-left-color: #ec4899; }

        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            color: white;
        }

        .stat-card.blue .stat-icon { background: linear-gradient(135deg, #ec4899, #db2777); }
        .stat-card.green .stat-icon { background: linear-gradient(135deg, #10b981, #059669); }
        .stat-card.orange .stat-icon { background: linear-gradient(135deg, #f59e0b, #d97706); }
        .stat-card.purple .stat-icon { background: linear-gradient(135deg, #ec4899, #db2777); }

        .stat-details {
            flex: 1;
        }

        .stat-number {
            font-size: 32px;
            font-weight: 800;
            color: #ffffff;
            line-height: 1;
            margin-bottom: 5px;
        }

        .stat-label {
            font-size: 14px;
            color: var(--gray);
            font-weight: 500;
        }

        /* Content Grid */
        .content-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
            gap: 20px;
        }

        .data-card {
            background: #0a0a0a;
            border-radius: 16px;
            box-shadow: var(--shadow);
            overflow: hidden;
            border: 1px solid rgba(236, 72, 153, 0.2);
        }

        .card-header {
            padding: 20px 25px;
            border-bottom: 1px solid var(--gray-light);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .card-title {
            font-size: 18px;
            font-weight: 700;
            color: #ffffff;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .card-title i {
            color: var(--primary);
        }

        .btn-link {
            color: var(--primary);
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            transition: var(--transition);
        }

        .btn-link:hover {
            color: var(--primary-dark);
        }

        .card-body {
            padding: 25px;
        }

        /* Table */
        .table-responsive {
            overflow-x: auto;
        }

        .data-table {
            width: 100%;
            border-collapse: collapse;
        }

        .data-table thead {
            background: rgba(236, 72, 153, 0.1);
        }

        .data-table th {
            padding: 12px;
            text-align: left;
            font-size: 13px;
            font-weight: 700;
            color: #ffffff;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .data-table td {
            padding: 12px;
            border-top: 1px solid var(--gray-light);
            font-size: 14px;
            color: #ffffff;
        }

        .data-table tbody tr:hover {
            background: rgba(236, 72, 153, 0.1);
        }

        /* Badge */
        .badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .badge-primary { background: rgba(236, 72, 153, 0.2); color: var(--primary); }
        .badge-blue { background: rgba(59, 130, 246, 0.1); color: #3b82f6; }
        .badge-pink { background: rgba(236, 72, 153, 0.1); color: #ec4899; }
        .badge-danger { background: rgba(239, 68, 68, 0.1); color: #ef4444; }
        .badge-success { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .badge-warning { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }
        .badge-secondary { background: rgba(107, 114, 128, 0.1); color: #6b7280; }

        /* List Group */
        .list-group {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .list-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 15px;
            background: rgba(236, 72, 153, 0.05);
            border-radius: 12px;
            transition: var(--transition);
            border: 1px solid rgba(236, 72, 153, 0.1);
        }

        .list-item:hover {
            background: rgba(236, 72, 153, 0.1);
            border-color: var(--primary);
        }

        .list-avatar {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 700;
            font-size: 18px;
        }

        .list-details {
            flex: 1;
        }

        .list-details h4 {
            font-size: 15px;
            font-weight: 600;
            color: #ffffff;
            margin-bottom: 3px;
        }

        .list-details p {
            font-size: 13px;
            color: var(--gray);
        }

        /* Empty State */
        .empty-state-small {
            text-align: center;
            padding: 40px 20px;
            color: var(--gray);
        }

        .empty-state-small i {
            font-size: 48px;
            margin-bottom: 15px;
            opacity: 0.3;
        }

        .empty-state-small p {
            font-size: 14px;
        }

        /* Responsive */
        @media (max-width: 1024px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 768px) {
            .sidebar {
                transform: translateX(-100%);
            }

            .sidebar.active {
                transform: translateX(0);
            }

            .main-content {
                margin-left: 0;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }

            .topbar {
                padding: 0 15px;
            }

            .page-content {
                padding: 20px 15px;
            }
        }
    </style>

    @yield('styles')
</head>
<body>
    <!-- Sidebar -->
    <aside class="sidebar">
        <div class="sidebar-brand">
            <div class="brand-content">
                <div class="brand-icon" style="background: transparent;">
                    <img src="/images/logo_smenda.png" alt="Logo SMENDA" style="width: 100%; height: 100%; object-fit: contain; border-radius: 12px;" onerror="this.style.display='none'; this.nextElementSibling.style.display='block';">
                    <i class="fas fa-graduation-cap" style="display: none;"></i>
                </div>
                <div class="brand-text">
                    <h2>SMKN 2 Buduran</h2>
                    <p>Admin Panel</p>
                </div>
            </div>
        </div>

        <nav class="sidebar-menu">
            <div class="menu-section">
                <div class="menu-label">Main Menu</div>
                <a href="{{ route('dashboard') }}" class="menu-item {{ request()->routeIs('dashboard') ? 'active' : '' }}">
                    <i class="fas fa-home"></i>
                    <span>Dashboard</span>
                </a>
            </div>

            <div class="menu-section">
                <div class="menu-label">Data Master</div>
                <a href="{{ route('manage-users.index') }}" class="menu-item {{ request()->routeIs('manage-users.*') ? 'active' : '' }}">
                    <i class="fas fa-users"></i>
                    <span>Manage Users</span>
                </a>
                <a href="{{ route('guru.index') }}" class="menu-item {{ request()->routeIs('guru.*') ? 'active' : '' }}">
                    <i class="fas fa-chalkboard-teacher"></i>
                    <span>Data Guru</span>
                </a>
                <a href="{{ route('kelas.index') }}" class="menu-item {{ request()->routeIs('kelas.*') ? 'active' : '' }}">
                    <i class="fas fa-school"></i>
                    <span>Data Kelas</span>
                </a>
            </div>

            <div class="menu-section">
                <div class="menu-label">Akademik</div>
                <a href="{{ route('jadwal.index') }}" class="menu-item {{ request()->routeIs('jadwal.*') ? 'active' : '' }}">
                    <i class="fas fa-calendar-alt"></i>
                    <span>Jadwal Pelajaran</span>
                </a>
                <a href="{{ route('teacher-attendance.index') }}" class="menu-item {{ request()->routeIs('teacher-attendance.*') ? 'active' : '' }}">
                    <i class="fas fa-clipboard-check"></i>
                    <span>Kehadiran Guru</span>
                </a>
                <a href="{{ route('guru-pengganti.index') }}" class="menu-item {{ request()->routeIs('guru-pengganti.*') ? 'active' : '' }}">
                    <i class="fas fa-exchange-alt"></i>
                    <span>Guru Pengganti</span>
                </a>
            </div>
        </nav>
    </aside>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Topbar -->
        <div class="topbar">
            <div class="topbar-left">
                <div class="breadcrumb">
                    <i class="fas fa-home"></i>
                    <span>/ @yield('title', 'Dashboard')</span>
                </div>
            </div>

            <div class="topbar-right">
                <div class="topbar-user">
                    <div class="user-avatar">
                        {{ strtoupper(substr(Auth::user()->email, 0, 1)) }}
                    </div>
                    <div class="user-info">
                        <strong>{{ Auth::user()->nama ?? Auth::user()->email }}</strong>
                        <small><i class="fas fa-crown"></i> Administrator</small>
                    </div>
                </div>
                <form action="{{ route('logout') }}" method="POST">
                    @csrf
                    <button type="submit" class="btn-logout">
                        <i class="fas fa-sign-out-alt"></i>
                        Logout
                    </button>
                </form>
            </div>
        </div>

        <!-- Page Content -->
        <div class="page-content">
            @yield('content')
        </div>
    </div>

    @yield('scripts')
</body>
</html>

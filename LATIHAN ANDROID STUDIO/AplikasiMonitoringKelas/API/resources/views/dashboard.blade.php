@extends('layouts.admin')

@section('title', 'Dashboard')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-chart-line"></i>
            Dashboard Overview
        </h1>
        <p class="page-subtitle">Welcome back! Here's what's happening in your school today.</p>
    </div>
</div>

<!-- Stats Cards -->
<div class="stats-grid">
    <div class="stat-card blue">
        <div class="stat-icon">
            <i class="fas fa-users"></i>
        </div>
        <div class="stat-details">
            <div class="stat-number">{{ $total_users }}</div>
            <div class="stat-label">Total Users</div>
        </div>
    </div>

    <div class="stat-card green">
        <div class="stat-icon">
            <i class="fas fa-chalkboard-teacher"></i>
        </div>
        <div class="stat-details">
            <div class="stat-number">{{ $total_guru }}</div>
            <div class="stat-label">Total Teachers</div>
        </div>
    </div>

    <div class="stat-card orange">
        <div class="stat-icon">
            <i class="fas fa-school"></i>
        </div>
        <div class="stat-details">
            <div class="stat-number">{{ $total_kelas }}</div>
            <div class="stat-label">Total Classes</div>
        </div>
    </div>

    <div class="stat-card purple">
        <div class="stat-icon">
            <i class="fas fa-calendar-alt"></i>
        </div>
        <div class="stat-details">
            <div class="stat-number">{{ $total_jadwal }}</div>
            <div class="stat-label">Scheduled Lessons</div>
        </div>
    </div>
</div>

<!-- Teacher Attendance Today -->
<div class="glass-card" style="margin-bottom: 2rem; animation: fadeInUp 0.6s ease-out 0.3s both;">
    <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
        <h2 class="card-title" style="font-size: 1.5rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
            <i class="fas fa-clipboard-check" style="color: #ec4899;"></i>
            Kehadiran Guru Hari Ini
        </h2>
        <a href="{{ route('teacher-attendance.index') }}" class="btn-modern btn-modern-sm">
            <i class="fas fa-arrow-right"></i> Lihat Detail
        </a>
    </div>
    <div class="card-body">
        <div class="attendance-stats" style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 1.25rem;">
            <div class="attendance-stat-card" style="background: linear-gradient(135deg, rgba(16, 185, 129, 0.15), rgba(5, 150, 105, 0.15)); border-left: 4px solid #10b981; padding: 1.25rem; border-radius: 12px; backdrop-filter: blur(10px); transition: all 0.3s ease;">
                <div style="display: flex; align-items: center; gap: 1rem;">
                    <div style="width: 48px; height: 48px; background: linear-gradient(135deg, #10b981, #059669); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);">
                        <i class="fas fa-check-circle" style="font-size: 20px;"></i>
                    </div>
                    <div>
                        <div style="font-size: 2rem; font-weight: 800; color: #10b981;">{{ $attendance_stats['hadir'] }}</div>
                        <div style="font-size: 0.85rem; color: #9ca3af; font-weight: 600;">Hadir</div>
                    </div>
                </div>
            </div>
            <div class="attendance-stat-card" style="background: linear-gradient(135deg, rgba(239, 68, 68, 0.15), rgba(220, 38, 38, 0.15)); border-left: 4px solid #ef4444; padding: 1.25rem; border-radius: 12px; backdrop-filter: blur(10px); transition: all 0.3s ease;">
                <div style="display: flex; align-items: center; gap: 1rem;">
                    <div style="width: 48px; height: 48px; background: linear-gradient(135deg, #ef4444, #dc2626); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; box-shadow: 0 4px 12px rgba(239, 68, 68, 0.3);">
                        <i class="fas fa-times-circle" style="font-size: 20px;"></i>
                    </div>
                    <div>
                        <div style="font-size: 2rem; font-weight: 800; color: #ef4444;">{{ $attendance_stats['tidak_hadir'] }}</div>
                        <div style="font-size: 0.85rem; color: #9ca3af; font-weight: 600;">Tidak Hadir</div>
                    </div>
                </div>
            </div>
            <div class="attendance-stat-card" style="background: linear-gradient(135deg, rgba(245, 158, 11, 0.15), rgba(217, 119, 6, 0.15)); border-left: 4px solid #f59e0b; padding: 1.25rem; border-radius: 12px; backdrop-filter: blur(10px); transition: all 0.3s ease;">
                <div style="display: flex; align-items: center; gap: 1rem;">
                    <div style="width: 48px; height: 48px; background: linear-gradient(135deg, #f59e0b, #d97706); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);">
                        <i class="fas fa-clock" style="font-size: 20px;"></i>
                    </div>
                    <div>
                        <div style="font-size: 2rem; font-weight: 800; color: #f59e0b;">{{ $attendance_stats['terlambat'] }}</div>
                        <div style="font-size: 0.85rem; color: #9ca3af; font-weight: 600;">Terlambat</div>
                    </div>
                </div>
            </div>
            <div class="attendance-stat-card" style="background: linear-gradient(135deg, rgba(236, 72, 153, 0.15), rgba(168, 85, 247, 0.15)); border-left: 4px solid #ec4899; padding: 1.25rem; border-radius: 12px; backdrop-filter: blur(10px); transition: all 0.3s ease;">
                <div style="display: flex; align-items: center; gap: 1rem;">
                    <div style="width: 48px; height: 48px; background: linear-gradient(135deg, #ec4899, #a855f7); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; box-shadow: 0 4px 12px rgba(236, 72, 153, 0.3);">
                        <i class="fas fa-envelope" style="font-size: 20px;"></i>
                    </div>
                    <div>
                        <div style="font-size: 2rem; font-weight: 800; color: #ec4899;">{{ $attendance_stats['izin'] }}</div>
                        <div style="font-size: 0.85rem; color: #9ca3af; font-weight: 600;">Izin</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Data Overview -->
<div class="content-grid">
    <!-- Recent Schedules -->
    <div class="glass-card" style="animation: fadeInUp 0.6s ease-out 0.4s both;">
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
            <h3 class="card-title" style="font-size: 1.25rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
                <i class="fas fa-calendar-week" style="color: #ec4899;"></i>
                Recent Schedules
            </h3>
            <a href="{{ route('jadwal.index') }}" class="btn-link" style="color: #ec4899; font-weight: 600; display: flex; align-items: center; gap: 0.5rem; transition: all 0.3s ease;">
                View All <i class="fas fa-arrow-right"></i>
            </a>
        </div>
        <div class="card-body">
            @if($recent_jadwal->count() > 0)
                <div class="table-responsive">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Teacher</th>
                                <th>Class</th>
                                <th>Day</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            @foreach($recent_jadwal as $jadwal)
                            <tr>
                                <td><strong>{{ $jadwal->mata_pelajaran }}</strong></td>
                                <td>{{ $jadwal->guru->nama }}</td>
                                <td><span class="badge badge-primary">{{ $jadwal->kelas->nama_kelas }}</span></td>
                                <td><span class="badge badge-blue">{{ $jadwal->hari }}</span></td>
                                <td>{{ substr($jadwal->jam_mulai, 0, 5) }} - {{ substr($jadwal->jam_selesai, 0, 5) }}</td>
                            </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div>
            @else
                <div class="empty-state-small">
                    <i class="fas fa-calendar-times"></i>
                    <p>No schedules found. Start by adding a new schedule!</p>
                </div>
            @endif
        </div>
    </div>

    <!-- Recent Teachers -->
    <div class="glass-card" style="animation: fadeInUp 0.6s ease-out 0.5s both;">
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
            <h3 class="card-title" style="font-size: 1.25rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
                <i class="fas fa-user-tie" style="color: #ec4899;"></i>
                Recent Teachers
            </h3>
            <a href="{{ route('guru.index') }}" class="btn-link" style="color: #ec4899; font-weight: 600; display: flex; align-items: center; gap: 0.5rem; transition: all 0.3s ease;">
                View All <i class="fas fa-arrow-right"></i>
            </a>
        </div>
        <div class="card-body">
            @if($recent_guru->count() > 0)
                <div class="list-group">
                    @foreach($recent_guru as $guru)
                    <div class="list-item" style="padding: 1rem; background: rgba(10, 10, 10, 0.4); border: 1px solid rgba(236, 72, 153, 0.2); border-radius: 12px; margin-bottom: 0.75rem; display: flex; align-items: center; gap: 1rem; transition: all 0.3s ease; backdrop-filter: blur(10px);">
                        <div class="list-avatar" style="width: 50px; height: 50px; background: linear-gradient(135deg, #ec4899, #a855f7); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; font-size: 1.25rem; font-weight: 700; box-shadow: 0 4px 12px rgba(236, 72, 153, 0.3);">
                            {{ strtoupper(substr($guru->nama, 0, 1)) }}
                        </div>
                        <div class="list-details" style="flex: 1;">
                            <h4 style="font-size: 1rem; font-weight: 700; color: #ffffff; margin-bottom: 0.25rem;">{{ $guru->nama }}</h4>
                            <p style="font-size: 0.85rem; color: #9ca3af; margin: 0;">
                                <i class="fas fa-id-card" style="color: #ec4899;"></i> NIP: {{ $guru->nip }} |
                                <i class="fas fa-book" style="color: #a855f7;"></i> {{ $guru->mata_pelajaran }}
                            </p>
                        </div>
                        <span class="badge" style="background: linear-gradient(135deg, rgba(236, 72, 153, 0.2), rgba(168, 85, 247, 0.2)); color: #ec4899; padding: 0.5rem 1rem; border-radius: 8px; font-weight: 600; border: 1px solid rgba(236, 72, 153, 0.3);">{{ $guru->jenis_kelamin }}</span>
                    </div>
                    @endforeach
                </div>
            @else
                <div class="empty-state-small">
                    <i class="fas fa-user-times"></i>
                    <p>No teachers found. Start by adding a new teacher!</p>
                </div>
            @endif
        </div>
    </div>
</div>
@endsection

@extends('layouts.admin')

@section('title', 'Data Guru')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-chalkboard-teacher"></i>
            Data Guru (Teachers)
        </h1>
        <p class="page-subtitle">Manage teachers information and assignments</p>
    </div>
    <a href="{{ route('guru.create') }}" class="btn-primary">
        <i class="fas fa-plus"></i> Add New Teacher
    </a>
</div>

@if(session('success'))
<div class="alert" style="padding: 18px 22px; border-radius: 14px; margin-bottom: 24px; display: flex; align-items: center; gap: 14px; animation: slideInDown 0.4s ease-out; font-size: 14px; font-weight: 600; backdrop-filter: blur(10px); background: rgba(16, 185, 129, 0.15); color: #6ee7b7; border: 2px solid rgba(16, 185, 129, 0.5); box-shadow: 0 4px 16px rgba(16, 185, 129, 0.2);">
    <i class="fas fa-check-circle" style="font-size: 20px; color: #10b981;"></i>
    {{ session('success') }}
</div>
@endif

<div class="glass-card">
    <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
        <h3 class="card-title" style="font-size: 1.25rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;">
            <i class="fas fa-list" style="color: #ec4899;"></i>
            Teachers List
        </h3>
        <div class="search-box" style="position: relative;">
            <i class="fas fa-search" style="position: absolute; left: 16px; top: 50%; transform: translateY(-50%); color: #9ca3af; font-size: 14px;"></i>
            <input type="text" id="searchInput" placeholder="Search teachers..." style="padding: 12px 16px 12px 42px; border: 2px solid rgba(236, 72, 153, 0.2); border-radius: 12px; background: rgba(10, 10, 10, 0.6); color: #ffffff; font-size: 14px; transition: all 0.3s ease; backdrop-filter: blur(10px); min-width: 300px;">
        </div>
    </div>
    <div class="card-body">
        @if($gurus->count() > 0)
            <div class="table-responsive">
                <table class="data-table" id="guruTable">
                    <thead>
                        <tr>
                            <th>NIP</th>
                            <th>Name</th>
                            <th>Subject</th>
                            <th>Gender</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach($gurus as $guru)
                        <tr>
                            <td><span class="badge badge-primary">{{ $guru->nip }}</span></td>
                            <td>
                                <div style="display: flex; align-items: center; gap: 10px;">
                                    <div class="list-avatar" style="width: 35px; height: 35px; font-size: 14px;">
                                        {{ strtoupper(substr($guru->nama, 0, 1)) }}
                                    </div>
                                    <strong>{{ $guru->nama }}</strong>
                                </div>
                            </td>
                            <td><span class="badge badge-info"><i class="fas fa-book"></i> {{ $guru->mata_pelajaran }}</span></td>
                            <td>
                                @if($guru->jenis_kelamin == 'Laki-laki')
                                    <span class="badge badge-blue"><i class="fas fa-mars"></i> {{ $guru->jenis_kelamin }}</span>
                                @else
                                    <span class="badge badge-pink"><i class="fas fa-venus"></i> {{ $guru->jenis_kelamin }}</span>
                                @endif
                            </td>
                            <td>{{ $guru->email }}</td>
                            <td><i class="fas fa-phone"></i> {{ $guru->telepon }}</td>
                            <td>
                                <div class="action-buttons">
                                    <a href="{{ route('guru.edit', $guru->id) }}" class="btn-action btn-edit" title="Edit">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <form action="{{ route('guru.destroy', $guru->id) }}" method="POST" style="display: inline;">
                                        @csrf
                                        @method('DELETE')
                                        <button type="submit" class="btn-action btn-delete" onclick="return confirm('Are you sure you want to delete this teacher?')" title="Delete">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        @endforeach
                    </tbody>
                </table>
            </div>

            <div class="pagination-wrapper">
                {{ $gurus->links() }}
            </div>
        @else
            <div class="empty-state">
                <i class="fas fa-user-tie"></i>
                <h3>No Teachers Found</h3>
                <p>Start by adding your first teacher to the system</p>
                <a href="{{ route('guru.create') }}" class="btn-primary">
                    <i class="fas fa-plus"></i> Add New Teacher
                </a>
            </div>
        @endif
    </div>
</div>

<style>
.btn-primary {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 14px 28px;
    background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
    color: white;
    border: none;
    border-radius: 12px;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
    text-decoration: none;
    transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
    box-shadow: 0 8px 24px rgba(236, 72, 153, 0.4),
                0 4px 12px rgba(168, 85, 247, 0.2);
    letter-spacing: 0.5px;
}

.btn-primary:hover {
    transform: translateY(-3px) scale(1.02);
    box-shadow: 0 12px 32px rgba(236, 72, 153, 0.5),
                0 6px 16px rgba(168, 85, 247, 0.3),
                0 0 0 4px rgba(236, 72, 153, 0.2);
}

.search-box input:focus {
    outline: none;
    border-color: var(--primary);
    box-shadow: 0 0 0 4px rgba(236, 72, 153, 0.15),
                0 8px 24px rgba(236, 72, 153, 0.2);
}

.action-buttons {
    display: flex;
    gap: 10px;
}

.btn-action {
    width: 38px;
    height: 38px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
    font-size: 15px;
    backdrop-filter: blur(10px);
}

.btn-edit {
    background: rgba(59, 130, 246, 0.15);
    color: #60a5fa;
    border: 1px solid rgba(59, 130, 246, 0.3);
}

.btn-edit:hover {
    background: linear-gradient(135deg, #3b82f6, #2563eb);
    color: white;
    transform: translateY(-2px) scale(1.05);
    box-shadow: 0 8px 16px rgba(59, 130, 246, 0.4);
}
    transform: translateY(-2px);
}

.btn-delete {
    background: rgba(239, 68, 68, 0.1);
    color: var(--danger);
}

.btn-delete:hover {
    background: var(--danger);
    color: white;
    transform: translateY(-2px);
}

.badge-info {
    background: rgba(99, 102, 241, 0.1);
    color: var(--primary);
}

.empty-state {
.btn-delete {
    background: rgba(239, 68, 68, 0.15);
    color: #f87171;
    border: 1px solid rgba(239, 68, 68, 0.3);
}

.btn-delete:hover {
    background: linear-gradient(135deg, #ef4444, #dc2626);
    color: white;
    transform: translateY(-2px) scale(1.05);
    box-shadow: 0 8px 16px rgba(239, 68, 68, 0.4);
}

.empty-state {
    text-align: center;
    padding: 80px 20px;
    animation: fadeInUp 0.6s ease-out;
}

.empty-state i {
    font-size: 100px;
    background: linear-gradient(135deg, var(--primary), var(--secondary));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    margin-bottom: 24px;
    filter: drop-shadow(0 4px 8px rgba(236, 72, 153, 0.3));
}

.empty-state h3 {
    font-size: 28px;
    color: #ffffff;
    margin-bottom: 12px;
    font-weight: 700;
}

.empty-state p {
    color: #9ca3af;
    margin-bottom: 30px;
    font-size: 16px;
}

.pagination-wrapper {
    margin-top: 24px;
    padding-top: 24px;
    border-top: 2px solid rgba(236, 72, 153, 0.2);
}
</style>

<script>
document.getElementById('searchInput').addEventListener('keyup', function() {
    let filter = this.value.toUpperCase();
    let table = document.getElementById('guruTable');
    let tr = table.getElementsByTagName('tr');

    for (let i = 1; i < tr.length; i++) {
        let td = tr[i].getElementsByTagName('td');
        let found = false;

        for (let j = 0; j < td.length - 1; j++) {
            if (td[j]) {
                if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                    found = true;
                    break;
                }
            }
        }

        tr[i].style.display = found ? '' : 'none';
    }
});
</script>
@endsection

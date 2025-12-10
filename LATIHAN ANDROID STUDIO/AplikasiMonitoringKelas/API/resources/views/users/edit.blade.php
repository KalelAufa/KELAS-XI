@extends('layouts.admin')

@section('title', 'Edit User')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-user-edit"></i>
            Edit User
        </h1>
        <p class="page-subtitle">Update user information</p>
    </div>
    <a href="{{ route('manage-users.index') }}" class="btn-secondary">
        <i class="fas fa-arrow-left"></i> Back to Users
    </a>
</div>

<div class="form-card">
    <form action="{{ route('manage-users.update', $user->id) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="form-grid">
            <div class="form-group">
                <label for="nama">
                    <i class="fas fa-user"></i> Full Name
                </label>
                <input type="text" id="nama" name="nama" class="form-control @error('nama') is-invalid @enderror"
                       value="{{ old('nama', $user->nama) }}" placeholder="Enter full name">
                @error('nama')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="email">
                    <i class="fas fa-envelope"></i> Email Address <span class="required">*</span>
                </label>
                <input type="email" id="email" name="email" class="form-control @error('email') is-invalid @enderror"
                       value="{{ old('email', $user->email) }}" placeholder="Enter email address" required>
                @error('email')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="password">
                    <i class="fas fa-lock"></i> New Password
                </label>
                <input type="password" id="password" name="password" class="form-control @error('password') is-invalid @enderror"
                       placeholder="Leave blank to keep current password">
                <small class="form-text">Leave blank if you don't want to change the password</small>
                @error('password')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group">
                <label for="role">
                    <i class="fas fa-user-tag"></i> Role <span class="required">*</span>
                </label>
                <select id="role" name="role" class="form-control @error('role') is-invalid @enderror" required>
                    <option value="">Select Role</option>
                    <option value="admin" {{ old('role', $user->role) == 'admin' ? 'selected' : '' }}>Admin</option>
                    <option value="siswa" {{ old('role', $user->role) == 'siswa' ? 'selected' : '' }}>Siswa</option>
                    <option value="kurikulum" {{ old('role', $user->role) == 'kurikulum' ? 'selected' : '' }}>Kurikulum</option>
                    <option value="kepala-sekolah" {{ old('role', $user->role) == 'kepala-sekolah' ? 'selected' : '' }}>Kepala Sekolah</option>
                </select>
                @error('role')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>

            <div class="form-group" id="kelas-field" style="display: none;">
                <label for="kelas_id">
                    <i class="fas fa-school"></i> Kelas <span class="required">*</span>
                </label>
                <select id="kelas_id" name="kelas_id" class="form-control @error('kelas_id') is-invalid @enderror">
                    <option value="">Select Kelas</option>
                    @foreach($kelas as $k)
                        <option value="{{ $k->id }}" {{ old('kelas_id', $user->kelas_id) == $k->id ? 'selected' : '' }}>
                            {{ $k->nama_kelas }}
                        </option>
                    @endforeach
                </select>
                @error('kelas_id')
                    <div class="invalid-feedback">{{ $message }}</div>
                @enderror
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-primary">
                <i class="fas fa-save"></i> Update User
            </button>
            <a href="{{ route('manage-users.index') }}" class="btn-secondary">
                <i class="fas fa-times"></i> Cancel
            </a>
        </div>
    </form>
</div>

<style>
.btn-secondary {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 14px 28px;
    background: rgba(10, 10, 10, 0.6);
    color: #ffffff;
    border: 2px solid rgba(236, 72, 153, 0.3);
    border-radius: 12px;
    font-size: 15px;
    font-weight: 700;
    cursor: pointer;
    text-decoration: none;
    transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
    backdrop-filter: blur(10px);
    letter-spacing: 0.5px;
}

.btn-secondary:hover {
    border-color: var(--primary);
    background: rgba(236, 72, 153, 0.1);
    transform: translateY(-3px) scale(1.02);
    box-shadow: 0 8px 16px rgba(236, 72, 153, 0.3);
}

.form-card {
    background: rgba(10, 10, 10, 0.6);
    backdrop-filter: blur(40px) saturate(180%);
    border: 1px solid rgba(236, 72, 153, 0.2);
    border-radius: 20px;
    padding: 40px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5), 0 0 1px rgba(236, 72, 153, 0.3);
}

.form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-group label {
    font-size: 14px;
    font-weight: 600;
    color: var(--dark);
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.form-group label i {
    color: var(--primary);
}

.required {
    color: var(--danger);
}

.form-control {
    padding: 12px 15px;
    background: rgba(255, 255, 255, 0.05);
    border: 2px solid rgba(255, 255, 255, 0.1);
    border-radius: 8px;
    font-size: 14px;
    color: var(--text-primary);
    transition: var(--transition);
    backdrop-filter: blur(10px);
}

.form-control::placeholder {
    color: rgba(255, 255, 255, 0.4);
}

select.form-control {
    background-color: rgba(26, 26, 26, 0.95);
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23EC4899' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 12px center;
    background-size: 12px;
    padding-right: 40px;
    cursor: pointer;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
}

select.form-control option {
    background-color: #1a1a1a;
    color: #ffffff;
    padding: 10px;
}

select.form-control option:hover,
select.form-control option:checked {
    background-color: #2d2d2d;
    color: #EC4899;
}

.form-control:focus {
    outline: none;
    background: rgba(255, 255, 255, 0.08);
    border-color: var(--primary);
    box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.1);
}

.form-control.is-invalid {
    border-color: var(--danger);
}

.invalid-feedback {
    color: var(--danger);
    font-size: 13px;
    margin-top: 5px;
}

.form-text {
    color: var(--gray);
    font-size: 12px;
    margin-top: 5px;
}

.form-actions {
    display: flex;
    gap: 12px;
    padding-top: 20px;
    border-top: 1px solid var(--gray-light);
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const roleSelect = document.getElementById('role');
    const kelasField = document.getElementById('kelas-field');
    const kelasInput = document.getElementById('kelas_id');

    function toggleKelasField() {
        if (roleSelect.value === 'siswa') {
            kelasField.style.display = 'flex';
            kelasInput.setAttribute('required', 'required');
        } else {
            kelasField.style.display = 'none';
            kelasInput.removeAttribute('required');
            kelasInput.value = '';
        }
    }

    // Check on page load
    toggleKelasField();

    // Check on role change
    roleSelect.addEventListener('change', toggleKelasField);
});
</script>
@endsection

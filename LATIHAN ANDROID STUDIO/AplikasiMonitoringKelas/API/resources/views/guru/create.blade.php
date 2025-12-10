@extends('layouts.admin')

@section('title', 'Add New Teacher')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-user-plus"></i>
            Add New Teacher
        </h1>
        <p class="page-subtitle">Register a new teacher to the system</p>
    </div>
    <a href="{{ route('guru.index') }}" class="btn-secondary">
        <i class="fas fa-arrow-left"></i> Back to Teachers
    </a>
</div>

<div class="glass-card" style="animation: fadeInUp 0.6s ease-out;">
    <form action="{{ route('guru.store') }}" method="POST">
        @csrf

        <div class="form-section" style="margin-bottom: 2rem;">
            <h3 class="section-title" style="font-size: 1.25rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; margin-bottom: 1.5rem;"><i class="fas fa-user-circle" style="color: #ec4899;"></i> Personal Information</h3>
            <div class="form-grid">
                <div class="form-group">
                    <label for="nip">
                        <i class="fas fa-id-card"></i> NIP (Teacher ID) <span class="required">*</span>
                    </label>
                    <input type="text" id="nip" name="nip" class="form-control @error('nip') is-invalid @enderror"
                           value="{{ old('nip') }}" placeholder="Enter NIP" required>
                    @error('nip')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="nama">
                        <i class="fas fa-user"></i> Full Name <span class="required">*</span>
                    </label>
                    <input type="text" id="nama" name="nama" class="form-control @error('nama') is-invalid @enderror"
                           value="{{ old('nama') }}" placeholder="Enter full name" required>
                    @error('nama')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="jenis_kelamin">
                        <i class="fas fa-venus-mars"></i> Gender <span class="required">*</span>
                    </label>
                    <select id="jenis_kelamin" name="jenis_kelamin" class="form-control @error('jenis_kelamin') is-invalid @enderror" required>
                        <option value="">Select Gender</option>
                        <option value="Laki-laki" {{ old('jenis_kelamin') == 'Laki-laki' ? 'selected' : '' }}>Laki-laki</option>
                        <option value="Perempuan" {{ old('jenis_kelamin') == 'Perempuan' ? 'selected' : '' }}>Perempuan</option>
                    </select>
                    @error('jenis_kelamin')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="mata_pelajaran">
                        <i class="fas fa-book"></i> Subject <span class="required">*</span>
                    </label>
                    <input type="text" id="mata_pelajaran" name="mata_pelajaran" class="form-control @error('mata_pelajaran') is-invalid @enderror"
                           value="{{ old('mata_pelajaran') }}" placeholder="e.g., Mathematics, Physics" required>
                    @error('mata_pelajaran')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <div class="form-section" style="margin-bottom: 2rem;">
            <h3 class="section-title" style="font-size: 1.25rem; font-weight: 700; display: flex; align-items: center; gap: 0.75rem; background: linear-gradient(135deg, #ec4899, #a855f7); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; margin-bottom: 1.5rem;"><i class="fas fa-address-card" style="color: #ec4899;"></i> Contact Information</h3>
            <div class="form-grid">
                <div class="form-group">
                    <label for="email">
                        <i class="fas fa-envelope"></i> Email Address <span class="required">*</span>
                    </label>
                    <input type="email" id="email" name="email" class="form-control @error('email') is-invalid @enderror"
                           value="{{ old('email') }}" placeholder="Enter email address" required>
                    @error('email')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="telepon">
                        <i class="fas fa-phone"></i> Phone Number <span class="required">*</span>
                    </label>
                    <input type="text" id="telepon" name="telepon" class="form-control @error('telepon') is-invalid @enderror"
                           value="{{ old('telepon') }}" placeholder="e.g., 081234567890" required>
                    @error('telepon')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group full-width">
                    <label for="alamat">
                        <i class="fas fa-map-marker-alt"></i> Address <span class="required">*</span>
                    </label>
                    <textarea id="alamat" name="alamat" class="form-control @error('alamat') is-invalid @enderror"
                              rows="3" placeholder="Enter complete address" required>{{ old('alamat') }}</textarea>
                    @error('alamat')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-primary">
                <i class="fas fa-save"></i> Save Teacher
            </button>
            <a href="{{ route('guru.index') }}" class="btn-secondary">
                <i class="fas fa-times"></i> Cancel
            </a>
        </div>
    </form>
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
    box-shadow: 0 8px 24px rgba(236, 72, 153, 0.4), 0 4px 12px rgba(168, 85, 247, 0.2);
    letter-spacing: 0.5px;
}

.btn-primary:hover {
    transform: translateY(-3px) scale(1.02);
    box-shadow: 0 12px 32px rgba(236, 72, 153, 0.5), 0 6px 16px rgba(168, 85, 247, 0.3), 0 0 0 4px rgba(236, 72, 153, 0.2);
}

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

.form-section {
    margin-bottom: 30px;
}

.section-title {
    font-size: 18px;
    font-weight: 700;
    color: var(--dark);
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid var(--gray-light);
    display: flex;
    align-items: center;
    gap: 10px;
}

.section-title i {
    color: var(--primary);
}

.form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-group.full-width {
    grid-column: 1 / -1;
}

.form-group label {
    font-size: 14px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    gap: 8px;
    letter-spacing: 0.3px;
}

.form-group label i {
    color: var(--primary);
    font-size: 16px;
}

.form-control {
    width: 100%;
    padding: 14px 18px;
    border: 2px solid rgba(236, 72, 153, 0.2);
    border-radius: 12px;
    font-size: 15px;
    font-family: inherit;
    transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
    background: rgba(10, 10, 10, 0.6);
    color: #ffffff;
    backdrop-filter: blur(10px);
}

.form-control:focus {
    outline: none;
    border-color: var(--primary);
    background: rgba(10, 10, 10, 0.8);
    box-shadow: 0 0 0 4px rgba(236, 72, 153, 0.15), 0 8px 24px rgba(236, 72, 153, 0.2);
    transform: translateY(-2px);
}

.form-control::placeholder {
    color: #6b7280;
}

.form-control.is-invalid {
    border-color: var(--danger);
    animation: shake 0.4s ease;
}

@keyframes shake {
    0%, 100% { transform: translateX(0); }
    25% { transform: translateX(-10px); }
    75% { transform: translateX(10px); }
}

.form-control.is-invalid:focus {
    box-shadow: 0 0 0 4px rgba(239, 68, 68, 0.15);
}

.required {
    color: var(--danger);
}

.form-control {
    width: 100%;
    padding: 14px 18px;
    border: 2px solid rgba(236, 72, 153, 0.2);
    border-radius: 12px;
    font-size: 15px;
    font-family: inherit;
    transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
    background: rgba(10, 10, 10, 0.6);
    color: #ffffff;
    backdrop-filter: blur(10px);
}

.form-control:focus {
    outline: none;
    border-color: var(--primary);
    box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.form-control.is-invalid {
    border-color: var(--danger);
}

.invalid-feedback {
    color: var(--danger);
    font-size: 13px;
    margin-top: 5px;
}

.form-actions {
    display: flex;
    gap: 12px;
    padding-top: 20px;
    border-top: 1px solid var(--gray-light);
}
</style>
@endsection

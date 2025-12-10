@extends('layouts.admin')

@section('title', 'Edit Class')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-edit"></i>
            Edit Class
        </h1>
        <p class="page-subtitle">Update class information</p>
    </div>
    <a href="{{ route('kelas.index') }}" class="btn-secondary">
        <i class="fas fa-arrow-left"></i> Back to Classes
    </a>
</div>

<div class="form-card">
    <form action="{{ route('kelas.update', $kelas->id) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="form-section">
            <h3 class="section-title"><i class="fas fa-info-circle"></i> Class Information</h3>
            <div class="form-grid">
                <div class="form-group">
                    <label for="nama_kelas">
                        <i class="fas fa-tag"></i> Class Name <span class="required">*</span>
                    </label>
                    <input type="text" id="nama_kelas" name="nama_kelas" class="form-control @error('nama_kelas') is-invalid @enderror"
                           value="{{ old('nama_kelas', $kelas->nama_kelas) }}" placeholder="e.g., X RPL 1, XI IPA 2" required>
                    @error('nama_kelas')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="tingkat">
                        <i class="fas fa-layer-group"></i> Grade Level <span class="required">*</span>
                    </label>
                    <input type="number" id="tingkat" name="tingkat" class="form-control @error('tingkat') is-invalid @enderror"
                           value="{{ old('tingkat', $kelas->tingkat) }}" placeholder="10, 11, or 12" min="10" max="12" required>
                    @error('tingkat')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="jurusan">
                        <i class="fas fa-graduation-cap"></i> Major <span class="required">*</span>
                    </label>
                    <input type="text" id="jurusan" name="jurusan" class="form-control @error('jurusan') is-invalid @enderror"
                           value="{{ old('jurusan', $kelas->jurusan) }}" placeholder="e.g., RPL, IPA, IPS" required>
                    @error('jurusan')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="wali_kelas_id">
                        <i class="fas fa-user-tie"></i> Homeroom Teacher <span class="required">*</span>
                    </label>
                    <select id="wali_kelas_id" name="wali_kelas_id" class="form-control @error('wali_kelas_id') is-invalid @enderror" required>
                        <option value="">Select Homeroom Teacher</option>
                        @foreach($gurus as $guru)
                            <option value="{{ $guru->id }}" {{ old('wali_kelas_id', $kelas->wali_kelas_id) == $guru->id ? 'selected' : '' }}>
                                {{ $guru->nama }} - {{ $guru->mata_pelajaran }}
                            </option>
                        @endforeach
                    </select>
                    @error('wali_kelas_id')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="ruangan">
                        <i class="fas fa-door-open"></i> Room <span class="required">*</span>
                    </label>
                    <input type="text" id="ruangan" name="ruangan" class="form-control @error('ruangan') is-invalid @enderror"
                           value="{{ old('ruangan', $kelas->ruangan) }}" placeholder="e.g., Room 101, Lab 2" required>
                    @error('ruangan')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="kapasitas">
                        <i class="fas fa-users"></i> Capacity <span class="required">*</span>
                    </label>
                    <input type="number" id="kapasitas" name="kapasitas" class="form-control @error('kapasitas') is-invalid @enderror"
                           value="{{ old('kapasitas', $kelas->kapasitas) }}" placeholder="e.g., 30, 35" min="1" required>
                    @error('kapasitas')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-primary">
                <i class="fas fa-save"></i> Update Class
            </button>
            <a href="{{ route('kelas.index') }}" class="btn-secondary">
                <i class="fas fa-times"></i> Cancel
            </a>
        </div>
    </form>
</div>

<style>
.btn-primary {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 12px 24px;
    background: linear-gradient(135deg, var(--primary), var(--primary-dark));
    color: white;
    border: none;
    border-radius: 10px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    text-decoration: none;
    transition: var(--transition);
    box-shadow: 0 4px 6px rgba(79, 70, 229, 0.2);
}

.btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(79, 70, 229, 0.3);
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
    font-family: inherit;
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

.form-actions {
    display: flex;
    gap: 12px;
    padding-top: 20px;
    border-top: 1px solid var(--gray-light);
}
</style>
@endsection

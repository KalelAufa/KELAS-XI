@extends('layouts.admin')

@section('title', 'Edit Schedule')

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-calendar-edit"></i>
            Edit Schedule
        </h1>
        <p class="page-subtitle">Update schedule information</p>
    </div>
    <a href="{{ route('jadwal.index') }}" class="btn-secondary">
        <i class="fas fa-arrow-left"></i> Back to Schedules
    </a>
</div>

<div class="form-card">
    <form action="{{ route('jadwal.update', $jadwal->id) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="form-section">
            <h3 class="section-title"><i class="fas fa-info-circle"></i> Schedule Information</h3>
            <div class="form-grid">
                <div class="form-group">
                    <label for="mata_pelajaran">
                        <i class="fas fa-book"></i> Subject <span class="required">*</span>
                    </label>
                    <input type="text" id="mata_pelajaran" name="mata_pelajaran" class="form-control @error('mata_pelajaran') is-invalid @enderror"
                           value="{{ old('mata_pelajaran', $jadwal->mata_pelajaran) }}" placeholder="e.g., Mathematics, Physics" required>
                    @error('mata_pelajaran')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="kelas_id">
                        <i class="fas fa-school"></i> Class <span class="required">*</span>
                    </label>
                    <select id="kelas_id" name="kelas_id" class="form-control @error('kelas_id') is-invalid @enderror" required>
                        <option value="">Select Class</option>
                        @foreach($kelas as $k)
                            <option value="{{ $k->id }}" {{ old('kelas_id', $jadwal->kelas_id) == $k->id ? 'selected' : '' }}>
                                {{ $k->nama_kelas }} - {{ $k->jurusan }}
                            </option>
                        @endforeach
                    </select>
                    @error('kelas_id')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="guru_id">
                        <i class="fas fa-chalkboard-teacher"></i> Teacher <span class="required">*</span>
                    </label>
                    <select id="guru_id" name="guru_id" class="form-control @error('guru_id') is-invalid @enderror" required>
                        <option value="">Select Teacher</option>
                        @foreach($gurus as $guru)
                            <option value="{{ $guru->id }}" {{ old('guru_id', $jadwal->guru_id) == $guru->id ? 'selected' : '' }}>
                                {{ $guru->nama }} - {{ $guru->mata_pelajaran }}
                            </option>
                        @endforeach
                    </select>
                    @error('guru_id')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="hari">
                        <i class="fas fa-calendar-day"></i> Day <span class="required">*</span>
                    </label>
                    <select id="hari" name="hari" class="form-control @error('hari') is-invalid @enderror" required>
                        <option value="">Select Day</option>
                        <option value="Senin" {{ old('hari', $jadwal->hari) == 'Senin' ? 'selected' : '' }}>Senin (Monday)</option>
                        <option value="Selasa" {{ old('hari', $jadwal->hari) == 'Selasa' ? 'selected' : '' }}>Selasa (Tuesday)</option>
                        <option value="Rabu" {{ old('hari', $jadwal->hari) == 'Rabu' ? 'selected' : '' }}>Rabu (Wednesday)</option>
                        <option value="Kamis" {{ old('hari', $jadwal->hari) == 'Kamis' ? 'selected' : '' }}>Kamis (Thursday)</option>
                        <option value="Jumat" {{ old('hari', $jadwal->hari) == 'Jumat' ? 'selected' : '' }}>Jumat (Friday)</option>
                        <option value="Sabtu" {{ old('hari', $jadwal->hari) == 'Sabtu' ? 'selected' : '' }}>Sabtu (Saturday)</option>
                    </select>
                    @error('hari')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="jam_mulai">
                        <i class="fas fa-clock"></i> Start Time <span class="required">*</span>
                    </label>
                    <input type="time" id="jam_mulai" name="jam_mulai" class="form-control @error('jam_mulai') is-invalid @enderror"
                           value="{{ old('jam_mulai', substr($jadwal->jam_mulai, 0, 5)) }}" required>
                    @error('jam_mulai')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group">
                    <label for="jam_selesai">
                        <i class="fas fa-clock"></i> End Time <span class="required">*</span>
                    </label>
                    <input type="time" id="jam_selesai" name="jam_selesai" class="form-control @error('jam_selesai') is-invalid @enderror"
                           value="{{ old('jam_selesai', substr($jadwal->jam_selesai, 0, 5)) }}" required>
                    @error('jam_selesai')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>

                <div class="form-group full-width">
                    <label for="ruangan">
                        <i class="fas fa-door-open"></i> Room <span class="required">*</span>
                    </label>
                    <input type="text" id="ruangan" name="ruangan" class="form-control @error('ruangan') is-invalid @enderror"
                           value="{{ old('ruangan', $jadwal->ruangan) }}" placeholder="e.g., Room 101, Lab 2" required>
                    @error('ruangan')
                        <div class="invalid-feedback">{{ $message }}</div>
                    @enderror
                </div>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-primary">
                <i class="fas fa-save"></i> Update Schedule
            </button>
            <a href="{{ route('jadwal.index') }}" class="btn-secondary">
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
} pointer;
    text-decoration: none;
    transition: var(--transition);
}

.btn-secondary:hover {
    border-color: var(--primary);
    color: var(--primary);
    background: rgba(255, 255, 255, 0.08);
    transform: translateY(-2px);
}

.form-card {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 16px;
    padding: 30px;
    box-shadow: var(--shadow);
    border: 1px solid rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(20px);
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

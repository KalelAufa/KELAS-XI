@extends('layouts.admin')

@section('title', 'Edit Guru Pengganti')

@section('styles')
<style>
    .form-card {
        background: rgba(10, 10, 10, 0.6);
        backdrop-filter: blur(40px) saturate(180%);
        border: 1px solid rgba(236, 72, 153, 0.2);
        border-radius: 20px;
        padding: 40px;
        box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5), 0 0 1px rgba(236, 72, 153, 0.3);
        max-width: 800px;
        margin: 0 auto;
        animation: fadeInUp 0.6s ease-out;
    }

    .form-header {
        text-align: center;
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 2px solid var(--gray-light);
    }

    .form-header i {
        font-size: 48px;
        color: var(--warning);
        margin-bottom: 15px;
    }

    .form-header h2 {
        font-size: 24px;
        color: var(--dark);
        margin-bottom: 5px;
    }

    .form-header p {
        color: var(--gray);
        font-size: 14px;
    }

    .form-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 20px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-group.full-width {
        grid-column: span 2;
    }

    .form-group label {
        display: block;
        font-size: 14px;
        font-weight: 600;
        color: var(--dark);
        margin-bottom: 8px;
    }

    .form-group label .required {
        color: var(--danger);
    }

    .form-control {
        width: 100%;
        padding: 14px 18px;
        border: 2px solid rgba(236, 72, 153, 0.2);
        border-radius: 12px;
        font-size: 15px;
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

    textarea.form-control {
        min-height: 120px;
        resize: vertical;
    }

    .form-actions {
        display: flex;
        justify-content: flex-end;
        gap: 15px;
        margin-top: 30px;
        padding-top: 20px;
        border-top: 2px solid var(--gray-light);
    }

    .btn-primary {
        background: linear-gradient(135deg, var(--primary), var(--primary-dark));
        color: white;
        border: none;
        padding: 14px 28px;
        border-radius: 12px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 10px;
        transition: var(--transition);
        text-decoration: none;
    }

    .btn-primary:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 15px rgba(99, 102, 241, 0.4);
    }

    .btn-secondary {
        background: var(--gray-light);
        color: var(--dark);
        border: none;
        padding: 14px 28px;
        border-radius: 12px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        display: inline-flex;
        align-items: center;
        gap: 10px;
        transition: var(--transition);
        text-decoration: none;
    }

    .btn-secondary:hover {
        background: var(--gray);
        color: white;
    }

    .alert-danger {
        background: rgba(239, 68, 68, 0.1);
        border-left: 4px solid #ef4444;
        color: #991b1b;
        padding: 16px 20px;
        border-radius: 12px;
        margin-bottom: 20px;
    }

    .alert-danger ul {
        margin: 10px 0 0 20px;
    }

    .current-info {
        background: linear-gradient(135deg, rgba(245, 158, 11, 0.1), rgba(217, 119, 6, 0.1));
        border-left: 4px solid #f59e0b;
        border-radius: 12px;
        padding: 16px 20px;
        margin-bottom: 25px;
    }

    .current-info h4 {
        color: #b45309;
        font-size: 14px;
        margin-bottom: 8px;
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .current-info p {
        color: #92400e;
        font-size: 13px;
        margin: 4px 0;
    }

    @media (max-width: 768px) {
        .form-grid {
            grid-template-columns: 1fr;
        }
        .form-group.full-width {
            grid-column: span 1;
        }
    }
</style>
@endsection

@section('content')
<div class="dashboard-header">
    <div>
        <h1 class="page-title">
            <i class="fas fa-edit"></i>
            Edit Guru Pengganti
        </h1>
        <p class="page-subtitle">Perbarui data penggantian guru</p>
    </div>
    <a href="{{ route('guru-pengganti.index') }}" class="btn-secondary">
        <i class="fas fa-arrow-left"></i>
        Kembali
    </a>
</div>

<div class="form-card">
    <div class="form-header">
        <i class="fas fa-edit"></i>
        <h2>Edit Data Guru Pengganti</h2>
        <p>Perbarui informasi penggantian guru</p>
    </div>

    <div class="current-info">
        <h4><i class="fas fa-info-circle"></i> Informasi Saat Ini</h4>
        <p><strong>Tanggal:</strong> {{ $guruPengganti->tanggal->format('d M Y') }}</p>
        <p><strong>Guru Asli:</strong> {{ $guruPengganti->guruAsli->nama ?? 'N/A' }} → <strong>Pengganti:</strong> {{ $guruPengganti->guruPengganti->nama ?? 'N/A' }}</p>
        <p><strong>Status:</strong> {{ $guruPengganti->status }}</p>
    </div>

    @if($errors->any())
    <div class="alert-danger">
        <strong><i class="fas fa-exclamation-circle"></i> Terjadi Kesalahan!</strong>
        <ul>
            @foreach($errors->all() as $error)
                <li>{{ $error }}</li>
            @endforeach
        </ul>
    </div>
    @endif

    <form action="{{ route('guru-pengganti.update', $guruPengganti) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="form-grid">
            <div class="form-group">
                <label>Tanggal <span class="required">*</span></label>
                <input type="date" name="tanggal" class="form-control" value="{{ old('tanggal', $guruPengganti->tanggal->format('Y-m-d')) }}" required>
            </div>

            <div class="form-group">
                <label>Jadwal Pelajaran <span class="required">*</span></label>
                <select name="jadwal_id" class="form-control" required>
                    <option value="">-- Pilih Jadwal --</option>
                    @foreach($jadwals as $jadwal)
                        <option value="{{ $jadwal->id }}" {{ old('jadwal_id', $guruPengganti->jadwal_id) == $jadwal->id ? 'selected' : '' }}>
                            {{ $jadwal->hari }} - {{ $jadwal->mata_pelajaran }} - {{ $jadwal->kelas->nama_kelas ?? 'N/A' }} ({{ substr($jadwal->jam_mulai, 0, 5) }})
                        </option>
                    @endforeach
                </select>
            </div>

            <div class="form-group">
                <label>Guru Asli (Yang Digantikan) <span class="required">*</span></label>
                <select name="guru_asli_id" class="form-control" required>
                    <option value="">-- Pilih Guru Asli --</option>
                    @foreach($gurus as $guru)
                        <option value="{{ $guru->id }}" {{ old('guru_asli_id', $guruPengganti->guru_asli_id) == $guru->id ? 'selected' : '' }}>
                            {{ $guru->nama }} - {{ $guru->mata_pelajaran }}
                        </option>
                    @endforeach
                </select>
            </div>

            <div class="form-group">
                <label>Guru Pengganti <span class="required">*</span></label>
                <select name="guru_pengganti_id" class="form-control" required>
                    <option value="">-- Pilih Guru Pengganti --</option>
                    @foreach($gurus as $guru)
                        <option value="{{ $guru->id }}" {{ old('guru_pengganti_id', $guruPengganti->guru_pengganti_id) == $guru->id ? 'selected' : '' }}>
                            {{ $guru->nama }} - {{ $guru->mata_pelajaran }}
                        </option>
                    @endforeach
                </select>
            </div>

            <div class="form-group">
                <label>Alasan <span class="required">*</span></label>
                <select name="alasan" class="form-control" required>
                    <option value="">-- Pilih Alasan --</option>
                    <option value="Sakit" {{ old('alasan', $guruPengganti->alasan) == 'Sakit' ? 'selected' : '' }}>🤒 Sakit</option>
                    <option value="Izin" {{ old('alasan', $guruPengganti->alasan) == 'Izin' ? 'selected' : '' }}>📝 Izin</option>
                    <option value="Cuti" {{ old('alasan', $guruPengganti->alasan) == 'Cuti' ? 'selected' : '' }}>🏖️ Cuti</option>
                    <option value="Dinas Luar" {{ old('alasan', $guruPengganti->alasan) == 'Dinas Luar' ? 'selected' : '' }}>🚗 Dinas Luar</option>
                    <option value="Lainnya" {{ old('alasan', $guruPengganti->alasan) == 'Lainnya' ? 'selected' : '' }}>📋 Lainnya</option>
                </select>
            </div>

            <div class="form-group">
                <label>Status <span class="required">*</span></label>
                <select name="status" class="form-control" required>
                    <option value="Pending" {{ old('status', $guruPengganti->status) == 'Pending' ? 'selected' : '' }}>⏳ Pending</option>
                    <option value="Disetujui" {{ old('status', $guruPengganti->status) == 'Disetujui' ? 'selected' : '' }}>✅ Disetujui</option>
                    <option value="Ditolak" {{ old('status', $guruPengganti->status) == 'Ditolak' ? 'selected' : '' }}>❌ Ditolak</option>
                    <option value="Selesai" {{ old('status', $guruPengganti->status) == 'Selesai' ? 'selected' : '' }}>✔️ Selesai</option>
                </select>
            </div>

            <div class="form-group full-width">
                <label>Keterangan Tambahan</label>
                <textarea name="keterangan" class="form-control" placeholder="Tambahkan keterangan jika diperlukan...">{{ old('keterangan', $guruPengganti->keterangan) }}</textarea>
            </div>
        </div>

        <div class="form-actions">
            <a href="{{ route('guru-pengganti.index') }}" class="btn-secondary">
                <i class="fas fa-times"></i>
                Batal
            </a>
            <button type="submit" class="btn-primary">
                <i class="fas fa-save"></i>
                Simpan Perubahan
            </button>
        </div>
    </form>
</div>
@endsection

@extends('layouts.app')

@section('title', 'Admin Login')

@section('styles')
<link rel="stylesheet" href="{{ asset('css/modern-ui.css') }}">
<style>
    body {
        background: radial-gradient(ellipse at top, #1a0a1f 0%, #0a0a0a 50%, #000000 100%);
        overflow: hidden;
    }

    .bg-pattern {
        background:
            radial-gradient(circle at 20% 50%, rgba(236, 72, 153, 0.2), transparent 40%),
            radial-gradient(circle at 80% 80%, rgba(168, 85, 247, 0.15), transparent 40%),
            radial-gradient(circle at 40% 20%, rgba(249, 115, 22, 0.1), transparent 40%);
        animation: bgFloat 25s ease-in-out infinite alternate;
    }

    .login-container {
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 20px;
        position: relative;
        z-index: 1;
    }

    .login-card {
        background: rgba(26, 26, 26, 0.4);
        backdrop-filter: blur(40px) saturate(180%);
        -webkit-backdrop-filter: blur(40px) saturate(180%);
        border-radius: 32px;
        padding: 60px;
        max-width: 520px;
        width: 100%;
        box-shadow: 0 30px 90px rgba(236, 72, 153, 0.25),
                    0 12px 36px rgba(0, 0, 0, 0.6);
        border: 1px solid rgba(236, 72, 153, 0.3);
        animation: fadeInScale 0.8s cubic-bezier(0.34, 1.56, 0.64, 1);
        position: relative;
    }

    .login-card::before {
        content: '';
        position: absolute;
        inset: -2px;
        border-radius: 32px;
        padding: 2px;
        background: linear-gradient(135deg, var(--primary), var(--accent), var(--secondary));
        -webkit-mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
        mask: linear-gradient(#fff 0 0) content-box, linear-gradient(#fff 0 0);
        -webkit-mask-composite: xor;
        mask-composite: exclude;
        opacity: 0.5;
        animation: borderRotate 6s linear infinite;
    }

    @keyframes fadeInScale {
        from {
            opacity: 0;
            transform: scale(0.9) translateY(30px);
        }
        to {
            opacity: 1;
            transform: scale(1) translateY(0);
        }
    }

    @keyframes borderRotate {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }

    .login-header {
        text-align: center;
        margin-bottom: 45px;
        animation: fadeInUp 0.8s ease-out 0.2s both;
    }

    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .login-icon {
        width: 90px;
        height: 90px;
        background: linear-gradient(135deg, var(--primary) 0%, var(--accent) 50%, var(--secondary) 100%);
        border-radius: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 25px;
        box-shadow: 0 20px 60px rgba(236, 72, 153, 0.4);
        animation: float 3s ease-in-out infinite, pulse-glow 2s ease-in-out infinite;
        position: relative;
    }

    .login-icon::before {
        content: '';
        position: absolute;
        inset: -4px;
        background: inherit;
        border-radius: 24px;
        filter: blur(20px);
        opacity: 0.6;
        z-index: -1;
    }

    @keyframes float {
        0%, 100% { transform: translateY(0); }
        50% { transform: translateY(-10px); }
    }

    @keyframes pulse-glow {
        0%, 100% {
            box-shadow: 0 20px 60px rgba(236, 72, 153, 0.4);
        }
        50% {
            box-shadow: 0 20px 80px rgba(236, 72, 153, 0.6);
        }
    }

    .login-icon i {
        font-size: 42px;
        color: white;
        filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.3));
    }

    .login-title {
        font-size: 36px;
        font-weight: 800;
        background: linear-gradient(135deg, #ec4899 0%, #a855f7 50%, #f97316 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        margin-bottom: 12px;
        letter-spacing: -0.5px;
    }

    .login-subtitle {
        color: #9ca3af;
        font-size: 15px;
        font-weight: 500;
    }

    .form-group {
        margin-bottom: 26px;
        animation: fadeInUp 0.6s ease-out 0.4s both;
    }

    .form-label {
        display: block;
        margin-bottom: 10px;
        font-weight: 600;
        color: #ffffff;
        font-size: 14px;
        letter-spacing: 0.3px;
    }

    .form-label .required {
        color: var(--danger);
        margin-left: 2px;
    }

    .input-group {
        position: relative;
    }

    .input-icon {
        position: absolute;
        left: 20px;
        top: 50%;
        transform: translateY(-50%);
        color: #9ca3af;
        font-size: 18px;
        transition: all 0.3s ease;
        z-index: 1;
    }

    .form-input {
        width: 100%;
        padding: 16px 20px 16px 54px;
        border: 2px solid rgba(236, 72, 153, 0.2);
        border-radius: 16px;
        font-size: 15px;
        font-family: inherit;
        transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
        background: rgba(10, 10, 10, 0.6);
        color: #ffffff;
        backdrop-filter: blur(10px);
    }

    .form-input:focus {
        outline: none;
        border-color: var(--primary);
        background: rgba(10, 10, 10, 0.8);
        box-shadow: 0 0 0 4px rgba(236, 72, 153, 0.15),
                    0 8px 24px rgba(236, 72, 153, 0.2);
        transform: translateY(-2px);
    }

    .form-input:focus + .input-icon,
    .form-input:not(:placeholder-shown) + .input-icon {
        color: var(--primary);
        transform: translateY(-50%) scale(1.1);
    }

    .form-input::placeholder {
        color: #6b7280;
    }

    .form-input.is-invalid {
        border-color: var(--danger);
        animation: shake 0.4s ease;
    }

    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        25% { transform: translateX(-10px); }
        75% { transform: translateX(10px); }
    }

    .form-input.is-invalid:focus {
        box-shadow: 0 0 0 4px rgba(239, 68, 68, 0.15);
    }

    .invalid-feedback {
        display: block;
        margin-top: 10px;
        color: var(--danger);
        font-size: 13px;
        font-weight: 500;
        animation: fadeInUp 0.3s ease;
    }

    .form-check {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 24px;
        animation: fadeInUp 0.6s ease-out 0.5s both;
    }

    .form-check-input {
        width: 20px;
        height: 20px;
        border: 2px solid rgba(236, 72, 153, 0.4);
        border-radius: 6px;
        cursor: pointer;
        transition: all 0.3s ease;
        appearance: none;
        background: rgba(10, 10, 10, 0.6);
        position: relative;
    }

    .form-check-input:checked {
        background: linear-gradient(135deg, var(--primary), var(--secondary));
        border-color: var(--primary);
    }

    .form-check-input:checked::after {
        content: '✓';
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        color: white;
        font-size: 14px;
        font-weight: bold;
    }

    .form-check-label {
        font-size: 14px;
        color: #d1d5db;
        cursor: pointer;
        user-select: none;
        margin: 0;
        font-weight: 500;
    }

    .btn {
        width: 100%;
        padding: 16px 28px;
        border: none;
        border-radius: 16px;
        font-size: 16px;
        font-weight: 700;
        cursor: pointer;
        transition: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 10px;
        font-family: inherit;
        position: relative;
        overflow: hidden;
        letter-spacing: 0.5px;
    }

    .btn::before {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        width: 0;
        height: 0;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.3);
        transform: translate(-50%, -50%);
        transition: width 0.6s, height 0.6s;
    }

    .btn:hover::before {
        width: 400px;
        height: 400px;
    }

    .btn span,
    .btn i {
        position: relative;
        z-index: 1;
    }

    .btn-primary {
        background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
        color: white;
        box-shadow: 0 8px 24px rgba(236, 72, 153, 0.4),
                    0 4px 12px rgba(168, 85, 247, 0.2);
        animation: fadeInUp 0.6s ease-out 0.6s both;
    }

    .btn-primary:hover {
        transform: translateY(-3px) scale(1.02);
        box-shadow: 0 12px 32px rgba(236, 72, 153, 0.5),
                    0 6px 16px rgba(168, 85, 247, 0.3),
                    0 0 0 4px rgba(236, 72, 153, 0.2);
    }

    .btn-primary:active {
        transform: translateY(-1px) scale(0.98);
    }

    .btn-primary:disabled {
        opacity: 0.6;
        cursor: not-allowed;
        transform: none;
    }

    .alert {
        padding: 18px 22px;
        border-radius: 14px;
        margin-bottom: 24px;
        display: flex;
        align-items: center;
        gap: 14px;
        animation: slideInDown 0.4s ease-out, shake 0.4s ease 0.5s;
        font-size: 14px;
        font-weight: 600;
        backdrop-filter: blur(10px);
    }

    @keyframes slideInDown {
        from {
            transform: translateY(-20px);
            opacity: 0;
        }
        to {
            transform: translateY(0);
            opacity: 1;
        }
    }

    .alert-danger {
        background: rgba(239, 68, 68, 0.15);
        color: #fca5a5;
        border: 2px solid rgba(239, 68, 68, 0.5);
        box-shadow: 0 4px 16px rgba(239, 68, 68, 0.2);
    }

    .alert-danger i {
        font-size: 20px;
        color: var(--danger);
    }

    .alert i {
        font-size: 20px;
    }

    .login-footer {
        text-align: center;
        margin-top: 32px;
        padding-top: 32px;
        border-top: 2px solid rgba(236, 72, 153, 0.2);
        color: #9ca3af;
        font-size: 13px;
        animation: fadeInUp 0.6s ease-out 0.7s both;
    }

    .login-footer strong {
        background: linear-gradient(135deg, var(--primary), var(--secondary));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        font-weight: 700;
    }

    .admin-badge {
        display: inline-flex;
        align-items: center;
        gap: 10px;
        background: linear-gradient(135deg, rgba(236, 72, 153, 0.15), rgba(168, 85, 247, 0.15));
        color: var(--primary);
        padding: 10px 20px;
        border-radius: 24px;
        font-size: 13px;
        font-weight: 700;
        margin-bottom: 24px;
        border: 1px solid rgba(236, 72, 153, 0.3);
        letter-spacing: 0.5px;
        animation: pulse-glow 2s ease-in-out infinite;
    }

    .admin-badge i {
        font-size: 16px;
    }

    @media (max-width: 576px) {
        .login-card {
            padding: 32px 24px;
            margin: 20px;
        }

        .login-title {
            font-size: 28px;
        }

        .login-icon {
            width: 80px;
            height: 80px;
        }

        .login-icon i {
            font-size: 36px;
        }

        .form-input {
            padding: 14px 18px 14px 50px;
        }

        .btn {
            padding: 14px 24px;
        }
    }
</style>
@endsection

@section('content')
<div class="login-container">
    <div class="login-card">
        <div class="login-header">
            <div class="login-icon" style="background: transparent; box-shadow: none;">
                <img src="/images/logo_smenda.png" alt="Logo SMENDA" style="width: 100%; height: 100%; object-fit: contain;" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                <i class="fas fa-graduation-cap" style="display: none;"></i>
            </div>
            <h1 class="login-title">SMKN 2 Buduran</h1>
            <p class="login-subtitle">Sign in to access the admin dashboard</p>
        </div>

        @if ($errors->any())
            <div class="alert alert-danger">
                <i class="fas fa-exclamation-circle"></i>
                <span>{{ $errors->first() }}</span>
            </div>
        @endif

        <form method="POST" action="{{ route('login.post') }}">
            @csrf

            <div class="form-group">
                <label class="form-label" for="email">
                    Email Address <span class="required">*</span>
                </label>
                <div class="input-group">
                    <i class="input-icon fas fa-envelope"></i>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        class="form-input @error('email') is-invalid @enderror"
                        placeholder="admin@example.com"
                        value="{{ old('email') }}"
                        required
                        autofocus
                        autocomplete="email"
                    >
                </div>
                @error('email')
                    <span class="invalid-feedback">{{ $message }}</span>
                @enderror
            </div>

            <div class="form-group">
                <label class="form-label" for="password">
                    Password <span class="required">*</span>
                </label>
                <div class="input-group">
                    <i class="input-icon fas fa-lock"></i>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        class="form-input @error('password') is-invalid @enderror"
                        placeholder="Enter your password"
                        required
                        autocomplete="current-password"
                    >
                </div>
                @error('password')
                    <span class="invalid-feedback">{{ $message }}</span>
                @enderror
            </div>

            <div class="form-check">
                <input
                    type="checkbox"
                    id="remember"
                    name="remember"
                    class="form-check-input"
                >
                <label class="form-check-label" for="remember">
                    Remember me for 30 days
                </label>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-sign-in-alt"></i>
                Sign In to Dashboard
            </button>
        </form>

        <div class="login-footer">
            <i class="fas fa-lock"></i>
            Secure Admin Portal &copy; {{ date('Y') }}
        </div>
    </div>
</div>
@endsection

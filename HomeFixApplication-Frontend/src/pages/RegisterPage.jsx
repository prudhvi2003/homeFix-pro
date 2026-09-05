import React, { useState } from 'react';
import { register } from '../services/api.js';
import { useNavigate, Link } from 'react-router-dom';

function RegisterPage() {
    const [formData, setFormData] = useState({ name: '', email: '', password: '' });
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register(formData);
            alert("Registration successful! Please login.");
            navigate("/login");
        } catch (err) {
            alert("Registration failed. Email might already exist.");
        }
    };

    return (
        <div className="container" style={{ maxWidth: '400px', marginTop: '50px' }}>
            <div className="card">
                <h2>Create Account</h2>
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: '15px' }}>
                        <label>Full Name</label>
                        <input type="text" style={{ width: '100%', padding: '8px' }} 
                            onChange={(e) => setFormData({...formData, name: e.target.value})} required />
                    </div>
                    <div style={{ marginBottom: '15px' }}>
                        <label>Email</label>
                        <input type="email" style={{ width: '100%', padding: '8px' }} 
                            onChange={(e) => setFormData({...formData, email: e.target.value})} required />
                    </div>
                    <div style={{ marginBottom: '15px' }}>
                        <label>Password</label>
                        <input type="password" style={{ width: '100%', padding: '8px' }} 
                            onChange={(e) => setFormData({...formData, password: e.target.value})} required />
                    </div>
                    <button type="submit" className="btn-primary" style={{ width: '100%' }}>Register</button>
                </form>
                <p style={{ marginTop: '15px', textAlign: 'center' }}>
                    Already have an account? <Link to="/login">Login here</Link>
                </p>
            </div>
        </div>
    );
}

export default RegisterPage;
import React, { useState } from 'react';
import { login } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { loginUser } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await login({ email, password });
            loginUser(data);
            alert("Login Successful!");
            navigate("/"); // Redirect to home
        } catch (err) {
            alert("Invalid Credentials");
        }
    };

    return (
        <div style={{ maxWidth: '300px', margin: '50px auto' }}>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input type="email" placeholder="Email" value={email} 
                    onChange={(e) => setEmail(e.target.value)} required 
                    style={{ display: 'block', width: '100%', marginBottom: '10px' }} />
                
                <input type="password" placeholder="Password" value={password} 
                    onChange={(e) => setPassword(e.target.value)} required 
                    style={{ display: 'block', width: '100%', marginBottom: '10px' }} />
                
                <button type="submit" style={{ width: '100%' }}>Login</button>
            </form>
        </div>
    );
}

export default LoginPage;
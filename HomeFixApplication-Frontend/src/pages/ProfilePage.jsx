import React from 'react';
import { useAuth } from '../context/AuthContext';

function ProfilePage() {
    const { user } = useAuth();

    if (!user) return <p>Please login to view profile.</p>;

    return (
        <div className="container" style={{ maxWidth: '600px', marginTop: '50px' }}>
            <div className="card" style={{ textAlign: 'center' }}>
                <div style={{ 
                    width: '100px', height: '100px', borderRadius: '50%', 
                    background: 'var(--primary)', color: 'white', 
                    display: 'flex', alignItems: 'center', justifyContent: 'center',
                    fontSize: '2rem', margin: '0 auto 20px'
                }}>
                    {user.name.charAt(0)}
                </div>
                <h2>{user.name}</h2>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Account Role:</strong> {user.role}</p>
                <hr />
                <p style={{ color: 'var(--secondary)', fontSize: '0.9rem' }}>
                    Member since: {new Date().toLocaleDateString()}
                </p>
            </div>
        </div>
    );
}

export default ProfilePage;
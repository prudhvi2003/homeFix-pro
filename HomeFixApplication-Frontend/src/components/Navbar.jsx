import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

function Navbar() {
    const { user, logoutUser } = useAuth();

    return (
        <nav style={{ 
            display: 'flex', 
            justifyContent: 'space-between', 
            alignItems: 'center',
            padding: '1rem 5%', 
            background: 'white', 
            boxShadow: '0 2px 4px rgb(0 0 0 / 0.1)',
            position: 'sticky',
            top: 0,
            zIndex: 100
        }}>
            <h2 style={{ color: 'var(--primary)', margin: 0 }}>HomeFix</h2>
            <div style={{ display: 'flex', gap: '1.5rem', alignItems: 'center' }}>
                <Link to="/" style={{ color: 'var(--text)', textDecoration: 'none', fontWeight: 500 }}>Home</Link>
                <Link to="/services" style={{ color: 'var(--text)', textDecoration: 'none', fontWeight: 500 }}>Services</Link>
                
                {user ? (
                    <>
                        <Link to="/my-bookings" style={{ color: 'var(--text)', textDecoration: 'none', fontWeight: 500 }}>My Bookings</Link>
                        <Link to="/profile" style={{ color: 'var(--text)', textDecoration: 'none' }}>Profile</Link>
                        {/* ADMIN ONLY LINKS */}
                        {user.role === 'ADMIN' && (
                            <>
                                <Link to="/admin" style={{ color: 'var(--primary)', textDecoration: 'none', fontWeight: 'bold' }}>Bookings</Link>
                                <Link to="/manage-services" style={{ color: 'var(--primary)', textDecoration: 'none', fontWeight: 'bold' }}>Add Service</Link>
                            </> 
                        )}
                        
                        <span style={{ color: 'var(--secondary)', fontSize: '0.9rem' }}>| {user.name}</span>
                        <button className="btn-danger" style={{ padding: '0.4rem 0.8rem' }} onClick={logoutUser}>Logout</button>
                    </>
                ) : (
                    // In the !user part (logged out view):  
                    <>
                        <Link to="/login" style={{ color: 'var(--text)', textDecoration: 'none' }}>Login</Link>
                        <Link to="/register" className="btn-primary" style={{ textDecoration: 'none' }}>Register</Link>
                    </>     
                )}
            </div>
        </nav>
    );
}

export default Navbar;
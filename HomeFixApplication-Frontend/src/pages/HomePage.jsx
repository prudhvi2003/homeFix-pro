import React from 'react';
import { Link } from 'react-router-dom'; // Ensure 'Link' is lowercase 'ink'

function HomePage() {
    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h1>Welcome to HomeFix Pro</h1>
            <p>Your one-stop solution for all home maintenance needs.</p>
            <Link to="/services">
                <button style={{ padding: '10px 20px', fontSize: '16px', cursor: 'pointer' }}>
                    Browse Services
                </button>
            </Link>
        </div>
    );
}

export default HomePage;
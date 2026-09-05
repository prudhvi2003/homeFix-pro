import React, { useState, useEffect } from 'react';
import { getMyBookings } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { cancelBooking } from '../services/api';

function MyBookingsPage() {
    const [bookings, setBookings] = useState([]);
    const { user } = useAuth();

    const handleCancel = async (id) => {
    if (window.confirm("Are you sure you want to cancel this booking?")) {
        try {
            await cancelBooking(id, user.email);
            // Refresh the list
            const data = await getMyBookings(user.email);
            setBookings(data);
        } catch (err) {
            alert("Failed to cancel booking.");
        }
    }
    };

    useEffect(() => {
        if (user) {
            const fetchBookings = async () => {
                const data = await getMyBookings(user.email);
                setBookings(data);
            };
            fetchBookings();
        }
    }, [user]);

    return (
        <div style={{ padding: '20px' }}>
            <h1>My Bookings</h1>
            {bookings.length === 0 ? (
                <p>You have no bookings yet.</p>
            ) : (
                <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr style={{ backgroundColor: '#f2f2f2' }}>
                            <th>Service</th>
                            <th>Date & Time</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {bookings.map(b => (
                            <tr key={b.id}>
                                <td>{b.serviceName}</td>
                                <td>{new Date(b.bookingDate).toLocaleString()}</td>
                                <td style={{ 
                                    fontWeight: 'bold', 
                                    color: b.status === 'PENDING' ? 'orange' : 'green' 
                                }}>
                                    {b.status}
                                </td>
                                <td>
                                    {b.status === 'PENDING' && (
                                        <button 
                                            onClick={() => handleCancel(b.id)}
                                            className="btn-danger"
                                            style={{ padding: '4px 8px', fontSize: '0.8rem' }}
                                        >
                                            Cancel
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default MyBookingsPage;
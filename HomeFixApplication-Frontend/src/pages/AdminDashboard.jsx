import React, { useState, useEffect } from 'react';
import { getAllBookings, updateBookingStatus } from '../services/api';

function AdminDashboard() {
    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        loadBookings();
    }, []);

    const loadBookings = async () => {
        const data = await getAllBookings();
        setBookings(data);
    };

    const handleStatusChange = async (id, newStatus) => {
        try {
            await updateBookingStatus(id, newStatus);
            alert("Status updated!");
            loadBookings(); // Refresh the list
        } catch (err) {
            alert("Update failed");
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1>Admin Dashboard - Manage Bookings</h1>
            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#eee' }}>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Service</th>
                        <th>Date</th>
                        <th>Current Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.map(b => (
                        <tr key={b.id}>
                            <td>{b.id}</td>
                            <td>{b.userName}</td>
                            <td>{b.serviceName}</td>
                            <td>{new Date(b.bookingDate).toLocaleString()}</td>
                            <td><strong>{b.status}</strong></td>
                            <td>
                                <button onClick={() => handleStatusChange(b.id, 'CONFIRMED')}>Confirm</button>
                                <button onClick={() => handleStatusChange(b.id, 'COMPLETED')} style={{ marginLeft: '5px' }}>Complete</button>
                                <button onClick={() => handleStatusChange(b.id, 'CANCELLED')} style={{ marginLeft: '5px', color: 'red' }}>Cancel</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminDashboard;
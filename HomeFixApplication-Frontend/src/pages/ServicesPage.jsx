import React, { useState, useEffect } from 'react';
import { getAllServices, createBooking } from '../services/api';
import { useAuth } from '../context/AuthContext';

function ServicesPage() {
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [category, setCategory] = useState('');

    const { user } = useAuth(); // Get the logged-in user
    
    // We'll store the selected date for each service ID
    const [selectedDates, setSelectedDates] = useState({});

    useEffect(() => {
        const fetchServices = async () => {
            const data = await getAllServices(searchTerm, category);
            setServices(data);
            setLoading(false);
        };
        fetchServices();
    }, [searchTerm, category]); // Re-run whenever search or category changes

    const handleDateChange = (serviceId, date) => {
        setSelectedDates({ ...selectedDates, [serviceId]: date });
    };

    const handleBook = async (serviceId) => {
        const date = selectedDates[serviceId];
        if (!date) {
            alert("Please select a date and time first!");
            return;
        }

        try {
            const bookingData = {
                serviceId: serviceId,
                bookingDate: date
            };
            await createBooking(bookingData, user.email);
            alert("Booking successful! An admin will review it.");
        } catch (err) {
            alert("Booking failed. Please try again.");
        }
    };

    if (loading) return <h2>Loading services...</h2>;

    console.log("ALL SERVICES:", services);

    return (
        <div className="container">
            <h1 style={{ marginBottom: '2rem' }}>Available Services</h1>

            <div style={{ display: 'flex', gap: '1rem', marginBottom: '2rem' }}>
                <input 
                    type="text" 
                    placeholder="Search services (e.g. 'AC')..." 
                    style={{ flex: 3, padding: '10px', borderRadius: '5px', border: '1px solid #ccc' }}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                <select 
                    style={{ flex: 1, padding: '10px', borderRadius: '5px' }}
                    onChange={(e) => setCategory(e.target.value)}
                >
                    <option value="">All Categories</option>
                    <option value="Repairs">Repairs</option>
                    <option value="Cleaning">Cleaning</option>
                    <option value="Painting">Painting</option>
                </select>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '2rem' }}>
                {services.map(service => (
                    <div key={service.id} className="card">
                        <img 
                            // If it starts with http, use it directly. Otherwise, it's an old local path.
                            src={service.imageUrl?.startsWith('http') 
                                ? service.imageUrl 
                                : `http://localhost:8080${service.imageUrl}`} 
                            alt={service.name} 
                            style={{ width: '100%', height: '200px', objectFit: 'cover', borderRadius: '8px' }} 
                        />
                        <span style={{ fontSize: '0.8rem', color: 'var(--primary)', fontWeight: 'bold' }}>
                            {service.category}
                        </span>
                        <h3 style={{ margin: '0.5rem 0' }}>{service.name}</h3>
                        <p style={{ color: 'var(--secondary)', fontSize: '0.9rem' }}>{service.description}</p>
                        <p style={{ fontSize: '1.25rem', fontWeight: 'bold' }}>${service.price}</p>
                        
                        {user ? (
                            <div style={{ marginTop: '1rem' }}>
                                <input 
                                    type="datetime-local" 
                                    className="input"
                                    style={{ width: '100%', marginBottom: '0.5rem', padding: '0.5rem' }}
                                    onChange={(e) => handleDateChange(service.id, e.target.value)}
                                />
                                <button 
                                    className="btn-success" 
                                    style={{ width: '100%' }} 
                                    onClick={() => handleBook(service.id)}
                                >
                                    Book Now
                                </button>
                            </div>
                        ) : (
                            <p style={{ color: 'var(--danger)', fontSize: '0.8rem' }}>
                                Login to book this service.
                            </p>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ServicesPage;

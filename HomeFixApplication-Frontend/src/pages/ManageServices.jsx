import React, { useState, useEffect } from 'react';
import { getAllServices, addService, deleteService } from '../services/api';

function ManageServices() {
    const [services, setServices] = useState([]);
    const [formData, setFormData] = useState({ name: '', description: '', price: '', category: '' });
    const [image, setImage] = useState(null);

    const loadServices = async () => {
        try {
            const data = await getAllServices(); 
            setServices(data);
        } catch (err) {
            console.error("Error loading services for admin:", err);
        }
    };

    // 3. Third, call the useEffect (Now it can "see" the function above it)
    useEffect(() => { 
        loadServices(); 
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this service?")) {
            try {
                await deleteService(id);
                alert("Service deleted successfully");
                loadServices(); // Refresh the list after deleting
            } catch (err) {
                alert("Failed to delete service. It might be linked to existing bookings.");
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Create the FormData container
        const data = new FormData();
        
        // 1. Construct the JSON object
        const serviceJson = {
            name: formData.name,
            description: formData.description,
            price: formData.price,
            category: formData.category
        };

        // 2. Wrap JSON in a Blob so the backend knows it is JSON
        data.append("service", new Blob([JSON.stringify(serviceJson)], { type: "application/json" }));
        
        // 3. Add the image file
        if (image) {
            data.append("image", image);
        }

        try {
            await addService(data);
            alert("Service added successfully!");
            setFormData({ name: '', description: '', price: '', category: '' });
            setImage(null);
            loadServices();
        } catch (err) {
            console.error(err);
            alert("Error adding service");
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1>Manage Services</h1>
            
            {/* ADD SERVICE FORM */}
            <form onSubmit={handleSubmit} style={{ marginBottom: '30px', background: '#f9f9f9', padding: '15px' }}>
                <h3>Add New Service</h3>
                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
                    <input type="text" placeholder="Name (min 3 chars)" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} required />
                    <input type="text" placeholder="Category" value={formData.category} onChange={(e) => setFormData({...formData, category: e.target.value})} required />
                    <input type="number" placeholder="Price" value={formData.price} onChange={(e) => setFormData({...formData, price: e.target.value})} required />
                    <textarea placeholder="Description" value={formData.description} onChange={(e) => setFormData({...formData, description: e.target.value})} required />
                    
                    {/* Cleaned up file input */}
                    <input type="file" onChange={(e) => setImage(e.target.files[0])} required />
            
                    <button type="submit" className="btn-primary">Add Service</button>
                </div>
            </form>

            {/* SERVICES LIST */}
            <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#eee' }}>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {services.map(s => (
                        <tr key={s.id}>
                            <td>{s.name}</td>
                            <td>{s.category}</td>
                            <td>${s.price}</td>
                            <td>
                                <button onClick={() => handleDelete(s.id)} style={{ color: 'red' }}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ManageServices;

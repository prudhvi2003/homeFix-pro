import axios from 'axios';

// 1. Get the URL from Vercel environment variables
const API_URL = import.meta.env.VITE_API_URL;

console.log("api.js initialized. Target Backend:", API_URL);

// 2. Create the instance with the Base URL
const api = axios.create({
    baseURL: API_URL
});

export const getAllServices = async (search = '', category = '') => {
    // We only need '/services' because the base URL is already attached
    const response = await api.get('/services', {
        params: { search, category }
    });
    return response.data;
};

export const login = async (credentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data;
};

export const register = async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
};

export const createBooking = async (bookingData, email) => {
    const response = await api.post(`/bookings?email=${email}`, bookingData);
    return response.data;
};

export const getMyBookings = async (email) => {
    const response = await api.get(`/bookings/my-bookings?email=${email}`);
    return response.data;
};

export const getAllBookings = async () => {
    const response = await api.get('/bookings/all');
    return response.data;
};

export const updateBookingStatus = async (id, status) => {
    const response = await api.put(`/bookings/${id}/status?status=${status}`);
    return response.data;
};

export const deleteService = async (id) => {
    await api.delete(`/services/${id}`);
};

export const addService = async (formData) => {
    const response = await api.post('/services', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
};

export const cancelBooking = async (id, email) => {
    await api.delete(`/bookings/${id}/cancel?email=${email}`);
};

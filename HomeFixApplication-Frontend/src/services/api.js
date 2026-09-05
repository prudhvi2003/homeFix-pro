import axios from 'axios';

// This checks if there is a production URL, otherwise uses localhost
const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

const axiosInstance = axios.create({
    baseURL: API_URL
});

console.log("api.js loaded");

export const getAllServices = async (search = '', category = '') => {
    // This creates a URL like: /api/services?search=ac&category=Repairs
    const response = await axiosInstance.get(`${API_URL}/services`, {
        params: { search, category }
    });
    return response.data;
};

export const login = async (credentials) => {
    try {
        const response = await axiosInstance.post(`${API_URL}/auth/login`, credentials);
        return response.data; // This returns { token: "...", user: {...} }
    } catch (error) {
        console.error("Login error:", error);
        throw error;
    }
};

export const createBooking = async (bookingData, email) => {
    try {
        // In Phase 15, we designed the backend to take email as a query parameter
        const response = await axiosInstance.post(`${API_URL}/bookings?email=${email}`, bookingData);
        return response.data;
    } catch (error) {
        console.error("Booking error:", error);
        throw error;
    }
};

export const getMyBookings = async (email) => {
    try {
        const response = await axiosInstance.get(`${API_URL}/bookings/my-bookings?email=${email}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching bookings:", error);
        throw error;
    }
};

export const getAllBookings = async () => {
    const response = await axiosInstance.get(`${API_URL}/bookings/all`);
    return response.data;
};

export const updateBookingStatus = async (id, status) => {
    const response = await axiosInstance.put(`${API_URL}/bookings/${id}/status?status=${status}`);
    return response.data;
};

export const deleteService = async (id) => {
    await axiosInstance.delete(`${API_URL}/services/${id}`);
};

export const addService = async (formData) => {
    // Note: formData here is a FormData object, not a simple object
    const response = await axiosInstance.post(`${API_URL}/services`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
};

export const register = async (userData) => {
    const response = await axiosInstance.post(`${API_URL}/auth/register`, userData);
    return response.data;
};

export const cancelBooking = async (id, email) => {
    await axiosInstance.delete(`${API_URL}/bookings/${id}/cancel?email=${email}`);
};
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
import axios from "axios";


export async function getRequest(apiEndpoint) {
    axios
        .get(`${API_BASE_URL}/${apiEndpoint}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.log(`An error occurred making a get request to ${apiEndpoint}:`, error);
            throw error;
        });
}


export async function postRequest(apiEndpoint, data) {
    axios
        .post(`${API_BASE_URL}/${apiEndpoint}`, data, {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.log(`An error occurred making a post request to ${apiEndpoint}:`, error);
            throw error;
        });
}


export async function deleteRequest(apiEndpoint) {
    axios
        .delete(`${API_BASE_URL}/${apiEndpoint}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.log(`An error occurred making a delete request to ${apiEndpoint}:`, error);
            throw error;
        });
}


export async function putRequest(apiEndpoint, data) {
    axios
        .put(`${API_BASE_URL}/${apiEndpoint}`, data, {
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.log(`An error occurred making a put request to ${apiEndpoint}:`, error);
            throw error;
        });
}


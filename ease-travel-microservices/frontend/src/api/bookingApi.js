import { apiRequest } from "./http";

export function createBooking(payload, token) {
  return apiRequest(
    "/api/v1/bookings",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getAllBookings(token) {
  return apiRequest("/api/v1/bookings", {}, token);
}

export function getBookingsByUser(userId, token) {
  return apiRequest(`/api/v1/bookings/user/${userId}`, {}, token);
}

export function cancelBooking(id, token) {
  return apiRequest(
    `/api/v1/bookings/${id}/cancel`,
    {
      method: "PUT"
    },
    token
  );
}

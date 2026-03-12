import { apiRequest } from "./http";

export function processPayment(payload, token) {
  return apiRequest(
    "/api/v1/payments",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getPaymentByBooking(bookingId, token) {
  return apiRequest(`/api/v1/payments/booking/${bookingId}`, {}, token);
}

export function getPaymentsByUser(userId, token) {
  return apiRequest(`/api/v1/payments/user/${userId}`, {}, token);
}

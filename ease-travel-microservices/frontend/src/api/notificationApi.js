import { apiRequest } from "./http";

export function sendNotification(payload, token) {
  return apiRequest(
    "/api/v1/notifications/send",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getAllNotifications(token) {
  return apiRequest("/api/v1/notifications", {}, token);
}

export function getNotificationsByUser(userId, token) {
  return apiRequest(`/api/v1/notifications/user/${userId}`, {}, token);
}

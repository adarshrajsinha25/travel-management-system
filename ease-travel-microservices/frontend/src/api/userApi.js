import { apiRequest } from "./http";

export function getAllUsers(token) {
  return apiRequest("/api/v1/users", {}, token);
}

export function getUserById(id, token) {
  return apiRequest(`/api/v1/users/${id}`, {}, token);
}

export function updateUser(id, payload, token) {
  return apiRequest(
    `/api/v1/users/${id}`,
    {
      method: "PUT",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function deleteUser(id, token) {
  return apiRequest(
    `/api/v1/users/${id}`,
    {
      method: "DELETE"
    },
    token
  );
}

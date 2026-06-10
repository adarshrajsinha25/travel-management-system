import { API_BASE_URL } from "../config";

function normalizeApiError(status, payload) {
  if (payload?.message) {
    return new Error(payload.message);
  }
  if (payload?.error) {
    return new Error(payload.error);
  }
  return new Error(`Request failed with status ${status}`);
}

export async function apiRequest(path, options = {}, token) {
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {})
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers
  });

  const contentType = response.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const payload = isJson ? await response.json() : null;

  if (!response.ok) {
    throw normalizeApiError(response.status, payload);
  }

  if (payload && Object.prototype.hasOwnProperty.call(payload, "success")) {
    if (payload.success === false) {
      throw new Error(payload.message || "Operation failed");
    }
    return payload.data;
  }

  return payload;
}

import { apiRequest } from "./http";

export function getTrips(token) {
  return apiRequest("/api/v1/trips", {}, token);
}

export function getAvailableTrips(token) {
  return apiRequest("/api/v1/trips/available", {}, token);
}

export function createTrip(payload, token) {
  return apiRequest(
    "/api/v1/trips",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getFlights(token) {
  return apiRequest("/api/v1/flights", {}, token);
}

export function searchFlights(origin, destination, token) {
  const params = new URLSearchParams({ origin, destination });
  return apiRequest(`/api/v1/flights/search?${params.toString()}`, {}, token);
}

export function createFlight(payload, token) {
  return apiRequest(
    "/api/v1/flights",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getHotels(token) {
  return apiRequest("/api/v1/hotels", {}, token);
}

export function searchHotels(city, token) {
  const params = new URLSearchParams({ city });
  return apiRequest(`/api/v1/hotels/search?${params.toString()}`, {}, token);
}

export function createHotel(payload, token) {
  return apiRequest(
    "/api/v1/hotels",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

export function getDestinations(token) {
  return apiRequest("/api/v1/destinations", {}, token);
}

export function searchDestinations(country, token) {
  const params = new URLSearchParams({ country });
  return apiRequest(`/api/v1/destinations/search?${params.toString()}`, {}, token);
}

export function createDestination(payload, token) {
  return apiRequest(
    "/api/v1/destinations",
    {
      method: "POST",
      body: JSON.stringify(payload)
    },
    token
  );
}

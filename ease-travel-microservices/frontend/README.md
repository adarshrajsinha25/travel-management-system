# EaseTravel Frontend (React + Vite)

This frontend is connected to the backend API gateway at `http://localhost:8080` and implements:

- Authentication (`/api/v1/auth/register`, `/api/v1/auth/login`)
- Dashboard overview
- Trips/Flights/Hotels/Destinations create + list + search
- Bookings create/list/cancel
- Payments process + history
- Notifications send + list
- Users profile update + admin user management

## Run

```bash
npm install
npm run dev
```

App URL (default): `http://localhost:5173`

## Build

```bash
npm run build
```

## Notes

- JWT token is stored in local storage and attached as `Authorization: Bearer <token>`.
- Most routes are protected and require backend services + API gateway running.
- For best results, start backend services as documented in `backend/README.md`.

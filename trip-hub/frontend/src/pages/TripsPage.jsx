import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthContext";
import {
  createDestination,
  createFlight,
  createHotel,
  createTrip,
  getDestinations,
  getFlights,
  getHotels,
  getTrips,
  searchDestinations,
  searchFlights,
  searchHotels
} from "../api/tripApi";
import { formatCurrency, formatDate, formatDateTime } from "../utils/format";

const tabs = ["trips", "flights", "hotels", "destinations"];

export default function TripsPage() {
  const { token } = useAuth();
  const [activeTab, setActiveTab] = useState("trips");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const [trips, setTrips] = useState([]);
  const [flights, setFlights] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [destinations, setDestinations] = useState([]);

  const [tripForm, setTripForm] = useState({
    name: "",
    description: "",
    price: "",
    availableSeats: "",
    departureDate: "",
    returnDate: "",
    imageUrl: ""
  });

  const [flightForm, setFlightForm] = useState({
    flightNumber: "",
    airline: "",
    origin: "",
    destination: "",
    departureTime: "",
    arrivalTime: "",
    price: "",
    availableSeats: ""
  });

  const [hotelForm, setHotelForm] = useState({
    name: "",
    address: "",
    city: "",
    stars: "3",
    pricePerNight: "",
    availableRooms: ""
  });

  const [destinationForm, setDestinationForm] = useState({
    name: "",
    country: "",
    description: "",
    imageUrl: ""
  });

  const [flightSearchForm, setFlightSearchForm] = useState({ origin: "", destination: "" });
  const [hotelSearchCity, setHotelSearchCity] = useState("");
  const [destinationSearchCountry, setDestinationSearchCountry] = useState("");

  const refreshData = async () => {
    setLoading(true);
    setError("");
    try {
      const [t, f, h, d] = await Promise.all([
        getTrips(token),
        getFlights(token),
        getHotels(token),
        getDestinations(token)
      ]);
      setTrips(t);
      setFlights(f);
      setHotels(h);
      setDestinations(d);
    } catch (err) {
      setError(err.message || "Failed to load trip resources");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshData();
  }, [token]);

  const onCreateTrip = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    try {
      await createTrip(
        {
          ...tripForm,
          price: Number(tripForm.price),
          availableSeats: Number(tripForm.availableSeats),
          returnDate: tripForm.returnDate || null
        },
        token
      );
      setSuccess("Trip created");
      setTripForm({
        name: "",
        description: "",
        price: "",
        availableSeats: "",
        departureDate: "",
        returnDate: "",
        imageUrl: ""
      });
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to create trip");
    }
  };

  const onCreateFlight = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    try {
      await createFlight(
        {
          ...flightForm,
          departureTime: new Date(flightForm.departureTime).toISOString(),
          arrivalTime: new Date(flightForm.arrivalTime).toISOString(),
          price: Number(flightForm.price),
          availableSeats: Number(flightForm.availableSeats)
        },
        token
      );
      setSuccess("Flight created");
      setFlightForm({
        flightNumber: "",
        airline: "",
        origin: "",
        destination: "",
        departureTime: "",
        arrivalTime: "",
        price: "",
        availableSeats: ""
      });
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to create flight");
    }
  };

  const onCreateHotel = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    try {
      await createHotel(
        {
          ...hotelForm,
          stars: Number(hotelForm.stars),
          pricePerNight: Number(hotelForm.pricePerNight),
          availableRooms: Number(hotelForm.availableRooms)
        },
        token
      );
      setSuccess("Hotel created");
      setHotelForm({
        name: "",
        address: "",
        city: "",
        stars: "3",
        pricePerNight: "",
        availableRooms: ""
      });
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to create hotel");
    }
  };

  const onCreateDestination = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    try {
      await createDestination(destinationForm, token);
      setSuccess("Destination created");
      setDestinationForm({
        name: "",
        country: "",
        description: "",
        imageUrl: ""
      });
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to create destination");
    }
  };

  const onSearchFlights = async (event) => {
    event.preventDefault();
    if (!flightSearchForm.origin || !flightSearchForm.destination) {
      return;
    }
    try {
      const result = await searchFlights(flightSearchForm.origin, flightSearchForm.destination, token);
      setFlights(result);
    } catch (err) {
      setError(err.message || "Failed to search flights");
    }
  };

  const onSearchHotels = async (event) => {
    event.preventDefault();
    if (!hotelSearchCity) {
      return;
    }
    try {
      const result = await searchHotels(hotelSearchCity, token);
      setHotels(result);
    } catch (err) {
      setError(err.message || "Failed to search hotels");
    }
  };

  const onSearchDestinations = async (event) => {
    event.preventDefault();
    if (!destinationSearchCountry) {
      return;
    }
    try {
      const result = await searchDestinations(destinationSearchCountry, token);
      setDestinations(result);
    } catch (err) {
      setError(err.message || "Failed to search destinations");
    }
  };

  return (
    <Layout title="Trips, Flights, Hotels & Destinations">
      <div className="tab-list">
        {tabs.map((tab) => (
          <button
            key={tab}
            type="button"
            className={activeTab === tab ? "tab active" : "tab"}
            onClick={() => setActiveTab(tab)}
          >
            {tab}
          </button>
        ))}
        <button type="button" className="tab" onClick={refreshData}>
          Refresh
        </button>
      </div>

      {success ? <p className="success-text">{success}</p> : null}
      {error ? <p className="error-text">{error}</p> : null}
      {loading ? <p>Loading data...</p> : null}

      {activeTab === "trips" ? (
        <section className="content-grid">
          <form className="panel form-grid" onSubmit={onCreateTrip}>
            <h3>Create Trip</h3>
            <label>
              Name
              <input
                value={tripForm.name}
                onChange={(e) => setTripForm((prev) => ({ ...prev, name: e.target.value }))}
                required
              />
            </label>
            <label>
              Description
              <textarea
                value={tripForm.description}
                onChange={(e) => setTripForm((prev) => ({ ...prev, description: e.target.value }))}
              />
            </label>
            <label>
              Price
              <input
                type="number"
                min="1"
                step="0.01"
                value={tripForm.price}
                onChange={(e) => setTripForm((prev) => ({ ...prev, price: e.target.value }))}
                required
              />
            </label>
            <label>
              Available seats
              <input
                type="number"
                min="1"
                value={tripForm.availableSeats}
                onChange={(e) => setTripForm((prev) => ({ ...prev, availableSeats: e.target.value }))}
                required
              />
            </label>
            <label>
              Departure date
              <input
                type="date"
                value={tripForm.departureDate}
                onChange={(e) => setTripForm((prev) => ({ ...prev, departureDate: e.target.value }))}
                required
              />
            </label>
            <label>
              Return date
              <input
                type="date"
                value={tripForm.returnDate}
                onChange={(e) => setTripForm((prev) => ({ ...prev, returnDate: e.target.value }))}
              />
            </label>
            <label>
              Image URL
              <input
                value={tripForm.imageUrl}
                onChange={(e) => setTripForm((prev) => ({ ...prev, imageUrl: e.target.value }))}
              />
            </label>
            <button type="submit">Create trip</button>
          </form>

          <div className="panel table-wrap">
            <h3>Trips ({trips.length})</h3>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Price</th>
                  <th>Seats</th>
                  <th>Status</th>
                  <th>Departure</th>
                </tr>
              </thead>
              <tbody>
                {trips.map((trip) => (
                  <tr key={trip.id}>
                    <td>{trip.id}</td>
                    <td>{trip.name}</td>
                    <td>{formatCurrency(trip.price)}</td>
                    <td>{trip.availableSeats}</td>
                    <td>{trip.status}</td>
                    <td>{formatDate(trip.departureDate)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      ) : null}

      {activeTab === "flights" ? (
        <section className="content-grid">
          <div className="panel">
            <form className="form-grid" onSubmit={onCreateFlight}>
              <h3>Create Flight</h3>
              <label>
                Flight number
                <input
                  value={flightForm.flightNumber}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, flightNumber: e.target.value }))}
                  required
                />
              </label>
              <label>
                Airline
                <input
                  value={flightForm.airline}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, airline: e.target.value }))}
                  required
                />
              </label>
              <label>
                Origin
                <input
                  value={flightForm.origin}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, origin: e.target.value }))}
                  required
                />
              </label>
              <label>
                Destination
                <input
                  value={flightForm.destination}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, destination: e.target.value }))}
                  required
                />
              </label>
              <label>
                Departure time
                <input
                  type="datetime-local"
                  value={flightForm.departureTime}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, departureTime: e.target.value }))}
                  required
                />
              </label>
              <label>
                Arrival time
                <input
                  type="datetime-local"
                  value={flightForm.arrivalTime}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, arrivalTime: e.target.value }))}
                  required
                />
              </label>
              <label>
                Price
                <input
                  type="number"
                  min="1"
                  step="0.01"
                  value={flightForm.price}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, price: e.target.value }))}
                  required
                />
              </label>
              <label>
                Available seats
                <input
                  type="number"
                  min="1"
                  value={flightForm.availableSeats}
                  onChange={(e) => setFlightForm((prev) => ({ ...prev, availableSeats: e.target.value }))}
                  required
                />
              </label>
              <button type="submit">Create flight</button>
            </form>
          </div>

          <div className="panel table-wrap">
            <h3>Search Flights</h3>
            <form className="inline-form" onSubmit={onSearchFlights}>
              <input
                placeholder="Origin"
                value={flightSearchForm.origin}
                onChange={(e) => setFlightSearchForm((prev) => ({ ...prev, origin: e.target.value }))}
              />
              <input
                placeholder="Destination"
                value={flightSearchForm.destination}
                onChange={(e) =>
                  setFlightSearchForm((prev) => ({ ...prev, destination: e.target.value }))
                }
              />
              <button type="submit">Search</button>
              <button type="button" onClick={refreshData}>
                Clear
              </button>
            </form>

            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Flight #</th>
                  <th>Route</th>
                  <th>Price</th>
                  <th>Seats</th>
                  <th>Departure</th>
                </tr>
              </thead>
              <tbody>
                {flights.map((flight) => (
                  <tr key={flight.id}>
                    <td>{flight.id}</td>
                    <td>{flight.flightNumber}</td>
                    <td>
                      {flight.origin} {"->"} {flight.destination}
                    </td>
                    <td>{formatCurrency(flight.price)}</td>
                    <td>{flight.availableSeats}</td>
                    <td>{formatDateTime(flight.departureTime)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      ) : null}

      {activeTab === "hotels" ? (
        <section className="content-grid">
          <div className="panel">
            <form className="form-grid" onSubmit={onCreateHotel}>
              <h3>Create Hotel</h3>
              <label>
                Name
                <input
                  value={hotelForm.name}
                  onChange={(e) => setHotelForm((prev) => ({ ...prev, name: e.target.value }))}
                  required
                />
              </label>
              <label>
                Address
                <input
                  value={hotelForm.address}
                  onChange={(e) => setHotelForm((prev) => ({ ...prev, address: e.target.value }))}
                  required
                />
              </label>
              <label>
                City
                <input
                  value={hotelForm.city}
                  onChange={(e) => setHotelForm((prev) => ({ ...prev, city: e.target.value }))}
                  required
                />
              </label>
              <label>
                Stars
                <input
                  type="number"
                  min="1"
                  max="5"
                  value={hotelForm.stars}
                  onChange={(e) => setHotelForm((prev) => ({ ...prev, stars: e.target.value }))}
                  required
                />
              </label>
              <label>
                Price/night
                <input
                  type="number"
                  min="1"
                  step="0.01"
                  value={hotelForm.pricePerNight}
                  onChange={(e) => setHotelForm((prev) => ({ ...prev, pricePerNight: e.target.value }))}
                  required
                />
              </label>
              <label>
                Available rooms
                <input
                  type="number"
                  min="0"
                  value={hotelForm.availableRooms}
                  onChange={(e) =>
                    setHotelForm((prev) => ({ ...prev, availableRooms: e.target.value }))
                  }
                  required
                />
              </label>
              <button type="submit">Create hotel</button>
            </form>
          </div>

          <div className="panel table-wrap">
            <h3>Search Hotels</h3>
            <form className="inline-form" onSubmit={onSearchHotels}>
              <input
                placeholder="City"
                value={hotelSearchCity}
                onChange={(e) => setHotelSearchCity(e.target.value)}
              />
              <button type="submit">Search</button>
              <button type="button" onClick={refreshData}>
                Clear
              </button>
            </form>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>City</th>
                  <th>Stars</th>
                  <th>Price/night</th>
                  <th>Rooms</th>
                </tr>
              </thead>
              <tbody>
                {hotels.map((hotel) => (
                  <tr key={hotel.id}>
                    <td>{hotel.id}</td>
                    <td>{hotel.name}</td>
                    <td>{hotel.city}</td>
                    <td>{hotel.stars}</td>
                    <td>{formatCurrency(hotel.pricePerNight)}</td>
                    <td>{hotel.availableRooms}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      ) : null}

      {activeTab === "destinations" ? (
        <section className="content-grid">
          <div className="panel">
            <form className="form-grid" onSubmit={onCreateDestination}>
              <h3>Create Destination</h3>
              <label>
                Name
                <input
                  value={destinationForm.name}
                  onChange={(e) =>
                    setDestinationForm((prev) => ({ ...prev, name: e.target.value }))
                  }
                  required
                />
              </label>
              <label>
                Country
                <input
                  value={destinationForm.country}
                  onChange={(e) =>
                    setDestinationForm((prev) => ({ ...prev, country: e.target.value }))
                  }
                  required
                />
              </label>
              <label>
                Description
                <textarea
                  value={destinationForm.description}
                  onChange={(e) =>
                    setDestinationForm((prev) => ({ ...prev, description: e.target.value }))
                  }
                />
              </label>
              <label>
                Image URL
                <input
                  value={destinationForm.imageUrl}
                  onChange={(e) =>
                    setDestinationForm((prev) => ({ ...prev, imageUrl: e.target.value }))
                  }
                />
              </label>
              <button type="submit">Create destination</button>
            </form>
          </div>

          <div className="panel table-wrap">
            <h3>Search Destinations</h3>
            <form className="inline-form" onSubmit={onSearchDestinations}>
              <input
                placeholder="Country"
                value={destinationSearchCountry}
                onChange={(e) => setDestinationSearchCountry(e.target.value)}
              />
              <button type="submit">Search</button>
              <button type="button" onClick={refreshData}>
                Clear
              </button>
            </form>

            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Country</th>
                  <th>Description</th>
                </tr>
              </thead>
              <tbody>
                {destinations.map((destination) => (
                  <tr key={destination.id}>
                    <td>{destination.id}</td>
                    <td>{destination.name}</td>
                    <td>{destination.country}</td>
                    <td>{destination.description || "-"}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      ) : null}
    </Layout>
  );
}

import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const navItems = [
  { to: "/", label: "Dashboard" },
  { to: "/trips", label: "Trips" },
  { to: "/bookings", label: "Bookings" },
  { to: "/payments", label: "Payments" },
  { to: "/notifications", label: "Notifications" },
  { to: "/users", label: "Users" }
];

export default function Layout({ title, children }) {
  const { authState, logout } = useAuth();
  const navigate = useNavigate();

  const onLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="app-shell">
      <aside className="side-nav">
        <Link to="/" className="brand">
          <span>ET</span>
          <div>
            <strong>EaseTravel</strong>
            <small>Microservice Console</small>
          </div>
        </Link>
        <nav>
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                isActive ? "nav-item nav-item-active" : "nav-item"
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
        <div className="user-chip">
          <p>{authState?.email}</p>
          <small>{authState?.role}</small>
          <button type="button" onClick={onLogout}>
            Sign out
          </button>
        </div>
      </aside>

      <main className="page-wrap">
        <header className="page-header">
          <h1>{title}</h1>
        </header>
        {children}
      </main>
    </div>
  );
}

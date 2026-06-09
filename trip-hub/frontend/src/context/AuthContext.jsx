import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { STORAGE_KEYS } from "../config";
import { login as loginApi, register as registerApi } from "../api/authApi";

const AuthContext = createContext(null);

function decodeJwtPayload(token) {
  try {
    const payload = token.split(".")[1];
    const normalized = payload.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(window.atob(normalized));
  } catch {
    return null;
  }
}

function readStoredAuth() {
  const raw = localStorage.getItem(STORAGE_KEYS.auth);
  if (!raw) {
    return null;
  }
  try {
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function AuthProvider({ children }) {
  const [authState, setAuthState] = useState(() => readStoredAuth());

  useEffect(() => {
    if (authState) {
      localStorage.setItem(STORAGE_KEYS.auth, JSON.stringify(authState));
    } else {
      localStorage.removeItem(STORAGE_KEYS.auth);
    }
  }, [authState]);

  const login = async (credentials) => {
    const authResponse = await loginApi(credentials);
    const jwtClaims = decodeJwtPayload(authResponse.token);
    const roleFromToken = jwtClaims?.role?.replace("ROLE_", "") || authResponse.role;

    const nextState = {
      token: authResponse.token,
      tokenType: authResponse.tokenType,
      userId: authResponse.userId,
      email: authResponse.email,
      role: roleFromToken
    };
    setAuthState(nextState);
    return nextState;
  };

  const register = async (payload) => registerApi(payload);

  const logout = () => {
    setAuthState(null);
  };

  const value = useMemo(
    () => ({
      authState,
      token: authState?.token || "",
      isAuthenticated: Boolean(authState?.token),
      isAdmin: authState?.role === "ADMIN",
      login,
      register,
      logout
    }),
    [authState]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return ctx;
}

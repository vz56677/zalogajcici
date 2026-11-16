import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import * as api from '../services/api.js';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => api.session());

  // boja teme ovisi o ulozi (body[data-role=org] u app.css)
  useEffect(() => { document.body.dataset.role = user?.role ?? 'buyer'; }, [user]);

  const loginGoogle = useCallback(async role => { await api.loginGoogle(role); setUser(api.session()); }, []);
  const logout = useCallback(() => { api.logout(); setUser(null); }, []);

  const value = useMemo(() => ({ user, loginGoogle, logout }), [user, loginGoogle, logout]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export const useAuth = () => useContext(AuthContext);

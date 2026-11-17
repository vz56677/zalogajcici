import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState
} from 'react';

import * as api from '../services/api.js';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let active = true;

    async function initialize() {
      try {
        const currentUser = await api.session();

        if (active) {
          setUser(currentUser);
        }
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }

    initialize();

    return () => {
      active = false;
    };
  }, []);

  useEffect(() => {
    document.body.dataset.role = user?.role ?? 'buyer';
  }, [user]);

  const loginGoogle = useCallback(role => {
    api.loginGoogle(role);
  }, []);

  const selectRole = useCallback(async (role, storeData) => {
    const currentUser = await api.selectRole(role, storeData);
    setUser(currentUser);
    return currentUser;
  }, []);

  const logout = useCallback(async () => {
    await api.logout();
    setUser(null);
  }, []);

  const value = useMemo(() => ({
    user,
    loading,
    loginGoogle,
    selectRole,
    logout
  }), [user, loading, loginGoogle, selectRole, logout]);

  return (
      <AuthContext.Provider value={value}>
        {children}
      </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
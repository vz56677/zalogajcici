import { createContext, useCallback, useContext, useRef, useState } from 'react';

const ToastContext = createContext(() => {});

export function ToastProvider({ children }) {
  const [items, setItems] = useState([]);
  const nextId = useRef(0);

  const toast = useCallback(msg => {
    const id = nextId.current++;
    setItems(l => [...l, { id, msg }]);
    setTimeout(() => setItems(l => l.filter(t => t.id !== id)), 2600);
  }, []);

  return (
    <ToastContext.Provider value={toast}>
      {children}
      {items.map(t => <div key={t.id} className="toast" role="status">{t.msg}</div>)}
    </ToastContext.Provider>
  );
}

export const useToast = () => useContext(ToastContext);

import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Dev server: kad se MOCK u services/api.js ugasi, pozivi prema backendu
// (Spring Boot na :8080) idu kroz proxy pa nema CORS problema ni dodatne konfiguracije.
const backend = 'http://localhost:8080';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': backend,
      '/oauth2': backend,
      '/login': backend,
      '/logout': backend,
    },
  },
});

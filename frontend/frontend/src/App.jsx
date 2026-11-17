import { useEffect } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import { useAuth } from './context/AuthContext.jsx';
import AuthPage from './pages/AuthPage.jsx';
import { HomePage, DetailPage } from './pages/BrowsePages.jsx';
import { OrdersPage, CampaignsPage, MapPage, ProfilePage } from './pages/AccountPages.jsx';
import { StoreDashboard, StoreOffersPage, StoreRequestsPage, StoreCampaignsPage, StoreProfilePage, StoreReviewsPage } from './pages/StorePages.jsx';

export default function App() {
  const { user } = useAuth();
  const { pathname } = useLocation();

  useEffect(() => { window.scrollTo(0, 0); }, [pathname]);

  if (!user || !user.role) return <AuthPage />;
  const org = user.role === 'org';
  const store = user.role === 'store';
  if (store) return (
    <Routes>
      <Route path="/" element={<StoreDashboard />} />
      <Route path="/store/offers" element={<StoreOffersPage />} />
      <Route path="/store/requests" element={<StoreRequestsPage />} />
      <Route path="/store/campaigns" element={<StoreCampaignsPage />} />
      <Route path="/store/profile" element={<StoreProfilePage />} />
      <Route path="/store/reviews" element={<StoreReviewsPage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );

  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/p/:id" element={<DetailPage />} />
      <Route path="/orders" element={<OrdersPage />} />
      <Route path="/campaigns" element={org ? <CampaignsPage /> : <Navigate to="/" replace />} />
      <Route path="/map" element={!org ? <MapPage /> : <Navigate to="/" replace />} />
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

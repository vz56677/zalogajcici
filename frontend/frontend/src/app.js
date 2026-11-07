import { session } from './services/api.js';
import { authPage } from './pages/auth.js';
import { homePage, detailPage } from './pages/browse.js';
import { ordersPage, campaignsPage, mapPage, profilePage } from './pages/account.js';

const root = document.getElementById('app');
async function render() {
  const user = session();
  document.body.dataset.role = user?.role ?? 'buyer';
  if (!user) return authPage(root, render);
  const [, page, arg] = (location.hash || '#/').split('/');
  if (page === 'p') return detailPage(root, user, arg);
  if (page === 'orders') return ordersPage(root, user);
  if (page === 'campaigns' && user.role === 'org') return campaignsPage(root, user);
  if (page === 'map' && user.role !== 'org') return mapPage(root, user);
  if (page === 'profile') return profilePage(root, user, render);
  return homePage(root, user);
}
addEventListener('hashchange', () => { render(); scrollTo(0, 0); });
render();

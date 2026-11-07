export const esc = s => String(s ?? '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
export const eur = n => n.toFixed(2).replace('.', ',') + ' €';
const I = (d, s = 22) => `<svg width="${s}" height="${s}" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">${d}</svg>`;
export const icon = {
  home: I('<path d="M3 11l9-8 9 8v9a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1z"/>'),
  pin: I('<path d="M12 21s7-6 7-12a7 7 0 0 0-14 0c0 6 7 12 7 12z"/><circle cx="12" cy="9" r="2.5"/>'),
  bag: I('<path d="M5 8h14l-1 12H6z"/><path d="M9 8a3 3 0 0 1 6 0"/>'),
  user: I('<circle cx="12" cy="8" r="4"/><path d="M4 21c1-4 4-6 8-6s7 2 8 6"/>'),
  bell: I('<path d="M6 16V11a6 6 0 0 1 12 0v5l2 2H4z"/><path d="M10 21h4"/>'),
  search: I('<circle cx="11" cy="11" r="7"/><path d="M20 20l-4-4"/>', 18),
  heart: I('<path d="M12 20s-8-5-8-11a4.5 4.5 0 0 1 8-2.5A4.5 4.5 0 0 1 20 9c0 6-8 11-8 11z"/>'),
  gift: I('<rect x="3" y="8" width="18" height="13" rx="1"/><path d="M12 8v13M3 12h18M12 8c-3 0-5-4-2-4 2 0 2 4 2 4zm0 0c3 0 5-4 2-4-2 0-2 4-2 4z"/>'),
  leaf: I('<path d="M5 19C5 9 11 4 20 4c0 9-5 15-15 15zM5 19l8-8"/>'),
};
export const google = `<svg width="18" height="18" viewBox="0 0 48 48" aria-hidden="true"><path fill="#EA4335" d="M24 9.5c3.5 0 6.6 1.2 9.1 3.6l6.8-6.8C35.8 2.4 30.3 0 24 0 14.6 0 6.5 5.4 2.6 13.2l7.9 6.1C12.4 13.6 17.7 9.5 24 9.5z"/><path fill="#4285F4" d="M46.5 24.5c0-1.6-.1-3.1-.4-4.5H24v9h12.7c-.6 3-2.3 5.5-4.8 7.2l7.5 5.8c4.4-4 7.1-10 7.1-17.5z"/><path fill="#FBBC05" d="M10.5 28.7a14.5 14.5 0 0 1 0-9.4l-7.9-6.1a24 24 0 0 0 0 21.6z"/><path fill="#34A853" d="M24 48c6.5 0 11.9-2.1 15.9-5.8l-7.5-5.8c-2.1 1.4-4.8 2.3-8.4 2.3-6.3 0-11.6-4.1-13.5-9.8l-7.9 6.1C6.5 42.6 14.6 48 24 48z"/></svg>`;
export const logoMark = `<div class="logo" style="color:var(--deep)">${icon.bag.replace('width="22" height="22"', 'width="60%" height="60%"')}</div>`;

// ilustracije proizvoda (jednostavni oblici kao na mockupu)
export function art(kind, size = 90) {
  const s = {
    bread: '<ellipse cx="50" cy="52" rx="38" ry="26" fill="#d8955f"/><path d="M34 36l10 32M50 32l8 36M66 38l6 28" stroke="#8a4b2a" stroke-width="5" stroke-linecap="round"/>',
    bowl: '<path d="M10 48h80a40 32 0 0 1-80 0z" fill="#0a6470"/><circle cx="36" cy="42" r="10" fill="#e5c4b0"/><circle cx="56" cy="38" r="9" fill="#a8d5a2"/><circle cx="72" cy="44" r="7" fill="#f09a4a"/>',
    basket: '<path d="M18 40h64l-8 42H26z" fill="#e5c4b0"/><path d="M34 40a16 16 0 0 1 32 0" stroke="#064e54" stroke-width="5" fill="none"/><circle cx="42" cy="58" r="7" fill="#f09a4a"/><circle cx="60" cy="60" r="7" fill="#a8d5a2"/>',
  }[kind] || '';
  return `<svg width="${size}" height="${size}" viewBox="0 0 100 100" aria-hidden="true">${s}</svg>`;
}

export function offerCard(o, role) {
  const free = o.sale === null;
  return `<a class="card ${o.kind === 'bowl' ? 'g' : ''}" href="#/p/${o.id}">
    <div class="pic"><span class="badge">Još ${o.left}</span>${art(o.kind, 80)}</div>
    <div class="body"><h3>${esc(o.name)}</h3><p>${esc(o.store)} · ${o.km} km</p>
    <div class="price ${free ? 'free' : ''}">${free ? 'Besplatno' : eur(o.sale)}${free ? '' : `<s>${eur(o.price)}</s>`}</div>
    <div class="when">Preuzimanje ${o.from} – ${o.to}</div></div></a>`;
}
export function offerRow(o) {
  const free = o.sale === null;
  return `<a class="row" href="#/p/${o.id}"><div class="thumb">${art(o.kind, 44)}</div>
    <div class="t"><h3>${esc(o.name)}</h3><p>${esc(o.store)} · ${o.km} km</p><p><b>Danas ${o.from} – ${o.to}</b></p></div>
    <div class="price ${free ? 'free' : ''}">${free ? 'Besplatno' : eur(o.sale)}${free ? '' : `<br><s>${eur(o.price)}</s>`}</div></a>`;
}
export function toast(msg) {
  const t = document.createElement('div'); t.className = 'toast'; t.setAttribute('role', 'status'); t.textContent = msg;
  document.body.append(t); setTimeout(() => t.remove(), 2600);
}

// Navigacija: donja traka (mobitel) + bočna traka (desktop) iz istih podataka
export const NAV = {
  buyer: [['#/', 'Istraži', 'home'], ['#/map', 'Karta', 'pin'], ['#/orders', 'Narudžbe', 'bag'], ['#/profile', 'Profil', 'user']],
  org: [['#/', 'Donacije', 'home'], ['#/campaigns', 'Akcije', 'gift'], ['#/orders', 'Zahtjevi', 'bag'], ['#/profile', 'Profil', 'user']],
};
export function shell(user, route, inner) {
  const items = NAV[user.role === 'org' ? 'org' : 'buyer'];
  const links = items.map(([h, l, i]) => `<a href="${h}" ${h === route ? 'aria-current="page"' : ''}>${icon[i]}<span>${l}</span></a>`).join('');
  return `<div class="shell"><aside class="side"><div class="brand">${logoMark}<span>SpasiZalogaj</span></div>${links}<div class="who">${esc(user.name)}<br>${user.role === 'org' ? 'Organizacija' : 'Kupac'}</div></aside>
  <main class="main">${inner}</main><nav class="nav" aria-label="Glavna navigacija">${links}</nav></div>`;
}

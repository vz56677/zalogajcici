const MOCK = true;
const KEY = 'sz_session', ORD = 'sz_orders';
const read = (k, d) => { try { return JSON.parse(localStorage.getItem(k)) ?? d } catch { return d } };

const OFFERS = [
  { id: 1, name: 'Pekarska vrećica iznenađenja', store: 'Pekara Zora', addr: 'Ulica primjere 12, Zagreb', km: 0.4, price: 9, sale: 2.99, left: 2, from: '18:30', to: '19:00', cat: 'Pekara', kind: 'bread', desc: 'Iznenađenje iz današnje ponude: kruh, peciva i slatkiši koji tijekom dana nisu prodani, a još su savršeno svježi.', tags: ['Kruh', 'Peciva', 'Slatko'] },
  { id: 2, name: 'Ručak u zdjeli', store: 'Zeleni kutak', addr: 'Gajeva 5, Zagreb', km: 0.9, price: 8.5, sale: 3.5, left: 4, from: '20:00', to: '20:30', cat: 'Jela', kind: 'bowl', desc: 'Topli obrok dana: povrće, žitarice i umak kuće.', tags: ['Vegetarijansko', 'Toplo'] },
  { id: 3, name: 'Kutija spašenih namirnica', store: 'Mala Trgovina', addr: 'Ilica 40, Zagreb', km: 1.2, price: 14, sale: 4.99, left: 6, from: '19:00', to: '20:00', cat: 'Voće i povrće', kind: 'basket', desc: 'Voće, povrće i trajnije namirnice pred istekom roka.', tags: ['Voće', 'Povrće'] },
  { id: 4, name: 'Tjestenina dana', store: 'Trattoria Luna', addr: 'Tkalčićeva 9, Zagreb', km: 1.6, price: 9.5, sale: 3.2, left: 3, from: '21:00', to: '21:30', cat: 'Jela', kind: 'bowl', desc: 'Porcija tjestenine dana, pakirana za van.', tags: ['Jela'] },
  { id: 5, name: 'Kruh i peciva za donaciju', store: 'Pekara Zora', addr: 'Ulica primjere 12, Zagreb', km: 0.4, price: 12, sale: null, left: 20, from: '19:00', to: '20:00', cat: 'Pekara', kind: 'bread', desc: 'Neprodani kruh i peciva dostupni samo humanitarnim organizacijama.', tags: ['Donacija', 'Kruh'] },
  { id: 6, name: 'Gajbe voća i povrća', store: 'Mala Trgovina', addr: 'Ilica 40, Zagreb', km: 1.2, price: 30, sale: null, left: 5, from: '17:00', to: '19:00', cat: 'Voće i povrće', kind: 'basket', desc: 'Gajbe sezonskog voća i povrća za donaciju.', tags: ['Donacija', 'Voće'] },
];
const CAMPAIGNS = [
  { id: 1, name: 'Zimska zaliha za Kuću nade', place: 'Zagreb, Trešnjevka', dates: '15.11. – 15.12.', need: 'Konzerve, tjestenina, ulje' },
  { id: 2, name: 'Dan bez gladnih', place: 'Zagreb, Trg bana Jelačića', dates: '1.12. – 3.12.', need: 'Kruh, voće, mliječni proizvodi' },
];
const delay = v => new Promise(r => setTimeout(() => r(v), 120));

export const session = () => read(KEY, null);
export const logout = () => localStorage.removeItem(KEY);

export async function login({ email, password, role, name }) {
  if (!email || !password) throw new Error('Unesi e-poštu i lozinku.');
  const base = email.split('@')[0];
  const s = { role, email, name: name || (role === 'org' ? 'Banka hrane Zagreb' : base.charAt(0).toUpperCase() + base.slice(1)) };
  localStorage.setItem(KEY, JSON.stringify(s));
  return delay(s);
}
export const loginGoogle = role => login({ email: 'ana@gmail.com', password: 'x', role, name: role === 'org' ? 'Banka hrane Zagreb' : role === 'store' ? 'Mala Trgovina' : 'Ana' });

export const listOffers = async (role) => delay(OFFERS.filter(o => role === 'org' ? o.sale === null : o.sale !== null));
export const getOffer = async id => delay(OFFERS.find(o => o.id == id));
export const listCampaigns = () => delay(CAMPAIGNS);

export const listOrders = () => delay(read(ORD, []));
export async function createOrder(offer, qty, role) {
  const o = read(ORD, []);
  const code = 'SZ-' + Math.random().toString(36).slice(2, 6).toUpperCase();
  o.unshift({ id: Date.now(), role, offerId: offer.id, name: offer.name, store: offer.store, qty, code,
    total: offer.sale === null ? 0 : +(offer.sale * qty).toFixed(2), until: offer.to, status: role === 'org' ? 'Na čekanju' : 'Rezervirano' });
  localStorage.setItem(ORD, JSON.stringify(o));
  return delay(code);
}
export async function cancelOrder(id) {
  localStorage.setItem(ORD, JSON.stringify(read(ORD, []).filter(x => x.id !== id)));
  return delay(true);
}

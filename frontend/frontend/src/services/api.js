const KEY = 'sz_session';
const ORD = 'sz_orders';
const PENDING_ROLE = 'sz_pending_role';

const read = (key, fallback) => {
  try {
    return JSON.parse(localStorage.getItem(key)) ?? fallback;
  } catch {
    return fallback;
  }
};
/*
const OFFERS = [
  { id: 1, name: 'Pekarska vrećica iznenađenja', store: 'Pekara Zora', addr: 'Ulica primjere 12, Zagreb', km: 0.4, price: 9, sale: 2.99, left: 2, from: '18:30', to: '19:00', cat: 'Pekara', kind: 'bread', desc: 'Iznenađenje iz današnje ponude: kruh, peciva i slatkiši koji tijekom dana nisu prodani, a još su savršeno svježi.', tags: ['Kruh', 'Peciva', 'Slatko'] },
  { id: 2, name: 'Ručak u zdjeli', store: 'Zeleni kutak', addr: 'Gajeva 5, Zagreb', km: 0.9, price: 8.5, sale: 3.5, left: 4, from: '20:00', to: '20:30', cat: 'Jela', kind: 'bowl', desc: 'Topli obrok dana: povrće, žitarice i umak kuće.', tags: ['Vegetarijansko', 'Toplo'] },
  { id: 3, name: 'Kutija spašenih namirnica', store: 'Mala Trgovina', addr: 'Ilica 40, Zagreb', km: 1.2, price: 14, sale: 4.99, left: 6, from: '19:00', to: '20:00', cat: 'Voće i povrće', kind: 'basket', desc: 'Voće, povrće i trajnije namirnice pred istekom roka.', tags: ['Voće', 'Povrće'] },
  { id: 4, name: 'Tjestenina dana', store: 'Trattoria Luna', addr: 'Tkalčićeva 9, Zagreb', km: 1.6, price: 9.5, sale: 3.2, left: 3, from: '21:00', to: '21:30', cat: 'Jela', kind: 'bowl', desc: 'Porcija tjestenine dana, pakirana za van.', tags: ['Jela'] },
  { id: 5, name: 'Kruh i peciva za donaciju', store: 'Pekara Zora', addr: 'Ulica primjere 12, Zagreb', km: 0.4, price: 12, sale: null, left: 20, from: '19:00', to: '20:00', cat: 'Pekara', kind: 'bread', desc: 'Neprodani kruh i peciva dostupni samo humanitarnim organizacijama.', tags: ['Donacija', 'Kruh'] },
  { id: 6, name: 'Gajbe voća i povrća', store: 'Mala Trgovina', addr: 'Ilica 40, Zagreb', km: 1.2, price: 30, sale: null, left: 5, from: '17:00', to: '19:00', cat: 'Voće i povrće', kind: 'basket', desc: 'Gajbe sezonskog voća i povrća za donaciju.', tags: ['Donacija', 'Voće'] }
];*/

const CAMPAIGNS = [
  { id: 1, name: 'Zimska zaliha za Kuću nade', place: 'Zagreb, Trešnjevka', dates: '15.11. – 15.12.', need: 'Konzerve, tjestenina, ulje' },
  { id: 2, name: 'Dan bez gladnih', place: 'Zagreb, Trg bana Jelačića', dates: '1.12. – 3.12.', need: 'Kruh, voće, mliječni proizvodi' }
];

const delay = value => new Promise(resolve => setTimeout(() => resolve(value), 120));

function getCsrfToken() {
  const cookie = document.cookie
      .split('; ')
      .find(row => row.startsWith('XSRF-TOKEN='));

  return cookie
      ? decodeURIComponent(cookie.substring('XSRF-TOKEN='.length))
      : '';
}

async function request(url, options = {}) {
  const response = await fetch(url, {
    credentials: 'same-origin',
    ...options,
    headers: {
      Accept: 'application/json',
      ...options.headers
    }
  });

  /*
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Greška poslužitelja: ${response.status}`);
  }*/

  if (!response.ok) {
    const text = await response.text();
    let message = text;
    try {
      message = JSON.parse(text).poruka ?? text;
    } catch {
      // odgovor nije JSON (npr. "Korisnik nije prijavljen.")
    }
    throw new Error(message || `Greška poslužitelja: ${response.status}`);
  }

  if (response.status === 204) return null;

  return response.json();
}

function mapUser(data) {
  const roles = {
    KUPAC: 'buyer',
    PRODAVAC: 'store',
    ORGANIZACIJA: 'org'
  };

  return {
    id: data.id,
    email: data.email,
    name: data.ime,
    role: roles[data.uloga] ?? null,
    prodajnoMjesto: data.prodajnoMjesto
        ? {
          id: data.prodajnoMjesto.id,
          name: data.prodajnoMjesto.naziv,
          type: data.prodajnoMjesto.vrsta,
          address: data.prodajnoMjesto.adresa,
          contact: data.prodajnoMjesto.kontakt,
          logoUrl: data.prodajnoMjesto.logoUrl,
          latitude: data.prodajnoMjesto.sirina,
          longitude: data.prodajnoMjesto.duzina
        }
        : null
  };
}

// Pretvara ponudu iz backenda (PonudaOdgovor) u oblik koji koriste stranice
const hhmm = iso => new Date(iso).toLocaleTimeString('hr-HR', { hour: '2-digit', minute: '2-digit' });

// "Danas", "Sutra" ili datum
const dayLabel = iso => {
  const day = d => new Date(d.getFullYear(), d.getMonth(), d.getDate());
  const diff = Math.round((day(new Date(iso)) - day(new Date())) / 86400000);
  if (diff === 0) return 'Danas';
  if (diff === 1) return 'Sutra';
  return new Date(iso).toLocaleDateString('hr-HR', { day: 'numeric', month: 'numeric' });
};

const artFor = cat => /pek|kruh/i.test(cat) ? 'bread' : /jel|obrok/i.test(cat) ? 'bowl' : 'basket';

function mapOffer(p) {
  return {
    id: p.id,
    name: p.naziv,
    cat: p.kategorija,
    desc: p.opis ?? '',
    tags: [p.kategorija],
    kind: artFor(p.kategorija),
    store: p.prodajnoMjesto.naziv,
    addr: p.prodajnoMjesto.adresa,
    price: Number(p.redovnaCijena),
    sale: p.namjena === 'DONACIJA' ? null : Number(p.prodajnaCijena),
    left: Number(p.raspolozivo),
    day: dayLabel(p.preuzimanjeOd),
    from: hhmm(p.preuzimanjeOd),
    to: hhmm(p.preuzimanjeDo)
  };
}

export async function session() {
  try {
    const data = await request('/api/me');
    const user = mapUser(data);

    if (user.role) {
      localStorage.setItem(KEY, JSON.stringify(user));
    } else {
      localStorage.removeItem(KEY);
    }

    return user;
  } catch {
    localStorage.removeItem(KEY);
    return null;
  }
}

export function loginGoogle(role) {
  if (role) {
    sessionStorage.setItem(PENDING_ROLE, role);
  }

  window.location.assign('/oauth2/authorization/google');
}

export function getPendingRole() {
  return sessionStorage.getItem(PENDING_ROLE) || 'buyer';
}

export async function selectRole(role, storeData = null) {
  const roles = {
    buyer: 'KUPAC',
    store: 'PRODAVAC',
    org: 'ORGANIZACIJA'
  };

  if (!roles[role]) {
    throw new Error('Nepoznata korisnička uloga.');
  }

  if (role === 'store' && !storeData) {
    throw new Error('Potrebni su podaci prodajnog mjesta.');
  }

  const data = await request('/api/me/uloga', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-XSRF-TOKEN': getCsrfToken()
    },
    body: JSON.stringify({
      uloga: roles[role],
      prodajnoMjesto: role === 'store' ? storeData : null
    })
  });

  sessionStorage.removeItem(PENDING_ROLE);

  const user = mapUser(data);
  localStorage.setItem(KEY, JSON.stringify(user));

  return user;
}

export async function logout() {
  try {
    await request('/logout', {
      method: 'POST',
      headers: {
        'X-XSRF-TOKEN': getCsrfToken()
      }
    });
  } finally {
    localStorage.removeItem(KEY);
    sessionStorage.removeItem(PENDING_ROLE);
    window.location.assign('/');
  }
}

/*
export const listOffers = async role =>
    delay(OFFERS.filter(offer =>
        role === 'org' ? offer.sale === null : offer.sale !== null
    ));

export const getOffer = async id =>
    delay(OFFERS.find(offer => offer.id == id));
*/

export const listOffers = async () =>
    (await request('/api/ponude')).map(mapOffer);

export const getOffer = async id => {
  try {
    return mapOffer(await request(`/api/ponude/${id}`));
  } catch {
    return null; // 404: ponuda ne postoji, istekla je ili je za donaciju
  }
};

export const listCampaigns = () => delay(CAMPAIGNS);

export const listOrders = () => delay(read(ORD, []));

export async function createOrder(offer, qty, role) {
  const orders = read(ORD, []);
  //const code = 'SZ-' + Math.random().toString(36).slice(2, 6).toUpperCase();

  //za hum org - jos nema backend za zahtjeve
  if (role == 'org'){
   orders.unshift({
      id: Date.now(),
      role,
      offerId: offer.id,
      name: offer.name,
      store: offer.store,
      qty,
      total: offer.sale === null ? 0 : +(offer.sale * qty).toFixed(2),
      until: offer.to,
      status: role === 'org' ? 'Na čekanju' : 'Rezervirano'
    });
    localStorage.setItem(ORD, JSON.stringify(orders));

    return delay(null);
  }

  //za kupca (prave rezervacije)
  const r = await request('/api/rezervacije', {
    method: 'POST', 
    headers: {'Content-type': 'application/json', 'X-XSRF-TOKEN': getCsrfToken()},
    body: JSON.stringify({stavke: [{ponudaId: offer.id, kolicina: qty}] })
  });

  
  // dok ne postoji GET /api/rezervacije/moje -> spremanje lokalno za "Narudzbe"
  orders.unshift({
    id: r.id, 
    role, 
    offerId: offer.id, 
    name: offer.name, 
    store: offer.store,
    qty, 
    total: Number(r.ukupnaCijena), 
    until: offer.to, 
    status: 'Čeka plaćanje'
  });
  localStorage.setItem(ORD, JSON.stringify(orders));
  return r;

 
}

export async function cancelOrder(id) {
  localStorage.setItem(
      ORD,
      JSON.stringify(read(ORD, []).filter(order => order.id !== id))
  );

  return delay(true);
}


// "18:00 – 19:00" (ili "18:00-19:00") -> početak i kraj preuzimanja danas
function pickupRange(text) {
  const m = text.match(/(\d{1,2})[:.](\d{2})\s*[–-]\s*(\d{1,2})[:.](\d{2})/);
  if (!m) throw new Error('Vrijeme preuzimanja upišite u obliku 18:00 – 19:00.');
  const at = (h, min) => {
    const d = new Date();
    d.setHours(Number(h), Number(min), 0, 0);
    return d.toISOString();
  };
  return [at(m[1], m[2]), at(m[3], m[4])];
}

// POST /api/ponude: prodajno mjesto backend uzima iz prijavljenog prodavača
export async function publishOffer(form) {
  const [od, doVrijeme] = pickupRange(form.pickup);
  const donacija = form.purpose === 'donacija';

  return request('/api/ponude', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-XSRF-TOKEN': getCsrfToken()
    },
    body: JSON.stringify({
      vrsta: form.type === 'Paket iznenađenja' ? 'PAKET' : 'PROIZVOD',
      namjena: donacija ? 'DONACIJA' : 'PRODAJA',
      naziv: form.name.trim(),
      kategorija: form.category,
      redovnaCijena: Number(form.regular || 0),
      prodajnaCijena: donacija ? 0 : Number(form.sale || 0),
      raspolozivo: Number(form.qty),
      preuzimanjeOd: od,
      preuzimanjeDo: doVrijeme,
      rokUpotrebe: form.expiry ? new Date(`${form.expiry}T23:59:00`).toISOString() : null
    })
  });
}

export async function removeOffer(id) {
  return request(`/api/ponude/${id}`, {
    method: 'DELETE',
    headers: {
      'X-XSRF-TOKEN': getCsrfToken()
    }
  });
}
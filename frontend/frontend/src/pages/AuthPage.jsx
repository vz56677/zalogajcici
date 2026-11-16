import { useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { GoogleLogo, LogoMark } from '../components/Icons.jsx';
import { getPendingRole } from '../services/api.js';

export default function AuthPage() {
  const { user, loginGoogle, selectRole } = useAuth();

  const [role, setRole] = useState(getPendingRole());
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [storeData, setStoreData] = useState({
    naziv: '',
    vrsta: '',
    adresa: '',
    kontakt: '',
    logoUrl: '',
    sirina: '',
    duzina: ''
  });

  const roles = [
    ['buyer', 'Kupac'],
    ['org', 'Organizacija'],
    ['store', 'Prodajno mjesto']
  ];

  function updateStoreData(event) {
    const { name, value } = event.target;

    setStoreData(previous => ({
      ...previous,
      [name]: value
    }));
  }

  async function handleGoogleLogin() {
    loginGoogle(role);
  }

  async function handleRegistration(event) {
    event.preventDefault();

    setLoading(true);
    setError('');

    try {
      let data = null;

      if (role === 'store') {
        if (
            !storeData.naziv.trim() ||
            !storeData.vrsta.trim() ||
            !storeData.adresa.trim() ||
            !storeData.kontakt.trim() ||
            storeData.sirina === '' ||
            storeData.duzina === ''
        ) {
          throw new Error('Ispunite sva obavezna polja.');
        }

        data = {
          naziv: storeData.naziv.trim(),
          vrsta: storeData.vrsta.trim(),
          adresa: storeData.adresa.trim(),
          kontakt: storeData.kontakt.trim(),
          logoUrl: storeData.logoUrl.trim() || null,
          sirina: Number(storeData.sirina),
          duzina: Number(storeData.duzina)
        };
      }

      await selectRole(role, data);
    } catch (err) {
      setError(
          err.message || 'Registracija nije uspjela. Pokušajte ponovno.'
      );
    } finally {
      setLoading(false);
    }
  }

  return (
      <div className="auth" data-r={role}>
        <section className="auth-hero">
          <LogoMark />
          <h1>SpasiZalogaj</h1>
          <small>by zalogajcici</small>
          <p>
            Odlična hrana, spašena od bacanja.
            Povezujemo kupce, trgovine i organizacije.
          </p>
        </section>

        <section className="auth-card">
          {!user ? (
              <>
                <h2>Dobro došli</h2>

                <p className="auth-intro">
                  Odaberite način korištenja platforme.
                </p>

                <div
                    className="seg role-seg"
                    role="group"
                    aria-label="Vrsta računa"
                >
                  {roles.map(([value, label]) => (
                      <button
                          key={value}
                          type="button"
                          aria-pressed={role === value}
                          onClick={() => setRole(value)}
                      >
                        {label}
                      </button>
                  ))}
                </div>

                {role === 'store' && (
                    <div className="role-hint">
                      Upravljajte ponudama hrane, rezervacijama i donacijama
                      svoje trgovine.
                    </div>
                )}

                {role === 'org' && (
                    <div className="role-hint">
                      Pristupite ponudama hrane namijenjenima humanitarnim
                      organizacijama.
                    </div>
                )}

                <button
                    className="btn alt"
                    type="button"
                    onClick={handleGoogleLogin}
                >
                  <GoogleLogo />
                  Nastavi s Googleom
                </button>

                <p className="small">
                  Prijava i registracija dostupne su isključivo putem Google računa.
                </p>
              </>
          ) : (
              <>
                <h2>Dovršite registraciju</h2>

                <p className="auth-intro">
                  Prijavljeni ste kao {user.email}.
                  Odaberite vrstu računa.
                </p>

                <div
                    className="seg role-seg"
                    role="group"
                    aria-label="Vrsta računa"
                >
                  {roles.map(([value, label]) => (
                      <button
                          key={value}
                          type="button"
                          aria-pressed={role === value}
                          onClick={() => {
                            setRole(value);
                            setError('');
                          }}
                      >
                        {label}
                      </button>
                  ))}
                </div>

                {role === 'store' && (
                    <form onSubmit={handleRegistration}>
                      <label>
                        Naziv prodajnog mjesta *
                        <input
                            name="naziv"
                            value={storeData.naziv}
                            onChange={updateStoreData}
                            required
                        />
                      </label>

                      <label>
                        Vrsta prodajnog mjesta *
                        <input
                            name="vrsta"
                            value={storeData.vrsta}
                            onChange={updateStoreData}
                            placeholder="Pekara, trgovina, restoran..."
                            required
                        />
                      </label>

                      <label>
                        Adresa *
                        <input
                            name="adresa"
                            value={storeData.adresa}
                            onChange={updateStoreData}
                            required
                        />
                      </label>

                      <label>
                        Kontakt *
                        <input
                            name="kontakt"
                            value={storeData.kontakt}
                            onChange={updateStoreData}
                            required
                        />
                      </label>

                      <label>
                        URL logotipa
                        <input
                            name="logoUrl"
                            type="url"
                            value={storeData.logoUrl}
                            onChange={updateStoreData}
                        />
                      </label>

                      <label>
                        Geografska širina *
                        <input
                            name="sirina"
                            type="number"
                            step="any"
                            min="-90"
                            max="90"
                            value={storeData.sirina}
                            onChange={updateStoreData}
                            required
                        />
                      </label>

                      <label>
                        Geografska dužina *
                        <input
                            name="duzina"
                            type="number"
                            step="any"
                            min="-180"
                            max="180"
                            value={storeData.duzina}
                            onChange={updateStoreData}
                            required
                        />
                      </label>

                      <button
                          className="btn"
                          type="submit"
                          disabled={loading}
                      >
                        {loading ? 'Registracija...' : 'Dovrši registraciju'}
                      </button>
                    </form>
                )}

                {role === 'buyer' && (
                    <button
                        className="btn"
                        type="button"
                        disabled={loading}
                        onClick={() => handleRegistration({
                          preventDefault: () => {}
                        })}
                    >
                      {loading ? 'Registracija...' : 'Nastavi kao kupac'}
                    </button>
                )}

                {role === 'org' && (
                    <div className="role-hint">
                      Registracija organizacija još nije podržana na
                      poslužitelju. Potrebna je dorada backend dijela.
                    </div>
                )}

                {error && (
                    <p className="err" role="alert">
                      {error}
                    </p>
                )}
              </>
          )}
        </section>
      </div>
  );
}
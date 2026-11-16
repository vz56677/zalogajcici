const Svg = ({ size = 22, children }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"
    strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">{children}</svg>
);

export const icons = {
  home: s => <Svg size={s}><path d="M3 11l9-8 9 8v9a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1z" /></Svg>,
  pin: s => <Svg size={s}><path d="M12 21s7-6 7-12a7 7 0 0 0-14 0c0 6 7 12 7 12z" /><circle cx="12" cy="9" r="2.5" /></Svg>,
  bag: s => <Svg size={s}><path d="M5 8h14l-1 12H6z" /><path d="M9 8a3 3 0 0 1 6 0" /></Svg>,
  user: s => <Svg size={s}><circle cx="12" cy="8" r="4" /><path d="M4 21c1-4 4-6 8-6s7 2 8 6" /></Svg>,
  bell: s => <Svg size={s}><path d="M6 16V11a6 6 0 0 1 12 0v5l2 2H4z" /><path d="M10 21h4" /></Svg>,
  search: s => <Svg size={s ?? 18}><circle cx="11" cy="11" r="7" /><path d="M20 20l-4-4" /></Svg>,
  heart: s => <Svg size={s}><path d="M12 20s-8-5-8-11a4.5 4.5 0 0 1 8-2.5A4.5 4.5 0 0 1 20 9c0 6-8 11-8 11z" /></Svg>,
  gift: s => <Svg size={s}><rect x="3" y="8" width="18" height="13" rx="1" /><path d="M12 8v13M3 12h18M12 8c-3 0-5-4-2-4 2 0 2 4 2 4zm0 0c3 0 5-4 2-4-2 0-2 4-2 4z" /></Svg>,
  leaf: s => <Svg size={s}><path d="M5 19C5 9 11 4 20 4c0 9-5 15-15 15zM5 19l8-8" /></Svg>,
};

export const Icon = ({ name, size }) => icons[name](size);

export const GoogleLogo = () => (
  <svg width="18" height="18" viewBox="0 0 48 48" aria-hidden="true">
    <path fill="#EA4335" d="M24 9.5c3.5 0 6.6 1.2 9.1 3.6l6.8-6.8C35.8 2.4 30.3 0 24 0 14.6 0 6.5 5.4 2.6 13.2l7.9 6.1C12.4 13.6 17.7 9.5 24 9.5z" />
    <path fill="#4285F4" d="M46.5 24.5c0-1.6-.1-3.1-.4-4.5H24v9h12.7c-.6 3-2.3 5.5-4.8 7.2l7.5 5.8c4.4-4 7.1-10 7.1-17.5z" />
    <path fill="#FBBC05" d="M10.5 28.7a14.5 14.5 0 0 1 0-9.4l-7.9-6.1a24 24 0 0 0 0 21.6z" />
    <path fill="#34A853" d="M24 48c6.5 0 11.9-2.1 15.9-5.8l-7.5-5.8c-2.1 1.4-4.8 2.3-8.4 2.3-6.3 0-11.6-4.1-13.5-9.8l-7.9 6.1C6.5 42.6 14.6 48 24 48z" />
  </svg>
);

export const LogoMark = () => (
  <div className="logo" style={{ color: 'var(--deep)' }}>{icons.bag('60%')}</div>
);

// ilustracije proizvoda (jednostavni oblici kao na mockupu)
const ART = {
  bread: (<>
    <ellipse cx="50" cy="52" rx="38" ry="26" fill="#d8955f" />
    <path d="M34 36l10 32M50 32l8 36M66 38l6 28" stroke="#8a4b2a" strokeWidth="5" strokeLinecap="round" />
  </>),
  bowl: (<>
    <path d="M10 48h80a40 32 0 0 1-80 0z" fill="#0a6470" />
    <circle cx="36" cy="42" r="10" fill="#e5c4b0" />
    <circle cx="56" cy="38" r="9" fill="#a8d5a2" />
    <circle cx="72" cy="44" r="7" fill="#f09a4a" />
  </>),
  basket: (<>
    <path d="M18 40h64l-8 42H26z" fill="#e5c4b0" />
    <path d="M34 40a16 16 0 0 1 32 0" stroke="#064e54" strokeWidth="5" fill="none" />
    <circle cx="42" cy="58" r="7" fill="#f09a4a" />
    <circle cx="60" cy="60" r="7" fill="#a8d5a2" />
  </>),
};

export const Art = ({ kind, size = 90 }) => (
  <svg width={size} height={size} viewBox="0 0 100 100" aria-hidden="true">{ART[kind]}</svg>
);

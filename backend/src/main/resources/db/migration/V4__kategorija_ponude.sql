UPDATE ponuda SET kategorija = CASE
                                   WHEN kategorija IN ('Pekara', 'Pekarski proizvodi') THEN 'PEKARA'
                                   WHEN kategorija = 'Voće i povrće'                   THEN 'VOCE_I_POVRCE'
                                   WHEN kategorija IN ('Jela', 'Gotovi obroci')        THEN 'GOTOVI_OBROCI'
                                   WHEN kategorija = 'Deserti'                         THEN 'DESERTI'
                                   ELSE 'OSTALO'
    END
WHERE kategorija NOT IN ('PEKARA', 'VOCE_I_POVRCE', 'GOTOVI_OBROCI', 'DESERTI', 'OSTALO');

ALTER TABLE ponuda
    ADD CONSTRAINT ponuda_kategorija_enum_check
        CHECK (kategorija IN ('PEKARA', 'VOCE_I_POVRCE', 'GOTOVI_OBROCI', 'DESERTI', 'OSTALO'));
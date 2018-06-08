
--
-- TOC entry 186 (class 1259 OID 48242)
-- Name: ansvarig; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE ansvarig (
    id integer NOT NULL,
    mottagningid integer,
    namn character varying(255),
    username character varying(255)
);

--
-- TOC entry 185 (class 1259 OID 48240)
-- Name: ansvarig_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE ansvarig_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 185
-- Name: ansvarig_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE ansvarig_id_seq OWNED BY ansvarig.id;


--
-- TOC entry 188 (class 1259 OID 48250)
-- Name: apotek; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE apotek (
    id integer NOT NULL,
    adress character varying(255),
    fritext character varying(255),
    namn character varying(255),
    postnr character varying(255),
    postort character varying(255),
    telefon character varying(255)
);


--
-- TOC entry 187 (class 1259 OID 48248)
-- Name: apotek_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE apotek_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 187
-- Name: apotek_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE apotek_id_seq OWNED BY apotek.id;


--
-- TOC entry 190 (class 1259 OID 48261)
-- Name: artikel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE artikel (
    id integer NOT NULL,
    artnr character varying(255),
    gruppid integer,
    namn character varying(255),
    ordning integer,
    storlek character varying(255)
);


--
-- TOC entry 189 (class 1259 OID 48259)
-- Name: artikel_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE artikel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2357 (class 0 OID 0)
-- Dependencies: 189
-- Name: artikel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE artikel_id_seq OWNED BY artikel.id;

--
-- TOC entry 194 (class 1259 OID 48283)
-- Name: bestinfo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE bestinfo (
    id integer NOT NULL,
    datum timestamp without time zone,
    fritext character varying(255),
    levdatum timestamp without time zone,
    pdid integer,
    utskrivare character varying(255)
);


--
-- TOC entry 193 (class 1259 OID 48281)
-- Name: bestinfo_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bestinfo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 193
-- Name: bestinfo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE bestinfo_id_seq OWNED BY bestinfo.id;


--
-- TOC entry 196 (class 1259 OID 48294)
-- Name: bestlakemedelrad; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE bestlakemedelrad (
    id integer NOT NULL,
    antal integer,
    bestid integer,
    patientlakemedelid integer
);


--
-- TOC entry 195 (class 1259 OID 48292)
-- Name: bestlakemedelrad_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bestlakemedelrad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2360 (class 0 OID 0)
-- Dependencies: 195
-- Name: bestlakemedelrad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE bestlakemedelrad_id_seq OWNED BY bestlakemedelrad.id;


--
-- TOC entry 198 (class 1259 OID 48302)
-- Name: bestpdrad; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE bestpdrad (
    id integer NOT NULL,
    antal integer,
    bestid integer,
    pdartikelid integer
);


--
-- TOC entry 197 (class 1259 OID 48300)
-- Name: bestpdrad_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE bestpdrad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2361 (class 0 OID 0)
-- Dependencies: 197
-- Name: bestpdrad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE bestpdrad_id_seq OWNED BY bestpdrad.id;


--
-- TOC entry 182 (class 1259 OID 48219)
-- Name: content; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE content (
    id character varying(255) NOT NULL,
    content character varying(100000)
);


--
-- TOC entry 200 (class 1259 OID 48310)
-- Name: dosering; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dosering (
    id integer NOT NULL,
    namn character varying(255),
    ordning integer
);


--
-- TOC entry 199 (class 1259 OID 48308)
-- Name: dosering_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE dosering_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2362 (class 0 OID 0)
-- Dependencies: 199
-- Name: dosering_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE dosering_id_seq OWNED BY dosering.id;


--
-- TOC entry 201 (class 1259 OID 48316)
-- Name: dtproperties; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dtproperties (
    id integer NOT NULL,
    property character varying(255) NOT NULL,
    lvalue oid,
    objectid integer,
    uvalue character varying(255),
    value character varying(255),
    version integer NOT NULL
);


--
-- TOC entry 183 (class 1259 OID 48227)
-- Name: dummy; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE dummy (
    id integer NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 48326)
-- Name: flik; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE flik (
    id integer NOT NULL,
    ordning integer,
    titel character varying(255),
    typ character varying(255)
);


--
-- TOC entry 202 (class 1259 OID 48324)
-- Name: flik_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE flik_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2363 (class 0 OID 0)
-- Dependencies: 202
-- Name: flik_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE flik_id_seq OWNED BY flik.id;


--
-- TOC entry 205 (class 1259 OID 48337)
-- Name: grupp; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE grupp (
    id integer NOT NULL,
    flikid integer,
    ordning integer,
    titel character varying(255)
);


--
-- TOC entry 204 (class 1259 OID 48335)
-- Name: grupp_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE grupp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2364 (class 0 OID 0)
-- Dependencies: 204
-- Name: grupp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE grupp_id_seq OWNED BY grupp.id;


--
-- TOC entry 207 (class 1259 OID 48345)
-- Name: lakemedel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lakemedel (
    id integer NOT NULL,
    artnr character varying(255),
    forpackning character varying(255),
    namn character varying(255),
    ordning integer,
    storlek character varying(255),
    styrka character varying(255),
    upphandlad boolean
);


--
-- TOC entry 206 (class 1259 OID 48343)
-- Name: lakemedel_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lakemedel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2365 (class 0 OID 0)
-- Dependencies: 206
-- Name: lakemedel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lakemedel_id_seq OWNED BY lakemedel.id;


--
-- TOC entry 184 (class 1259 OID 48232)
-- Name: link; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE link (
    id integer NOT NULL,
    label character varying(255),
    privatecontent boolean,
    url character varying(255)
);


--
-- TOC entry 209 (class 1259 OID 48356)
-- Name: mottagning; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mottagning (
    id integer NOT NULL,
    apotekid integer,
    namn character varying(255)
);


--
-- TOC entry 208 (class 1259 OID 48354)
-- Name: mottagning_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mottagning_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2366 (class 0 OID 0)
-- Dependencies: 208
-- Name: mottagning_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mottagning_id_seq OWNED BY mottagning.id;


--
-- TOC entry 211 (class 1259 OID 48364)
-- Name: pages; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pages (
    id integer NOT NULL,
    collection character varying(255),
    description character varying(255),
    tickettime integer,
    url character varying(255) NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 48362)
-- Name: pages_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2367 (class 0 OID 0)
-- Dependencies: 210
-- Name: pages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE pages_id_seq OWNED BY pages.id;


--
-- TOC entry 213 (class 1259 OID 48375)
-- Name: patient; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE patient (
    id integer NOT NULL,
    adress character varying(255) NOT NULL,
    efternamn character varying(255) NOT NULL,
    epost character varying(255),
    fornamn character varying(255) NOT NULL,
    isdeleted boolean,
    mobil character varying(255),
    pas integer,
    pnr character varying(255) NOT NULL,
    portkod character varying(255),
    postnr character varying(255) NOT NULL,
    postort character varying(255) NOT NULL,
    samtycke boolean,
    telefon character varying(255) NOT NULL,
    utdeldag character varying(255),
    utdeltext character varying(255),
    utdelvecka character varying(255)
);


--
-- TOC entry 212 (class 1259 OID 48373)
-- Name: patient_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE patient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2368 (class 0 OID 0)
-- Dependencies: 212
-- Name: patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE patient_id_seq OWNED BY patient.id;


--
-- TOC entry 215 (class 1259 OID 48386)
-- Name: patient_lakemedel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE patient_lakemedel (
    id integer NOT NULL,
    created timestamp without time zone,
    doseringtext character varying(255),
    isdeleted boolean,
    isdeletedby integer,
    isdeletedtime timestamp without time zone,
    lakarid integer,
    lakemedelid integer,
    patientid integer
);


--
-- TOC entry 214 (class 1259 OID 48384)
-- Name: patient_lakemedel_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE patient_lakemedel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2369 (class 0 OID 0)
-- Dependencies: 214
-- Name: patient_lakemedel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE patient_lakemedel_id_seq OWNED BY patient_lakemedel.id;


--
-- TOC entry 217 (class 1259 OID 48394)
-- Name: pd; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pd (
    id integer NOT NULL,
    datum timestamp without time zone,
    ersatter integer,
    giltighet timestamp without time zone,
    las integer,
    patientid integer,
    sskid integer
);


--
-- TOC entry 216 (class 1259 OID 48392)
-- Name: pd_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pd_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2370 (class 0 OID 0)
-- Dependencies: 216
-- Name: pd_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE pd_id_seq OWNED BY pd.id;


--
-- TOC entry 219 (class 1259 OID 48402)
-- Name: pdartikel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pdartikel (
    id integer NOT NULL,
    artikelid integer,
    pdid integer
);


--
-- TOC entry 218 (class 1259 OID 48400)
-- Name: pdartikel_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pdartikel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2371 (class 0 OID 0)
-- Dependencies: 218
-- Name: pdartikel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE pdartikel_id_seq OWNED BY pdartikel.id;


--
-- TOC entry 221 (class 1259 OID 48410)
-- Name: placering; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE placering (
    id integer NOT NULL,
    apotekid integer,
    mottagningid integer,
    usersid integer
);


--
-- TOC entry 220 (class 1259 OID 48408)
-- Name: placering_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE placering_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2372 (class 0 OID 0)
-- Dependencies: 220
-- Name: placering_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE placering_id_seq OWNED BY placering.id;


--
-- TOC entry 223 (class 1259 OID 48418)
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE roles (
    id integer NOT NULL,
    rolename character varying(255)
);


--
-- TOC entry 222 (class 1259 OID 48416)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2373 (class 0 OID 0)
-- Dependencies: 222
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- TOC entry 225 (class 1259 OID 48426)
-- Name: rolespages; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE rolespages (
    id integer NOT NULL,
    pagesid integer NOT NULL,
    rightslevel integer NOT NULL,
    rolesid integer NOT NULL,
    tickettime integer
);


--
-- TOC entry 224 (class 1259 OID 48424)
-- Name: rolespages_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE rolespages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2374 (class 0 OID 0)
-- Dependencies: 224
-- Name: rolespages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE rolespages_id_seq OWNED BY rolespages.id;


--
-- TOC entry 227 (class 1259 OID 48434)
-- Name: sysdiagrams; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sysdiagrams (
    diagram_id integer NOT NULL,
    definition oid,
    name character varying(255) NOT NULL,
    principal_id integer NOT NULL,
    version integer
);


--
-- TOC entry 226 (class 1259 OID 48432)
-- Name: sysdiagrams_diagram_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sysdiagrams_diagram_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2375 (class 0 OID 0)
-- Dependencies: 226
-- Name: sysdiagrams_diagram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sysdiagrams_diagram_id_seq OWNED BY sysdiagrams.diagram_id;


--
-- TOC entry 229 (class 1259 OID 48442)
-- Name: ticket; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE ticket (
    id integer NOT NULL,
    expire timestamp without time zone NOT NULL,
    userid integer NOT NULL
);


--
-- TOC entry 228 (class 1259 OID 48440)
-- Name: ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2376 (class 0 OID 0)
-- Dependencies: 228
-- Name: ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE ticket_id_seq OWNED BY ticket.id;


--
-- TOC entry 231 (class 1259 OID 48450)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    /*id integer NOT NULL,*/
    name character varying(255),
    password character varying(255),
    typ character varying(255),
    username character varying(255) NOT NULL,
    password_encrypted_flag boolean default false
);


--
-- TOC entry 230 (class 1259 OID 48448)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2377 (class 0 OID 0)
-- Dependencies: 230
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 233 (class 1259 OID 48461)
-- Name: usersroles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE usersroles (
    id integer NOT NULL,
    rolesid integer NOT NULL,
    usersid integer NOT NULL,
    username character varying(255)
);

--
-- TOC entry 232 (class 1259 OID 48459)
-- Name: usersroles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE usersroles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2378 (class 0 OID 0)
-- Dependencies: 232
-- Name: usersroles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE usersroles_id_seq OWNED BY usersroles.id;


--
-- TOC entry 2152 (class 2604 OID 48245)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY ansvarig ALTER COLUMN id SET DEFAULT nextval('ansvarig_id_seq'::regclass);


--
-- TOC entry 2153 (class 2604 OID 48253)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY apotek ALTER COLUMN id SET DEFAULT nextval('apotek_id_seq'::regclass);


--
-- TOC entry 2154 (class 2604 OID 48264)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY artikel ALTER COLUMN id SET DEFAULT nextval('artikel_id_seq'::regclass);

--
-- TOC entry 2156 (class 2604 OID 48286)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestinfo ALTER COLUMN id SET DEFAULT nextval('bestinfo_id_seq'::regclass);


--
-- TOC entry 2157 (class 2604 OID 48297)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestlakemedelrad ALTER COLUMN id SET DEFAULT nextval('bestlakemedelrad_id_seq'::regclass);


--
-- TOC entry 2158 (class 2604 OID 48305)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestpdrad ALTER COLUMN id SET DEFAULT nextval('bestpdrad_id_seq'::regclass);


--
-- TOC entry 2159 (class 2604 OID 48313)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY dosering ALTER COLUMN id SET DEFAULT nextval('dosering_id_seq'::regclass);


--
-- TOC entry 2160 (class 2604 OID 48329)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY flik ALTER COLUMN id SET DEFAULT nextval('flik_id_seq'::regclass);


--
-- TOC entry 2161 (class 2604 OID 48340)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY grupp ALTER COLUMN id SET DEFAULT nextval('grupp_id_seq'::regclass);


--
-- TOC entry 2162 (class 2604 OID 48348)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lakemedel ALTER COLUMN id SET DEFAULT nextval('lakemedel_id_seq'::regclass);


--
-- TOC entry 2163 (class 2604 OID 48359)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY mottagning ALTER COLUMN id SET DEFAULT nextval('mottagning_id_seq'::regclass);


--
-- TOC entry 2164 (class 2604 OID 48367)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pages ALTER COLUMN id SET DEFAULT nextval('pages_id_seq'::regclass);


--
-- TOC entry 2165 (class 2604 OID 48378)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY patient ALTER COLUMN id SET DEFAULT nextval('patient_id_seq'::regclass);


--
-- TOC entry 2166 (class 2604 OID 48389)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY patient_lakemedel ALTER COLUMN id SET DEFAULT nextval('patient_lakemedel_id_seq'::regclass);


--
-- TOC entry 2167 (class 2604 OID 48397)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pd ALTER COLUMN id SET DEFAULT nextval('pd_id_seq'::regclass);


--
-- TOC entry 2168 (class 2604 OID 48405)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pdartikel ALTER COLUMN id SET DEFAULT nextval('pdartikel_id_seq'::regclass);


--
-- TOC entry 2169 (class 2604 OID 48413)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY placering ALTER COLUMN id SET DEFAULT nextval('placering_id_seq'::regclass);


--
-- TOC entry 2170 (class 2604 OID 48421)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- TOC entry 2171 (class 2604 OID 48429)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY rolespages ALTER COLUMN id SET DEFAULT nextval('rolespages_id_seq'::regclass);


--
-- TOC entry 2172 (class 2604 OID 48437)
-- Name: diagram_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY sysdiagrams ALTER COLUMN diagram_id SET DEFAULT nextval('sysdiagrams_diagram_id_seq'::regclass);


--
-- TOC entry 2173 (class 2604 OID 48445)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY ticket ALTER COLUMN id SET DEFAULT nextval('ticket_id_seq'::regclass);

--
-- TOC entry 2175 (class 2604 OID 48464)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY usersroles ALTER COLUMN id SET DEFAULT nextval('usersroles_id_seq'::regclass);

--
-- TOC entry 2185 (class 2606 OID 48247)
-- Name: ansvarig_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ansvarig
    ADD CONSTRAINT ansvarig_pkey PRIMARY KEY (id);


--
-- TOC entry 2187 (class 2606 OID 48258)
-- Name: apotek_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY apotek
    ADD CONSTRAINT apotek_pkey PRIMARY KEY (id);


--
-- TOC entry 2189 (class 2606 OID 48269)
-- Name: artikel_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY artikel
    ADD CONSTRAINT artikel_pkey PRIMARY KEY (id);

--
-- TOC entry 2193 (class 2606 OID 48291)
-- Name: bestinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestinfo
    ADD CONSTRAINT bestinfo_pkey PRIMARY KEY (id);


--
-- TOC entry 2195 (class 2606 OID 48299)
-- Name: bestlakemedelrad_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestlakemedelrad
    ADD CONSTRAINT bestlakemedelrad_pkey PRIMARY KEY (id);


--
-- TOC entry 2197 (class 2606 OID 48307)
-- Name: bestpdrad_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY bestpdrad
    ADD CONSTRAINT bestpdrad_pkey PRIMARY KEY (id);


--
-- TOC entry 2179 (class 2606 OID 48226)
-- Name: content_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY content
    ADD CONSTRAINT content_pkey PRIMARY KEY (id);


--
-- TOC entry 2199 (class 2606 OID 48315)
-- Name: dosering_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dosering
    ADD CONSTRAINT dosering_pkey PRIMARY KEY (id);


--
-- TOC entry 2201 (class 2606 OID 48323)
-- Name: dtproperties_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dtproperties
    ADD CONSTRAINT dtproperties_pkey PRIMARY KEY (id, property);


--
-- TOC entry 2181 (class 2606 OID 48231)
-- Name: dummy_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY dummy
    ADD CONSTRAINT dummy_pkey PRIMARY KEY (id);


--
-- TOC entry 2203 (class 2606 OID 48334)
-- Name: flik_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY flik
    ADD CONSTRAINT flik_pkey PRIMARY KEY (id);


--
-- TOC entry 2205 (class 2606 OID 48342)
-- Name: grupp_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY grupp
    ADD CONSTRAINT grupp_pkey PRIMARY KEY (id);


--
-- TOC entry 2207 (class 2606 OID 48353)
-- Name: lakemedel_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lakemedel
    ADD CONSTRAINT lakemedel_pkey PRIMARY KEY (id);


--
-- TOC entry 2183 (class 2606 OID 48239)
-- Name: link_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY link
    ADD CONSTRAINT link_pkey PRIMARY KEY (id);


--
-- TOC entry 2209 (class 2606 OID 48361)
-- Name: mottagning_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mottagning
    ADD CONSTRAINT mottagning_pkey PRIMARY KEY (id);


--
-- TOC entry 2211 (class 2606 OID 48372)
-- Name: pages_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT pages_pkey PRIMARY KEY (id);


--
-- TOC entry 2215 (class 2606 OID 48391)
-- Name: patient_lakemedel_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY patient_lakemedel
    ADD CONSTRAINT patient_lakemedel_pkey PRIMARY KEY (id);


--
-- TOC entry 2213 (class 2606 OID 48383)
-- Name: patient_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (id);


--
-- TOC entry 2217 (class 2606 OID 48399)
-- Name: pd_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pd
    ADD CONSTRAINT pd_pkey PRIMARY KEY (id);


--
-- TOC entry 2219 (class 2606 OID 48407)
-- Name: pdartikel_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pdartikel
    ADD CONSTRAINT pdartikel_pkey PRIMARY KEY (id);


--
-- TOC entry 2221 (class 2606 OID 48415)
-- Name: placering_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY placering
    ADD CONSTRAINT placering_pkey PRIMARY KEY (id);


--
-- TOC entry 2223 (class 2606 OID 48423)
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2225 (class 2606 OID 48431)
-- Name: rolespages_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY rolespages
    ADD CONSTRAINT rolespages_pkey PRIMARY KEY (id);


--
-- TOC entry 2227 (class 2606 OID 48439)
-- Name: sysdiagrams_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sysdiagrams
    ADD CONSTRAINT sysdiagrams_pkey PRIMARY KEY (diagram_id);


--
-- TOC entry 2229 (class 2606 OID 48447)
-- Name: ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- TOC entry 2231 (class 2606 OID 48458)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userName);


--
-- TOC entry 2233 (class 2606 OID 48466)
-- Name: usersroles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usersroles
    ADD CONSTRAINT usersroles_pkey PRIMARY KEY (id);


-- Completed on 2018-02-09 09:47:37

--
-- PostgreSQL database dump complete
--


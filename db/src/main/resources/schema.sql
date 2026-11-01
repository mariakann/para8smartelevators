-- ELEVATE: σχήμα της βάσης (Supabase / PostgreSQL)

CREATE TABLE public.employees (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone NOT NULL DEFAULT now(),
  full_name text NOT NULL,
  email text NOT NULL UNIQUE,
  CONSTRAINT employees_pkey PRIMARY KEY (id)
);
CREATE TABLE public.elevators (
  id character varying NOT NULL,
  created_at timestamp with time zone DEFAULT now(),
  capacity integer NOT NULL,
  current_floor integer DEFAULT 0,
  status text DEFAULT 'IDLE'::text,
  CONSTRAINT elevators_pkey PRIMARY KEY (id)
);
CREATE TABLE public.floors (
  id integer NOT NULL,
  name text,
  is_restricted boolean DEFAULT false,
  CONSTRAINT floors_pkey PRIMARY KEY (id)
);
CREATE TABLE public.meetings (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone DEFAULT now(),
  floor_id integer,
  calendar_event_id text,
  start_time timestamp with time zone NOT NULL,
  CONSTRAINT meetings_pkey PRIMARY KEY (id),
  CONSTRAINT meetings_floor_id_fkey FOREIGN KEY (floor_id) REFERENCES public.floors(id)
);
CREATE TABLE public.meeting_participants (
  meeting_id bigint NOT NULL,
  employee_id bigint NOT NULL,
  CONSTRAINT meeting_participants_pkey PRIMARY KEY (meeting_id, employee_id),
  CONSTRAINT meeting_participants_meeting_id_fkey FOREIGN KEY (meeting_id) REFERENCES public.meetings(id),
  CONSTRAINT meeting_participants_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employees(id)
);
CREATE TABLE public.floor_permissions (
  floor_id integer NOT NULL,
  employee_id bigint NOT NULL,
  CONSTRAINT floor_permissions_pkey PRIMARY KEY (floor_id, employee_id),
  CONSTRAINT floor_permissions_floor_id_fkey FOREIGN KEY (floor_id) REFERENCES public.floors(id),
  CONSTRAINT floor_permissions_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employees(id)
);
CREATE TABLE public.ride_requests (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone DEFAULT now(),
  employee_id bigint,
  elevator_id character varying,
  origin_floor integer NOT NULL,
  destination_floor integer NOT NULL,
  meeting_id bigint,
  priority_level integer NOT NULL DEFAULT 3 CHECK (priority_level >= 1 AND priority_level <= 3),
  status text NOT NULL DEFAULT 'PENDING'::text CHECK (status = ANY (ARRAY['PENDING'::text, 'ASSIGNED'::text, 'IN_PROGRESS'::text, 'COMPLETED'::text, 'CANCELLED'::text])),
  CONSTRAINT ride_requests_pkey PRIMARY KEY (id),
  CONSTRAINT ride_requests_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employees(id),
  CONSTRAINT ride_requests_elevator_id_fkey FOREIGN KEY (elevator_id) REFERENCES public.elevators(id),
  CONSTRAINT ride_requests_origin_floor_fkey FOREIGN KEY (origin_floor) REFERENCES public.floors(id),
  CONSTRAINT ride_requests_destination_floor_fkey FOREIGN KEY (destination_floor) REFERENCES public.floors(id),
  CONSTRAINT ride_requests_meeting_id_fkey FOREIGN KEY (meeting_id) REFERENCES public.meetings(id),
  CONSTRAINT ride_requests_floors_differ CHECK (origin_floor <> destination_floor),
  CONSTRAINT ride_requests_meeting_priority_check CHECK (priority_level <> 2 OR meeting_id IS NOT NULL)
);
COMMENT ON COLUMN public.ride_requests.priority_level IS '1 = emergency, 2 = meeting, 3 = normal';

CREATE TABLE public.access_codes (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  meeting_id bigint,
  code_hash text NOT NULL,
  is_active boolean DEFAULT true,
  expires_at timestamp with time zone,
  CONSTRAINT access_codes_pkey PRIMARY KEY (id),
  CONSTRAINT access_codes_meeting_id_fkey FOREIGN KEY (meeting_id) REFERENCES public.meetings(id)
);
CREATE TABLE public.emergency_events (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone DEFAULT now(),
  elevator_id character varying,
  event_type text NOT NULL,
  resolved boolean DEFAULT false,
  CONSTRAINT emergency_events_pkey PRIMARY KEY (id),
  CONSTRAINT emergency_events_elevator_id_fkey FOREIGN KEY (elevator_id) REFERENCES public.elevators(id)
);
CREATE TABLE public.audit_logs (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone DEFAULT now(),
  action_type text NOT NULL,
  employee_id bigint,
  ride_request_id bigint,
  emergency_event_id bigint,
  details text,
  CONSTRAINT audit_logs_pkey PRIMARY KEY (id),
  CONSTRAINT audit_logs_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES public.employees(id),
  CONSTRAINT audit_logs_ride_request_id_fkey FOREIGN KEY (ride_request_id) REFERENCES public.ride_requests(id),
  CONSTRAINT audit_logs_emergency_event_id_fkey FOREIGN KEY (emergency_event_id) REFERENCES public.emergency_events(id)
);
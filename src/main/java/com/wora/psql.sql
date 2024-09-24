CREATE TYPE project_status AS ENUM ('IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE component_type AS ENUM ('WORKER', 'MATERIAL');

CREATE TABLE clients (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    is_professional BOOLEAN
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    project_name VARCHAR(255),
    profit_margin FLOAT,
    total_cost FLOAT,
    project_status project_status,
    discount FLOAT null,
    client_id UUID REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE estimates (
    id UUID PRIMARY KEY,
    estimated_amount FLOAT,
    issue_date DATE,
    validity_date DATE,
    is_accepted BOOLEAN,
    project_id UUID REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE components (
    id UUID PRIMARY KEY,
    tva FLOAT,
    name VARCHAR(255),
    component_type component_type,
    project_id UUID REFERENCES projects(id) ON DELETE CASCADE
);

CREATE table workers(
    id uuid PRIMARY KEY,
    hourly_rate FLOAT,
    work_hours FLOAT,
    worker_productivity FLOAT
) INHERITS(components);

CREATE TABLE materials(
    id uuid PRIMARY KEY,
    unit_cost FLOAT,
    quantity FLOAT,
    transport_cost FLOAT,
    quality_coefficient FLOAT
) INHERITS(components);

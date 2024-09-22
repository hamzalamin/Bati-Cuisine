CREATE TYPE project_status AS ENUM ('IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE component_type AS ENUM ('WORKER', 'MATERIAL');

CREATE TABLE clients(
    id uuid PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    is_professional BOOLEAN
);

CREATE TABLE projects(
    id uuid PRIMARY KEY,
    project_name VARCHAR(255),
    profit_margin FLOAT,
    total_cost FLOAT,
    project_status project_status,
    project_tva FLOAT,
    client_id uuid REFERENCES clients(id)
);

CREATE TABLE estimates(
    id uuid PRIMARY KEY,
    estimated_amount FLOAT,
    issue_date DATE,
    validity_date DATE,
    is_accepted BOOLEAN,
    project_id uuid REFERENCES projects(id)
);

CREATE TABLE components(
    id uuid PRIMARY KEY,
    tva FLOAT,
    component_type component_type,
    project_id uuid REFERENCES projects(id)
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

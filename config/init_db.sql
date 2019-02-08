CREATE TABLE resume
(
  uuid      VARCHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT                    NOT NULL
);

CREATE TABLE contacts
(
  id          SERIAL,
  resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT        NOT NULL,
  contact     TEXT        NOT NULL
);
CREATE UNIQUE INDEX contacts_uuid_type_index
  ON contacts (resume_uuid, type);

CREATE TABLE sections
(
  id          SERIAL,
  resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT        NOT NULL,
  section     TEXT        NOT NULL
);
CREATE UNIQUE INDEX sections_uuid_type_index
  ON sections (resume_uuid, type);
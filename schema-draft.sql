-- =========================
-- Drop tables
-- =========================

DROP TABLE IF EXISTS event_tags;
DROP TABLE IF EXISTS event_participants;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS users;

-- =========================
-- Users
-- =========================

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    bio TEXT,
    field VARCHAR(100),
    profile_image_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Types
-- =========================

CREATE TABLE types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- =========================
-- Tags
-- =========================

CREATE TABLE tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- =========================
-- Events
-- =========================

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    event_start_time TIMESTAMP NOT NULL,
    event_end_time TIMESTAMP NOT NULL,
    capacity INTEGER CHECK (capacity > 0),
    location VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    target_user VARCHAR(255),
    event_image_url VARCHAR(500),
    event_status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    owner_id INTEGER NOT NULL,
    type_id INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_events_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_events_type
        FOREIGN KEY (type_id)
        REFERENCES types(id)
        ON DELETE SET NULL,

    CONSTRAINT chk_event_time
        CHECK (event_end_time > event_start_time),

    CONSTRAINT chk_event_status
        CHECK (event_status IN ('OPEN', 'CLOSED', 'CANCELLED'))
);

-- =========================
-- Event Participants
-- =========================

CREATE TABLE event_participants (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_participants_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_event_participants_event
        FOREIGN KEY (event_id)
        REFERENCES events(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_event_participants_user_event
        UNIQUE (user_id, event_id)
);

-- =========================
-- Event Tags
-- =========================

CREATE TABLE event_tags (
    id SERIAL PRIMARY KEY,
    event_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,

    CONSTRAINT fk_event_tags_event
        FOREIGN KEY (event_id)
        REFERENCES events(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_event_tags_tag
        FOREIGN KEY (tag_id)
        REFERENCES tags(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_event_tags_event_tag
        UNIQUE (event_id, tag_id)
);

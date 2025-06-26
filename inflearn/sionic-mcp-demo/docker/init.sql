-- URL Shortener Database Initialization Script

-- Create url_mappings table
CREATE TABLE IF NOT EXISTS url_mappings (
    id SERIAL PRIMARY KEY,
    short_key VARCHAR(10) UNIQUE NOT NULL,
    original_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index for short_key for fast lookup
CREATE INDEX IF NOT EXISTS idx_url_mappings_short_key ON url_mappings(short_key);

-- Create index for created_at for potential analytics
CREATE INDEX IF NOT EXISTS idx_url_mappings_created_at ON url_mappings(created_at);

-- Insert sample data for testing
INSERT INTO url_mappings (short_key, original_url) VALUES 
    ('sample1', 'https://www.example.com'),
    ('sample2', 'https://www.google.com')
ON CONFLICT (short_key) DO NOTHING;

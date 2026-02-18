-- ISEP ERP Database Initialization Script
-- Create database and initial schema

-- Create extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_schools_code ON schools(code);
CREATE INDEX IF NOT EXISTS idx_schools_active ON schools(is_active);
CREATE INDEX IF NOT EXISTS idx_students_student_id ON students(student_id);
CREATE INDEX IF NOT EXISTS idx_students_school ON students(school_id);
CREATE INDEX IF NOT EXISTS idx_students_active ON students(is_active);
CREATE INDEX IF NOT EXISTS idx_courses_code ON courses(course_code);
CREATE INDEX IF NOT EXISTS idx_courses_school ON courses(school_id);
CREATE INDEX IF NOT EXISTS idx_enrollments_student ON enrollments(student_id);
CREATE INDEX IF NOT EXISTS idx_enrollments_course ON enrollments(course_id);
CREATE INDEX IF NOT EXISTS idx_enrollments_status ON enrollments(status);
CREATE INDEX IF NOT EXISTS idx_grades_student ON grades(student_id);
CREATE INDEX IF NOT EXISTS idx_grades_course ON grades(course_id);

-- Create default admin user (password: admin123)
INSERT INTO users (id, email, password, first_name, last_name, role, is_active, created_at, updated_at)
VALUES (
    uuid_generate_v4(),
    'admin@isep-erp.sn',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', -- admin123
    'System',
    'Administrator',
    'SUPER_ADMIN',
    true,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

-- Create default school (ISEP)
INSERT INTO schools (
    id, name, code, email, phone, address, city, country,
    school_type, school_size, has_dormitories, has_restaurant,
    has_research_lab, has_enterprise_partnership, license_start,
    license_end, is_active, max_students, current_student_count,
    academic_year, rector_name, created_at, updated_at
)
VALUES (
    uuid_generate_v4(),
    'Institut Supérieur d''Enseignement Professionnel',
    'ISEP',
    'contact@isep.sn',
    '338257925',
    'Avenue Cheikh Anta Diop',
    'Dakar',
    'Sénégal',
    'PROFESSIONAL_INSTITUTE',
    'MEDIUM',
    false,
    true,
    true,
    true,
    CURRENT_DATE,
    CURRENT_DATE + INTERVAL '1 year',
    true,
    2000,
    0,
    '2024-2025',
    'Prof. Ndiaye',
    NOW(),
    NOW()
) ON CONFLICT (code) DO NOTHING;

-- Create default academic year settings
INSERT INTO academic_settings (id, academic_year, start_date, end_date, is_active, created_at, updated_at)
VALUES (
    uuid_generate_v4(),
    '2024-2025',
    '2024-10-01',
    '2025-07-31',
    true,
    NOW(),
    NOW()
) ON CONFLICT (academic_year) DO NOTHING;

-- Create system settings
INSERT INTO system_settings (setting_key, setting_value, description, created_at, updated_at)
VALUES 
    ('app.name', 'ISEP ERP', 'Application name', NOW(), NOW()),
    ('app.version', '4.0.0', 'Application version', NOW(), NOW()),
    ('app.timezone', 'Africa/Dakar', 'Default timezone', NOW(), NOW()),
    ('app.currency', 'XOF', 'Default currency', NOW(), NOW()),
    ('app.language', 'fr', 'Default language', NOW(), NOW()),
    ('security.session_timeout', '30', 'Session timeout in minutes', NOW(), NOW()),
    ('security.max_login_attempts', '3', 'Maximum login attempts', NOW(), NOW()),
    ('backup.auto_enabled', 'true', 'Auto backup enabled', NOW(), NOW()),
    ('backup.frequency', 'daily', 'Backup frequency', NOW(), NOW()),
    ('backup.retention_days', '30', 'Backup retention period in days', NOW(), NOW())
ON CONFLICT (setting_key) DO NOTHING;

COMMIT;

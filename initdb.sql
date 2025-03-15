-- Insert into tbl_user
INSERT INTO tbl_user (created_at, created_by, updated_at, updated_by, date_of_birth, email, first_name, last_name, password, phone, username, gender, status, type)
VALUES
    (NOW(), 1, NOW(), 1, '1990-01-01', 'admin@gmail.com', 'Admin', 'User', 'hashed_password', '1234567890', 'admin', 'MALE', 'ACTIVE', 'ADMIN'),
    (NOW(), 1, NOW(), 1, '1985-05-15', 'john.doe@gmail.com', 'John', 'Doe', 'hashed_password', '0987654321', 'johndoe', 'MALE', 'ACTIVE', 'USER'),
    (NOW(), 1, NOW(), 1, '1992-07-20', 'jane.doe@gmail.com', 'Jane', 'Doe', 'hashed_password', '0123456789', 'janedoe', 'FEMALE', 'ACTIVE', 'USER'),
    (NOW(), 1, NOW(), 1, '1988-11-30', 'alice.smith@gmail.com', 'Alice', 'Smith', 'hashed_password', '0678901234', 'alicesmith', 'FEMALE', 'ACTIVE', 'USER'),
    (NOW(), 1, NOW(), 1, '1995-03-25', 'bob.brown@gmail.com', 'Bob', 'Brown', 'hashed_password', '0456789012', 'bobbrown', 'MALE', 'ACTIVE', 'USER');

-- Insert into tbl_address
INSERT INTO tbl_address (created_at, created_by, updated_at, updated_by, address_type, apartment_number, building, city, country, floor, street, street_number, user_id)
VALUES
    (NOW(), 1, NOW(), 1, 1, '101', 'Admin Tower', 'Admin City', 'Adminland', '10', 'Admin Street', '100', 1),
    (NOW(), 1, NOW(), 1, 2, '202', 'John Building', 'New York', 'USA', '5', '5th Avenue', '12', 2),
    (NOW(), 1, NOW(), 1, 3, '303', 'Jane Tower', 'Los Angeles', 'USA', '7', 'Sunset Blvd', '45', 3),
    (NOW(), 1, NOW(), 1, 1, '404', 'Alice Residence', 'Chicago', 'USA', '2', 'Michigan Ave', '78', 4),
    (NOW(), 1, NOW(), 1, 2, '505', 'Bob Complex', 'Houston', 'USA', '8', 'Main St', '90', 5);

-- Insert into tbl_role
INSERT INTO tbl_role (created_at, created_by, updated_at, updated_by, description, name)
VALUES
    (NOW(), 1, NOW(), 1, 'Administrator Role', 'ADMIN'),
    (NOW(), 1, NOW(), 1, 'Manager Role', 'MANAGER'),
    (NOW(), 1, NOW(), 1, 'Editor Role', 'EDITOR'),
    (NOW(), 1, NOW(), 1, 'Viewer Role', 'VIEWER'),
    (NOW(), 1, NOW(), 1, 'Guest Role', 'GUEST');

-- Insert into tbl_permission
INSERT INTO tbl_permission (created_at, created_by, updated_at, updated_by, description, name)
VALUES
    (NOW(), 1, NOW(), 1, 'Full access', 'ALL_PERMISSIONS'),
    (NOW(), 1, NOW(), 1, 'Manage users', 'MANAGE_USERS'),
    (NOW(), 1, NOW(), 1, 'Edit content', 'EDIT_CONTENT'),
    (NOW(), 1, NOW(), 1, 'View content', 'VIEW_CONTENT'),
    (NOW(), 1, NOW(), 1, 'Limited access', 'LIMITED_ACCESS');

-- Insert into tbl_role_has_permission
INSERT INTO tbl_role_has_permission (created_at, created_by, updated_at, updated_by, permission_id, role_id)
VALUES
    (NOW(), 1, NOW(), 1, 1, 1),
    (NOW(), 1, NOW(), 1, 2, 2),
    (NOW(), 1, NOW(), 1, 3, 3),
    (NOW(), 1, NOW(), 1, 4, 4),
    (NOW(), 1, NOW(), 1, 5, 5);

-- Insert into tbl_user_has_role
INSERT INTO tbl_user_has_role (created_at, created_by, updated_at, updated_by, role_id, user_id)
VALUES
    (NOW(), 1, NOW(), 1, 1, 1),
    (NOW(), 1, NOW(), 1, 2, 2),
    (NOW(), 1, NOW(), 1, 3, 3),
    (NOW(), 1, NOW(), 1, 4, 4),
    (NOW(), 1, NOW(), 1, 5, 5);

-- Insert into tbl_group
INSERT INTO tbl_group (created_at, created_by, updated_at, updated_by, description, name, role_id)
VALUES
    (NOW(), 1, NOW(), 1, 'Admin Group', 'ADMINS', 1),
    (NOW(), 1, NOW(), 1, 'Manager Group', 'MANAGERS', 2),
    (NOW(), 1, NOW(), 1, 'Editor Group', 'EDITORS', 3),
    (NOW(), 1, NOW(), 1, 'Viewer Group', 'VIEWERS', 4),
    (NOW(), 1, NOW(), 1, 'Guest Group', 'GUESTS', 5);

-- Insert into tbl_group_has_user
INSERT INTO tbl_group_has_user (created_at, created_by, updated_at, updated_by, group_id, user_id)
VALUES
    (NOW(), 1, NOW(), 1, 1, 1),
    (NOW(), 1, NOW(), 1, 2, 2),
    (NOW(), 1, NOW(), 1, 3, 3),
    (NOW(), 1, NOW(), 1, 4, 4),
    (NOW(), 1, NOW(), 1, 5, 5);

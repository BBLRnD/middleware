INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, `type`, module)
VALUES('accessControl', NULL, 1, 'icon-navigation', 0, NULL, 'S_ADMIN', 'Access Control', 'parent', 'ACCESS_CONTROL');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, `type`, module)
VALUES('configuration', NULL, 1, NULL, 1, 'accessControl', 'S_ADMIN', 'Configuration', 'group', 'ACCESS_CONTROL');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, `type`, module)
VALUES('roleConfig', 'RoleConfigComponent', 0, NULL, 2, 'configuration', 'S_ADMIN', 'Role Config', 'component', 'ACCESS_CONTROL');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, `type`, module)
VALUES('userDetails', 'UserDetailsComponent', 0, NULL, 2, 'configuration', 'S_ADMIN', 'User Creation', 'component', 'ACCESS_CONTROL');
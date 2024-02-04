INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('generateReport', 'ReportComponent', 0, NULL, 2, 'OPERATIONS', 'report', 'S_ADMIN', 'Generate', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('report', NULL, 1, NULL, 1, 'OPERATIONS', 'operations', 'S_ADMIN', 'Report', 'group');



INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('remittance', NULL, 1, NULL, 1, 'OPERATIONS', 'operations', 'S_ADMIN', 'Remittance', 'group');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('remittanceDisbursement', 'RemittanceDisbursementComponent', 0, NULL, 2, 'OPERATIONS', 'remittance', 'S_ADMIN', 'Remittance Disbursement', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('corporate', 'CorporateComponent', 0, NULL, 2, 'OPERATIONS', 'onboardingAmends', 'S_ADMIN', 'Corporate', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('onboardingAmends', NULL, 1, NULL, 1, 'OPERATIONS', 'operations', 'S_ADMIN', 'Onboarding Amends', 'group');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('retail', 'RetailComponent', 0, NULL, 2, 'OPERATIONS', 'onboardingAmends', 'S_ADMIN', 'Retail', 'component');
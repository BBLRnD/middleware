INSERT INTO menus (id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`) VALUES('billPay', NULL, 1, NULL, 1, 'OPERATIONS', 'operations', 'S_ADMIN', 'Bill Pay', 'group');
INSERT INTO menus (id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`) VALUES('insurancePremiumCollection', 'InsurancePremiumCollectionComponent', 0, NULL, 2, 'OPERATIONS', 'billPay', 'S_ADMIN', 'Insurance Premium Collection', 'component');
INSERT INTO menus (id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`) VALUES('creditCard', 'CreditCardComponent', 0, NULL, 2, 'OPERATIONS', 'billPay', 'S_ADMIN', 'Credit Card', 'component');
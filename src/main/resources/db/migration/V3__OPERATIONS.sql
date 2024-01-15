INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('bankTransfers', 'BankTransfersComponent', 0, NULL, 2, 'fundTransfers', 'S_ADMIN', 'Bank Transfers', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('selfOperations', NULL, 1, NULL, 1, 'operations', 'S_ADMIN', 'Self Operations', 'OPERATIONS', 'group');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('commissionSummary', 'CommissionSummaryComponent', 0, NULL, 2, 'summaries', 'S_ADMIN', 'Commission Summary', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('customerSummary', 'CustomerSummaryComponent', 0, NULL, 2, 'summaries', 'S_ADMIN', 'Customer Summary', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('externalMoveMoney', 'ExternalMoveMoneyComponent', 0, NULL, 2, 'selfOperations', 'S_ADMIN', 'External Move Money', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('facCashWithdrawal', 'FacCashWithdrawalComponent', 0, NULL, 2, 'fundTransfers', 'S_ADMIN', 'FAC(Token) Cash Withdrawal', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('fundTransfers', NULL, 1, NULL, 1, 'operations', 'S_ADMIN', 'Fund Transfer', 'OPERATIONS', 'group');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('generateFac', 'GenerateFacComponent', 0, NULL, 2, 'fundTransfers', 'S_ADMIN', 'Generate FAC (Token)', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('moveMoney', 'MoveMoneyComponent', 0, NULL, 2, 'selfOperations', 'S_ADMIN', 'Move Money', 'OPERATIONS', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('operations', NULL, 1, 'icon-navigation', 0, NULL, 'S_ADMIN', 'Operations', 'OPERATIONS', 'parent');
INSERT INTO menus
(id, component, has_children, icon, layer, parent_id, `role`, title, module, `type`)
VALUES('summaries', NULL, 1, NULL, 1, 'operations', 'S_ADMIN', 'Summary', 'OPERATIONS', 'group');

INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('balanceInquiry', 'BalanceInquiryComponent', 0, NULL, 2, 'OPERATIONS', 'fundTransfers', 'S_ADMIN', 'Balance Inquiry', 'component');
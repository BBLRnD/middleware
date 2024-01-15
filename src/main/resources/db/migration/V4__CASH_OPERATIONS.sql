INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('cashOperations', NULL, 1, NULL, 1, 'OPERATIONS', 'operations', 'S_ADMIN', 'Cash Operations', 'group');

INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('cashDeposit', 'CashDepositComponent', 0, NULL, 2, 'OPERATIONS', 'cashOperations', 'S_ADMIN', 'Cash Deposit', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('cashRefund', 'CashRefundComponent', 0, '', 2, 'OPERATIONS', 'cashOperations', 'S_ADMIN', 'Cash Refund', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('cashWithdrawal', 'CashWithdrawalComponent', 0, NULL, 2, 'OPERATIONS', 'cashOperations', 'S_ADMIN', 'Cash Withdrawal', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('loanDisbursement', 'LoanDisbursementComponent', 0, NULL, 2, 'OPERATIONS', 'cashOperations', 'S_ADMIN', 'Loan Disbursement', 'component');
INSERT INTO menus
(id, component, has_children, icon, layer, module, parent_id, `role`, title, `type`)
VALUES('loanInstallment', 'LoanInstallmentComponent', 0, NULL, 2, 'OPERATIONS', 'cashOperations', 'S_ADMIN', 'Loan Installment', 'component');
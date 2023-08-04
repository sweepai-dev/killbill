drop procedure if exists cleanAccount;
DELIMITER //
CREATE PROCEDURE cleanAccount(p_account_ids TEXT)
BEGIN

    DECLARE v_account_id VARCHAR(36);
    DECLARE v_account_record_id bigint /*! unsigned */;
    DECLARE v_tenant_record_id bigint /*! unsigned */;

    -- Split the p_account_ids text into individual account IDs
    SET @account_ids = REPLACE(p_account_ids, ',', "','");
    SET @sql = CONCAT("SELECT id FROM accounts WHERE id IN ('", @account_ids, "')");

    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    -- Loop through each account ID
    WHILE (FETCH NEXT FROM stmt INTO v_account_id) DO
        select record_id, tenant_record_id from accounts WHERE id = v_account_id into v_account_record_id, v_tenant_record_id;

    call trimAccount(p_account_id);

        DELETE FROM account_history WHERE target_record_id = v_account_record_id and tenant_record_id = v_tenant_record_id;
        DELETE FROM accounts WHERE record_id = v_account_record_id and tenant_record_id = v_tenant_record_id;
        DELETE FROM audit_log WHERE account_record_id = v_account_record_id and tenant_record_id = v_tenant_record_id;
        DELETE FROM payment_method_history WHERE account_record_id = v_account_record_id and tenant_record_id = v_tenant_record_id;
        DELETE FROM payment_methods WHERE account_record_id = v_account_record_id and tenant_record_id = v_tenant_record_id;
    END WHILE;

    END;
//
DELIMITER ;

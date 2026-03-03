show databases;

use order_service;
use inventory_service;


SELECT * FROM flyway_schema_history ORDER BY installed_rank DESC;
delete from flyway_schema_history where version = '2';

SET SQL_SAFE_UPDATES = 0;

# Check current state
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

# Delete failed migration
DELETE FROM flyway_schema_history WHERE version = '1';

# Verify deletion
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

# Re-enable safe mode
SET SQL_SAFE_UPDATES = 1;




show tables;
drop table t_orders;
select * from flyway_schema_history;
select * from t_orders;
select * from t_inventory;
ALTER TABLE TBL_TRAININGS ADD COLUMN COL_CREATED_BY VARCHAR(255) AFTER COL_CREATED_ON;
ALTER TABLE TBL_TRAININGS ADD COLUMN COL_MODIFIED_BY VARCHAR(255) AFTER COL_MODIFIED_ON;
SELECT tah.est_hr , tah.history_createdon from task_audit_history tah 
	where task_id=64 and est_hr != est_hr_new ORDER BY history_createdon asc;

SELECT tah.project_id_new as "project_id", tah.history_createdon from task_audit_history tah where task_id=64 and project_id != project_id_new ORDER BY history_createdon asc;

SELECT tah.project_id as "project_id", tah.history_createdon from task_audit_history tah where task_id=64 ORDER BY history_createdon asc limit 1;

SELECT tah.audit_id, tah.project_id_new as "project_id", tah.history_createdon from task_audit_history tah where task_id=64 and project_id != project_id_new 
UNION 
SELECT tah.audit_id, tah.project_id as "project_id", tah.history_createdon from task_audit_history tah where task_id=64 ORDER BY history_createdon asc limit 1;

/* --------------------- */

select DISTINCT est_hr, history_createdon from task_audit_history_v1;

LOAD DATA 
    CHARACTERSET ZHS16GBK 
    APPEND INTO TABLE TBL_APRV_CHAIN
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	BATCH_NO                   "trim(:BATCH_NO)",
    SEQ                        "trim(:SEQ)",
    MONT_CODE                  "trim(:MONT_CODE)",
    MONT_NAME                  "trim(:MONT_NAME)",
    MATR_CODE                  "trim(:MATR_CODE)",
    MATR_NAME                  "trim(:MATR_NAME)",
    MATR_BUY_DEPT              "trim(:MATR_BUY_DEPT)",
    MATR_BUY_DEPT_NAME         "trim(:MATR_BUY_DEPT_NAME)",
    MATR_AUDIT_DEPT      	   "trim(:MATR_AUDIT_DEPT)",
    MATR_AUDIT_DEPT_NAME       "trim(:MATR_AUDIT_DEPT_NAME)",
    FINC_DEPT_S                "trim(:FINC_DEPT_S)",
    FINC_DEPT_S_NAME           "trim(:FINC_DEPT_S_NAME)",
    FINC_DEPT_2                "trim(:FINC_DEPT_2)",
    FINC_DEPT_2_NAME           "trim(:FINC_DEPT_2_NAME)",
    FINC_DEPT_1                "trim(:FINC_DEPT_1)",
    FINC_DEPT_1_NAME           "trim(:FINC_DEPT_1_NAME)",
    EXCEL_NO                   "trim(:EXCEL_NO)"
)

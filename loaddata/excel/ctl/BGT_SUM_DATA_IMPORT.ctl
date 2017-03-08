LOAD DATA 
    CHARACTERSET ZHS16GBK 
    APPEND INTO TABLE ERP_BUDGET_SUM_TEMP
    WHEN (1:3)<>'|||' 
    FIELDS TERMINATED BY ' | ' 
    TRAILING NULLCOLS
(
	BATCH_NO                   "trim(:BATCH_NO)",
    SEQ                        "trim(:SEQ)",
    BGT_ORGCODE                  "trim(:BGT_ORGCODE)",
    BGT_ORGNAME                  "trim(:BGT_ORGNAME)",
    BGT_MONTCODE                  "trim(:BGT_MONTCODE)",
    BGT_MONTNAME                  "trim(:BGT_MONTNAME)",
    BGT_MATRCODE                   "trim(:BGT_MATRCODE)",
    BGT_MATRNAME                   "trim(:BGT_MATRNAME)",
	FILLER_1                   FILLER,
	FILLER_2                   FILLER,
    BGT_SUM                   "trim(:BGT_SUM)",
    EXCEL_NO                   "trim(:EXCEL_NO)"
)

			       